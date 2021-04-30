package com.lft.mybatis.chapter05.entity;

import java.util.List;

/**
 * Class Name:      Department
 * Package Name:    com.lft.mybatis.chapter04.entity
 * <p>
 * Function: 		A {@code Department} object With Some FUNCTION.
 * Date:            2021-04-29 13:57
 * <p>
 * @author Laifengting / E-mail:laifengting@foxmail.com
 * @version 1.0.0
 * @since JDK 8
 */
public class Department {
	private Integer id;
	private String deptName;
	private List<Employee> employees;
	
	public Integer getId() {
		return id;
	}
	
	public List<Employee> getEmployees() {
		return employees;
	}
	
	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getDeptName() {
		return deptName;
	}
	
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	
	@Override
	public String toString() {
		return "Department{" +
				"id=" + id +
				", deptName='" + deptName + '\'' +
				'}';
	}
}
