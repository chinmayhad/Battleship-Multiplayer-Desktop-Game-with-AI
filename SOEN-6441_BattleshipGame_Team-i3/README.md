# SOEN-6441-APP-Team-i3
SOEN-6441 Project - Battleship Game - (Summer 2019) By Team I3.

Our goals will be conciseness, readability, and simplicity.

This was last updated for Java on July 10, 2019.

### We will try to Follow following Coding Standards in our Project.

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

* **1.5** In general, there should be a space following a comma.

```Java
int[] myArray = {1, 2, 3, 4, 5}
```

* **1.6** There should be a space before and after a binary operator such as `+`, `==`, or `->`. There should also not be a space after a `(` and before a `)`.

```Java
int myValue = 20 + (30 / 2) * 3
if (1 + 1 == 3) {
    Error("Broken.")
}
```

* **1.7** When calling a function that has many parameters, put each argument on a separate line with a single extra indentation.

```Java
someFunctionWithManyArguments(
    oneArgument: "Hello, I am a string",
    twoArgument: true,
    threeArgument: 10)
```

## 2. Naming

* **2.1** Use `PascalCase` for type names (e.g. `struct`, `enum`, `class`, etc.).

* **2.2** Use `camelCase` (initial lowercase letter) for function, method, property, constant, variable, argument names, enum cases, etc.

* **2.3** When dealing with an acronym or other name that is usually written in all caps, actually use all caps in any names that use this in code. The exception is if this word is at the start of a name that needs to start with lowercase - in this case, use all lowercase for the acronym.

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
* **2.4** Names should be descriptive and unambiguous.

```Java
// PREFERRED
class RoundAnimatingButton: UIButton { /* ... */ }

// NOT PREFERRED
class CustomButton: UIButton { /* ... */ }
```

* **2.5** Do not abbreviate, use shortened names, or single letter names.

```Java
// PREFERRED
class RoundAnimatingButton: UIButton {
    let animationDuration: TimeInterval

    func startAnimating() {
        let firstSubview = subviews.first
    }

}

// NOT PREFERRED
class RoundAnimating: UIButton {
    let aniDur: TimeInterval

    func srtAnmating() {
        let v = subviews.first
    }
}
```
* **2.6** When naming function arguments, make sure that the function can be read easily to understand the purpose of each argument.

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

* **3.1.5** When writing doc comments, prefer brevity where possible.


## 4. Architectural Design Patterns

#### We will try to follow following architectural design pattern

### 4.1 MVC

MVC Pattern stands for Model-View-Controller Pattern. This pattern is used to separate application's concerns.

Model - Model represents an object or JAVA POJO carrying data. It can also have logic to update controller if its data changes.

View - View represents the visualization of the data that model contains.

Controller - Controller acts on both model and view. It controls the data flow into model object and updates the view whenever data changes. It keeps view and model separate.
