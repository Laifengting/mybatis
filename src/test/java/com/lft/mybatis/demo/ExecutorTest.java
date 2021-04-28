package com.lft.mybatis.demo;

import org.apache.ibatis.executor.BatchExecutor;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.ReuseExecutor;
import org.apache.ibatis.executor.SimpleExecutor;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.Transaction;
import org.apache.ibatis.transaction.jdbc.JdbcTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class Name:      ExecutorTest
 * Package Name:    com.lft.mybatis.demo
 * <p>
 * Function: 		A {@code ExecutorTest} object With Some FUNCTION.
 * Date:            2021-04-27 23:12
 * <p>
 * @author Laifengting / E-mail:laifengting@foxmail.com
 * @version 1.0.0
 * @since JDK 8
 */
public class ExecutorTest {
	/**
	 * 配置
	 */
	private Configuration configuration;
	/**
	 * 事务
	 */
	private Transaction transaction;
	/**
	 * 连接
	 */
	private Connection connection;
	
	/**
	 * 单元测试开始之前执行
	 * @throws IOException
	 * @throws SQLException
	 */
	@BeforeEach
	private void init() throws IOException, SQLException {
		System.out.println("================测试之前执行，准备工作，创建配置、事务、连接对象================");
		// 1. 创建 SqlSessionFactoryBuilder 对象
		SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
		// 2. 通过 SqlSessionFactoryBuilder 对象 的 build() 方法，创建 SqlSessionFactory 对象
		// 2.1 使用 Resources.getResourceAsStream() 将 mybatis-config.xml 配置文件读取成 InputStream 流对象。
		InputStream inputStream = Resources.getResourceAsStream("com/lft/mybatis/chapter01/mybatis-config1.xml");
		// 2.2 将 InputStream 流对象，通过 SqlSessionFactoryBuilder 生成 SqlSessionFactory
		SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(inputStream);
		// 3. 通过 SqlSessionFactory 对象的 getConfiguration() 方法，获取 Configuration 对象。
		configuration = sqlSessionFactory.getConfiguration();
		// 4. 通过配置对象，获取环境对象，再通过环境对象获取数据源对象，再通过数据源对象获取连接
		connection = configuration.getEnvironment().getDataSource().getConnection();
		// 5. 将 Connection 对象通过 new JdbcTransaction()，获取事务
		transaction = new JdbcTransaction(connection);
	}
	
	/**
	 * 简单执行器测试
	 */
	@Test
	public void simpleExecutorTest() throws SQLException {
		System.out.println("================================= 正式开始 =================================");
		// 1. 将配置对象和事务对象通过 new SimpleExecutor()创建出一个 SimpleExecutor 对象。
		SimpleExecutor executor = new SimpleExecutor(configuration, transaction);
		// 2. 通过 SimpleExecutor 对象，调用 doQuery() 方法执行数据库的查询或者修改操作。
		// 2.1 通过 Configuration 对象的 getMappedStatement() 方法，获取 MappedStatement SQL声明映射对象。
		// getMappedStatement() 方法需要传入一个 String 类型的 id 参数，就是 XxxMapper.xml 的名称空间 + id，通过这个可以直接定位到具体的 SQL 语句。
		MappedStatement ms = configuration.getMappedStatement("org.mybatis.example.BlogMapper.selectBlog");
		// 2.2 执行查询操作 doQuery() 方法需要 5 个参数：1.SQL 声明映射 2.参数 3.行范围 4.结果处理器 5.动态SQL语句
		List<Object> list1 = executor.doQuery(ms, "1", RowBounds.DEFAULT, Executor.NO_RESULT_HANDLER, ms.getBoundSql("1"));
		System.out.println(list1.get(0));
		// 3. 相同的SQL语句和参数，再执行一遍。
		List<Object> list2 = executor.doQuery(ms, "1", RowBounds.DEFAULT, Executor.NO_RESULT_HANDLER, ms.getBoundSql("1"));
		System.out.println(list2.get(0));
		System.out.println(list1.get(0) == list2.get(0));
	}
	
	/**
	 * 重用执行器测试
	 */
	@Test
	public void reuseExecutorTest() throws SQLException {
		System.out.println("================================= 正式开始 =================================");
		// 1. 将配置对象和事务对象通过 new ReuseExecutor()创建出一个 ReuseExecutor 对象。
		ReuseExecutor executor = new ReuseExecutor(configuration, transaction);
		// 2. 通过 ReuseExecutor 对象，调用 doQuery() 方法执行数据库的查询或者修改操作。
		// 2.1 通过 Configuration 对象的 getMappedStatement() 方法，获取 MappedStatement SQL声明映射对象。
		// getMappedStatement() 方法需要传入一个 String 类型的 id 参数，就是 XxxMapper.xml 的名称空间 + id，通过这个可以直接定位到具体的 SQL 语句。
		MappedStatement ms = configuration.getMappedStatement("org.mybatis.example.BlogMapper.selectBlog");
		// 2.2 执行查询操作 doQuery() 方法需要 5 个参数：1.SQL 声明映射 2.参数 3.行范围 4.结果处理器 5.动态SQL语句
		List<Object> list1 = executor.doQuery(ms, "1", RowBounds.DEFAULT, Executor.NO_RESULT_HANDLER, ms.getBoundSql("1"));
		System.out.println(list1.get(0));
		// 3. 相同的SQL语句和参数，再执行一遍。
		List<Object> list2 = executor.doQuery(ms, "1", RowBounds.DEFAULT, Executor.NO_RESULT_HANDLER, ms.getBoundSql("1"));
		System.out.println(list2.get(0));
	}
	
	/**
	 * 批处理执行器测试
	 * 注意：批处理只针对增删改操作
	 */
	@Test
	public void batchExecutorTest() throws SQLException {
		System.out.println("================================= 正式开始 =================================");
		// 1. 将配置对象和事务对象通过 nw BatchExecutor()创建出一个 BatchExecutor 对象。
		BatchExecutor executor = new BatchExecutor(configuration, transaction);
		// 2. 通过 BatchExecutor 对象，调用 doQuery() 方法执行数据库的查询或者修改操作。
		// 2.1 通过 Configuration 对象的 getMappedStatement() 方法，获取 MappedStatement SQL声明映射对象。
		// getMappedStatement() 方法需要传入一个 String 类型的 id 参数，就是 XxxMapper.xml 的名称空间 + id，通过这个可以直接定位到具体的 SQL 语句。
		// MappedStatement ms = configuration.getMappedStatement("org.mybatis.example.BlogMapper.selectBlog");
		// // 2.2 执行查询操作 doQuery() 方法需要 5 个参数：1.SQL 声明映射 2.参数 3.行范围 4.结果处理器 5.动态SQL语句
		// List<Object> list1 = executor.doQuery(ms, 1, RowBounds.DEFAULT, Executor.NO_RESULT_HANDLER, ms.getBoundSql(1));
		// System.out.println(list1.get(0));
		// // 3. 相同的SQL语句和参数，再执行一遍。
		// List<Object> list2 = executor.doQuery(ms, 1, RowBounds.DEFAULT, Executor.NO_RESULT_HANDLER, ms.getBoundSql(1));
		// System.out.println(list2.get(0));
		MappedStatement ms = configuration.getMappedStatement("org.mybatis.example.BlogMapper.updateBlog");
		Map param = new HashMap();
		param.put("arg0", "1");
		param.put("arg1", "Helen");
		int i = executor.doUpdate(ms, param);
		System.out.println(i);
	}
}
