package com.litao.basic.innerclass;

interface CallBack {
	void execute();
}

class Callee1 implements CallBack {

	@Override
	public void execute() {
		System.out.println(this + " callback is executed");
	}
}

class Callee2 {
	private class Closure implements CallBack {

		@Override
		public void execute() {
			Callee2.this.doSomeThing();
		}

	}

	public CallBack getCallBack() {
		return new Closure();
	}

	private void doSomeThing() {
		System.out.println(this + " callback is executed");
	}
}

class Caller {
	private CallBack callBack = null;

	public Caller(CallBack callBack) {
		this.callBack = callBack;
	}

	public void go() {
		System.out.println(this + " go() begin");
		if (callBack != null)
			callBack.execute();
		System.out.println(this + " go() end");
	}
}

public class Callbacks {
	public static void main(String[] args) {
		Callee1 c1 = new Callee1();
		Callee2 c2 = new Callee2();

		/* method 1: class implement CallBack */
		Caller caller = new Caller(c1);
		caller.go();

		/* method 2: inner class implement CallBack */
		caller = new Caller(c2.getCallBack());
		caller.go();
	}

}
