package com.litao.basic.io;

import java.io.IOException;
import java.io.StringReader;

public class MemoryInput {

	public static void main(String[] args) throws IOException {
		StringReader in = new StringReader("hello, world\nhello, java");
		int c;
		while((c = in.read()) != -1)
			System.out.print((char)c);
	}

}
