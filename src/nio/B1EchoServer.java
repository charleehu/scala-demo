package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class B1EchoServer {

	public static void main(String[] args) throws IOException, InterruptedException {
		ServerSocketChannel ssc = ServerSocketChannel.open();
		ssc.socket().setReuseAddress(true);
		ssc.socket().bind(new InetSocketAddress(8000));
		
		for (;;) {
			SocketChannel sc = ssc.accept();
			
			ByteBuffer bb = ByteBuffer.allocate(1024);
			while (sc.read(bb) != -1) {
				bb.flip();
				sc.write(bb);
				bb.clear();
			}
			
		}
	}
}
