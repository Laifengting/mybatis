package com.lft.mybatis.chapter05;

import com.lft.mybatis.chapter05.dao.EmployeeMapperDynamicSql;
import com.lft.mybatis.chapter05.entity.Employee;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.List;

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
public class DynamicSqlTest {
	
	@Test
	public void testIfAndWhere() {
		SqlSession sqlSession = null;
		try {
			// 1. 根据 xml 配置文件（全局配置文件）创建一个 SqlSessionFactory 对象
			String resource = "com/lft/mybatis/chapter05/mybatis-config.xml";
			InputStream inputStream = Resources.getResourceAsStream(resource);
			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			
			// 2. 获取 SqlSession 对象，能直接执行已经映射的 sql 语句
			// 【注意】该方法不会自动提交
			sqlSession = sqlSessionFactory.openSession();
			
			// 3. 获取 EmployeeMapper 映射接口对象
			EmployeeMapperDynamicSql mapper = sqlSession.getMapper(EmployeeMapperDynamicSql.class);
			
			// 4. 通过接口对象调用相应的方法执行数据库操作。
			Employee employee = new Employee();
			employee.setId(1);
			// employee.setLastName("SuHai");
			List<Employee> employeeList = mapper.getEmployeeByConditionIf(employee);
			System.out.println(employeeList);
			
			// 【注意】 openSession() 无参方法默认不会自动提交。需要手动提交。
			sqlSession.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 5. 关闭 SqlSession
			sqlSession.close();
		}
	}
	
	@Test
	public void testIfAndTrim() {
		SqlSession sqlSession = null;
		try {
			// 1. 根据 xml 配置文件（全局配置文件）创建一个 SqlSessionFactory 对象
			String resource = "com/lft/mybatis/chapter05/mybatis-config.xml";
			InputStream inputStream = Resources.getResourceAsStream(resource);
			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			
			// 2. 获取 SqlSession 对象，能直接执行已经映射的 sql 语句
			// 【注意】该方法不会自动提交
			sqlSession = sqlSessionFactory.openSession();
			
			// 3. 获取 EmployeeMapper 映射接口对象
			EmployeeMapperDynamicSql mapper = sqlSession.getMapper(EmployeeMapperDynamicSql.class);
			
			// 4. 通过接口对象调用相应的方法执行数据库操作。
			Employee employee = new Employee();
			employee.setId(2);
			employee.setLastName("a");
			employee.setEmail("jack@qq.com");
			employee.setGender("0");
			List<Employee> employeeList = mapper.getEmployeeByConditionTrim(employee);
			System.out.println(employeeList);
			
			// 【注意】 openSession() 无参方法默认不会自动提交。需要手动提交。
			sqlSession.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 5. 关闭 SqlSession
			sqlSession.close();
		}
	}
	
	@Test
	public void testChoose() {
		SqlSession sqlSession = null;
		try {
			// 1. 根据 xml 配置文件（全局配置文件）创建一个 SqlSessionFactory 对象
			String resource = "com/lft/mybatis/chapter05/mybatis-config.xml";
			InputStream inputStream = Resources.getResourceAsStream(resource);
			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			
			// 2. 获取 SqlSession 对象，能直接执行已经映射的 sql 语句
			// 【注意】该方法不会自动提交
			sqlSession = sqlSessionFactory.openSession();
			
			// 3. 获取 EmployeeMapper 映射接口对象
			EmployeeMapperDynamicSql mapper = sqlSession.getMapper(EmployeeMapperDynamicSql.class);
			
			// 4. 通过接口对象调用相应的方法执行数据库操作。
			Employee employee = new Employee();
			// employee.setId(2);
			employee.setLastName("a");
			// employee.setEmail("jack@qq.com");
			// employee.setGender("0");
			List<Employee> employeeList = mapper.getEmployeeByConditionChoose(employee);
			System.out.println(employeeList);
			
			// 【注意】 openSession() 无参方法默认不会自动提交。需要手动提交。
			sqlSession.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 5. 关闭 SqlSession
			sqlSession.close();
		}
	}
	
	@Test
	public void testSet() {
		SqlSession sqlSession = null;
		try {
			// 1. 根据 xml 配置文件（全局配置文件）创建一个 SqlSessionFactory 对象
			String resource = "com/lft/mybatis/chapter05/mybatis-config.xml";
			InputStream inputStream = Resources.getResourceAsStream(resource);
			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			
			// 2. 获取 SqlSession 对象，能直接执行已经映射的 sql 语句
			// 【注意】该方法不会自动提交
			sqlSession = sqlSessionFactory.openSession();
			
			// 3. 获取 EmployeeMapper 映射接口对象
			EmployeeMapperDynamicSql mapper = sqlSession.getMapper(EmployeeMapperDynamicSql.class);
			
			// 4. 通过接口对象调用相应的方法执行数据库操作。
			Employee employee = new Employee();
			employee.setId(1);
			employee.setLastName("Tom");
			employee.setEmail("tom@qq.com");
			// employee.setGender("0");
			mapper.updateEmployee2(employee);
			
			// 【注意】 openSession() 无参方法默认不会自动提交。需要手动提交。
			sqlSession.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 5. 关闭 SqlSession
			sqlSession.close();
		}
	}
}
