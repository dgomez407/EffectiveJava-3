package com.litao.basic.nio;

import java.nio.charset.Charset;
import java.util.Map.Entry;
import java.util.SortedMap;

public class AvailableCharSets {

	public static void main(String[] args) {
		SortedMap<String, Charset> charSets = Charset.availableCharsets();
		for (Entry<String, Charset> entry : charSets.entrySet()) {
			String cName = entry.getKey();
			Charset charSet = entry.getValue();
			System.out.print(cName + ": ");
			for(String aliase : charSet.aliases()) {
				System.out.print(aliase);
			}
			System.out.println();
		}
	}

}
