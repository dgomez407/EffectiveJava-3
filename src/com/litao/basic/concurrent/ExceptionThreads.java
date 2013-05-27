package com.litao.basic.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

class MyUncaughExceptionHandler implements Thread.UncaughtExceptionHandler {

	@Override
	public void uncaughtException(Thread t, Throwable e) {
		System.out.println("caught " + e + "@" + t);
	}

}

class ExceptionThread implements Runnable {

	@Override
	public void run() {
		Thread t = Thread.currentThread();
		System.out.println("run() by " + t);
		System.out.println("ExceptionHandler: " + t.getUncaughtExceptionHandler());
		throw new RuntimeException();
	}
}

class HandlerThreadFactory implements ThreadFactory {

	@Override
	public Thread newThread(Runnable r) {
		System.out.println(this + "creating new Thread");
		Thread t = new Thread(r);
		System.out.println("created " + t);
		t.setUncaughtExceptionHandler(new MyUncaughExceptionHandler());
		System.out.println("ExceptionHandler: " + t.getUncaughtExceptionHandler());
		return t;
	}

}

public class ExceptionThreads {

	public static void main(String[] args) {
		// ExecutorService executorService = Executors.newCachedThreadPool(new HandlerThreadFactory());
		// executorService.execute(new ExceptionThread());

		// Thread t = new Thread(new ExceptionThread());
		// t.setUncaughtExceptionHandler(new MyUncaughExceptionHandler());
		// t.start();

		Thread.setDefaultUncaughtExceptionHandler(new MyUncaughExceptionHandler());
		ExecutorService executorService = Executors.newCachedThreadPool(new HandlerThreadFactory());
		executorService.execute(new ExceptionThread());
	}

}
