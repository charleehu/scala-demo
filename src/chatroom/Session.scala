package chatroom

import scala.actors.Actor
import java.nio.channels.SocketChannel
import java.nio.charset.Charset

class Session(name: String, sm: SessionManager, sc: SocketChannel) extends Actor {

  override def act {
    var running = true
    loopWhile(running) {
      react {
        case msg: String =>
          val charset = Charset.forName("utf8");
          val bb = charset.encode(msg);
          sc.write(bb);
        case Logout(name) =>
          running = false
      }
    }
  }
}