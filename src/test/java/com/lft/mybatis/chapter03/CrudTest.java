package com.lft.mybatis.chapter03;

import com.lft.mybatis.chapter03.dao.EmployeeMapper;
import com.lft.mybatis.chapter03.entity.Employee;
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
public class CrudTest {
	
	@Test
	public void testAddEmployee() {
		SqlSession sqlSession = null;
		try {
			// 1. 根据 xml 配置文件（全局配置文件）创建一个 SqlSessionFactory 对象
			String resource = "com/lft/mybatis/chapter03/mybatis-config.xml";
			InputStream inputStream = Resources.getResourceAsStream(resource);
			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			
			// 2. 获取 SqlSession 对象，能直接执行已经映射的 sql 语句
			// 【注意】该方法不会自动提交
			sqlSession = sqlSessionFactory.openSession();
			
			// 3. 获取 EmployeeMapper 映射接口对象
			EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
			
			// 4. 通过接口对象调用相应的方法执行数据库操作。
			Employee employee = new Employee();
			employee.setLastName("Suhai");
			employee.setEmail("suhai@qq.com");
			employee.setGender("1");
			// 5. 添加
			System.out.println(mapper.addEmp(employee));
			System.out.println(employee.getId());
			
			// 6. 修改
			// employee.setId(9);
			// employee.setLastName("~~~~SuHai~~~~");
			// System.out.println(mapper.updateEmp(employee));
			
			// 7. 删除
			// System.out.println(mapper.deleteEmpById(9));
			
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
