package com.litao.basic.concurrent;

class Sleeper extends Thread {
	private int duration;

	public Sleeper(String name, int sleepTime) {
		super(name);
		this.duration = sleepTime;
		start();
	}

	@Override
	public void run() {
		try {
			sleep(duration);
			System.out.println(getName() + " has awakened");
		} catch (InterruptedException e) {
			System.out.println(getName() + " was interrupted. isInterrupted(): " + isInterrupted());
		}
	}
}

class Joiner extends Thread {
	private Sleeper sleeper;

	public Joiner(String name, Sleeper sleeper) {
		super(name);
		this.sleeper = sleeper;
		start();
	}

	@Override
	public void run() {
		try {
			sleeper.join();
		} catch (InterruptedException e) {
			System.out.println("Interrupted");
		}
		System.out.println(getName() + " join completed");
	}
}

public class Joins {

	public static void main(String[] args) {
		Sleeper sleepy = new Sleeper("Sleepy", 1500), grumpy = new Sleeper("Grumpy", 1500);
		Joiner dopey = new Joiner("Dopey", sleepy), doc = new Joiner("Doc", grumpy);
		grumpy.interrupt();
	}

}
