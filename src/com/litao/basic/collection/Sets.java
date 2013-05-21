package com.litao.basic.collection;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

class Person implements Comparable<Person> {
	private String name;
	private int age;

	public Person(String name, int age) {
		super();
		this.name = name;
		this.age = age;
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
		return "Person [name=" + name + ", age=" + age + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + age;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Person other = (Person) obj;
		if (age != other.age)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public int compareTo(Person o) {
		if (o == null)
			throw new NullPointerException();

		if (name == null) {
			if (o.name != null)
				return -1;
		} else {
			int nameDiff = name.compareTo(o.name);
			if (nameDiff != 0)
				return nameDiff;
		}

		//  �˴���Ҫʹ��age-o.age, ����int�����Ǳ�ڷ���
		return age > o.age ? 1 : (age == o.age ? 0 : -1);
	}
}

public class Sets {

	public static void main(String[] args) {
		Person p1 = new Person("kobe", 22);
		Person p2 = new Person("wade", 21);
		Person p3 = new Person("james", 23);
		Person p4 = new Person("james", 23);
		Person p5 = new Person("james", 20);
		
		// ʹ��Set�ӿڱ�����дequals()
		// ʹ��HashSet�ӿڱ�����дhashcode()
		// ʹ��TreeSet�ӿڱ���ʵ��Comparable�ӿڣ���ʵ��compareTo()����
		
		Set<Person> set = new HashSet<Person>();
		set.add(p1);
		set.add(p2);
		set.add(p3);
		set.add(p4);
		set.add(p5);
		set.add(null);
		System.out.println(set);

		set = new TreeSet<Person>();
		set.add(p1);
		set.add(p2);
		set.add(p3);
		set.add(p4);
		set.add(p5);
		//set.add(null); // �޷��ɹ�����ΪTreeSet����TreeMap����TreeSet�е�Ԫ�ص���TreeMap��key��������TreeMap��key����Ϊ��
		System.out.println(set);
	}
}
