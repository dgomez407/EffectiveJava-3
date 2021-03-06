package com.litao.chapter2;

import java.util.Arrays;
import java.util.EmptyStackException;

class Stack {
	private Object[] elements;
	private int size = 0;
	private static final int DEFAULT_INITIAL_CAPACITY = 16;

	public Stack() {
		elements = new Object[DEFAULT_INITIAL_CAPACITY];
	}

	public void push(Object e) {
		ensureCapacity();
		elements[size++] = e;
	}

	public Object pop() {
		if (size == 0) {
			throw new EmptyStackException();
		}
		Object result = elements[--size];
		elements[size] = null; // Eliminate obsolete reference
		return result;
	}

	/*
	 * Ensure space for at least one more element roughly doubling the capacity each time the array needs to grow
	 */
	private void ensureCapacity() {
		if (elements.length == size) {
			elements = Arrays.copyOf(elements, 2 * size + 1);
		}
	}
}

public class Item6 {

	public static void main(String[] args) {
		Stack stack = new Stack();
		stack.push(1);
		stack.push(2);
		System.out.println(stack.pop());
		System.out.println(stack.pop());
	}

}
