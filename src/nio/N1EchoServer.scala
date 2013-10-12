package nio

import java.nio.channels.ServerSocketChannel
import java.net.InetSocketAddress
import java.nio.channels.Selector
import java.nio.channels.SelectionKey
import scala.actors.Actor._

object N1EchoServer {

  def main(args: Array[String]) {
    var ssc = ServerSocketChannel.open();
    ssc.socket().setReuseAddress(true);
    ssc.socket().bind(new InetSocketAddress(8000));
    //non-blocking
    ssc.configureBlocking(false);
    
    var sel = Selector.open();
    ssc.register(sel, SelectionKey.OP_ACCEPT, new AcceptHandler());
    
    while (true) {
      sel.select();
      var iterator = sel.selectedKeys().iterator();
      while (iterator.hasNext()) {
        var key = iterator.next();
        iterator.remove();
        var handler = key.attachment().asInstanceOf[Handler];
        handler.handle(sel, ssc, key);
      }
    }
  }
}

