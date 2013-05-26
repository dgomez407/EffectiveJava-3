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
		// StrongReference �� Java ��Ĭ������ʵ��,���ᾡ���ܳ�ʱ��Ĵ���� JVM �ڣ� ��û���κζ���ָ����ʱJava GC ִ�к󽫻ᱻ����
		MyClass referent1 = new MyClass("StrongReference");
		MyClass strongReference = referent1;
		referent1 = null;
		System.gc();
		Thread.sleep(1000);

		// WeakReference�� ����˼��,��һ��������,�������õĶ����� JVM �ڲ�����ǿ����ʱ, Java GC �� weak reference ���ᱻ�Զ�����
		MyClass referent2 = new MyClass("WeakReference");
		WeakReference<MyClass> weakRerference = new WeakReference<MyClass>(referent2);
		System.out.println(weakRerference.get());
		referent2 = null;
		System.gc();
		Thread.sleep(1000);

		// SoftReference �� WeakReference �����Ի���һ�£� ������������ SoftReference �ᾡ���ܳ��ı�������ֱ�� JVM �ڴ治��ʱ�Żᱻ����(�������֤), ��һ����ʹ�� SoftReference �ǳ��ʺϻ���Ӧ��
		MyClass referent3 = new MyClass("SoftReference");
		SoftReference<MyClass> softReference = new SoftReference<MyClass>(referent3);
		System.out.println(softReference.get());
		referent3 = null;
		System.gc();
		Thread.sleep(1000);

		// WeakHashMap ʹ�� WeakReference ��Ϊ key�� һ��û��ָ�� key ��ǿ����, WeakHashMap ��Java GC ���Զ�ɾ����ص� entry
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

		// ��һ�� WeakReference ��ʼ���� null ʱ�� ����ָ��Ķ����Ѿ�׼�������գ� ��ʱ������һЩ���ʵ�������. ��һ�� ReferenceQueue ����һ�� Reference �Ĺ��캯���� �����󱻻���ʱ�� ��������Զ������������뵽 ReferenceQueue �У�
		// WeakHashMap �������� ReferenceQueue ����� key �Ѿ�û��ǿ���õ� entries.
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

		// Phantom Reference(��������) �� WeakReference �� SoftReference �кܴ�Ĳ�ͬ,��Ϊ���� get() ������Զ���� null, ��Ҳ���������ֵ�����
		// PhantomReference Ψһ���ô����Ǹ��� referent��ʱ�� enqueue �� ReferenceQueue ��
		MyClass referent5 = new MyClass("PhantomReference");
		// ��һ�� ReferenceQueue ����һ�� Reference �Ĺ��캯���� �����󱻻���ʱ�� ��������Զ������������뵽 ReferenceQueue �У� WeakHashMap �������� ReferenceQueue ����� key �Ѿ�û��ǿ���õ� entries
		PhantomReference<MyClass> phantomReference = new PhantomReference<MyClass>(referent5, referenceQueue);
		System.out.println(phantomReference.get());
		referent5 = null;
		System.gc();
		// PhantomReference�������ô��� ��һ�� ������������׼ȷ��֪�������ʱ�����ڴ���ɾ���� ������Կ��Ա�����һЩ�����������(���� Distributed GC��XWork �� google-guice ��Ҳʹ�� PhantomReference ����һЩ�����Թ���).
		// ����� �����Ա��� finalization ������һЩ����������, �����ᵽ PhantomReference ��Ψһ���þ��Ǹ��� referent ��ʱ�� enqueue �� ReferenceQueue ��,���� WeakReference Ҳ�ж�Ӧ�Ĺ���, ���ߵ����𵽵������� ?
		// ���Ҫ˵�� Object �� finalize ����, �˷������� gc ִ��ǰ������, ���ĳ������������ finalize �����������ڷ����ڴ��������ǿ����,�⽫������һ�ֵ� GC �޷�������������п���
		// ��������� GC�� ���Ľ���������� JVM ���кܶ� Garbage ȴ OutOfMemory�� ʹ�� PhantomReference �Ϳ��Ա���������⣬ ��Ϊ PhantomReference ���� finalize ����ִ�к���յģ�Ҳ����ζ�Ŵ�ʱ�Ѿ��������õ�ԭ��������,Ҳ�Ͳ��������������,��Ȼ����һ���ܼ��˵�����, һ�㲻�����.
	}
}
