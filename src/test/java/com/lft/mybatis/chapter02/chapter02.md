# MyBatis 全局配置文件 configuration
## properties
MyBatis 用来引入外部 properties 配置文件的内容。
```properties
    <properties resource = "chapter02/jdbc/jdbc.properties">
        <!-- 启用默认值特性 -->
        <property name = "org.apache.ibatis.parsing.PropertyParser.enable-default-value" value = "true"/>
        <!-- 修改默认值的分隔符 -->
        <property name = "org.apache.ibatis.parsing.PropertyParser.default-value-separator" value = "?:"/>
    </properties>
```
## settings
> 这是 MyBatis 中极为重要的调整设置，它们会改变 MyBatis 的运行时行为。 下表描述了设置中各项设置的含义、默认值等。

https://mybatis.org/mybatis-3/zh/configuration.html#settings

|设置名|描述|有效值|默认值|
|:-------------------------------|:-----------------------------------------------------------|:-----------------------------------------------------------|:----------------------------------------------------|
|cacheEnabled|全局性地开启或关闭所有映射器配置文件中已配置的任何缓存。|true\|false|true|
|lazyLoadingEnabled|延迟加载的全局开关。当开启时，所有关联对象都会延迟加载。特定关联关系中可通过设置`fetchType`属性来覆盖该项的开关状态。|true\|false|false|
|aggressiveLazyLoading|开启时，任一方法的调用都会加载该对象的所有延迟加载属性。否则，每个延迟加载属性会按需加载（参考`lazyLoadTriggerMethods`)。|true\|false|false（在3.4.1及之前的版本中默认为true）|
|multipleResultSetsEnabled|是否允许单个语句返回多结果集（需要数据库驱动支持）。|true\|false|true|
|useColumnLabel|使用列标签代替列名。实际表现依赖于数据库驱动，具体可参考数据库驱动的相关文档，或通过对比测试来观察。|true\|false|true|
|useGeneratedKeys|允许JDBC支持自动生成主键，需要数据库驱动支持。如果设置为true，将强制使用自动生成主键。尽管一些数据库驱动不支持此特性，但仍可正常工作（如Derby）。|true\|false|False|
|autoMappingBehavior|指定MyBatis应如何自动映射列到字段或属性。NONE表示关闭自动映射；PARTIAL只会自动映射没有定义嵌套结果映射的字段。FULL会自动映射任何复杂的结果集（无论是否嵌套）。|NONE,PARTIAL,FULL|PARTIAL|
|autoMappingUnknownColumnBehavior|指定发现自动映射目标未知列（或未知属性类型）的行为。`NONE`:不做任何反应`WARNING`:输出警告日志（`'org.apache.ibatis.session.AutoMappingUnknownColumnBehavior'`的日志等级必须设置为`WARN`）`FAILING`:映射失败(抛出`SqlSessionException`)|NONE,WARNING,FAILING|NONE|
|defaultExecutorType|配置默认的执行器。SIMPLE就是普通的执行器；REUSE执行器会重用预处理语句（PreparedStatement）；BATCH执行器不仅重用语句还会执行批量更新。|SIMPLEREUSEBATCH|SIMPLE|
|defaultStatementTimeout|设置超时时间，它决定数据库驱动等待数据库响应的秒数。|任意正整数|未设置(null)|
|defaultFetchSize|为驱动的结果集获取数量（fetchSize）设置一个建议值。此参数只可以在查询设置中被覆盖。|任意正整数|未设置(null)|
|defaultResultSetType|指定语句默认的滚动策略。（新增于3.5.2）|FORWARD_ONLY\|SCROLL_SENSITIVE\|SCROLL_INSENSITIVE\|DEFAULT（等同于未设置）|未设置(null)|
|safeRowBoundsEnabled|是否允许在嵌套语句中使用分页（RowBounds）。如果允许使用则设置为false。|true\|false|False|
|safeResultHandlerEnabled|是否允许在嵌套语句中使用结果处理器（ResultHandler）。如果允许使用则设置为false。|true\|false|True|
|mapUnderscoreToCamelCase|是否开启驼峰命名自动映射，即从经典数据库列名A_COLUMN映射到经典Java属性名aColumn。|true\|false|False|
|localCacheScope|MyBatis利用本地缓存机制（LocalCache）防止循环引用和加速重复的嵌套查询。默认值为SESSION，会缓存一个会话中执行的所有查询。若设置值为STATEMENT，本地缓存将仅用于执行语句，对相同SqlSession的不同查询将不会进行缓存。|SESSION\|STATEMENT|SESSION|
|jdbcTypeForNull|当没有为参数指定特定的JDBC类型时，空值的默认JDBC类型。某些数据库驱动需要指定列的JDBC类型，多数情况直接用一般类型即可，比如NULL、VARCHAR或OTHER。|JdbcType常量，常用值：NULL、VARCHAR或OTHER。|OTHER|
|lazyLoadTriggerMethods|指定对象的哪些方法触发一次延迟加载。|用逗号分隔的方法列表。|equals,clone,hashCode,toString|
|defaultScriptingLanguage|指定动态SQL生成使用的默认脚本语言。|一个类型别名或全限定类名。|org.apache.ibatis.scripting.xmltags.XMLLanguageDriver|
|defaultEnumTypeHandler|指定Enum使用的默认`TypeHandler`。（新增于3.4.5）|一个类型别名或全限定类名。|org.apache.ibatis.type.EnumTypeHandler|
|callSettersOnNulls|指定当结果集中值为null的时候是否调用映射对象的setter（map对象时为put）方法，这在依赖于Map.keySet()或null值进行初始化时比较有用。注意基本类型（int、boolean等）是不能设置成null的。|true\|false|false|
|returnInstanceForEmptyRow|当返回行的所有列都是空时，MyBatis默认返回`null`。当开启这个设置时，MyBatis会返回一个空实例。请注意，它也适用于嵌套的结果集（如集合或关联）。（新增于3.4.2）|true\|false|false|
|logPrefix|指定MyBatis增加到日志名称的前缀。|任何字符串|未设置|
|logImpl|指定MyBatis所用日志的具体实现，未指定时将自动查找。|SLF4J\|LOG4J\|LOG4J2\|JDK_LOGGING\|COMMONS_LOGGING\|STDOUT_LOGGING\|NO_LOGGING|未设置|
|proxyFactory|指定Mybatis创建可延迟加载对象所用到的代理工具。|CGLIB\|JAVASSIST|JAVASSIST（MyBatis3.3以上）|
|vfsImpl|指定VFS的实现|自定义VFS的实现的类全限定名，以逗号分隔。|未设置|
|useActualParamName|允许使用方法签名中的名称作为语句参数名称。为了使用该特性，你的项目必须采用Java8编译，并且加上`-parameters`选项。（新增于3.4.1）|true\|false|true|
|configurationFactory|指定一个提供`Configuration`实例的类。这个被返回的Configuration实例用来加载被反序列化对象的延迟加载属性值。这个类必须包含一个签名为`staticConfigurationgetConfiguration()`的方法。（新增于3.2.3）|一个类型别名或完全限定类名。|未设置|
|shrinkWhitespacesInSql|从SQL中删除多余的空格字符。请注意，这也会影响SQL中的文字字符串。(新增于3.5.5)|true\|false|false|
|defaultSqlProviderType|Specifiesansqlproviderclassthatholdsprovidermethod(Since3.5.6).Thisclassapplytothe`type`(or`value`)attributeonsqlproviderannotation(e.g.`@SelectProvider`),whentheseattributewasomitted.|Atypealiasorfullyqualifiedclassname|Notset|
## typeAliases
> 下面是一些为常见的 Java 类型内建的类型别名。它们都是不区分大小写的，注意，为了应对原始类型的命名重复，采取了特殊的命名风格。

