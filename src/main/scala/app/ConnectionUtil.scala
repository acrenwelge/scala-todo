package app

import org.mongodb.scala.MongoDatabase
import java.{util => ju}
import java.io.FileReader
import org.mongodb.scala._

object ConnectionUtil {

    def getConnection: MongoDatabase = {
        val props: ju.Properties = new ju.Properties()
        props.load(new FileReader("src/main/resources/app.properties"))
        val uri = props.getProperty("db.connectionString")
        val client: MongoClient = MongoClient(uri)
        client.getDatabase("TodoApp")
    }
}