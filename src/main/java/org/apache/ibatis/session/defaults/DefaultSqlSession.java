/**
 * Copyright 2009-2020 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.ibatis.session.defaults;

import org.apache.ibatis.binding.BindingException;
import org.apache.ibatis.cursor.Cursor;
import org.apache.ibatis.exceptions.ExceptionFactory;
import org.apache.ibatis.exceptions.TooManyResultsException;
import org.apache.ibatis.executor.BatchResult;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.result.DefaultMapResultHandler;
import org.apache.ibatis.executor.result.DefaultResultContext;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.reflection.ParamNameResolver;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The default implementation for {@link SqlSession}.
 * Note that this class is not Thread-Safe.
 * @author Clinton Begin
 */
public class DefaultSqlSession implements SqlSession {
	
	private final Configuration configuration;
	private final Executor executor;
	
	private final boolean autoCommit;
	private boolean dirty;
	private List<Cursor<?>> cursorList;
	
	public DefaultSqlSession(Configuration configuration, Executor executor) {
		this(configuration, executor, false);
	}
	
	public DefaultSqlSession(Configuration configuration, Executor executor, boolean autoCommit) {
		this.configuration = configuration;
		this.executor = executor;
		this.dirty = false;
		this.autoCommit = autoCommit;
	}
	
	@Override
	public <T> T selectOne(String statement) {
		return this.selectOne(statement, null);
	}
	
	/**
	 * 执行查询方法步骤1——返回的是一个结果
	 * @param statement Unique identifier matching the statement to use.
	 * @param parameter A parameter object to pass to the statement.
	 * @param <T>
	 * @return
	 */
	@Override
	public <T> T selectOne(String statement, Object parameter) {
		// Popular vote was to return null on 0 results and throw exception on too many.
		// 查询一个的底层还是执行 查询出集合。
		List<T> list = this.selectList(statement, parameter);
		// 如果 集合中的结果数量是1则返回索引为0的元素。
		if (list.size() == 1) {
			return list.get(0);
		} else if (list.size() > 1) {
			// 查询出的结果 多于1个则抛出异常。
			throw new TooManyResultsException("Expected one result (or null) to be returned by selectOne(), but found: " + list.size());
		} else {
			// 如果结果集合中是0个返回null
			return null;
		}
	}
	
	@Override
	public <E> List<E> selectList(String statement) {
		return this.selectList(statement, null);
	}
	
	/**
	 * 执行查询的方法步骤2——在这里封装一个行边界对象
	 * @param statement Unique identifier matching the statement to use.
	 * @param parameter A parameter object to pass to the statement.
	 * @param <E>
	 * @return
	 */
	@Override
	public <E> List<E> selectList(String statement, Object parameter) {
		return this.selectList(statement, parameter, RowBounds.DEFAULT);
	}
	
	/**
	 * 执行查询的方法步骤3——在这里再添加一个结果处理器
	 * @param statement Unique identifier matching the statement to use.
	 * @param parameter A parameter object to pass to the statement.
	 * @param rowBounds Bounds to limit object retrieval
	 * @param <E>
	 * @return
	 */
	@Override
	public <E> List<E> selectList(String statement, Object parameter, RowBounds rowBounds) {
		return selectList(statement, parameter, rowBounds, Executor.NO_RESULT_HANDLER);
	}
	
	@Override
	public <K, V> Map<K, V> selectMap(String statement, String mapKey) {
		return this.selectMap(statement, null, mapKey, RowBounds.DEFAULT);
	}
	
	@Override
	public <K, V> Map<K, V> selectMap(String statement, Object parameter, String mapKey) {
		return this.selectMap(statement, parameter, mapKey, RowBounds.DEFAULT);
	}
	
	@Override
	public <K, V> Map<K, V> selectMap(String statement, Object parameter, String mapKey, RowBounds rowBounds) {
		final List<? extends V> list = selectList(statement, parameter, rowBounds);
		final DefaultMapResultHandler<K, V> mapResultHandler = new DefaultMapResultHandler<>(mapKey,
																							 configuration.getObjectFactory(),
																							 configuration
																									 .getObjectWrapperFactory(),
																							 configuration
																									 .getReflectorFactory());
		final DefaultResultContext<V> context = new DefaultResultContext<>();
		for (V o : list) {
			context.nextResultObject(o);
			mapResultHandler.handleResult(context);
		}
		return mapResultHandler.getMappedResults();
	}
	
	@Override
	public <T> Cursor<T> selectCursor(String statement) {
		return selectCursor(statement, null);
	}
	
	@Override
	public <T> Cursor<T> selectCursor(String statement, Object parameter) {
		return selectCursor(statement, parameter, RowBounds.DEFAULT);
	}
	
	@Override
	public <T> Cursor<T> selectCursor(String statement, Object parameter, RowBounds rowBounds) {
		try {
			MappedStatement ms = configuration.getMappedStatement(statement);
			Cursor<T> cursor = executor.queryCursor(ms, wrapCollection(parameter), rowBounds);
			registerCursor(cursor);
			return cursor;
		} catch (Exception e) {
			throw ExceptionFactory.wrapException("Error querying database.  Cause: " + e, e);
		} finally {
			ErrorContext.instance().reset();
		}
	}
	
	@Override
	public void select(String statement, Object parameter, ResultHandler handler) {
		select(statement, parameter, RowBounds.DEFAULT, handler);
	}
	
