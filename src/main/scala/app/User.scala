package app

case class User(val username: String, val password: String, val todos: List[Todo]) {

    def login(uname: String, pw: String): Boolean = {
        if (uname.equals(this.username) && pw.equals(this.password)) true else false
    }
}