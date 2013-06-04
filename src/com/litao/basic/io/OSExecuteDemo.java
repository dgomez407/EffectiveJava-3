package com.litao.basic.io;

import java.io.BufferedReader;
import java.io.InputStreamReader;

class OSExceuteException extends RuntimeException {
	public OSExceuteException(String why) {
		super(why);
	}
}

class OSExecute {
	public static void command(String command) {
		boolean err = false;
		try {
			Process process = new ProcessBuilder(command.split(" ")).start();

			BufferedReader results = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String s;
			while ((s = results.readLine()) != null) {
				System.out.println(s);
			}

			BufferedReader errors = new BufferedReader(new InputStreamReader(process.getErrorStream()));
			while ((s = errors.readLine()) != null) {
				System.err.println(s);
				err = true;
			}
		} catch (Exception e) {
			if (!command.startsWith("CMD /C")) {
				command("CMD /C " + command);
			} else {
				throw new RuntimeException(e);
			}
		}

		if (err) {
			throw new OSExceuteException("Errors executing " + command);
		}
	}
}

public class OSExecuteDemo {

	public static void main(String[] args) {
		OSExecute.command("javap OSExecuteDemo");
	}

}
