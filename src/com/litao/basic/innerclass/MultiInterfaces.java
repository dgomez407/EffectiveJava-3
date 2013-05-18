package com.litao.basic.innerclass;

/* two ways that a class implement mutiple interfaces */
interface A {
	void a();
}

interface B {
	void b();
}

class X implements A, B {

	@Override
	public void a() {
		System.out.println(this + " a()");
	}

	@Override
	public void b() {
		System.out.println(this + " b()");
	}

}

class Y implements A {
	B makeB() {
		return new B() {

			@Override
			public void b() {
				System.out.println(this + " b()");
			}

		};
	}

	@Override
	public void a() {
		System.out.println(this + " a()");
	}

}

public class MultiInterfaces {
	private static void takeA(A a) {
		a.a();
	}
	
	private static void takeB(B b) {
		b.b();
	}

	public static void main(String[] args) {
		X x = new X();
		takeA(x);
		takeB(x);
		
		Y y = new Y();
		takeA(y);
		takeB(y.makeB());
	}

}
