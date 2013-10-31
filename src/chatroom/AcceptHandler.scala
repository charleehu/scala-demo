package chatroom

import java.nio.channels.ServerSocketChannel
import java.nio.channels.SelectionKey
import java.nio.channels.Selector
import scala.concurrent.ops

class AcceptHandler extends Handler {
  override def handle(cs: ChatServer, sk: SelectionKey) {
    if (!sk.isAcceptable()) return;

    var sc = cs.ssc.accept();
    if (sc == null) return;
    
    sc.configureBlocking(false);
    sc.register(cs.sel, SelectionKey.OP_READ, new RequestHandler());
    
    cs.session ! new Login(sc);
  }
}