package com.litao.basic.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

public class TextFile extends ArrayList<String> {

	public static String read(String fileName) throws IOException {
		StringBuffer sb = new StringBuffer();
		BufferedReader in = new BufferedReader(new FileReader(new File(fileName).getAbsoluteFile()));
		String s;
		while ((s = in.readLine()) != null) {
			sb.append(s);
			sb.append("\n");
		}
		in.close();

		return sb.toString();
	}

	public static void write(String fileName, String text) throws IOException {
		PrintWriter out = new PrintWriter(new File(fileName).getAbsoluteFile());
		out.print(text);
		out.close();
	}

	public TextFile(String fileName, String splitter) throws IOException {
		super(Arrays.asList(read(fileName).split(splitter)));
		if (get(0).equals("")) {
			remove(0);
		}
	}

	public TextFile(String fileName) throws IOException {
		this(fileName, "\n");
	}

	public void write(String fileName) throws IOException {
		PrintWriter out = new PrintWriter(new File(fileName).getAbsoluteFile());
		for (String item : this) {
			out.println(item);
		}
		out.close();
	}

	public static void main(String[] args) throws IOException {
		String file = TextFile.read("src/com/litao/basic/io/TextFile.java");
		TextFile.write("test.txt", file);
		TextFile text = new TextFile("test.txt");
		int lineCount = 1;
		for (String line : text) {
			System.out.println(lineCount++ + ": " + line);
		}
		text.write("test2.txt");
	}

}
