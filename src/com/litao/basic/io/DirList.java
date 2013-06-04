package com.litao.basic.io;

import java.io.File;
import java.io.FilenameFilter;
import java.sql.Array;
import java.util.Arrays;
import java.util.regex.Pattern;

class DirFilter implements FilenameFilter {
	private Pattern pattern;

	public DirFilter(String regex) {
		pattern = Pattern.compile(regex);
	}

	@Override
	public boolean accept(File dir, String name) {
		return pattern.matcher(name).matches();
	}

}

public class DirList {

	public static void main(String[] args) {
		File path = new File(".");
		/* method 1: implements interface FilenameFilter */
		// String[] list = path.list(new DirFilter("\\..*"));
		/* method 2: use anonymous inner class */
		String[] list = path.list(new FilenameFilter() {
			private Pattern pattern = Pattern.compile("\\..*");

			@Override
			public boolean accept(File dir, String name) {
				return pattern.matcher(name).matches();
			}
		});
		Arrays.sort(list, String.CASE_INSENSITIVE_ORDER);
		for (String dirItem : list) {
			System.out.println(dirItem);
		}
	}

}
