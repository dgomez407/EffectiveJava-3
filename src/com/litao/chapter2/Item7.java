package com.litao.chapter2;

// Finalizer Guardian idiom
class Foo {
	// Sole purpose of this object is to finalize outer Foo object
	private final Object finalizerGuardian = new Object() {

		// Finalize outer Foo object
		@Override
		protected void finalize() throws Throwable {
			System.out.println("[Foo-finalize] do some clean work...");
		}

	};

	// Explicit termination method
	public void destroy() {
		System.out.println("[Foo-destroy] do some clean work...");
	}
}

class FooChild extends Foo {

	// child will not call father's finalize() automatically, should call manually
	// to avoid this, add Finalizer Guardian instead of finalize
	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		System.out.println("[FooChild-destroy] do some clean work...");
	}

}

public class Item7 {

	public static void main(String[] args) {
		FooChild child = new FooChild();
		child = null;
		System.gc();
	}

}
