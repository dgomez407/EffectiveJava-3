package com.litao.basic.collection;

import java.util.BitSet;

public class BitSets {

	public static void main(String[] args) {
		BitSet bit = new BitSet(100);

		bit.set(1);
		bit.set(10);

		BitSet anBit = new BitSet();
		anBit.set(10);
		anBit.set(5);
		//bit.and(anBit);
		bit.or(anBit);

		for (int i = 0; i < bit.length(); i++) {
			System.out.println(bit.get(i));
		}
	}

}
