package com.litao.basic.serialize;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

enum Gender {
	MALE, FEMALE
}

class Person implements Serializable {
	private String name = null;
	transient private Integer age = null; // 当某个字段被声明为transient后，默认序列化机制就会忽略该字段
	private Gender gender = null;

	public Person() {
		System.out.println("none-arg constructor");
	}

	public Person(String name, Integer age, Gender gender) {
		System.out.println("arg constructor");
		this.name = name;
		this.age = age;
		this.gender = gender;
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

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	@Override
	public String toString() {
		return "Person [name=" + name + ", age=" + age + ", gender=" + gender + "]";
	}

	// private方法，使用反射调用
	private void writeObject(ObjectOutputStream out) throws IOException {
		// 执行默认的序列化机制，此时会忽略掉age字段。
		out.defaultWriteObject();
		// 将age字段写入到ObjectOutputStream中
		out.writeInt(this.age);
	}

	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		in.defaultReadObject();
		this.age = in.readInt();
	}
}

public class SimpleSerial {

	public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException {
		File file = new File("person.out");

		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
		Person person = new Person("Peter", 22, Gender.MALE);
		out.writeObject(person);
		out.close();

		ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
		Person newPerson = (Person) in.readObject();
		in.close();
		System.out.println(newPerson);
	}

}
