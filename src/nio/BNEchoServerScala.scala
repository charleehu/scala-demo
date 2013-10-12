package nio

import java.nio.channels.ServerSocketChannel
import java.net.InetSocketAddress
import scala.actors.Actor._
import java.nio.channels.SocketChannel
import java.nio.ByteBuffer

object BNEchoServerScala {

  def main(args: Array[String]) {
    val ssc = ServerSocketChannel.open();
    ssc.socket().setReuseAddress(true);
    ssc.socket().bind(new InetSocketAddress(8000));
    
    //report actor
    val rpAct = actor {
      loopWhile (true) {
        reactWithin(1000) {
    	  case msg: String => println(msg);
    	}
      }
    }
    
    while (true) {
      val sc = ssc.accept();
      
      //service actor
      actor {
        rpAct ! echo(sc);
      }
    }
  }
  
  def echo(sc: SocketChannel) = {
    val bb = ByteBuffer.allocate(1024);
    while (sc.read(bb) != -1) {
      bb.flip();//prepare write
      sc.write(bb);
      bb.clear();
    }
    
    "client lose connect"
  }
}