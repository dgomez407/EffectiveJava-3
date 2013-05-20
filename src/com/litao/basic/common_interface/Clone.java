package com.litao.basic.common_interface;

// ����ʵ��Clonable�ӿ�, ��ָʾObject.clone()�������ԺϷ��ضԸ���ʵ�����а��ֶθ���
// ��дclone()����
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

	// ���������ش˶����һ������
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

	// ��ȿ�¡,������ھۺϹ�ϵ��ʱ��,��¡�ͱ��뿼�Ǿۺ϶���Ŀ�¡
	@Override
	protected Object clone() throws CloneNotSupportedException {
		// �ȵ��ø���Ŀ�¡�������п�¡����
		DeepClone deepClone = (DeepClone) super.clone();
		// ���ڿ�¡����Ķ���deepClone,������ԱshallowCloneΪnull,���ܵ���clone(),�������ָ���쳣
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
