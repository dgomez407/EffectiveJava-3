package com.litao.basic.collection;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.WeakHashMap;

class MyClass {
	private String identifier;

	public MyClass(String identifier) {
		this.identifier = identifier;
		System.out.println(this.identifier + " created ...");
	}

	@Override
	protected void finalize() throws Throwable {
		System.out.println(this.identifier + " destroyed ...");
	}

	@Override
	public String toString() {
		return "MyClass [identifier=" + identifier + "]";
	}

}

public class References {

	public static void main(String[] args) throws InterruptedException {
		// StrongReference 是 Java 的默认引用实现,它会尽可能长时间的存活于 JVM 内， 当没有任何对象指向它时Java GC 执行后将会被回收
		MyClass referent1 = new MyClass("StrongReference");
		MyClass strongReference = referent1;
		referent1 = null;
		System.gc();
		Thread.sleep(1000);

		// WeakReference， 顾名思义,是一个弱引用,当所引用的对象在 JVM 内不再有强引用时, Java GC 后 weak reference 将会被自动回收
		MyClass referent2 = new MyClass("WeakReference");
		WeakReference<MyClass> weakRerference = new WeakReference<MyClass>(referent2);
		System.out.println(weakRerference.get());
		referent2 = null;
		System.gc();
		Thread.sleep(1000);

		// SoftReference 于 WeakReference 的特性基本一致， 最大的区别在于 SoftReference 会尽可能长的保留引用直到 JVM 内存不足时才会被回收(虚拟机保证), 这一特性使得 SoftReference 非常适合缓存应用
		MyClass referent3 = new MyClass("SoftReference");
		SoftReference<MyClass> softReference = new SoftReference<MyClass>(referent3);
		System.out.println(softReference.get());
		referent3 = null;
		System.gc();
		Thread.sleep(1000);

		// WeakHashMap 使用 WeakReference 作为 key， 一旦没有指向 key 的强引用, WeakHashMap 在Java GC 后将自动删除相关的 entry
		Map<MyClass, String> weakHashMap = new WeakHashMap<MyClass, String>();
		MyClass key = new MyClass("WeakHashMap_Key");
		String value = "WeakHashMap_Value";
		weakHashMap.put(key, value);
		key = null;
		System.gc();
		Thread.sleep(1000);
		if (!weakHashMap.containsValue(value)) {
			System.out.println("WeakHashMap don't contains value " + value);
		}

		// 当一个 WeakReference 开始返回 null 时， 它所指向的对象已经准备被回收， 这时可以做一些合适的清理工作. 将一个 ReferenceQueue 传给一个 Reference 的构造函数， 当对象被回收时， 虚拟机会自动将这个对象插入到 ReferenceQueue 中，
		// WeakHashMap 就是利用 ReferenceQueue 来清除 key 已经没有强引用的 entries.
		MyClass referent4 = new MyClass("ReferenceQueue");
		ReferenceQueue<MyClass> referenceQueue = new ReferenceQueue<MyClass>();
		WeakReference<MyClass> weakReference2 = new WeakReference<MyClass>(referent4, referenceQueue);
		System.out.println(weakReference2.isEnqueued());
		Reference<? extends MyClass> polled = referenceQueue.poll();
		System.out.println(polled);
		referent4 = null;
		System.gc();
		Thread.sleep(1000);
		System.out.println(weakReference2.isEnqueued());
		Reference<? extends MyClass> removed = referenceQueue.remove();
		System.out.println(removed);

		// Phantom Reference(幽灵引用) 与 WeakReference 和 SoftReference 有很大的不同,因为它的 get() 方法永远返回 null, 这也正是它名字的由来
		// PhantomReference 唯一的用处就是跟踪 referent何时被 enqueue 到 ReferenceQueue 中
		MyClass referent5 = new MyClass("PhantomReference");
		// 将一个 ReferenceQueue 传给一个 Reference 的构造函数， 当对象被回收时， 虚拟机会自动将这个对象插入到 ReferenceQueue 中， WeakHashMap 就是利用 ReferenceQueue 来清除 key 已经没有强引用的 entries
		PhantomReference<MyClass> phantomReference = new PhantomReference<MyClass>(referent5, referenceQueue);
		System.out.println(phantomReference.get());
		referent5 = null;
		System.gc();
		// PhantomReference有两个好处， 其一， 它可以让我们准确地知道对象何时被从内存中删除， 这个特性可以被用于一些特殊的需求中(例如 Distributed GC，XWork 和 google-guice 中也使用 PhantomReference 做了一些清理性工作).
		// 其二， 它可以避免 finalization 带来的一些根本性问题, 上文提到 PhantomReference 的唯一作用就是跟踪 referent 何时被 enqueue 到 ReferenceQueue 中,但是 WeakReference 也有对应的功能, 两者的区别到底在哪呢 ?
		// 这就要说到 Object 的 finalize 方法, 此方法将在 gc 执行前被调用, 如果某个对象重载了 finalize 方法并故意在方法内创建本身的强引用,这将导致这一轮的 GC 无法回收这个对象并有可能
		// 引起任意次 GC， 最后的结果就是明明 JVM 内有很多 Garbage 却 OutOfMemory， 使用 PhantomReference 就可以避免这个问题， 因为 PhantomReference 是在 finalize 方法执行后回收的，也就意味着此时已经不可能拿到原来的引用,也就不会出现上述问题,当然这是一个很极端的例子, 一般不会出现.
	}
}
