package com.litao.basic.nio;

import java.nio.ByteBuffer;

public class ByteBuffers {

	public static void main(String[] args) {
		ByteBuffer buffer = ByteBuffer.allocate(10); // allocate an byte array with length 10 as ByteBuffer
		for (int i = 0; i < buffer.capacity(); i++) {
			buffer.put((byte) i); // call method put
		}

		buffer.position(3);
		buffer.limit(7);
		ByteBuffer slice = buffer.slice(); // get buffer slice, slice and buffer share the same memory
		for (int i = 0; i < slice.capacity(); i++) {
			slice.put(i, (byte) (slice.get(i) * 11));
		}

		buffer.position(0); // set buffer position
		buffer.limit(buffer.capacity()); // set buffer limit
		// traverse buffer
		while (buffer.hasRemaining()) {
			System.out.println(buffer.get()); // call method get
		}

		ByteBuffer newBuffer = buffer.duplicate().asReadOnlyBuffer(); // dulicate a new buffer, but it's read only
	}

}
