package com.litao.basic.nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class MappedByteBuffers {
	private static final int start = 0;
	private static final int size = 1024;

	public static void main(String[] args) throws IOException {
		FileChannel fc = new RandomAccessFile("MappedFile.txt", "rw").getChannel();
		// 内存映射文件 I/O 是一种读和写文件数据的方法，它可以比常规的基于流或者基于通道的 I/O 快得多。
		// 改变数组的单个元素这样的简单操作，就可能会直接修改磁盘上的文件。修改数据与将数据保存到磁盘是没有分开的
		MappedByteBuffer mbb = fc.map(FileChannel.MapMode.READ_WRITE, start, size);
		mbb.put(0, (byte) 97);
		mbb.put(1023, (byte) 122);
		fc.close();

		System.out.println((char) mbb.get(0));
		System.out.println((char) mbb.get(1023));
	}

}
