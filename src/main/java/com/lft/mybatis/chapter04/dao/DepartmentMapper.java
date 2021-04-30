package com.lft.mybatis.chapter04.dao;

import com.lft.mybatis.chapter04.entity.Department;

/**
 * Class Name:      DepartmentMapper
 * Package Name:    com.lft.mybatis.chapter04.dao
 * <p>
 * Function: 		A {@code DepartmentMapper} object With Some FUNCTION.
 * Date:            2021-04-29 19:55
 * <p>
 * @author Laifengting / E-mail:laifengting@foxmail.com
 * @version 1.0.0
 * @since JDK 8
 */
public interface DepartmentMapper {
	Department getDeptById(Integer id);
	
	Department getDeptByIdPlus(Integer id);
	
	Department getDeptByStep(Integer id);
}
