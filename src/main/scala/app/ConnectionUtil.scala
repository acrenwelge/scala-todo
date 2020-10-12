package app

import org.mongodb.scala.MongoDatabase
import java.{util => ju}
import java.io.FileReader
import org.mongodb.scala._

object ConnectionUtil {

    def getConnection: MongoDatabase = {
        val props: ju.Properties = new ju.Properties()
        props.load(new FileReader("src/main/resources/app.properties"))
        val db_uname = props.getProperty("db.username")
        val db_pw = props.getProperty("db.password")
        val db_name = props.getProperty("db.name")
        val db_url = props.getProperty("db.url")
        val uri = s"mongodb+srv://$db_uname:$db_pw@$db_url/$db_name?retryWrites=true&w=majority"
        val client: MongoClient = MongoClient(uri)
        client.getDatabase("TodoApp")
    }
}