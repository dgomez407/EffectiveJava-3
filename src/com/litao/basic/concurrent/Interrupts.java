package com.litao.basic.concurrent;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

class SleepBlocked implements Runnable {

	@Override
	public void run() {
		try {
			TimeUnit.SECONDS.sleep(100);
		} catch (InterruptedException e) {
			System.out.println("InterruptedException");
		}
		System.out.println("Exiting SleepBlocked.run()");
	}

}

class IOBlocked implements Runnable {
	private InputStream in;

	public IOBlocked(InputStream is) {
		in = is;
	}

	@Override
	public void run() {
		try {
			System.out.println("Waiting for read():");
			in.read();
		} catch (IOException e) {
			if (Thread.currentThread().isInterrupted()) {
				System.out.println("Interrupted from blocked I/O");
			} else {
				throw new RuntimeException(e);
			}
		}
		System.out.println("Exiting IOBlocked.run()");
	}

}

class SynchronizedBlocked implements Runnable {
	// Never releases lock
	public synchronized void f() {
		while (true) {
			Thread.yield();
		}
	}

	public SynchronizedBlocked() {
		new Thread() {

			@Override
			public void run() {
				f();
			}

		}.start();
	}

	@Override
	public void run() {
		System.out.println("Trying to call f()");
		f();
		System.out.println("Exiting SynchronizedBlocked.run()");
	}

}

public class Interrupts {
	private static ExecutorService executorService = Executors.newCachedThreadPool();

	static void test(Runnable r) throws InterruptedException {
		Future<?> f = executorService.submit(r);
		TimeUnit.MILLISECONDS.sleep(100);
		System.out.println("Interrupting " + r.getClass().getName());
		f.cancel(true); // Interrupts if running
		System.out.println("Interrupt sent to " + r.getClass().getName());
	}

	public static void main(String[] args) throws InterruptedException {
		test(new SleepBlocked()); // 可中断的阻塞
		test(new IOBlocked(System.in)); // 不可中断的阻塞
		test(new SynchronizedBlocked()); // 不可中断的阻塞
		TimeUnit.SECONDS.sleep(3);
		System.out.println("Aborting with System.exit(0)");
		System.exit(0);
	}

}
