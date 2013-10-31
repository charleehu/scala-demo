package chatroom

import java.nio.channels.SelectionKey
import java.nio.channels.Selector
import java.nio.channels.ServerSocketChannel
import java.nio.channels.SocketChannel
import java.nio.ByteBuffer
import scala.actors.Actor._
import scala.actors.Actor
import java.nio.charset.Charset

class RequestHandler extends Handler {
  
  override def handle(cs: ChatServer, sk: SelectionKey): Unit = {
    if (!sk.isReadable()) return;
    
    actor {
      
    	var sc = sk.channel().asInstanceOf[SocketChannel];
    	
    	
    	var charset = Charset.forName("utf8");
    	
    	var bb = ByteBuffer.allocate(1024);
    	if (sc.read(bb) != -1) {
    		bb.flip();
    		val msg = charset.decode(bb).toString();
    		
    		if (msg != "") 
    		  cs.session ! new Msg(msg);
    	}
    }
    
  }

}