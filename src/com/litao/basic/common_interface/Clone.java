package com.litao.basic.common_interface;

// 必须实现Clonable接口, 以指示Object.clone()方法可以合法地对该类实例进行按字段复制
// 重写clone()方法
class ShallowClone implements Cloneable {
	private String str;

	public ShallowClone(String str) {
		super();
		this.str = str;
	}

	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}

	// 创建并返回此对象的一个副本
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	@Override
	public String toString() {
		return "ShallowClone [str=" + str + "]";
	}

}

class DeepClone implements Cloneable {
	private ShallowClone shallowClone;
	private String str;

	public DeepClone(ShallowClone shallowClone, String str) {
		super();
		this.shallowClone = shallowClone;
		this.str = str;
	}

	public ShallowClone getShallowClone() {
		return shallowClone;
	}

	public void setShallowClone(ShallowClone shallowClone) {
		this.shallowClone = shallowClone;
	}

	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}

	// 深度克隆,当类存在聚合关系的时候,克隆就必须考虑聚合对象的克隆
	@Override
	protected Object clone() throws CloneNotSupportedException {
		// 先调用父类的克隆方法进行克隆操作
		DeepClone deepClone = (DeepClone) super.clone();
		// 对于克隆后出的对象deepClone,如果其成员shallowClone为null,则不能调用clone(),否则出空指针异常
		if (deepClone.shallowClone != null)
			deepClone.shallowClone = (ShallowClone) this.shallowClone.clone();

		return deepClone;
	}

	@Override
	public String toString() {
		return "DeepClone [shallowClone=" + shallowClone + ", str=" + str + "]";
	}

}

public class Clone {

	public static void main(String[] args) throws CloneNotSupportedException {
		ShallowClone shallowClone = new ShallowClone("hello");
		ShallowClone shallowClone2 = (ShallowClone) shallowClone.clone();
		shallowClone2.setStr("world");
		System.out.println(shallowClone);
		System.out.println(shallowClone2);
		
		DeepClone deepClone = new DeepClone(shallowClone, "hello");
		DeepClone deepClone2 = (DeepClone) deepClone.clone();
		deepClone2.setShallowClone(shallowClone2);
		System.out.println(deepClone);
		System.out.println(deepClone2);
	}
}
