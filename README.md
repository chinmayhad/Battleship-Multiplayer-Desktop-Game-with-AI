# Battleship MultiPlayer Desktop Game with AI
**SOEN-6441 Adv. Programming Practices - (Summer 2019)**

This is a electronic variant of the classic game **Battleship** published by Milton Bradley(1967).
The objective is to sink the opponent's fleet of five ships.

Unlike previous versions of Battleship, players have an optional **SALVO mode** that can hit multiple grid squares in one turn.

Our goals, while developing the game, would be code readability, maintainability and design a system confirms to software architecture standards.

(This was last updated for Java Code on July 10, 2019.)

## Artificial Intelligence for playing against computer:
Player can play against the computer. In the beginning of every match, player will be asked if they want to play
in single fire mode or SALVO mode, enabling player to hit multiple grid squares in one turn. 

For playing against computer, we have developed Artificial Intelligence. The computer learns the accuracy of human player's moves and accordingly plans
the further shots on human player's grid.

## MultiPlayer Support:
Player can play against another human player who is having this game set up on his/her computer. That computer has to be in the same network.


## We will try to follow following Coding Standards in our Project.

## 1. Code Formatting

* **1.1** Ensure that there is a newline at the end of every file.
* **1.2** Ensure that there is no trailing whitespace anywhere.
* **1.3** Do not place opening braces on new lines

```Java
class SomeClass {
    func someMethod() {
        if (x == y) {
            /* ... */
        } else if (x == z) {
            /* ... */
        } else {
            /* ... */
        }
    }

    /* ... */
}
```

* **1.4** When writing a type for a property, constant, variable, a key for a dictionary, a function argument, or a superclass, don't add a space before the colon.

```Java
// specifying type
let pirateClass: PirateClass

// dictionary syntax (note that we left-align as opposed to aligning colons)
let ninjaDictionary: [String: String] = [
    "Farmer": "false",
    "Cow": "true"
]

// calling a function
someFunction(someArgument: "Kitten")

// superclasses
class PirateClass: Object {
    /* ... */
}
```


## 2. Naming

* **2.1** Use `PascalCase` for type names (e.g. `struct`, `enum`, `class`, etc.).

* **2.2** Use `camelCase` (initial lowercase letter) for function, method, property, constant, variable, argument names, enum cases, etc.

* **2.3** When dealing with an acronym or other name that is usually written in all caps, actually use all caps in any names that use this in code. The exception is if this word is at the start of a name that needs to start with lowercase - in this case, use all lowercase for the acronym.

* **2.4** Names should be descriptive and unambiguous.

* **2.5** Do not abbreviate, use shortened names, or single letter names.

* **2.6** When naming function arguments, make sure that the function can be read easily to understand the purpose of each argument.


```Java
// "HTML" is at the start of a constant name, so we use lowercase "html"
String htmlBodyContent = "<p>Hello, World!</p>"
// Prefer using ID to Id
Int profileID = 1
// Prefer URLFinder to UrlFinder
class URLFinder {
    /* ... */
}
```

## 3. Documentation/Comments

### 3.1 Documentation

If a function complicated, consider adding a doc comment for the function since there could be some information that the method signature does not make immediately obvious.

Guidelines:

* **3.1.1** Even if the doc comment takes up one line, use block (`/** */`).

* **3.1.2** Do not prefix each additional line with a `*`.

* **3.1.3** For complicated classes, describe the usage of the class.

* **3.1.4** When mentioning code, use code ticks - \`

```Java
/**
 This does something with a `Class`, perchance.
 - warning: Make sure that `someValue` is `true` before running this function.
 */
 myFunction() {
    /* ... */
}
```


## 4. Architectural Design Patterns

#### We will try to follow following architectural design pattern

### 4.1 MVC

MVC Pattern stands for Model-View-Controller Pattern. This pattern is used to separate application's concerns.

* **Model** - Model represents an object or JAVA POJO carrying data. It can also have logic to update controller if its data changes.

* **View** - View represents the visualization of the data that model contains.

* **Controller** - Controller acts on both model and view. It controls the data flow into model object and updates the view whenever data changes. It keeps view and model separate.

