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
		// �ڴ�ӳ���ļ� I/O ��һ�ֶ���д�ļ����ݵķ����������Աȳ���Ļ��������߻���ͨ���� I/O ��öࡣ
		// �ı�����ĵ���Ԫ�������ļ򵥲������Ϳ��ܻ�ֱ���޸Ĵ����ϵ��ļ����޸������뽫���ݱ��浽������û�зֿ���
		MappedByteBuffer mbb = fc.map(FileChannel.MapMode.READ_WRITE, start, size);
		mbb.put(0, (byte) 97);
		mbb.put(1023, (byte) 122);
		fc.close();

		System.out.println((char) mbb.get(0));
		System.out.println((char) mbb.get(1023));
	}

}
