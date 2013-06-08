package com.litao.basic.io;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class TransferTo {

	public static void main(String[] args) throws IOException {
		FileChannel in = new FileInputStream("src/com/litao/basic/io/ChannelCopy.java").getChannel();
		FileChannel out = new FileOutputStream("data.txt").getChannel();
		in.transferTo(0, in.size(), out);
		// out.transferFrom(in, 0, in.size()); // transferFrom is also OK
	}

}
