package com.litao.basic.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class LiftOff implements Runnable {
	protected int countDown = 10;
	private static int taskCount = 0;
	private final int id = taskCount++;

	public LiftOff() {

	}

	public LiftOff(int countDown) {
		this.countDown = countDown;
	}

	public String status() {
		return "#" + id + "(" + (countDown > 0 ? countDown : "Liftoff!") + "). ";
	}

	@Override
	public void run() {
		while (countDown-- > 0) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(status());
			Thread.yield(); // 将CPU从一个线程转到另一个线程
		}
	}
}

public class ExecutorServices {
	public static void main(String[] args) {
		// ExecutorService executorService = Executors.newCachedThreadPool();
		// ExecutorService executorService = Executors.newSingleThreadExecutor();
		ExecutorService executorService = Executors.newFixedThreadPool(3);
		for (int i = 0; i < 5; i++) {
			executorService.execute(new LiftOff());
		}
		executorService.shutdown(); // shutdown() is non block method
		System.out.println("main thread finished");
	}
}
