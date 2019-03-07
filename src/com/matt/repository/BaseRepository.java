package com.matt.repository;

import com.matt.dataAccess.JdbcConnector;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

@Repository
public class BaseRepository {

	protected static Logger log = Logger.getLogger(BaseRepository.class);

	@Autowired
	@Qualifier("misDao")
	protected JdbcConnector misDao;

	public Map<String, Object> deepToMap(Object bean)
			throws IllegalAccessException {
		Map<String, Object> map = new LinkedHashMap<>();

		putValues(bean, map, null);

		return map;
	}

	private void putValues(
			Object bean, Map<String, Object> map, String prefix
	) throws IllegalAccessException {
		Class<?> cls = bean.getClass();

		for(Field field : cls.getDeclaredFields()) {
			field.setAccessible(true);

			Object value = field.get(bean);
			String key;
			if(prefix == null) {
				key = field.getName();
			} else {
				key = prefix + "." + field.getName();
			}

			if(isValue(value)) {
				map.put(key, value);
			} else {
				putValues(value, map, key);
			}
		}
	}

	private final Set<Class<?>> valueClasses = (
			Collections.unmodifiableSet(new HashSet<>(Arrays.asList(
					Object.class,    String.class, Boolean.class,
					Character.class, Byte.class,   Short.class,
					Integer.class,   Long.class,   Float.class,
					Double.class
			)))
	);

	private boolean isValue(Object value) {
		return value == null || valueClasses.contains(value.getClass());
	}

	protected <T> String getUpdateScriptByPk(String tableName, String pk, T model){
		Class c = model.getClass();
		Field[] fields = c.getDeclaredFields();
		List<String> updateCols = new ArrayList<>();
		String sql = "UPDATE " + tableName + " SET ";
		for(Field field : fields) {
			if(!pk.equals(field.getName()) && runGetter(field.getName(), model) != null){
				updateCols.add(field.getName());
//				sql += field.getName() + " = :" + field.getName();
//				if(Arrays.asList(fields).indexOf(field) != (fields.length-1)){
//					sql += ", ";
//				}
			}
		}
		for(String colName : updateCols){
			sql += colName + " = :" + colName;
			if(updateCols.indexOf(colName) != (updateCols.size()-1)){
				sql += ", ";
			}
		}
		sql += " WHERE " + pk + " = :" + pk;
		return sql;
	}

	private <T> Object runGetter(String fieldName, T o) {
		// MZ: Find the correct method
		for (Method method : o.getClass().getMethods()) {
			if ((method.getName().startsWith("get")) && (method.getName().length() == (fieldName.length() + 3))) {
				if (method.getName().toLowerCase().endsWith(fieldName.toLowerCase())) {
					// MZ: Method found, run it
					try {
						return method.invoke(o);
					} catch (IllegalAccessException e) {
						log.error("Could not determine method: " + method.getName());
					} catch (InvocationTargetException e) {
						log.error("Could not determine method: " + method.getName());
					}

				}
			}
		}
		return null;
	}

	protected <T> String[] getBeanPropertiesArr(T bean, String generatedKey){
		Class c = bean.getClass();
		Field[] fields = c.getDeclaredFields();
		List<String> propNames = new ArrayList<>();
		for(Field field : fields) {
			if(!generatedKey.equals(field.getName()) && runGetter(field.getName(), bean) != null){
				propNames.add(field.getName());
			}
		}
		return propNames.toArray(new String[propNames.size()]);
	}
}
