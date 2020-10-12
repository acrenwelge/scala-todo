package dao

import app.ConnectionUtil
import app.User
import org.mongodb.scala.model.Filters._
import org.mongodb.scala.bson.collection.immutable.Document
import org.mongodb.scala.SingleObservable
import app.Todo
import scala.collection.JavaConverters._
import java.{util => ju}
import org.mongodb.scala.Observable
import org.mongodb.scala.Completed
import org.mongodb.scala.Observer

sealed trait UserDAO {
    def insertUser(newUser: User)
    def getUser(username: String): SingleObservable[Document]
    def updateUser(userToUpdate: User)
    def deleteUser(username: String)
}

class UserMongo extends UserDAO {

    val collection = ConnectionUtil.getConnection.getCollection("Users")

    override def insertUser(newUser: User): Unit = {
        // DB should have unique constraint on "username" field to prevent duplicates
        val userDoc: Document = UserMongo.userToDoc(newUser)
        collection.insertOne(userDoc).subscribe(new Observer[Completed] {
          override def onNext(result: Completed): Unit = println(s"Result: ${result}")
          override def onError(e: Throwable): Unit = println(s"Error trying to add user ${newUser.username}: $e")
          override def onComplete(): Unit = println("onComplete")
        })
    }

    override def getUser(username: String): SingleObservable[Document] = {
        collection.find(equal("username", username)).first
    }

    override def updateUser(userToUpdate: User): Unit = {
        
    }
    
    override def deleteUser(username: String): Unit = {
        
    }
    
}

object UserMongo {
    def docToUser(userDoc: Document): User = {
        val username = userDoc.getString("username")
        val password = userDoc.getString("password")
        val todosArr = userDoc.get("todos").getOrElse(null).asArray()
        val todoList = new ju.ArrayList[Todo]
        todosArr.forEach(todoObj => {
            val doc = todoObj.asDocument()
            val descr = doc.get("description").asString().getValue()
            val priority = doc.get("priority").asInt32().getValue()
            val completed = doc.get("completed").asBoolean().getValue()
            todoList.add(new Todo(descr, priority, completed))
        })
        new User(username, password, todoList.asScala.toList)
    }

    def userToDoc(user: User) = Document(
            "username" -> user.username,
            "password" -> user.password,
            )
        // var todoArr: Seq[Document] = Seq()
        // user.todos.foreach((todo: Todo) => {
        //     val todoDoc = Document(
        //         "description" -> todo.description,
        //         "completed" -> todo.completed,
        //         "priority" -> todo.priority
        //     )
        //     todoArr = todoArr ++ todoDoc
        // })
        // userDoc += todoArr
}