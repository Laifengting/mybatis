/**
 * Copyright 2009-2020 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.ibatis.reflection;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.binding.MapperMethod.ParamMap;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.SortedMap;
import java.util.TreeMap;

public class ParamNameResolver {
	
	public static final String GENERIC_NAME_PREFIX = "param";
	
	private final boolean useActualParamName;
	
	/**
	 * <p>
	 * The key is the index and the value is the name of the parameter.<br />
	 * The name is obtained from {@link Param} if specified. When {@link Param} is not specified,
	 * the parameter index is used. Note that this index could be different from the actual index
	 * when the method has special parameters (i.e. {@link RowBounds} or {@link ResultHandler}).
	 * </p>
	 * <ul>
	 * <li>aMethod(@Param("M") int a, @Param("N") int b) -&gt; {{0, "M"}, {1, "N"}}</li>
	 * <li>aMethod(int a, int b) -&gt; {{0, "0"}, {1, "1"}}</li>
	 * <li>aMethod(int a, RowBounds rb, int b) -&gt; {{0, "0"}, {2, "1"}}</li>
	 * </ul>
	 */
	private final SortedMap<Integer, String> names;
	
	private boolean hasParamAnnotation;
	
	/**
	 * 构造器，通过配置类和方法，初始化参数names等属性。
	 * @param config
	 * @param method
	 */
	public ParamNameResolver(Configuration config, Method method) {
		// 在配置中读取是否使用实际参数名的值，赋值给 当前属性。
		this.useActualParamName = config.isUseActualParamName();
		// 从方法对象中读取所有的参数类型。
		final Class<?>[] paramTypes = method.getParameterTypes();
		// 从方法对象中读取所有的注解。
		final Annotation[][] paramAnnotations = method.getParameterAnnotations();
		// 新建一个排序的Map
		final SortedMap<Integer, String> map = new TreeMap<>();
		// 获取到注解的数量。
		int paramCount = paramAnnotations.length;
		// get names from @Param annotations
		// 遍历所有的注解。获取到注解的名称。
		for (int paramIndex = 0; paramIndex < paramCount; paramIndex++) {
			// 检查参数类型是不是特殊参数
			if (isSpecialParameter(paramTypes[paramIndex])) {
				// skip special parameters
				continue;
			}
			String name = null;
			// 遍历每个参数注解
			for (Annotation annotation : paramAnnotations[paramIndex]) {
				// 判断是不是属于 Param 注解
				if (annotation instanceof Param) {
					// 如果是，把 有 Param注解标记设置为 true
					hasParamAnnotation = true;
					// 获取注解中的自定的名称。
					name = ((Param) annotation).value();
					break;
				}
			}
			// 判断自定义注解中的参数名是不是为null
			if (name == null) {
				// @Param was not specified.
				// 如果为空，说明没有定义注解，再判断是不是使用实际参数名,默认是开启的。
				if (useActualParamName) {
					// 多方法和参数索引中获取实际参数名
					name = getActualParamName(method, paramIndex);
				}
				// 此时如果name 还是 null,说明关闭了使用实际参数名
				if (name == null) {
					// use the parameter index as the name ("0", "1", ...)
					// gcode issue #71
					// 把数字当作参数名，因为第一次 map 是空的。所以从 0 开始。
					name = String.valueOf(map.size());
				}
			}
			// 循环判断结果，把参数索引，和参数名放到 map 中。
			map.put(paramIndex, name);
		}
		// 把收集完的参数名，返回给 names
		names = Collections.unmodifiableSortedMap(map);
	}
	
	private static boolean isSpecialParameter(Class<?> clazz) {
		return RowBounds.class.isAssignableFrom(clazz) || ResultHandler.class.isAssignableFrom(clazz);
	}
	
	private String getActualParamName(Method method, int paramIndex) {
		return ParamNameUtil.getParamNames(method).get(paramIndex);
	}
	
	/**
	 * Returns parameter names referenced by SQL providers.
	 * @return the names
	 */
	public String[] getNames() {
		return names.values().toArray(new String[0]);
	}
	
	/**
	 * <p>
	 * A single non-special parameter is returned without a name.
	 * Multiple parameters are named using the naming rule.
	 * In addition to the default names, this method also adds the generic names (param1, param2,
	 * ...).
	 * </p>
	 * @param args the args
	 * @return the named params
	 */
	public Object getNamedParams(Object[] args) {
		// 获取参数名的数量
		final int paramCount = names.size();
		// 判断如果要转换的参数为null,参数的数量为0，直接返回null
		if (args == null || paramCount == 0) {
			return null;
		}
		// 判断如果参数没有注解并且参数为一个。
		else if (!hasParamAnnotation && paramCount == 1) {
			// 就将这唯一的一个参数包装后返回。
			Object value = args[names.firstKey()];
			return wrapToMapIfCollection(value, useActualParamName ? names.get(0) : null);
		}
		// 有注解 或者 参数数量不只一个
		else {
			// 创建一个Map
			final Map<String, Object> param = new ParamMap<>();
			int i = 0;
			// 遍历 所有参数名。
			for (Map.Entry<Integer, String> entry : names.entrySet()) {
				// 把参数的值作为 key,原先排序 Map 中的key 也就是序号作为 value 放到新的 param 数组中
				// 目的就是 把 id,lastName 作为 key，把传入的参数args[0],args[1]作为值
				param.put(entry.getValue(), args[entry.getKey()]);
				// add generic param names (param1, param2, ...)
				// 拼接默认参数名 就是 param1，...，paramN
				final String genericParamName = GENERIC_NAME_PREFIX + (i + 1);
				// ensure not to overwrite parameter named with @Param
				// 判断一下，确保不会覆盖参数名
				if (!names.containsValue(genericParamName)) {
					// 然后以 拼接的参数名 param1 为 key, 把传入的参数args[0],args[1]作为值
					param.put(genericParamName, args[entry.getKey()]);
				}
				// i + 1,让循环下一次的时候 param1 变成 param2
				i++;
			}
			// 返回 参数 Map
			return param;
		}
	}
	
	/**
	 * Wrap to a {@link ParamMap} if object is {@link Collection} or array.
	 * @param object          a parameter object
	 * @param actualParamName an actual parameter name
	 *                        (If specify a name, set an object to {@link ParamMap} with specified name)
	 * @return a {@link ParamMap}
	 * @since 3.5.5
	 */
	public static Object wrapToMapIfCollection(Object object, String actualParamName) {
		// 判断参数是不是集合
		if (object instanceof Collection) {
			// 创建一个 HashMap 子类对象
			ParamMap<Object> map = new ParamMap<>();
			// 放入 map 中
			map.put("collection", object);
			// 判断参数是不是List集合
			if (object instanceof List) {
				// 再放一次
				map.put("list", object);
			}
			Optional.ofNullable(actualParamName).ifPresent(name -> map.put(name, object));
			// 返回 map
			return map;
		}
		// 判断参数非空，并且属于数组
		else if (object != null && object.getClass().isArray()) {
			// 创建一个 HashMap 子类对象
			ParamMap<Object> map = new ParamMap<>();
			// 放入 map 中
			map.put("array", object);
			Optional.ofNullable(actualParamName).ifPresent(name -> map.put(name, object));
			// 返回 map
			return map;
		}
		// 如果不属于集合也不是数组，直接返回该参数
		return object;
	}
	
}
