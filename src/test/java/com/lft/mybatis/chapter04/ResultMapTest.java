package com.lft.mybatis.chapter04;

import com.lft.mybatis.chapter04.dao.DepartmentMapper;
import com.lft.mybatis.chapter04.dao.EmployeeMapper;
import com.lft.mybatis.chapter04.dao.EmployeeResultMapMapper;
import com.lft.mybatis.chapter04.entity.Department;
import com.lft.mybatis.chapter04.entity.Employee;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.Map;

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
public class ResultMapTest {
	
	@Test
	public void testSelectList() {
		SqlSession sqlSession = null;
		try {
			// 1. 根据 xml 配置文件（全局配置文件）创建一个 SqlSessionFactory 对象
			String resource = "com/lft/mybatis/chapter04/mybatis-config.xml";
			InputStream inputStream = Resources.getResourceAsStream(resource);
			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			
			// 2. 获取 SqlSession 对象，能直接执行已经映射的 sql 语句
			// 【注意】该方法不会自动提交
			sqlSession = sqlSessionFactory.openSession();
			
			// 3. 获取 EmployeeMapper 映射接口对象
			EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
			
			// 4. 通过接口对象调用相应的方法执行数据库操作。
			// 测试返回一个 List 集合。
			// List<Employee> employeeList = mapper.getEmpByLastNameLike("a");
			// for (Employee employee : employeeList) {
			// 	System.out.println(employee);
			// }
			
			// 测试返回一个 Map 集合。
			// Map<String, Object> empMap = mapper.getEmpByIdReturnMap(10);
			// System.out.println(empMap);
			
			Map<Integer, Employee> empMap = mapper.getEmpByLastNameLikeReturnMap("a");
			System.out.println(empMap);
			
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
	public void testResultMapBean() {
		SqlSession sqlSession = null;
		try {
			// 1. 根据 xml 配置文件（全局配置文件）创建一个 SqlSessionFactory 对象
			String resource = "com/lft/mybatis/chapter04/mybatis-config.xml";
			InputStream inputStream = Resources.getResourceAsStream(resource);
			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			
			// 2. 获取 SqlSession 对象，能直接执行已经映射的 sql 语句
			// 【注意】该方法不会自动提交
			sqlSession = sqlSessionFactory.openSession();
			
			// 3. 获取 EmployeeMapper 映射接口对象
			EmployeeResultMapMapper mapper = sqlSession.getMapper(EmployeeResultMapMapper.class);
			
			// 4. 通过接口对象调用相应的方法执行数据库操作。
			// Employee employee = mapper.getEmpById(10);
			// Employee employee = mapper.getEmpAndDeptById(10);
			// Employee employee = mapper.getEmpByIdStep(10);
			// System.out.println(employee.getLastName());
			// System.out.println(employee.getDep().getDeptName());
			Employee employee = mapper.getEmpByIdStep(1);
			System.out.println(employee);
			System.out.println(employee.getDep());
			
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
	public void testResultMapList() {
		SqlSession sqlSession = null;
		try {
			// 1. 根据 xml 配置文件（全局配置文件）创建一个 SqlSessionFactory 对象
			String resource = "com/lft/mybatis/chapter04/mybatis-config.xml";
			InputStream inputStream = Resources.getResourceAsStream(resource);
			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			
			// 2. 获取 SqlSession 对象，能直接执行已经映射的 sql 语句
			// 【注意】该方法不会自动提交
			sqlSession = sqlSessionFactory.openSession();
			
			// 3. 获取 EmployeeMapper 映射接口对象
			DepartmentMapper mapper = sqlSession.getMapper(DepartmentMapper.class);
			
			// 4. 通过接口对象调用相应的方法执行数据库操作。
			// Employee employee = mapper.getEmpById(10);
			// Employee employee = mapper.getEmpAndDeptById(10);
			// Department department = mapper.getDeptByIdPlus(1);
			// System.out.println(department.getId());
			// System.out.println(department.getDeptName());
			// System.out.println(department.getEmployees());
			Department department = mapper.getDeptByStep(1);
			System.out.println(department.getId());
			System.out.println(department.getDeptName());
			System.out.println(department.getEmployees());
			
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
