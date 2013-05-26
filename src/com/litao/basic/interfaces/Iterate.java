package com.litao.basic.interfaces;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class Teacher {
	private String name;
	private int age;

	public Teacher(String name, int age) {
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
		return "Teacher [name=" + name + ", age=" + age + "]";
	}

}

class TeacherList implements Iterable<Teacher> {
	private List<Teacher> teacherList = new ArrayList<Teacher>();

	public void add(Teacher t) {
		teacherList.add(t);
	}

	@Override
	public Iterator<Teacher> iterator() {
		return teacherList.iterator();
	}

}

public class Iterate {

	public static void main(String[] args) {
		TeacherList list = new TeacherList();

		Teacher t1 = new Teacher("kobe", 22);
		Teacher t2 = new Teacher("wade", 23);
		Teacher t3 = new Teacher("james", 21);

		list.add(t1);
		list.add(t2);
		list.add(t3);

		for (Teacher t : list) {
			System.out.println(t);
		}

	}

}
