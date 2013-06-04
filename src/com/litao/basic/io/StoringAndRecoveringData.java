package com.litao.basic.io;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class StoringAndRecoveringData {

	public static void main(String[] args) throws IOException {
		DataOutputStream out = new DataOutputStream(new FileOutputStream("Data.txt"));
		out.writeDouble(Math.PI);
		out.writeUTF("That was pi");
		out.writeDouble(Math.sqrt(2));
		out.writeUTF("Square root of 2");
		out.close();
		
		DataInputStream in = new DataInputStream(new FileInputStream("Data.txt"));
		System.out.println(in.readDouble());
		System.out.println(in.readUTF());
		System.out.println(in.readDouble());
		System.out.println(in.readUTF());
		in.close();
	}

}
