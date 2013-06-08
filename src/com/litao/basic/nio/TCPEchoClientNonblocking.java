package com.litao.basic.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class TCPEchoClientNonblocking {

	public static void main(String[] args) throws IOException {
		String server = "127.0.0.1";
		int servPort = 8000;
		byte[] argument = args[0].getBytes();

		// Create channel and set to nonblocking
		SocketChannel clntChan = SocketChannel.open();
		// Initiate connection to server and repeatedly poll until complete
		if (!clntChan.connect(new InetSocketAddress(server, servPort))) {
			while (!clntChan.finishConnect()) {
				System.out.print("."); // Do something else
			}
		}
		ByteBuffer writeBuf = ByteBuffer.wrap(argument);
		ByteBuffer readBuf = ByteBuffer.allocate(argument.length);
		int totalBytesRcvd = 0; // Total bytes received so far
		int bytesRcvd; // Bytes received in last read
		while (totalBytesRcvd < argument.length) {
			if (writeBuf.hasRemaining()) {
				clntChan.write(writeBuf);
			}
			if ((bytesRcvd = clntChan.read(readBuf)) == -1) {
				throw new SocketException("Connection closed prematurely");
			}
			totalBytesRcvd += bytesRcvd;
			System.out.print("."); // Do something else
		}
		// convert to String per default charset
		System.out.println("Received: " + new String(readBuf.array(), 0, totalBytesRcvd));
		
		clntChan.write(ByteBuffer.wrap("nima".getBytes()));
		
		System.in.read();
		clntChan.close();
	}

}
