package chatroom

import scala.actors.Actor
import scala.collection.immutable.HashMap

class SessionManager extends Actor {
  var sessions = new HashMap[String, Session]

  override def act {
    loopWhile(true) {
      react {
        case Login(sc) =>
          val name = "player" + sessions.size;
          val session = new Session(name, this, sc)
          session.start
          sessions += (name -> session)
          this ! new Msg("System: " + name + " login");
        case Logout(name) =>
          sessions(name) ! new Logout(name)
          sessions -= name
          this ! new Msg("System: " + name + " logout");
        case Msg(msg) =>
          println("get msg: " + msg)
          sessions.foreach(elm => elm._2 ! msg)
        case _ =>
          println("unknow event")
      }
    }
  }
}