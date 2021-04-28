package com.lft.mybatis.chapter03.dao;

import com.lft.mybatis.chapter03.entity.Employee;

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
public interface EmployeeMapper {
	Employee getEmpById(Integer id);
	
	/**
	 * 只需要在接口方法上添加 Integer/int/Long/long/Boolean/boolean/void 会自动返回操作影响的行数。布尔类型影响0行为 false，其他为 true
	 * @param employee
	 */
	Integer addEmp(Employee employee);
	
	Integer updateEmp(Employee employee);
	
	boolean deleteEmpById(Integer id);
}
