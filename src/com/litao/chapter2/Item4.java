package com.litao.chapter2;

// Noninstantiable utility class
class UtilityClass {
	// Suppress default constructor for noninstantiability
	private UtilityClass() {
		throw new AssertionError();
	}

	public final static void f() {
		System.out.println("do something...");
	}
}

public class Item4 {

	public static void main(String[] args) {
		UtilityClass.f();
	}

}
