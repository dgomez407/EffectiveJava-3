package com.litao.basic.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class ChannelCopy {
	private static final int BSIZE = 1024;
	
	public static void main(String[] args) throws IOException {
		FileChannel in = new FileInputStream("src/com/litao/basic/io/ChannelCopy.java").getChannel();
		FileChannel out = new FileOutputStream("data.txt").getChannel();
		ByteBuffer buffer = ByteBuffer.allocate(BSIZE);
		while(in.read(buffer) != -1) {
			buffer.flip(); // Prepare for writing
			out.write(buffer);
			buffer.clear(); // Prepare for reading
		}
		
		in.close();
		out.close();
	}

}
