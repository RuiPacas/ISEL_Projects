# Design Notes

This document contains a set of notes and recommendations to consider when designing the software organization for this semester's project.
Expect this list to grow during the semester.

* The execution of each command type should be _handled_ by a different _component_.
  * A possible design is to have an `CommandHandler` interface and then one implementing class per command.
  * It should be easy to add a new command without having to change multiple existing classes or methods.

* Use a _router_ to locate the proper handler, given a request's method and path.
  * This _router_ should not need to know all _handlers_ before hand. Instead, the _router_ should provide a way for the application to register new _handlers_, and the associated _method_ and _path template_.

* A _path_ and a _path template_ are not the same thing.

* Make sure non-managed resources used during the execution of a command, namely JDBC objects, are correctly closed at the end.
  * Ideally this responsability should be entrally handled by infrastructure code and not by code that is specific to each command.

* JDBC connections should not be shared between commands.

* To enable command transactional behaviour, namely atomicity of changes to the database, a single JDBC connection should be used for all DB interactions performed during a command execution.

* Avoid shared mutable state that could be accessed during the execution of multiple commands.
  * Namely, in the third phase, the project will be used in a multi-threading context, where multiple commands will be executed simultaneously.

* Do not spread user interface responsabilites (e.g. showing messages to the user) across all code base. Instead, this resposability should be delimited to a well defined set of components.

* Avoid using `String` to represent composite values.
  * For instance, if a request is represented by a _method_, a _path_, and _parameter_, create a class to represent this concept and avoid keep using a single `String` to represent it across the code base.
