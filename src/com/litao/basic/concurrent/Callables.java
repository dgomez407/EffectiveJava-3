package com.litao.basic.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

class TaskWithResult implements Callable<Integer> {
	private int id;

	public TaskWithResult(int id) {
		this.id = id;
	}

	@Override
	public Integer call() throws Exception {
		Thread.sleep(2000);
		System.out.println(id + " call()");
		return id;
	}

}

public class Callables {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ExecutorService executorService = Executors.newCachedThreadPool();
		List<Future<Integer>> results = new ArrayList<Future<Integer>>();
		for (int i = 0; i < 10; i++) {
			System.out.println("new thread " + i);
			results.add(executorService.submit(new TaskWithResult(i)));
		}
		executorService.shutdown();

		for (Future<Integer> future : results) {
			// isDone() is non block method
			System.out.println(future.isDone());
			// get() is block method
			System.out.println(future.get());
		}
	}

}
