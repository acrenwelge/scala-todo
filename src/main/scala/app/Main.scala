package app

import com.typesafe.scalalogging.Logger
import org.mongodb.scala._
import dao.UserDAO
import dao.UserMongo
import java.{util => ju}
import scala.io.StdIn.readLine

object Main extends App {
  val logger: Logger = Logger("main")
  logger.info("Starting up")
  var loginCount = 0
  var currentUser: Option[User] = Option.empty
  val userDao: UserDAO = new UserMongo
  mainMenu

  def mainMenu() {
    while(true) {
      println("""
--------------------
 SCALA - TODO - APP
--------------------

Commands:
* register
* login
* logout
* list
* complete
* users
* exit
      """)
      val line = readLine()
      val cmds = line.split(" ")
      cmds(0) match {
        case "register" => register()
        case "login" => loginMenu()
        case "logout" => logout()
        case "list"  => list()
        case "users" => showAllUsers()
        case "exit"  => System.exit(0)
        case _ => System.err.println(s"\'${cmds(0)}\' is not a valid command")
      }
    }
  }

  def register() {
    println("Choose a username")
    val name = readLine()
    println("Choose a password")
    val pw = readLine()
    userDao.insertUser(new User(name, pw, List(new Todo("Complete me!",1,false))))
  }

  def loginMenu() {
    println("Enter your username")
    val username = readLine()
    println("Enter your password")
    val pw = readLine()
    userDao.getUser(username).subscribe((userDoc: Document) => {
      val user = UserMongo.docToUser(userDoc)
      currentUser = Option.apply(user)
      if (user.login(username, pw)) {
        loginCount = 0
        println(s"Welcome, ${user.username}")
        mainMenu()
      } else {
        loginCount += 1
        if (loginCount < 3) {
          System.err.println("Invalid credentials - try again")
          loginMenu()
        } else {
          System.err.println("Invalid credentials - exiting")
          System.exit(0)
        }
      }
    })
  }

  def list() {
    if (currentUser.isEmpty) {
      System.err.println("Please login first")
      mainMenu()
    } else {
      println("ALL TODOS:")
      currentUser.get.todos.foreach((todo: Todo) => {
        println(s"TODO: ${todo.description}")
      })
    }
  }

  def showAllUsers() {
    userDao.getAllUsers.subscribe(new Observer[Seq[Document]]() {
      def onNext(result: Seq[Document]): Unit = {
        result.foreach(doc => println(s"user: ${doc.get("username").get.asString().getValue()}"))
      }
      def onError(e: Throwable): Unit = {
        System.err.println("Unable to retrieve all users")
      }
      def onComplete(): Unit = None
    })
  }

  def logout() {
    println(s"Goodbye, ${currentUser.get.username}")
    currentUser = Option.empty
  }
}