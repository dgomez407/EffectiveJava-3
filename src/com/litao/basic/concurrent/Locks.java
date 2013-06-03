package com.litao.basic.concurrent;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class AttemptLocking {
	private ReentrantLock lock = new ReentrantLock();

	public void untimed() {
		boolean captured = lock.tryLock();
		try {
			System.out.println("tryLock(): " + captured);
		} finally {
			if (captured)
				lock.unlock();
		}
	}

	public void timed() {
		boolean captured = false;
		try {
			captured = lock.tryLock(2, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		try {
			System.out.println("tryLock(2, TimeUnit.SECONDS): " + captured);
		} finally {
			if (captured)
				lock.unlock();
		}
	}

	public Lock getLock() {
		return lock;
	}
}

public class Locks {

	public static void main(String[] args) {
		final AttemptLocking al = new AttemptLocking();
		al.untimed();
		al.timed();

		new Thread() {
			{
				setDaemon(true);
			}

			@Override
			public void run() {
				al.getLock().lock();
				System.out.println("acquired");
			}

		}.start();

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		al.untimed();
		al.timed();
	}
}
