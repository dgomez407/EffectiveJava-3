package com.litao.basic.io;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

public class FormattedMemoryInput {

	public static void main(String[] args) throws IOException {
		DataInputStream in = new DataInputStream(new ByteArrayInputStream("hello, world\nhello, java".getBytes()));
		while(in.available() != 0)
			System.out.print((char)in.readByte());
	}

}
