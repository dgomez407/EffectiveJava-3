package com.litao.chapter2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/*
 * readResolve is called after readObject has returned
 * conversely writeResolve is called before writeObject and probably on a different object
 * readResolve() will ensure the singleton contract while serialization
 */

// Enum singleton - the preferred approach
enum Elvis {
	INSTANCE;

	public void f() {
		System.out.println("do something...");
	}
}

// Singleton with static factory
class Singleton implements Serializable {
	private final static Singleton INSTANCE = new Singleton();

	private Singleton() {
		System.out.println("constructor is called...");
	}

	public static Singleton getInstance() {
		return INSTANCE;
	}

	// 对于一个正在被反序列化的对象，如果它的类定义了一个readResolve方法，并且具备正确的声明，
	// 那么在反序列化之后，新建对象上的readResolve方法就会被调用。
	// 然后，该方法返回的对象引用将被返回，取代新建的对象。
	// 在这个特性的绝大多数用法中，指向新建对象的引用不需要再被保留，因此立即成为垃圾回收的对象。
	// readResolve is called after readObject has returned (conversely writeResolve is called before writeObject and probably on a different object).
	private Object readResolve() {
		System.out.println("readResolve is called...");
		return INSTANCE;
	}

	@Override
	public String toString() {
		return "Singleton []";
	}

}

public class Item3 {

	public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException {
		File file = new File("singleton.out");

		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
		Singleton singleton = Singleton.getInstance();
		out.writeObject(singleton);
		out.close();

		ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
		Singleton newSingleton = (Singleton) in.readObject();
		in.close();
		System.out.println(newSingleton);
	}

}
