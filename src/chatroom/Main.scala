package chatroom

import scala.actors.Actor._
object Main {

  def main(args: Array[String]) {
    val server = new ChatServer(8000);
    server.startup;
  }
}