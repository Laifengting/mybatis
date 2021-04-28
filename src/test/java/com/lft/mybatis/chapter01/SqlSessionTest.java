package com.lft.mybatis.chapter01;

import com.lft.mybatis.chapter01.dao.EmployeeMapper;
import com.lft.mybatis.chapter01.entity.Employee;
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
	
	/**
	 * 一、原生 SqlSession 方式
	 * 1. 根据 xml 配置文件（全局配置文件）创建一个 SqlSessionFactory 对象
	 * 2. sql 映射文件；配置了每一个 sql ，以及 sql 的封装规则等
	 * 3. 将 sql 映射文件注册在全局配置文件中
	 * 4. 写代码：
	 * //	1) 根据全局配置文件得到 SqlSessionFactory;
	 * //	2) 使用 sqlSessionFactory 获取到 sqlSession 对象;
	 * //	3) 通过 sqlSession 对象来执行增删改查。(一个 sqlSession 就是代表和数据库的一次会话，用完关闭)
	 * //	4) 使用 SQL 的唯一标识（名称空间 + id）来告诉 MyBatis 执行哪个 SQL 语句，SQL 语句都保存在 SQl 映射文件中（XxxxMapper.xml）
	 */
	@Test
	public void testSqlSessionWithXml() {
		SqlSession sqlSession = null;
		try {
			// 1. 根据 xml 配置文件（全局配置文件）创建一个 SqlSessionFactory 对象
			String resource = "com/lft/mybatis/chapter01/mybatis-config1.xml";
			// 将配置文件读取到流中。
			InputStream inputStream = Resources.getResourceAsStream(resource);
			// 创建一个 SqlSessionFactoryBuilder 对象
			SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
			// 将 inputStream 流通过 SqlSessionFactoryBuilder 对象的 build 方法生成 SqlSessionFactory 对象
			SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(inputStream);
			// 2. 获取 SqlSession 对象，能直接执行已经映射的 sql 语句
			sqlSession = sqlSessionFactory.openSession();
			// 3. 执行 查询语句
			Employee employee = sqlSession.selectOne("com.lft.mybatis.EmployeeMapper.selectEmp", 1);
			System.out.println(employee);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 4. 关闭 SqlSession
			sqlSession.close();
		}
	}
	
	/**
	 * 二、接口方式
	 * 1. 接口式编程
	 * //	原生：			Dao		==>		DaoImpl
	 * //	MyBatis:		Mapper	==>		XxxMapper.xml
	 * <p>
	 * 2. SqlSession 代表和数据库的一次会话，用完关闭。
	 * <p>
	 * 3. SqlSession 和 connection 一样都不是线程案例的。每次使用都应该去获取新的对象。
	 * <p>
	 * 4. XxxMapper 接口没有实现类，但是 MyBatis 会为这个接口生成一个代理对象。将接口和 xml 进行绑定。
	 * //	EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
	 * <p>
	 * 5. 两个重要的配置文件：
	 * //	① MyBatis 全局配置文件（数据源，事务管理器，系统运行时环境信息等）
	 * //	② SQL 映射文件：保存了每一个 SQL 语句的映射信息。
	 */
	@Test
	public void testSqlSessionWithDao() {
		SqlSession sqlSession = null;
		try {
			// 1. 根据 xml 配置文件（全局配置文件）创建一个 SqlSessionFactory 对象
			String resource = "com/lft/mybatis/chapter01/mybatis-config2.xml";
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
	
}
