package com.litao.basic.concurrent;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Food {

	@Override
	public String toString() {
		return "Food";
	}

}

class Customer implements Runnable {
	private final int id;
	private Restaurant restaurant;

	public Customer(int id, Restaurant restaurant) {
		this.id = id;
		this.restaurant = restaurant;
	}

	@Override
	public void run() {
		try {
			while (!Thread.interrupted()) {
				Thread.sleep(2000);
				Food food = restaurant.get();
				System.out.println(this + " get " + food);
			}
		} catch (InterruptedException e) {
			System.out.println(this + " is interrupted");
		}
	}

	@Override
	public String toString() {
		return "Customer-" + id;
	}

}

class Chef implements Runnable {
	private final int id;
	private Restaurant restaurant;

	public Chef(int id, Restaurant restaurant) {
		this.id = id;
		this.restaurant = restaurant;
	}

	@Override
	public void run() {
		try {
			while (!Thread.interrupted()) {
				Thread.sleep(1000);
				Food food = new Food();
				restaurant.put(food);
				System.out.println(this + " put " + food);
			}
		} catch (InterruptedException e) {
			System.out.println(this + " is interrupted");
		}
	}

	@Override
	public String toString() {
		return "Chef-" + id;
	}

}

interface Restaurant {
	public void put(Food food);

	public Food get();
}

// use synchronized & wait & notify
class Restaurant1 implements Restaurant {
	private final int num = 20;
	private Queue<Food> foods = new LinkedList<Food>();

	@Override
	public synchronized void put(Food food) {
		while (foods.size() == num) {
			try {
				System.out.println("foods is full!");
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		foods.offer(food);
		notifyAll();
	}

	@Override
	public synchronized Food get() {
		while (foods.isEmpty()) {
			try {
				System.out.println("foods is empty!");
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		Food food = foods.poll();
		notifyAll();
		return food;
	}
}

// use Lock & Condition
class Restaurant2 implements Restaurant {
	private Lock lock = new ReentrantLock();
	private Condition condition = lock.newCondition();

	private final int num = 20;
	private Queue<Food> foods = new LinkedList<Food>();

	public void put(Food food) {
		lock.lock();
		try {
			while (foods.size() == num) {
				try {
					System.out.println("foods is full!");
					condition.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			foods.offer(food);
			condition.signalAll();
		} finally {
			lock.unlock();
		}
	}

	public synchronized Food get() {
		lock.lock();
		try {
			while (foods.isEmpty()) {
				try {
					System.out.println("foods is empty!");
					condition.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			Food food = foods.poll();
			condition.signalAll();
			return food;
		} finally {
			lock.unlock();
		}
	}
}

public class ProducerConsumer {

	public static void main(String[] args) {
		// Restaurant restaurant = new Restaurant1(); // use synchronized & wait & notify
		Restaurant restaurant = new Restaurant2(); // use Lock & Condition
		Customer[] customers = { new Customer(1, restaurant), new Customer(2, restaurant), new Customer(3, restaurant) };
		Chef[] chefs = { new Chef(1, restaurant), new Chef(2, restaurant) };

		ExecutorService executorService = Executors.newCachedThreadPool();
		for (Chef chef : chefs) {
			executorService.execute(chef);
		}
		for (Customer customer : customers) {
			executorService.execute(customer);
		}

		executorService.shutdown();
	}
}
