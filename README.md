# Scala - Todo App

This is an implementation of a console-based todo application in Scala. To get started, make sure you have `scala` and `sbt` installed, then run `sbt run`.

Features to use:
* `login {username} {password}` - login with username and password
* `logout`
* `list {regex}` - list your todos, passing an optional regular expression
  * `--tag {tag}` - filter by tag
  * `--priority {priority}` - filter by priority
* `complete {todoNum} {todoNum}` - mark the specified todos as complete
* `add {description}` - add a todo with the description
  * `--priority` - attach a priority
  * `--tag {tag}` - attach a tag

## MongoDB Schema

```json
{
    "username": string,
    "password": string,
    "todos": [
        {
            "priority": Int32,
            "description":  string,
            "completed": false
        }
    ]
}
```