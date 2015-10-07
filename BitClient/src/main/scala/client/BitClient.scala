//AUTHOR SIDDHANT DESHMUKH
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
object bitcoin extends App {

  /////////////////////////////////
  /////////////////////////////////
  //////////TAKE INPUT/////////
  ///////////////////////////////
  //////////////////////////////

  val zeros = readLine("Enter the number of preceeding zeros to compute on the client :")
  var z = zeros.toInt
  var start = ""
  for (j <- 1 to z) {
    start = start + "0"
  }

  val zerosremote = readLine("Enter the number of preceeding zeros to compute on the server :")
  var zremote = zerosremote.toInt
  var startremote = ""
  for (j <- 1 to zremote) {
    startremote = startremote + "0"
  }

  val number = (readLine("Enter the number of output strings wanted on the client :")).toInt
  val numberremote = (readLine("Enter the number of output strings wanted on the server :")).toInt

  var ip = readLine("Enter the IP address of server :")
  var port = readLine("Enter the port number of server :")

  /////////////////////////////////
  /////////////////////////////////
  //////////INITIALIZATION/////////
  ///////////////////////////////
  //////////////////////////////

  //Useful is class in package common that cointains shared functions between 
  //client and server.
  var u = new Useful

  // Create the Bitcoin Akka system
  val system = ActorSystem("BitcoinSystem")

  // create the owner, which will print the result and shutdown the system
  val Owner = system.actorOf(Props[Owner], name = "Owner")

  // create the master who supervises actors on client and server
  val master = system.actorOf(Props(new Master(Owner)), name = "master")

  // start MINING!
  master ! Mine(start)
  val starttime: Long = System.currentTimeMillis
  /////////////////////////////////
  /////////////////////////////////
  //////////MASTER////////////////
  ///////////////////////////////
  //////////////////////////////

  class Master(owner: ActorRef)
      extends Actor {
    val remote = context.actorFor("akka://BitServerSystem@" + ip + ":" + port + "/user/RemoteMaster")

    val workerRouter = context.actorOf(Props[Worker].withRouter(RoundRobinRouter(number)), name = "workerRouter")

    def receive = {
      case Mine(start) ⇒

        for (i <- 0 to (number - 1)) {

          workerRouter ! Work(start)
        }

        remote ! Remotemine(startremote, numberremote)

      case Result(arr) ⇒
        var t = (System.currentTimeMillis - starttime).millis

        owner ! MiningDone(arr, t)

      case Message(m) ⇒
        println("message received is :" + m)
    }

  }

  //////////////////////////////////
  /////////////////////////////////
  //////////WORKER////////////////
  ///////////////////////////////
  //////////////////////////////

  class Worker extends Actor {

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
      case Work(start) ⇒
        sender ! Result(startMining(start)) // perform the work
        self ! PoisonPill
    }

  }

  //////////////////////////////////
  /////////////////////////////////
  //////////LiISTENER////////////////
  ///////////////////////////////
  //////////////////////////////

  class Owner extends Actor {
    val remote = context.actorFor("akka://BitServerSystem@" + ip + ":" + port + "/user/RemoteMaster")
    var counter = 0 //number of desired coins

    def receive = {
      case MiningDone(arr, duration) ⇒

        counter += 1

        if (counter < number) {
          println("\nString number " + counter + " is:  " + arr(0) + "\nCorresponding Sha256 hash is: " + arr(1) + " \n")

        } else if (counter == number) {
          println("\nString number " + counter + " is:  " + arr(0) + "\nCorresponding Sha256 hash is: " + arr(1) + " \n")
          println("duration is :" + duration)
          context.stop(master)
          context.system.shutdown()
        }

    }
  }

} 


 










  
