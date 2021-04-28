package com.lft.mybatis.chapter02.dao;

import com.lft.mybatis.chapter02.entity.Employee;
import org.apache.ibatis.annotations.Select;

/**
 * Class Name:      EmployeeMapper
 * Package Name:    com.lft.mybatis.dao
 * <p>
 * Function: 		A {@code EmployeeMapper} object With Some FUNCTION.
 * Date:            2021-04-28 10:31
 * <p>
 * @author Laifengting / E-mail:laifengting@foxmail.com
 * @version 1.0.0
 * @since JDK 8
 */
public interface EmployeeMapperAnnotation {
	@Select ("select * from tbl_employee where id = #{id}")
	Employee getEmpById(Integer id);
}
