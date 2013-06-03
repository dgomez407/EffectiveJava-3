package com.litao.basic.concurrent;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

class Toast {
	public enum Status {
		DRY, BUTTERED, JAMMED
	}

	private Status status = Status.DRY;
	private final int id;

	public Toast(int idn) {
		id = idn;
	}

	public void butter() {
		status = Status.BUTTERED;
	}

	public void jam() {
		status = Status.JAMMED;
	}

	public Status getStatus() {
		return status;
	}

	public int getId() {
		return id;
	}

	@Override
	public String toString() {
		return "Toast " + id + ": " + status;
	}

}

class Toaster implements Runnable {
	private BlockingQueue<Toast> toastQueue;
	private int count = 0;
	private Random rand = new Random(47);

	public Toaster(BlockingQueue<Toast> tq) {
		toastQueue = tq;
	}

	@Override
	public void run() {
		try {
			while (!Thread.interrupted()) {
				TimeUnit.MILLISECONDS.sleep(100 + rand.nextInt(500));
				Toast t = new Toast(count++);
				System.out.println(t);
				toastQueue.put(t);
			}
		} catch (InterruptedException e) {
			System.out.println("Toaster interrupted");
		}
		System.out.println("Toaster off");
	}

}

class Butter implements Runnable {
	private BlockingQueue<Toast> dryQueue, butteredQueue;

	public Butter(BlockingQueue<Toast> dry, BlockingQueue<Toast> buttered) {
		dryQueue = dry;
		butteredQueue = buttered;
	}

	@Override
	public void run() {
		try {
			while (!Thread.interrupted()) {
				Toast t = dryQueue.take();
				t.butter();
				System.out.println(t);
				butteredQueue.put(t);
			}
		} catch (InterruptedException e) {
			System.out.println("Butter interrupted");
		}
		System.out.println("Butter off");
	}

}

class Jammer implements Runnable {
	private BlockingQueue<Toast> butteredQueue, finishedQueue;

	public Jammer(BlockingQueue<Toast> buttered, BlockingQueue<Toast> finished) {
		butteredQueue = buttered;
		finishedQueue = finished;
	}

	@Override
	public void run() {
		try {
			while (!Thread.interrupted()) {
				Toast t = butteredQueue.take();
				t.jam();
				System.out.println(t);
				finishedQueue.put(t);
			}
		} catch (InterruptedException e) {
			System.out.println("Jammer interrupted");
		}
		System.out.println("Jammer off");
	}

}

class Eater implements Runnable {
	private BlockingQueue<Toast> finishedQueue;
	private int counter = 0;

	public Eater(BlockingQueue<Toast> finished) {
		finishedQueue = finished;
	}

	@Override
	public void run() {
		try {
			while (!Thread.interrupted()) {
				Toast t = finishedQueue.take();
				if (t.getId() != counter++ || t.getStatus() != Toast.Status.JAMMED) {
					System.out.println(">>>>Error: " + t);
					System.exit(1);
				} else {
					System.out.println("Chomp! " + t);
				}
			}
		} catch (InterruptedException e) {
			System.out.println("Eater interrupted");
		}
		System.out.println("Eater off");
	}

}

public class BlockingQueues {

	public static void main(String[] args) throws InterruptedException {
		BlockingQueue<Toast> dryQueue = new LinkedBlockingQueue<Toast>();
		BlockingQueue<Toast> butteredQueue = new LinkedBlockingQueue<Toast>();
		BlockingQueue<Toast> finishedQueue = new LinkedBlockingQueue<Toast>();
		
		ExecutorService executorService = Executors.newCachedThreadPool();
		executorService.execute(new Toaster(dryQueue));
		executorService.execute(new Butter(dryQueue, butteredQueue));
		executorService.execute(new Jammer(butteredQueue, finishedQueue));
		
		//TimeUnit.SECONDS.sleep(5);
		//executorService.shutdownNow();
	}

}
