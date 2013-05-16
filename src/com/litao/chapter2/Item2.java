package com.litao.chapter2;

interface Builder<T> {
	public T build();
}

// Builder Pattern
class Student {
	private int id;
	private String name;
	private int age;
	private int score;

	static class CreateBuilder implements Builder<Student> {
		// Required parameters
		private int id;
		private String name;

		// Optional parameters - initialized to default values
		private int age = 18;
		private int score = 60;

		// constructor with required parameters
		public CreateBuilder(int id, String name) {
			this.id = id;
			this.name = name;
		}

		// Optional parameters as method
		public CreateBuilder age(int age) {
			this.age = age;
			return this;
		}

		public CreateBuilder score(int score) {
			this.score = score;
			return this;
		}

		// call method build to create object
		@Override
		public Student build() {
			return new Student(this);
		}

	}

	// private constructor with parameter builder
	private Student(CreateBuilder builder) {
		this.id = builder.id;
		this.name = builder.name;
		this.age = builder.age;
		this.score = builder.score;
	}

	@Override
	public String toString() {
		return "Student [id=" + id + ", name=" + name + ", age=" + age + ", score=" + score + "]";
	}
}

public class Item2 {

	public static void main(String[] args) {
		Student student = new Student.CreateBuilder(10, "Tom").age(20).build();
		System.out.println(student);
	}

}