| 别名       | 映射的类型 |
| :--------- | :--------- |
| _byte      | byte       |
| _long      | long       |
| _short     | short      |
| _int       | int        |
| _integer   | int        |
| _double    | double     |
| _float     | float      |
| _boolean   | boolean    |
| string     | String     |
| byte       | Byte       |
| long       | Long       |
| short      | Short      |
| int        | Integer    |
| integer    | Integer    |
| double     | Double     |
| float      | Float      |
| boolean    | Boolean    |
| date       | Date       |
| decimal    | BigDecimal |
| bigdecimal | BigDecimal |
| object     | Object     |
| map        | Map        |
| hashmap    | HashMap    |
| list       | List       |
| arraylist  | ArrayList  |
| collection | Collection |
| iterator   | Iterator   |

## typeHandlers
MyBatis 在设置预处理语句（PreparedStatement）中的参数或从结果集中取出一个值时， 都会用类型处理器将获取到的值以合适的方式转换成 Java 类型。下表描述了一些默认的类型处理器。

**提示** 从 3.4.5 开始，MyBatis 默认支持 JSR-310（日期和时间 API） 。

| 类型处理器                   | Java 类型                       | JDBC 类型                                                    |
| :--------------------------- | :------------------------------ | :----------------------------------------------------------- |
| `BooleanTypeHandler`         | `java.lang.Boolean`, `boolean`  | 数据库兼容的 `BOOLEAN`                                       |
| `ByteTypeHandler`            | `java.lang.Byte`, `byte`        | 数据库兼容的 `NUMERIC` 或 `BYTE`                             |
| `ShortTypeHandler`           | `java.lang.Short`, `short`      | 数据库兼容的 `NUMERIC` 或 `SMALLINT`                         |
| `IntegerTypeHandler`         | `java.lang.Integer`, `int`      | 数据库兼容的 `NUMERIC` 或 `INTEGER`                          |
| `LongTypeHandler`            | `java.lang.Long`, `long`        | 数据库兼容的 `NUMERIC` 或 `BIGINT`                           |
| `FloatTypeHandler`           | `java.lang.Float`, `float`      | 数据库兼容的 `NUMERIC` 或 `FLOAT`                            |
| `DoubleTypeHandler`          | `java.lang.Double`, `double`    | 数据库兼容的 `NUMERIC` 或 `DOUBLE`                           |
| `BigDecimalTypeHandler`      | `java.math.BigDecimal`          | 数据库兼容的 `NUMERIC` 或 `DECIMAL`                          |
| `StringTypeHandler`          | `java.lang.String`              | `CHAR`, `VARCHAR`                                            |
| `ClobReaderTypeHandler`      | `java.io.Reader`                | -                                                            |
| `ClobTypeHandler`            | `java.lang.String`              | `CLOB`, `LONGVARCHAR`                                        |
| `NStringTypeHandler`         | `java.lang.String`              | `NVARCHAR`, `NCHAR`                                          |
| `NClobTypeHandler`           | `java.lang.String`              | `NCLOB`                                                      |
| `BlobInputStreamTypeHandler` | `java.io.InputStream`           | -                                                            |
| `ByteArrayTypeHandler`       | `byte[]`                        | 数据库兼容的字节流类型                                       |
| `BlobTypeHandler`            | `byte[]`                        | `BLOB`, `LONGVARBINARY`                                      |
| `DateTypeHandler`            | `java.util.Date`                | `TIMESTAMP`                                                  |
| `DateOnlyTypeHandler`        | `java.util.Date`                | `DATE`                                                       |
| `TimeOnlyTypeHandler`        | `java.util.Date`                | `TIME`                                                       |
| `SqlTimestampTypeHandler`    | `java.sql.Timestamp`            | `TIMESTAMP`                                                  |
| `SqlDateTypeHandler`         | `java.sql.Date`                 | `DATE`                                                       |
| `SqlTimeTypeHandler`         | `java.sql.Time`                 | `TIME`                                                       |
| `ObjectTypeHandler`          | Any                             | `OTHER` 或未指定类型                                         |
| `EnumTypeHandler`            | Enumeration Type                | VARCHAR 或任何兼容的字符串类型，用来存储枚举的名称（而不是索引序数值） |
| `EnumOrdinalTypeHandler`     | Enumeration Type                | 任何兼容的 `NUMERIC` 或 `DOUBLE` 类型，用来存储枚举的序数值（而不是名称）。 |
| `SqlxmlTypeHandler`          | `java.lang.String`              | `SQLXML`                                                     |
| `InstantTypeHandler`         | `java.time.Instant`             | `TIMESTAMP`                                                  |
| `LocalDateTimeTypeHandler`   | `java.time.LocalDateTime`       | `TIMESTAMP`                                                  |
| `LocalDateTypeHandler`       | `java.time.LocalDate`           | `DATE`                                                       |
| `LocalTimeTypeHandler`       | `java.time.LocalTime`           | `TIME`                                                       |
| `OffsetDateTimeTypeHandler`  | `java.time.OffsetDateTime`      | `TIMESTAMP`                                                  |
| `OffsetTimeTypeHandler`      | `java.time.OffsetTime`          | `TIME`                                                       |
| `ZonedDateTimeTypeHandler`   | `java.time.ZonedDateTime`       | `TIMESTAMP`                                                  |
| `YearTypeHandler`            | `java.time.Year`                | `INTEGER`                                                    |
| `MonthTypeHandler`           | `java.time.Month`               | `INTEGER`                                                    |
| `YearMonthTypeHandler`       | `java.time.YearMonth`           | `VARCHAR` 或 `LONGVARCHAR`                                   |
| `JapaneseDateTypeHandler`    | `java.time.chrono.JapaneseDate` | `DATE`                                                       |

