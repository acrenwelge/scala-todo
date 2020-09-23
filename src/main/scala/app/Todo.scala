package app

class Todo(var description: String, var priority: Int) {
    // def description_= (newDescription: String): Unit = {
    //     if (newDescription.length() > 0) description = newDescription
    // }
    // def priority_= (newPriority: Int): Unit = {
    //     if (newPriority > 0) priority = newPriority
    // }
    override def toString(): String = {
        s"Todo => priority: $priority - description: $description"
    }
}