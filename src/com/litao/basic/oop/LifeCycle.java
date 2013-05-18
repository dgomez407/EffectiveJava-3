package com.litao.basic.oop;

class Father {
	public static String father_static_field = "father static field";
	public String father_field = "father field";
	static {
		System.out.println(father_static_field);
		System.out.println("father static block");
	}
	
	{
		System.out.println("father non static block");
	}
	
	public Father() {
		System.out.println(father_field);
		System.out.println("father constructor");
	}

	@Override
	protected void finalize() throws Throwable {
		System.out.println("father finalize");
	}
}

class Child extends Father {
	public static String child_static_field = "child static field";
	public String child_field = "child field";
	public String name;
	
	static {
		System.out.println(child_static_field);
		System.out.println("child static block");
	}
	
	{
		System.out.println("child non static block");
	}
	
	public Child(String name) {
		this.name = name;
		System.out.println(name + ": " + child_field);
		System.out.println(name + ": child constructor");
	}

	@Override
	protected void finalize() throws Throwable {
		System.out.println(name + ": child finalize");
		super.finalize();
	}
}

public class LifeCycle {
	public static void test() {
		Child c1 = new Child("c1");
	}
	
	public static void main(String[] args) throws Exception {
		test();
		Child c2 = new Child("c2");
		Child c3 = new Child("c3");
		c2 = null;
		System.gc();	// c1, c2 will execute gc, but c3 won't
	}
}
