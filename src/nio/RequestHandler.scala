package nio

import java.nio.channels.SelectionKey
import java.nio.channels.Selector
import java.nio.channels.ServerSocketChannel
import java.nio.channels.SocketChannel
import java.nio.ByteBuffer
import scala.actors.Actor._
import scala.actors.Actor

class RequestHandler extends Handler {
  
  override def handle(sel: Selector, ssc: ServerSocketChannel, sk: SelectionKey): Unit = {
    if (!sk.isReadable()) return;
    
    actor {
      
    	var sc = sk.channel().asInstanceOf[SocketChannel];
    	
    	var bb = ByteBuffer.allocate(1024);
    	while (sc.read(bb) != -1) {
    		bb.flip();
    		sc.write(bb);
    		bb.clear();
    	}
    }
    
  }

}