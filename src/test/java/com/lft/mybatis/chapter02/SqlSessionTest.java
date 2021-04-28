package com.lft.mybatis.chapter02;

import com.lft.mybatis.chapter02.dao.EmployeeMapper;
import com.lft.mybatis.chapter02.dao.EmployeeMapperAnnotation;
import com.lft.mybatis.chapter02.entity.Employee;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.Test;

import java.io.InputStream;

/**
 * Class Name:      MyBatisTest
 * Package Name:    com.lft.mybatis.test
 * <p>
 * Function: 		A {@code MyBatisTest} object With Some FUNCTION.
 * Date:            2021-04-28 10:06
 * <p>
 * @author Laifengting / E-mail:laifengting@foxmail.com
 * @version 1.0.0
 * @since JDK 8
 */
public class SqlSessionTest {
	@Test
	public void testSqlSessionWithDao() {
		SqlSession sqlSession = null;
		try {
			// 1. 根据 xml 配置文件（全局配置文件）创建一个 SqlSessionFactory 对象
			String resource = "com/lft/mybatis/chapter02/mybatis-config.xml";
			InputStream inputStream = Resources.getResourceAsStream(resource);
			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			
			// 2. 获取 SqlSession 对象，能直接执行已经映射的 sql 语句
			sqlSession = sqlSessionFactory.openSession();
			
			// 3. 获取 EmployeeMapper 映射接口对象
			EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
			// mapper 接口对象指向的是 一个代理对象，代理对象去执行增删改操作。
			// org.apache.ibatis.binding.MapperProxy@619713e5
			System.out.println(mapper);
			// 4. 通过接口对象调用相应的方法执行数据库操作。
			Employee employee = mapper.getEmpById(1);
			System.out.println(employee);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 5. 关闭 SqlSession
			sqlSession.close();
		}
	}
	
	@Test
	public void testSqlSessionWithDaoAnnotation() {
		SqlSession sqlSession = null;
		try {
			// 1. 根据 xml 配置文件（全局配置文件）创建一个 SqlSessionFactory 对象
			String resource = "com/lft/mybatis/chapter02/mybatis-config.xml";
			InputStream inputStream = Resources.getResourceAsStream(resource);
			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			
			// 2. 获取 SqlSession 对象，能直接执行已经映射的 sql 语句
			sqlSession = sqlSessionFactory.openSession();
			
			// 3. 获取 EmployeeMapper 映射接口对象
			EmployeeMapperAnnotation mapper = sqlSession.getMapper(EmployeeMapperAnnotation.class);
			// mapper 接口对象指向的是 一个代理对象，代理对象去执行增删改操作。
			// org.apache.ibatis.binding.MapperProxy@619713e5
			System.out.println(mapper);
			// 4. 通过接口对象调用相应的方法执行数据库操作。
			Employee employee = mapper.getEmpById(1);
			System.out.println(employee);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 5. 关闭 SqlSession
			sqlSession.close();
		}
	}
	
}
