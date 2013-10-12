package nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileDemo {

	public static void main(String[] args) throws IOException {
		try (
				FileInputStream fis = new FileInputStream("/Users/rrgame/Downloads/proxy.pac");
				FileOutputStream fos = new FileOutputStream("/Users/rrgame/Downloads/proxy.pac.copy")
			) {
			FileChannel fc1 = fis.getChannel();
			FileChannel fc2 = fos.getChannel();
			
			ByteBuffer bb = ByteBuffer.allocate(1024 * 4);
			while (fc1.read(bb) != -1) {
				bb.flip();
				fc2.write(bb);
				bb.clear();
			}
			fc1.close();
			fc2.close();
		}
		
	}
}
