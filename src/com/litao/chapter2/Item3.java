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

	// ����һ�����ڱ������л��Ķ�����������ඨ����һ��readResolve���������Ҿ߱���ȷ��������
	// ��ô�ڷ����л�֮���½������ϵ�readResolve�����ͻᱻ���á�
	// Ȼ�󣬸÷������صĶ������ý������أ�ȡ���½��Ķ���
	// ��������Եľ�������÷��У�ָ���½���������ò���Ҫ�ٱ����������������Ϊ�������յĶ���
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
