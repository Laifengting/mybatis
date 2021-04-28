package com.lft.mybatis.demo.test01;

/**
 * Class Name:      Blog
 * Package Name:    com.lft.mybatis.demo
 * <p>
 * Function: 		A {@code Blog} object With Some FUNCTION.
 * Date:            2021-04-27 23:16
 * <p>
 * @author Laifengting / E-mail:laifengting@foxmail.com
 * @version 1.0.0
 * @since JDK 8
 */

public class Blog {
	private String id;
	private String name;
	private Integer age;
	
	public Blog() {
	}
	
	public Blog(String id, String name, Integer age) {
		this.id = id;
		this.name = name;
		this.age = age;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Integer getAge() {
		return age;
	}
	
	public void setAge(Integer age) {
		this.age = age;
	}
	
	@Override
	public String toString() {
		return "Blog{" +
				"id='" + id + '\'' +
				", name='" + name + '\'' +
				", age=" + age +
				'}';
	}
}
