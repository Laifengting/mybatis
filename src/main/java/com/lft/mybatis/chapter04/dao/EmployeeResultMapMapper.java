package com.lft.mybatis.chapter04.dao;

import com.lft.mybatis.chapter04.entity.Employee;

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
public interface EmployeeResultMapMapper {
	Employee getEmpById(Integer id);
	
	Employee getEmpAndDeptById(Integer id);
	
	Employee getEmpByIdStep(Integer id);
	
	Employee getEmpByIdStep2(Integer id);
	
	List<Employee> getEmpByDeptId(Integer deptId);
}
