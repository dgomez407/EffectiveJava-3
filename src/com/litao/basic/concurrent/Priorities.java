package com.litao.basic.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class SimplePriority implements Runnable {
	private static int id = -1;
	private String name;
	private int priority;

	public SimplePriority(int priority) {
		this.id++;
		this.name = "SimplePriority-" + id;
		this.priority = priority;
	}

	@Override
	public void run() {
		Thread thread = Thread.currentThread();
		thread.setName(name);
		thread.setPriority(priority);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(thread + " run()");
	}

}

public class Priorities {

	public static void main(String[] args) {
		ExecutorService executorService = Executors.newCachedThreadPool();
		for (int i = 0; i < 5; i++) {
			executorService.execute(new SimplePriority(Thread.MAX_PRIORITY));
		}
		executorService.shutdown();
	}

}
