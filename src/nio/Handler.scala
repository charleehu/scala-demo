package nio

import java.nio.channels.SelectionKey
import java.nio.channels.ServerSocketChannel
import java.nio.channels.Selector

trait Handler {

  def handle(sel: Selector, ssc: ServerSocketChannel, sk: SelectionKey);
}