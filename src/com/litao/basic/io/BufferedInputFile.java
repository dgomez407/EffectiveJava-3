package com.litao.basic.io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class BufferedInputFile {

	public static String read(String fileName) throws IOException {
		BufferedReader in = new BufferedReader(new FileReader(fileName));
		String s;
		StringBuffer sb = new StringBuffer();
		while((s = in.readLine()) != null)
			sb.append(s + "\n");
		in.close();
		return sb.toString();
	}
	
	public static void main(String[] args) throws IOException {
		System.out.println(read("src/com/litao/basic/io/BufferedInputFile.java"));
	}

}