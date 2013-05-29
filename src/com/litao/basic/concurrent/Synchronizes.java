package com.litao.basic.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

abstract class IntGenerator {
	private volatile boolean canceled = false;

	public abstract int next();

	public void cancel() {
		canceled = true;
	}

	public boolean isCanceled() {
		return canceled;
	}
}

class EvenChecker implements Runnable {
	private IntGenerator generator;
	private final int id;

	public EvenChecker(IntGenerator g, int ident) {
		generator = g;
		id = ident;
	}

	@Override
	public void run() {
		while (!generator.isCanceled()) {
			int val = generator.next();
			if (val % 2 != 0) {
				System.out.println(val + " not even!");
				generator.cancel();
			}
		}
	}

	public static void test(IntGenerator gp, int count) {
		System.out.println("Press Control-C to exit");
		ExecutorService executorService = Executors.newCachedThreadPool();
		for (int i = 0; i < count; i++) {
			executorService.execute(new EvenChecker(gp, i));
		}
		executorService.shutdown();
	}
}

class EvenGenerator extends IntGenerator {
	private int currentEvenValue = 0;

	@Override
	public synchronized int next() {
		++currentEvenValue;
		Thread.yield(); // Cause failure faster
		++currentEvenValue;
		return currentEvenValue;
	}
}

class MutexEvenGenerator extends IntGenerator {
	private int currentEvenValue = 0;
	private Lock lock = new ReentrantLock();

	@Override
	public int next() {
		lock.lock();
		try {
			++currentEvenValue;
			Thread.yield(); // Cause failure faster
			++currentEvenValue;
			return currentEvenValue;
		} finally {
			lock.unlock();
		}
	}
}

class AtomicEvenGenerator extends IntGenerator {
	private AtomicInteger currentEvenValue = new AtomicInteger(0);

	@Override
	public int next() {
		return currentEvenValue.addAndGet(2);
	}
}

public class Synchronizes {

	public static void main(String[] args) {
		EvenChecker.test(new EvenGenerator(), 10); // use synchronized
		EvenChecker.test(new MutexEvenGenerator(), 10); // use lock
		EvenChecker.test(new AtomicEvenGenerator(), 10); // use AtomicInteger
	}

}
