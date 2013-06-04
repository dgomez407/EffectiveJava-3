package com.litao.basic.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;

public class BasicFileOutput {

	public static void main(String[] args) throws IOException {
		String file = "BasicFileOutput.out";
		BufferedReader in = new BufferedReader(new StringReader("hello, world\nhello, java"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file)));
		// PrintWriter out = new PrintWriter(file); // also buffer
		int lineCount = 1;
		String s;
		while ((s = in.readLine()) != null)
			out.println(lineCount++ + ": " + s);
		out.close(); // flush out buffer
		in.close();
		System.out.println(BufferedInputFile.read(file));
	}

}
