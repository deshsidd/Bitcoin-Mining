package server

import akka.actor._
import scala.util.Random
import scala.collection.immutable.StringOps
import akka.routing.RoundRobinRouter
import akka.routing.RandomRouter
import akka.routing.SmallestMailboxRouter
import common._
import scala.concurrent._
import scala.concurrent.duration._
import akka.actor.PoisonPill

object BitServer extends App {

  /////////////////////////////////
  /////////////////////////////////
  //////////INITIALIZATION/////////
  ///////////////////////////////
  //////////////////////////////

  // create Bit server system on server
  val system = ActorSystem("BitServerSystem")

  //create remote master
  val RemoteMaster = system.actorOf(Props[RemoteMaster], name = "RemoteMaster")

  //Useful is class in package common that cointains shared functions between 
  //client and server.
  var u = new Useful

  //////////////////////////////////
  /////////////////////////////////
  //////////REMOTE ACTOR////////////////
  ///////////////////////////////
  //////////////////////////////
  class RemoteMaster extends Actor {
    var counter = 0

    // get context of client owner to send results
    val local = context.actorFor("akka://BitcoinSystem@127.0.0.1:5150/user/Owner")

    def receive = {
      case Message(msg) =>
        println(s"RemoteActor received message '$msg'")

      case Remotemine(start, numberremote) =>

        // Create remote worker router
        val RemoteWorkerRouter = context.actorOf(Props[RemoteWorker].withRouter(RoundRobinRouter(numberremote)), name = "RemoteWorkerRouter")
        val starttime: Long = System.currentTimeMillis
        for (i <- 0 to (numberremote - 1)) {

          RemoteWorkerRouter ! RemoteWork(start, numberremote, starttime)
        }
      case RemoteResult(arr, numberremote, starttime) ⇒

        counter += 1
        println("\nString number " + counter + " is:  " + arr(0) + "\nCorresponding Sha256 hash is: " + arr(1) + " \n")

        if (counter == numberremote) {
          var duration = (System.currentTimeMillis - starttime).millis
          println("duration is :" + duration)

          context.system.shutdown()

        }

      case _ =>
        println("RemoteActor got something unexpected.")

    }
  }
  //////////////////////////////////
  /////////////////////////////////
  //////////REMOTE WORKER///////////////
  ///////////////////////////////
  //////////////////////////////

  class RemoteWorker extends Actor {

    def startMining(start: String): Array[String] =
      {
        var arr = new Array[String](2)
        var s = ""
        var sha = ""
        var ctr = 1
        while (!(sha.startsWith(start))) {
          s = u.generateString()
          sha = u.sha256(s)

          ctr += 1

        }
        arr(0) = s
        arr(1) = sha

        arr
      }

    def receive = {
      case RemoteWork(start, numberremote, starttime) ⇒
        sender ! RemoteResult(startMining(start), numberremote, starttime) // perform the work

    }

  }

}