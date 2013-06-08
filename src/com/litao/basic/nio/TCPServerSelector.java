package com.litao.basic.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;


public class TCPServerSelector {

	private static final int BUFSIZE = 256; // Buffer size (bytes)
	private static final int TIMEOUT = 30000; // Wait timeout (milliseconds)
	private static final int[] ports = { 8000, 8001, 8002 };

	public static void main(String[] args) throws IOException {
		// Create a selector to multiplex listening sockets and connections
		Selector selector = Selector.open();
		for (int port : ports) {
			ServerSocketChannel listnChannel = ServerSocketChannel.open();
			listnChannel.socket().bind(new InetSocketAddress(port));
			listnChannel.configureBlocking(false); // must be nonblocking to register Register selector with channel.
			// The returned key is ignored
			listnChannel.register(selector, SelectionKey.OP_ACCEPT);
		}

		// Create a handler that will implement the protocol
		TCPProtocol protocol = new TCPEchoSelectorProtocol(BUFSIZE);
		while (true) {
			// Run forever, processing available I/O operations
			// Wait for some channel to be ready (or timeout)
			if (selector.select(TIMEOUT) == 0) {
				// returns # of ready chans
				System.out.print(".");
				continue;
			}

			System.out.println("Before Channel List: ");
			for(SelectionKey key : selector.keys()) {
				System.out.println("    " + key.channel().toString());
			}
			
			System.out.println("Processing Channel List: ");
			// Get the set of keys with I/O to process
			for (SelectionKey key : selector.selectedKeys()) {
				// Server socket channel has pending connection requests?
				if (key.isAcceptable()) {
					System.out.println("    isAcceptable: " + key.channel().toString());
					protocol.handleAccept(key);
				}
				// Client socket channel has pending data?
				if (key.isReadable()) {
					System.out.println("    isReadable: " + key.channel().toString());
					protocol.handleRead(key);
				}
				// Client socket channel is available for writing and key is valid (i.e., channel not closed)?
				if (key.isValid() && key.isWritable()) {
					System.out.println("    isWritable: " + key.channel().toString());
					protocol.handleWrite(key);
				}

				selector.selectedKeys().remove(key); // remove from set of selected keys
			}
			
			System.out.println("After Channel List: ");
			for(SelectionKey key : selector.keys()) {
				System.out.println("    " + key.channel().toString());
			}
			System.out.println();
		}

	}

}