你可以重写已有的类型处理器或创建你自己的类型处理器来处理不支持的或非标准的类型。 具体做法为：实现 `org.apache.ibatis.type.TypeHandler` 接口， 或继承一个很便利的类 `org.apache.ibatis.type.BaseTypeHandler`， 并且可以（可选地）将它映射到一个 JDBC 类型。
```java
// ExampleTypeHandler.java
@MappedJdbcTypes(JdbcType.VARCHAR)
public class ExampleTypeHandler extends BaseTypeHandler<String> {

  @Override
  public void setNonNullParameter(PreparedStatement ps, int i, String parameter, JdbcType jdbcType) throws SQLException {
    ps.setString(i, parameter);
  }

  @Override
  public String getNullableResult(ResultSet rs, String columnName) throws SQLException {
    return rs.getString(columnName);
  }

  @Override
  public String getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
    return rs.getString(columnIndex);
  }

  @Override
  public String getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
    return cs.getString(columnIndex);
  }
}
```
## objectFactory
> 每次 MyBatis 创建结果对象的新实例时，它都会使用一个对象工厂（ObjectFactory）实例来完成实例化工作。 默认的对象工厂需要做的仅仅是实例化目标类，要么通过默认无参构造方法，要么通过存在的参数映射来调用带有参数的构造方法。 如果想覆盖对象工厂的默认行为，可以通过创建自己的对象工厂来实现。比如：

