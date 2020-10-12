package app

import com.typesafe.scalalogging.Logger
import org.mongodb.scala._
import dao.UserDAO
import dao.UserMongo
import java.{util => ju}

object Main extends App {
  val logger: Logger = Logger("main")
  logger.info("Starting up")
  val console = new ju.Scanner(System.in)
  var loginCount = 0
  var currentUser: Option[User] = Option.empty
  mainMenu

  def mainMenu() {
    while(true) {
      println("""
      --------------------
      SCALA - TODO - APP
      --------------------

      Commands:
      * login
      * logout
      * list
      * complete
      * exit
      """)
      val line = console.nextLine()
      val cmds = line.split(" ")
      cmds(0) match {
        case "login" => loginMenu()
        case "logout" => logout()
        case "list"  => list()
        case "exit"  => System.exit(0)
        case _ => System.err.println(s"\'${cmds(0)}\' is not a valid command")
      }
    }
  }

  def loginMenu() {
    println("Enter your username")
    val username = console.nextLine()
    println("Enter your password")
    val pw = console.nextLine()
    val userDao: UserDAO = new UserMongo
    userDao.getUser(username).subscribe((userDoc: Document) => {
      val user = UserMongo.docToUser(userDoc)
      currentUser = Option.apply(user)
      if (user.login(username, pw)) {
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
      System.err.println("please login first")
      mainMenu()
    } else {
      println("ALL TODOS:")
      currentUser.get.todos.foreach((todo: Todo) => {
      println(s"TODO: ${todo.description}")
    })
    }
  }

  def logout() {
    currentUser = Option.empty
  }
}