package chatroom

import java.nio.channels.SocketChannel

sealed trait Event
case class Login(sc: SocketChannel) extends Event
case class Logout(name: String) extends Event
case class Msg(msg: String) extends Event