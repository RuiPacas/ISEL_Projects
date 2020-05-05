# JDBC

## Introduction

The [JDBC specification](https://download.oracle.com/otndocs/jcp/jdbc-4_2-mrel2-eval-spec/index.html) defines an interface for programmatic access to relational databases using the Java programming language.

Implementations of this interface are typically provided by RDBMS manufacturers, in the form of JAR files.
For example, the `sqljdbc4.jar` file, present in the `/vendor/main` folder, contains the JDBC implementation for interacting with SQL Server.

## Concepts

1. The `Connection` interface represents a connection to the RDBMS. All interactions with the RDBMS are done in the context of a `Connection` instance (i.e. an instance of a class that implements the `Connection` interface).

1. The `PreparedStatement` interface represents a statement (e.g. query, insertion, deletion) to perform in the context of a connection. 
`PreparedStatement` instances (i.e. instances of a class implementing the `PreparedStatement` interface) are obtained via `Connection` factory methods (e.g. [`Connection.prepareStatement`](https://docs.oracle.com/javase/7/docs/api/java/sql/Connection.html#prepareStatement(java.lang.String))).

1. The pt.isel.ls.result of executing a `PreparedStatement` (e.g. an SQL query) is typically a `ResultSet` instance, allowing programmatic access to a set of rows.


## 1. The `Connection` interface

The `Connection` interface represents a connection to the RDBMS
* All interactions with the RDBMS are done in the context of a `Connection` instance (i.e. an instance of a class that implements the `Connection` interface).
* For multiple interactions to participate in the same transaction, they need to be done in the context of the same `Connection` instance.
* A `Connection` instance uses non-managed resources on the Java application and on the RDBMS. So, special care must be taken to ensure these instances are properly closed when they aren't being used. Failure to do so tipically results on the RDBMS refusing the establishment of new connections, which serious consequences for the application availability.
  * Note that the management of connections should not be delegated to the JVM garbage collector. Instead, proper connection termination, via the `close` method`, should be explicitly performed.
* `Connection` instances are not thread-safe and must not be used simultaneously by more than one thread.

There are two ways to obtain `Connection` instances:
  * Using the `DriverManager.getConnection` static method, which will try to use a loaded `Driver`.
  * Using a concrete `DataSource` implementation. This is the recommended way to obtain connections, namely because it supports connection pooling, i.e., the ability to reuse connections without constantly paying the price to establish a new connection.

A `DataSource` instance exposes a `getConnection` method, so it can be used as a connection provider.
To obtain a `DataSource` instance:
* Directly create an instance of a class that implements the `DataSource` interface (e.g `new SQLServerDataSource()`).
* Set all required properties via _setter_  methods (e.g. `setURL(String)` and `setPassword(String)`).

A call to the `close` method of a connection will also close all statements objects created from that connection.

## 2. The `PreparedStatement` interface

The `PreparedStatement` interface represents a statement (e.g. query, insertion, deletion) to be performed in the context of a connection. `PreparedStatement` instances (i.e. instances of a class implementing the `PreparedStatement` interface) are obtained via `Connection` factory methods (e.g. [`Connection.prepareStatement`](https://docs.oracle.com/javase/7/docs/api/java/sql/Connection.html#prepareStatement(java.lang.String))).

A `PreparedStatement` **must** be used instead of a `Statement` everytime the query includes information that is not completely controlled by the application (e.g. information provided by the user) to avoid SQL-injection attacks.

Namely, values provided via the application interface (i.e. are not fully controlled by the application) **must never** be concatenated into a query.

The query string provided when a `PreparedStatement` is created (e.g. via `Connection.prepareStatement(String sql)`) can contain _parameter markers_, represented by the `?` character.
Before a `PreparedStament` is executed, it must receive the parameter value for all parameter markers, via the `setXxxx(int parameterIndex, ...)` methods.
The `parameterIndex` defines the position (starting at `1`) of the parameter to assign. 

For instance, the query `select name from Students where number=?` contains a marker on the `number` condition.
Setting the parameter with the value `12345` can be achieved by doing `ps.setInt(1, 12345);`.

The method `executeQuery` executes a statement and returns the `ResultSet` with the pt.isel.ls.result.

Closing a `PreparedStatement` object will close and invalidate any instances of `ResultSet` produced by that `PreparedStatement` object.

When inserting rows into tables using auto-generated values, it is possible to retrieve those values using the followings steps:

* Execute the insert using a `execute`/`executeUpdate` overload receiving a `autoGeneratedKeys` parameter set with `Statement.RETURN_GENERATED_KEYS`.
* Then, call `getGeneratedKeys` to obtain a pt.isel.ls.result set containing the generated keys.

## 3. The `ResultSet` interface

The `ResultSet` interface is used to represent a row set obtained as the pt.isel.ls.result of a query.

A `ResultSet` object maintains a cursor, which points to its current row.
The method `next()` moves the cursor to the next position, returning:
* `true` if the new position is still withing the bounds of the set.
* `false` if the new position is outside the bounds of the set, meaning that the end has been reached.

At the beginning, the cursor is positioned before the first row, so a call to `next()` must be performed before accessing any column.
If the first call to `next()` returns `false`, then the pt.isel.ls.result set is empty.

When the cursor is positioned on a valid row, then the row's columns can be accessed by one of two ways:
* Using a `getXxxx(int columnIndex)` method that receices the column index (starting at `1`).
* Using a `getXxxx(String columnLabel)`method that receives the column label.

The `Xxxx` defines the type (e.g. `getInt`) of the value to retrieve.
