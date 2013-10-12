package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class BNEchoServer {

	public static void main(String[] args) throws IOException {
		ServerSocketChannel ssc = ServerSocketChannel.open();
		ssc.socket().setReuseAddress(true);
		ssc.socket().bind(new InetSocketAddress(8000));
		
		for (;;) {
			final SocketChannel sc = ssc.accept();
			
			new Thread() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					super.run();
					
					try {
						ByteBuffer bb = ByteBuffer.allocate(1);
						while (sc.read(bb) != -1) {
							bb.flip();
							sc.write(bb);
							bb.clear();
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			}.start();
		}
	}
}
