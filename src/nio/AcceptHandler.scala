package nio

import java.nio.channels.ServerSocketChannel
import java.nio.channels.SelectionKey
import java.nio.channels.Selector
import scala.concurrent.ops

class AcceptHandler extends Handler {
  override def handle(sel: Selector, ssc: ServerSocketChannel, sk: SelectionKey) {
    if (!sk.isAcceptable()) return;

    var sc = ssc.accept();
    if (sc == null) return;
    
    sc.configureBlocking(false);
    sc.register(sel, SelectionKey.OP_READ, new RequestHandler());
  }
}