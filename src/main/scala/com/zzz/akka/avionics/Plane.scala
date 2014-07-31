package com.zzz.akka.avionics

import akka.actor.{Props, ActorLogging, Actor}
import com.zzz.akka.avionics.EventSource.RegisterListener
import com.zzz.akka.avionics.Plane.GiveMeControl

/**
 * @author zhangh
 * @version $Id: 2014/7/30 15:58 
 */
object Plane {
  case object GiveMeControl

}

class Plane extends Actor with ActorLogging {
   import Altimeter._
  import Plane._
  val altimeter =context.actorOf(Props(Altimeter()),"Altimeter")
  val controls = context.actorOf(Props(new ControlSurfaces(altimeter)))

  override def preStart() {
    altimeter ! RegisterListener(self)
  }
  def receive = {
    case GiveMeControl =>
      log.info("Plane giving me contro")
      sender ! controls

    case AltitudeUpdate(altitude) =>
      log.info(s"Altitude is now:$altitude")
  }
}