	@Override
	public void select(String statement, ResultHandler handler) {
		select(statement, null, RowBounds.DEFAULT, handler);
	}
	
	@Override
	public void select(String statement, Object parameter, RowBounds rowBounds, ResultHandler handler) {
		selectList(statement, parameter, rowBounds, handler);
	}
	
	@Override
	public int insert(String statement) {
		return insert(statement, null);
	}
	
	@Override
	public int insert(String statement, Object parameter) {
		return update(statement, parameter);
	}
	
	@Override
	public int update(String statement) {
		return update(statement, null);
	}
	
	@Override
	public int update(String statement, Object parameter) {
		try {
			dirty = true;
			MappedStatement ms = configuration.getMappedStatement(statement);
			return executor.update(ms, wrapCollection(parameter));
		} catch (Exception e) {
			throw ExceptionFactory.wrapException("Error updating database.  Cause: " + e, e);
		} finally {
			ErrorContext.instance().reset();
		}
	}
	
	@Override
	public int delete(String statement) {
		return update(statement, null);
	}
	
	@Override
	public int delete(String statement, Object parameter) {
		return update(statement, parameter);
	}
	
	@Override
	public void commit() {
		commit(false);
	}
	
	@Override
	public void commit(boolean force) {
		try {
			executor.commit(isCommitOrRollbackRequired(force));
			dirty = false;
		} catch (Exception e) {
			throw ExceptionFactory.wrapException("Error committing transaction.  Cause: " + e, e);
		} finally {
			ErrorContext.instance().reset();
		}
	}
	
	@Override
	public void rollback() {
		rollback(false);
	}
	
	@Override
	public void rollback(boolean force) {
		try {
			executor.rollback(isCommitOrRollbackRequired(force));
			dirty = false;
		} catch (Exception e) {
			throw ExceptionFactory.wrapException("Error rolling back transaction.  Cause: " + e, e);
		} finally {
			ErrorContext.instance().reset();
		}
	}
	
	@Override
	public List<BatchResult> flushStatements() {
		try {
			return executor.flushStatements();
		} catch (Exception e) {
			throw ExceptionFactory.wrapException("Error flushing statements.  Cause: " + e, e);
		} finally {
			ErrorContext.instance().reset();
		}
	}
	
	@Override
	public void close() {
		try {
			executor.close(isCommitOrRollbackRequired(false));
			closeCursors();
			dirty = false;
		} finally {
			ErrorContext.instance().reset();
		}
	}
	
	private void closeCursors() {
		if (cursorList != null && !cursorList.isEmpty()) {
			for (Cursor<?> cursor : cursorList) {
				try {
					cursor.close();
				} catch (IOException e) {
					throw ExceptionFactory.wrapException("Error closing cursor.  Cause: " + e, e);
				}
			}
			cursorList.clear();
		}
	}
	
	@Override
	public void clearCache() {
		executor.clearLocalCache();
	}
	
	@Override
	public Configuration getConfiguration() {
		return configuration;
	}
	
	/**
	 * 通过配置对象，调用 getMapper() 方法
	 * @param type Mapper interface class
	 * @param <T>
	 * @return
	 */
	@Override
	public <T> T getMapper(Class<T> type) {
		return configuration.getMapper(type, this);
	}
	
	@Override
	public Connection getConnection() {
		try {
			return executor.getTransaction().getConnection();
		} catch (SQLException e) {
			throw ExceptionFactory.wrapException("Error getting a new connection.  Cause: " + e, e);
		}
	}
	
	private boolean isCommitOrRollbackRequired(boolean force) {
		return (!autoCommit && dirty) || force;
	}
	
	private <T> void registerCursor(Cursor<T> cursor) {
		if (cursorList == null) {
			cursorList = new ArrayList<>();
		}
		cursorList.add(cursor);
	}
	
	/**
	 * 执行查询的方法步骤4——真正的执行查询的方法
	 * @param statement
	 * @param parameter
	 * @param rowBounds
	 * @param handler
	 * @param <E>
	 * @return
	 */
	private <E> List<E> selectList(String statement, Object parameter, RowBounds rowBounds, ResultHandler handler) {
		try {
			// 通过配置类获取 SQL 语句映射对象
			MappedStatement ms = configuration.getMappedStatement(statement);
			// 先调用 wrapCollection(parameter)方法 将集合或者数组类型的参数包装成 Map。单个参数不包装
			// 调用 executor.query() 执行查询
			return executor.query(ms, wrapCollection(parameter), rowBounds, handler);
		} catch (Exception e) {
			throw ExceptionFactory.wrapException("Error querying database.  Cause: " + e, e);
		} finally {
			ErrorContext.instance().reset();
		}
	}
	
	/**
	 * 将 SQL 语句需要的参数包装成集合
	 * @param object 设置成 final 是为了避免被修改
	 * @return
	 */
	private Object wrapCollection(final Object object) {
		return ParamNameResolver.wrapToMapIfCollection(object, null);
	}
	
	/**
	 * @deprecated Since 3.5.5
	 */
	@Deprecated
	public static class StrictMap<V> extends HashMap<String, V> {
		
		private static final long serialVersionUID = -5741767162221585340L;
		
		@Override
		public V get(Object key) {
			if (!super.containsKey(key)) {
				throw new BindingException("Parameter '" + key + "' not found. Available parameters are " + this.keySet());
			}
			return super.get(key);
		}
		
	}
	
}
