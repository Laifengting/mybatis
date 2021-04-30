## XML 映射器 1

### 单个参数
MyBatis 不会做特殊处理，直接可以使用 #{任意参数名} 就可以取出参数。

### 多个参数
**操作**：

- 方法：`Employee getEmpByIdAndLastName(Integer id, String lastName);`

- 取值：

	```xml
	<select id = "getEmpByIdAndLastName" resultType = "com.lft.mybatis.chapter03.entity.Employee">
	    select * from tbl_employee where id = #{id} AND last_name = #{lastName}
	</select>
	```

- 异常：`org.apache.ibatis.binding.BindingException:Parameter 'id' not found.Available parameters are [arg1, arg0, param1, param2]`

- 原因：

	MyBatis 会做特殊处理，多个参数会被封装成一个 Map
	        key:param1,param2...paramN，或者参数的索引也可以(0,1,...,N-1)。
	        value:就是传入的参数值。
	        \#{}就是从 map 中获取指定的 key 的值。

- 解决方式：

	1. 方式一：使用 param1,param2,...paramN 来取值    #{param1}
	2. 方式二：使用 arg0,arg1,...,argN               #{arg0}
	3. 方式三：命名参数。在接口形参前加 @Param("id")     #{id}
		Employee getEmpByIdAndLastName(@Param ("id") Integer id, @Param ("lastName") String lastName);

### POJO
如果多个参数正好是我们业务逻辑的数据模型，我们就可以直接传入 POJO
`#{属性名}`：取出传入的 POJO 属性值

### Map
如果多个参数不是业务模型中的数据，没有对应的 POJO，为了方便，我们也可以传入 Map
`#{key}`：取出 map 中key对应的值

### TO
如果多个参数不是业务模型的数据，但是经常要使用，推荐来编写一个 TO(Transfer Object) 数据传输对象。
Page{
    int index;
    int size;
}

`#{index}`

`#{size}`



> 扩展

`Employee getEmpByIdAndLastName(@Param ("id") Integer id,String lastName);`
取值：
    #{id | param1 | arg0} 
    #{param2 | arg1}



`Employee getEmpByIdAndLastName(Integer id,@Param("e") Employee emp);`

取值：
    #{param1 | arg0} 
    #{param2.lastName | arg1.lastName | e.lastName}

【特别注意】：如果是 Collection(List,Set)、数组类型，也会特殊处理。也就是把传入的list 或者 数组 封装在 Map 中。
            key:    如果属于 Collection => collection，再判断是不是 List => list
                    如果属于 数组 => array



`Employee getEmpById(List<Integer> ids,String[] lastName);`

取值：
    #{list[0]} 
    #{array[0]}



> 参数值的获取

`#{}`：可以获取 map 中的值或者 POJO 的属性的值。 
`${}`：可以获取 map 中的值或者 POJO 的属性的值。 

**区别**：

`#{}`：是以预编译的形式，将参数设置到 SQL 语句中，PreparedStatement；防止 SQL 注入
`${}`：取出的值直接拼接在 SQL 语句上。会有安全问题。



大多情况下，推荐使用 `#{}` 来获取参数的值。



在原生 JDBC 不支持占位符的地方，我们就只可以使用 `${}` 进行取值。

- 比如分表：按照年份分表拆分。
	-  `select * from ${year}_salary where xxxx;`
- 比如排序：
	- `select * from tbl_employee order by ${f_name} ${order};`



> 参数处理

`#{}` 更丰富的用法：

规定参数的一些规则：

- `javaType`、`jdbcType`、`mode(存储过程)`、`numericScale`、`resultMap`、`typeHandler`、`jdbcTypeName`、`expression(未来准备支持的功能)`

- `jdbcType`：通常需要在某种特定的条件下被设置：

	在我们的数据为 null 的时候，有些数据库可能不能识别 MyBatis 对 null 的默认处理。比如 Oracle 就会报错。

	JdbcType OTHER：无效的类型；因为 MyBatis对所有的 null 都映射的是 原生 Jdbc 的 OTHER 类型需要手动指定 JdbcType 为 NULL

	```xml
	<!-- 添加员工 -->
	<insert id = "addEmp" parameterType = "com.lft.mybatis.chapter03.entity.Employee" databaseId = "mysql" useGeneratedKeys = "true" keyProperty = "id">
		INSERT INTO tbl_employee(last_name, email, gender) VALUES (#{lastName}, #{email,jdbcType=NULL}, #{gender})
	</insert>
	```

	全局配置中：jdbcTypeForNull 的默认值是 OTHER；Oracle 不支持。

	解决方式1：#{参数,jdbcType=NULL}

	解决方式2：在全局配置中修改 jdbcTypeForNull 的值。

	```xml
	<settings>
	    ...
	    <!-- 修改默认Null -->
	    <setting name = "jdbcTypeForNull" value = "NULL"/>
	</settings>
	```

- 

