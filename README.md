# Scala - Todo App

This is an implementation of a console-based todo application in Scala. Features to use:
* `login {username} {password}` - login with username and password
* `logout`
* `list {regex}` - list your todos, passing an optional regular expression
  * `--tag {tag}` - filter by tag
  * `--priority {priority}` - filter by priority
* `complete {todoNum} {todoNum}` - mark the specified todos as complete
* `add {description}` - add a todo with the description
  * `--priority` - attach a priority
  * `--tag {tag}` - attach a tag

## Getting Started
* Add an `app.properties` file in `src/main/resources` and define:
  * `db.username`
  * `db.password`
  * `db.url`
  * `db.name` (name of the MongoDB cluster)
* Install `scala` and `sbt`
* Execute: `sbt run`

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
