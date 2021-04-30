package com.lft.mybatis.chapter05.dao;

import com.lft.mybatis.chapter05.entity.Employee;

import java.util.List;

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
public interface EmployeeMapperDynamicSql {
	
	List<Employee> getEmployeeByConditionIf(Employee employee);
	
	List<Employee> getEmployeeByConditionTrim(Employee employee);
	
	List<Employee> getEmployeeByConditionChoose(Employee employee);
	
	void updateEmployee(Employee employee);
	
	void updateEmployee2(Employee employee);
}
