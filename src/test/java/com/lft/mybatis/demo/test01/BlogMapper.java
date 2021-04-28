package com.lft.mybatis.demo.test01;

import org.apache.ibatis.annotations.Param;

/**
 * Class Name:      BlogMapp
 * Package Name:    com.lft.mybatis.demo
 * <p>
 * Function: 		A {@code BlogMapp} object With Some FUNCTION.
 * Date:            2021-04-27 23:35
 * <p>
 * @author Laifengting / E-mail:laifengting@foxmail.com
 * @version 1.0.0
 * @since JDK 8
 */
public interface BlogMapper {
	Blog selectBlog(@Param ("id") String id);
	
	int updateBlog(@Param ("name") String name, @Param ("id") String id);
}
