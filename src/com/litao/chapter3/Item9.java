package com.litao.chapter3;

import java.util.HashMap;
import java.util.Map;

class PhoneNumber implements Comparable<PhoneNumber> {
	private final short areaCode;
	private final short prefix;
	private final short lineNumber;

	public PhoneNumber(int areaCode, int prefix, int lineNumber) {
		rangeCheck(areaCode, 999, "area code");
		rangeCheck(prefix, 999, "prefix");
		rangeCheck(lineNumber, 9999, "line number");

		this.areaCode = (short) areaCode;
		this.prefix = (short) prefix;
		this.lineNumber = (short) lineNumber;
	}

	private static void rangeCheck(int arg, int max, String name) {
		if (arg < 0 || arg > max) {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (!(obj instanceof PhoneNumber))
			return false;
		PhoneNumber pn = (PhoneNumber) obj;
		// lineNumber、prefix、areaCode的区分度依次减弱
		return pn.lineNumber == lineNumber && pn.prefix == prefix && pn.areaCode == areaCode;
	}

	@Override
	public int hashCode() {
		int result = 17;
		result = 31 * result + areaCode;
		result = 31 * result + prefix;
		result = 31 * result + lineNumber;
		return result;
	}

	@Override
	public int compareTo(PhoneNumber pn) {
		if (pn == null)
			throw new NullPointerException();

		int areaCodeDiff = areaCode - pn.areaCode;
		if (areaCodeDiff != 0)
			return areaCodeDiff;

		int prefixDiff = prefix - pn.prefix;
		if (prefixDiff != 0)
			return prefixDiff;

		return lineNumber - pn.lineNumber;
	}
}

public class Item9 {

	public static void main(String[] args) {
		Map<PhoneNumber, String> map = new HashMap<PhoneNumber, String>();
		map.put(new PhoneNumber(707, 867, 5309), "Jenny");
		System.out.println(map.get(new PhoneNumber(707, 867, 5309)));

		PhoneNumber pn1 = new PhoneNumber(707, 867, 5309);
		PhoneNumber pn2 = new PhoneNumber(704, 864, 5304);
		System.out.println(pn1.compareTo(pn2));
		System.out.println(pn1.compareTo(pn1));
	}

}
