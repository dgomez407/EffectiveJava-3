package com.litao.basic.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Person {
	@DataField(name = "name")
	private String name;
	@DataField(name = "age")
	private int age;

	public Person() {

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return String.format("(%s, %d)", name, age);
	}

}

/* construct an object using reflection */
public class ORMapping {
	public static Person constructPerson(Map<String, Object> data) throws Exception {
		Class<Person> clazz = Person.class;
		// call default no-param constructor
		Person p = clazz.getConstructor(new Class[] {}).newInstance(new Object[] {});

		// get the declared fields
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			// get the field name
			String fieldName = field.getName();

			// get method name
			String getMethodName = String.format("get%s%s", fieldName.substring(0, 1).toUpperCase(), fieldName.substring(1));
			// set method name
			String setMethodName = String.format("set%s%s", fieldName.substring(0, 1).toUpperCase(), fieldName.substring(1));

			// get method
			Method getMethod = clazz.getMethod(getMethodName, new Class[] {});
			// set method
			Method setMethod = clazz.getMethod(setMethodName, new Class[] { field.getType() });

			// get the map field name from annotation
			String mapFieldName = field.getAnnotation(DataField.class).name();
			Object value = data.get(mapFieldName);

			// invoke object's set method
			setMethod.invoke(p, new Object[] { value });
		}

		return p;
	}

	public static void main(String[] args) throws Exception {
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		String nameField = "name";
		String ageField = "age";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(nameField, "Kobe");
		map.put(ageField, 32);
		dataList.add(map);
		map = new HashMap<String, Object>();
		map.put(nameField, "Wade");
		map.put(ageField, 18);
		dataList.add(map);
		map = new HashMap<String, Object>();
		map.put(nameField, "James");
		map.put(ageField, 20);
		dataList.add(map);

		for (Map<String, Object> data : dataList) {
			Person p = constructPerson(data);
			System.out.println(p);
		}
	}
}