```java
// ExampleObjectFactory.java
public class ExampleObjectFactory extends DefaultObjectFactory {
  public Object create(Class type) {
    return super.create(type);
  }
  public Object create(Class type, List<Class> constructorArgTypes, List<Object> constructorArgs) {
    return super.create(type, constructorArgTypes, constructorArgs);
  }
  public void setProperties(Properties properties) {
    super.setProperties(properties);
  }
  public <T> boolean isCollection(Class<T> type) {
    return Collection.class.isAssignableFrom(type);
  }}
```

```xml
<!-- mybatis-config.xml -->
<objectFactory type="org.mybatis.example.ExampleObjectFactory">
  <property name="someProperty" value="100"/>
</objectFactory>
```

ObjectFactory 接口很简单，它包含两个创建实例用的方法，一个是处理默认无参构造方法的，另外一个是处理带参数的构造方法的。 另外，setProperties 方法可以被用来配置 ObjectFactory，在初始化你的 ObjectFactory 实例后， objectFactory 元素体中定义的属性会被传递给 setProperties 方法。

## plugins

> MyBatis 允许你在映射语句执行过程中的某一点进行拦截调用。默认情况下，MyBatis 允许使用插件来拦截的方法调用包括：

- Executor (update, query, flushStatements, commit, rollback, getTransaction, close, isClosed)
- ParameterHandler (getParameterObject, setParameters)
- ResultSetHandler (handleResultSets, handleOutputParameters)
- StatementHandler (prepare, parameterize, batch, update, query)

