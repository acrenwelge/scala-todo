package app

import com.typesafe.scalalogging.Logger
import org.mongodb.scala._
import scala.concurrent.Await
import scala.concurrent.duration.Duration
import java.util.concurrent.TimeUnit
import org.slf4j.LoggerFactory
import java.{util => ju}
import java.io.FileReader

object Main extends App {
  val logger : Logger = Logger("main")
  logger.info("Starting up")
  println("Welcome to ScalaTodo. Please login")
  // println("Enter your username")
  // val sc = new java.util.Scanner(System.in)
  // val username = sc.nextLine()
  // println("Enter your password")
  // val pw = sc.nextLine();
  // val user = User(username, pw)
  // println(s"User info - username: ${user.username}; pw: ${user.password}")
  val props: ju.Properties = new ju.Properties()
  props.load(new FileReader("src/main/resources/app.properties"))
  val uri = props.getProperty("db.connectionString")
    // System.setProperty("org.mongodb.async.type", "netty")
  val client: MongoClient = MongoClient(uri)
  val db: MongoDatabase = client.getDatabase("TodoApp")
  // db.setLogLevel(5)
  val collection = db.getCollection("Users")
  // collection.find().collect().subscribe((results: Seq[Document]) => println(s"Found: #${results.size}"))

  // insert a document
  val document: Document = Document("_id" -> 1, "x" -> 1)
  val insertObservable: Observable[Completed] = collection.insertOne(document)

  insertObservable.subscribe(new Observer[Completed] {
    override def onNext(result: Completed): Unit = println(s"onNext: $result")
    override def onError(e: Throwable): Unit = println(s"onError: $e")
    override def onComplete(): Unit = println("onComplete")
  })

  collection.find().collect().subscribe((results: Seq[Document]) => println(s"Found: #${results.size}"))
  //client.close()
}
