package chatroom

import java.nio.channels.ServerSocketChannel
import java.net.InetSocketAddress
import java.nio.channels.Selector
import java.nio.channels.SelectionKey
import scala.actors.Actor

class ChatServer(port: Int) extends Actor {
  val ssc = ServerSocketChannel.open();
  ssc.socket().setReuseAddress(true);
  ssc.socket().bind(new InetSocketAddress(port));
  ssc.configureBlocking(false);
  
  val sel = Selector.open();
  
  val session = new SessionManager();
  
  override def act {
    ssc.register(sel, SelectionKey.OP_ACCEPT, new AcceptHandler());
    while (true) {
      sel.select();
      val iterator = sel.selectedKeys().iterator();
      while (iterator.hasNext()) {
        val sk = iterator.next();
        iterator.remove();
        sk.attachment().asInstanceOf[Handler].handle(this, sk);
      }
    }
  }
  
  def startup() {
    session.start;
    this.start;
  }
  
  def shutdown() {
    ssc.close();
    sel.close();
  }
}