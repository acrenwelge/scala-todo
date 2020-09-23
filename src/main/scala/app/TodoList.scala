package app

import java.util._

class TodoList {
    var todos: List[Todo] = new ArrayList[Todo]

    def addTodo(todo: Todo): Unit = {
        todos.add(todo)
    }
}