这些类中方法的细节可以通过查看每个方法的签名来发现，或者直接查看 MyBatis 发行包中的源代码。 如果你想做的不仅仅是监控方法的调用，那么你最好相当了解要重写的方法的行为。 因为在试图修改或重写已有方法的行为时，很可能会破坏 MyBatis 的核心模块。 这些都是更底层的类和方法，所以使用插件的时候要特别当心。

通过 MyBatis 提供的强大机制，使用插件是非常简单的，只需实现 Interceptor 接口，并指定想要拦截的方法签名即可。

```java
// ExamplePlugin.java
@Intercepts({@Signature(
  type= Executor.class,
  method = "update",
  args = {MappedStatement.class,Object.class})})
public class ExamplePlugin implements Interceptor {
  private Properties properties = new Properties();
  public Object intercept(Invocation invocation) throws Throwable {
    // implement pre processing if need
    Object returnObject = invocation.proceed();
    // implement post processing if need
    return returnObject;
  }
  public void setProperties(Properties properties) {
    this.properties = properties;
  }
}
```

```xml
<!-- mybatis-config.xml -->
<plugins>
  <plugin interceptor="org.mybatis.example.ExamplePlugin">
    <property name="someProperty" value="100"/>
  </plugin>
</plugins>
```



上面的插件将会拦截在 Executor 实例中所有的 “update” 方法调用， 这里的 Executor 是负责执行底层映射语句的内部对象。

**提示** **覆盖配置类**

除了用插件来修改 MyBatis 核心行为以外，还可以通过完全覆盖配置类来达到目的。只需继承配置类后覆盖其中的某个方法，再把它传递到 SqlSessionFactoryBuilder.build(myConfig) 方法即可。再次重申，这可能会极大影响 MyBatis 的行为，务请慎之又慎。

## environments

## environment

## transactionManager
## dataSource
## databaseIdProvider
## mappers
既然 MyBatis 的行为已经由上述元素配置完了，我们现在就要来定义 SQL 映射语句了。 但首先，我们需要告诉 MyBatis 到哪里去找到这些语句。 在自动查找资源方面，Java 并没有提供一个很好的解决方案，所以最好的办法是直接告诉 MyBatis 到哪里去找映射文件。 你可以使用相对于类路径的资源引用，或完全限定资源定位符（包括 `file:///` 形式的 URL），或类名和包名等。例如：

```xml
<!-- 使用相对于类路径的资源引用 -->
<mappers>
  <mapper resource="org/mybatis/builder/AuthorMapper.xml"/>
  <mapper resource="org/mybatis/builder/BlogMapper.xml"/>
  <mapper resource="org/mybatis/builder/PostMapper.xml"/>
</mappers>

<!-- 使用完全限定资源定位符（URL） -->
<mappers>
  <mapper url="file:///var/mappers/AuthorMapper.xml"/>
  <mapper url="file:///var/mappers/BlogMapper.xml"/>
  <mapper url="file:///var/mappers/PostMapper.xml"/>
</mappers>

<!-- 使用映射器接口实现类的完全限定类名 -->
<mappers>
  <mapper class="org.mybatis.builder.AuthorMapper"/>
  <mapper class="org.mybatis.builder.BlogMapper"/>
  <mapper class="org.mybatis.builder.PostMapper"/>
</mappers>

<!-- 将包内的映射器接口实现全部注册为映射器 -->
<mappers>
  <package name="org.mybatis.builder"/>
</mappers>
```

这些配置会告诉 MyBatis 去哪里找映射文件，剩下的细节就应该是每个 SQL 映射文件了，也就是接下来我们要讨论的。
