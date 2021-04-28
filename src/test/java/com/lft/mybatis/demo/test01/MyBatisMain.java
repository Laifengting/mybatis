package com.lft.mybatis.demo.test01;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * Class Name:      MyBatisMain
 * Package Name:    com.lft.mybatis.demo
 * <p>
 * Function: 		A {@code MyBatisMain} object With Some FUNCTION.
 * Date:            2021-04-27 18:40
 * <p>
 * @author Laifengting / E-mail:laifengting@foxmail.com
 * @version 1.0.0
 * @since JDK 8
 */
public class MyBatisMain {
	public static void main(String[] args) throws IOException {
		// 全局配置
		String resource = "com/lft/mybatis/chapter01/mybatis-config1.xml";
		// 将配置文件读取为一个输入流
		InputStream inputStream = Resources.getResourceAsStream(resource);
		// 使用 SqlSessionFactoryBuilder 将 inputStream 创建出 SqlSessionFactory
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		
		// sqlSessionFactory 开启会话
		SqlSession session = sqlSessionFactory.openSession();
		Blog blog = session.selectOne("org.mybatis.example.BlogMapper.selectBlog", 1);
		System.out.println(blog);
	}
}
