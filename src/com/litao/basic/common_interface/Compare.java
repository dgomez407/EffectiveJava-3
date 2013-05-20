package com.litao.basic.common_interface;

import java.util.Arrays;
import java.util.Comparator;

class Person {
	protected String name;
	protected int age;

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
		return "(" + name + ", " + age + ")";
	}

}

class PersonComparator implements Comparator<Person> {

	@Override
	public int compare(Person p1, Person p2) {
		return p1.age - p2.age;
	}

}

class Student extends Person implements Comparable<Student> {
	protected int score;

	public Student(String name, int age, int score) {
		super(name, age);
		this.score = score;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	@Override
	public String toString() {
		return "(" + name + ", " + age + ", " + score + ")";
	}

	@Override
	public int compareTo(Student o) {
		return score - o.score;
	}
}

public class Compare {

	public static void main(String[] args) {
		Person[] persons = { new Person("Tom", 20), new Person("Peter", 18), new Person("Wade", 22) };
		System.out.println(Arrays.asList(persons));
		Arrays.sort(persons, new PersonComparator());	// 可以使用多种Comparator，进而采取不同的比较策略
		System.out.println(Arrays.asList(persons));

		Student[] students = { new Student("Tom", 20, 96), new Student("Peter", 18, 98), new Student("Wade", 22, 94) };
		System.out.println(Arrays.asList(students));
		Arrays.sort(students);
		System.out.println(Arrays.asList(students));
	}
}
