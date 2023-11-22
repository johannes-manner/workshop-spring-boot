# Plain JDBC, JpaRepositories, Transactions - How is all this related?

We have to start somewhere. I decided for the JpaRepositories. By default, every method within
a JpaRepository starts its own transaction.

Ok wait a minute - already confused: (1) Why should every method within the repositories start a new
transaction? (2) How does this look like in more detail? (3) When are the transactions committed?...

Foundation for Jakarta's/Spring's `@Transactional` is the JDBC API. Here some of the former question
will be already answered.
How would a Transaction look like in JDBC terms (again thanks to Marco Behler for has great
[tutorial](https://www.marcobehler.com/guides/spring-transaction-management-transactional-in-depth)):

```java
import java.sql.Connection;

//Oversimplified version of a JDBC transaction
Connection connection=dataSource.getConnection(); // (1)

    try(connection){
    connection.setAutoCommit(false); // (2)
    // execute some SQL statements...
    connection.commit(); // (3)

    }catch(SQLException e){
    connection.rollback(); // (4)
    }
```

So questions (2) and (3) are already answered.
You may ask yourself - why should I care?
The thing is the execution of `commit()` which finally saved all changed done to the DB.
And this also answers question (1) since the default behavior of repository methods is to read
consistent data, write and update data compliant to the ACID properties. So looking at
every `read`, `save` or `update` operation in isolation, this makes totally sense.

**BUT**: This results in a lot of accesses to the DB...

Ok all individual JpaRepository methods per default create their own transactional context
and commit. So the following code results in **numberOfMovies transactions** which are committed
one after another (leading to "inconsistent" data in a sense that the batch here is not a batch).

```java
  public void createFakeMovies(Integer numberOfMovies){
    long nextId=this.movieRepository.count()+1;
    for(int i=0;i<numberOfMovies; i++){
    movieRepository.save(...);
    }
    }
```

Execute the test `ConcurrencyTest.generateRandomData` with a big number of requests and
access your localhost
on [http://localhost:8080/v1/movies/count](http://localhost:8080/v1/movies/count)
while the test is running. You will see inconsistent data and quite a long test duration.

Restart your test (Run the test with 10 requests, that
is already sufficient). The output is as follows when you enable the logging of transactions in
the `application.properties`.

```agsl
2023-11-15T22:57:52.763+01:00 INFO 11488 --- [nio-8080-exec-1]
d.l.s.d.controller.MovieRESTController   : Try create a lot of fake movies Thread:
http-nio-8080-exec-1 Controller: de.lion5.spring.dvd.controller.MovieRESTController@6d351738
2023-11-15T22:57:52.768+01:00 TRACE 11488 --- [nio-8080-exec-1]
o.s.t.i.TransactionInterceptor           : Getting transaction
for [org.springframework.data.jpa.repository.support.SimpleJpaRepository.count]
2023-11-15T22:57:52.884+01:00 TRACE 11488 --- [nio-8080-exec-1]
o.s.t.i.TransactionInterceptor           : Completing transaction
for [org.springframework.data.jpa.repository.support.SimpleJpaRepository.count]
2023-11-15T22:57:52.885+01:00 TRACE 11488 --- [nio-8080-exec-1]
o.s.t.i.TransactionInterceptor           : Getting transaction
for [org.springframework.data.jpa.repository.support.SimpleJpaRepository.save]
2023-11-15T22:57:52.889+01:00 TRACE 11488 --- [nio-8080-exec-1]
o.s.t.i.TransactionInterceptor           : Completing transaction
for [org.springframework.data.jpa.repository.support.SimpleJpaRepository.save]
2023-11-15T22:57:52.918+01:00 TRACE 11488 --- [nio-8080-exec-1]
o.s.t.i.TransactionInterceptor           : Getting transaction
for [org.springframework.data.jpa.repository.support.SimpleJpaRepository.save]
2023-11-15T22:57:52.920+01:00 TRACE 11488 --- [nio-8080-exec-1]
o.s.t.i.TransactionInterceptor           : Completing transaction
for [org.springframework.data.jpa.repository.support.SimpleJpaRepository.save]
2023-11-15T22:57:52.922+01:00 TRACE 11488 --- [nio-8080-exec-1]
o.s.t.i.TransactionInterceptor           : Getting transaction
for [org.springframework.data.jpa.repository.support.SimpleJpaRepository.save]
2023-11-15T22:57:52.923+01:00 TRACE 11488 --- [nio-8080-exec-1]
o.s.t.i.TransactionInterceptor           : Completing transaction
for [org.springframework.data.jpa.repository.support.SimpleJpaRepository.save]
2023-11-15T22:57:52.927+01:00 TRACE 11488 --- [nio-8080-exec-1]
o.s.t.i.TransactionInterceptor           : Getting transaction
for [org.springframework.data.jpa.repository.support.SimpleJpaRepository.save]
2023-11-15T22:57:52.929+01:00 TRACE 11488 --- [nio-8080-exec-1]
o.s.t.i.TransactionInterceptor           : Completing transaction
for [org.springframework.data.jpa.repository.support.SimpleJpaRepository.save]
2023-11-15T22:57:52.932+01:00 TRACE 11488 --- [nio-8080-exec-1]
o.s.t.i.TransactionInterceptor           : Getting transaction
for [org.springframework.data.jpa.repository.support.SimpleJpaRepository.save]
2023-11-15T22:57:52.934+01:00 TRACE 11488 --- [nio-8080-exec-1]
o.s.t.i.TransactionInterceptor           : Completing transaction
for [org.springframework.data.jpa.repository.support.SimpleJpaRepository.save]
2023-11-15T22:57:52.936+01:00 TRACE 11488 --- [nio-8080-exec-1]
o.s.t.i.TransactionInterceptor           : Getting transaction
for [org.springframework.data.jpa.repository.support.SimpleJpaRepository.save]
2023-11-15T22:57:52.938+01:00 TRACE 11488 --- [nio-8080-exec-1]
o.s.t.i.TransactionInterceptor           : Completing transaction
for [org.springframework.data.jpa.repository.support.SimpleJpaRepository.save]
2023-11-15T22:57:52.940+01:00 TRACE 11488 --- [nio-8080-exec-1]
o.s.t.i.TransactionInterceptor           : Getting transaction
for [org.springframework.data.jpa.repository.support.SimpleJpaRepository.save]
2023-11-15T22:57:52.942+01:00 TRACE 11488 --- [nio-8080-exec-1]
o.s.t.i.TransactionInterceptor           : Completing transaction
for [org.springframework.data.jpa.repository.support.SimpleJpaRepository.save]
2023-11-15T22:57:52.944+01:00 TRACE 11488 --- [nio-8080-exec-1]
o.s.t.i.TransactionInterceptor           : Getting transaction
for [org.springframework.data.jpa.repository.support.SimpleJpaRepository.save]
2023-11-15T22:57:52.946+01:00 TRACE 11488 --- [nio-8080-exec-1]
o.s.t.i.TransactionInterceptor           : Completing transaction
for [org.springframework.data.jpa.repository.support.SimpleJpaRepository.save]
2023-11-15T22:57:52.948+01:00 TRACE 11488 --- [nio-8080-exec-1]
o.s.t.i.TransactionInterceptor           : Getting transaction
for [org.springframework.data.jpa.repository.support.SimpleJpaRepository.save]
2023-11-15T22:57:52.949+01:00 TRACE 11488 --- [nio-8080-exec-1]
o.s.t.i.TransactionInterceptor           : Completing transaction
for [org.springframework.data.jpa.repository.support.SimpleJpaRepository.save]
2023-11-15T22:57:52.953+01:00 TRACE 11488 --- [nio-8080-exec-1]
o.s.t.i.TransactionInterceptor           : Getting transaction
for [org.springframework.data.jpa.repository.support.SimpleJpaRepository.save]
2023-11-15T22:57:52.955+01:00 TRACE 11488 --- [nio-8080-exec-1]
o.s.t.i.TransactionInterceptor           : Completing transaction
for [org.springframework.data.jpa.repository.support.SimpleJpaRepository.save]
2023-11-15T22:57:52.959+01:00 INFO 11488 --- [nio-8080-exec-1]
d.l.s.d.controller.MovieRESTController   : Created fake movies Thread: http-nio-8080-exec-1
Controller: de.lion5.spring.dvd.controller.MovieRESTController@6d351738
2023-11-15T22:57:52.959+01:00 INFO 11488 --- [nio-8080-exec-1]
d.l.s.d.controller.MovieRESTController   : Response is generated :) Thread: http-nio-8080-exec-1
Controller: de.lion5.spring.dvd.controller.MovieRESTController@6d351738
```

Ok steps to resolve this auto-commit situation after each save operation.

1. Can we disable this default behavior that every JpaRepository method start its own transaction?
   Luckily yes :) In `PersistenceJPARepository` (a configuration class you have to include on
   purpose) we can change the flag `enableDefaultTransactions`
   to `false`.

```java

@Configuration
@EnableJpaRepositories(basePackages = {"de.lion5.spring.dvd.repository"},
    enableDefaultTransactions = false)
public class PersistenceJPARepository {

}
```

Rerun the test with 10 users.
You will get an exception: `TransactionRequiredException`

```agsl
2023-11-15T23:03:27.059+01:00  INFO 6860 --- [nio-8080-exec-1] d.l.s.d.controller.MovieRESTController   : Try create a lot of fake movies  Thread: http-nio-8080-exec-1 Controller: de.lion5.spring.dvd.controller.MovieRESTController@4add385d
2023-11-15T23:03:27.059+01:00 TRACE 6860 --- [nio-8080-exec-1] o.s.t.i.TransactionInterceptor           : No need to create transaction for [org.springframework.data.jpa.repository.support.SimpleJpaRepository.count]: This method is not transactional.
2023-11-15T23:03:27.175+01:00 TRACE 6860 --- [nio-8080-exec-1] o.s.t.i.TransactionInterceptor           : No need to create transaction for [org.springframework.data.jpa.repository.support.SimpleJpaRepository.save]: This method is not transactional.
2023-11-15T23:03:27.187+01:00 ERROR 6860 --- [nio-8080-exec-1] o.a.c.c.C.[.[.[/].[dispatcherServlet]    : Servlet.service() for servlet [dispatcherServlet] in context with path [] threw exception [Request processing failed: org.springframework.dao.InvalidDataAccessApiUsageException: No EntityManager with actual transaction available for current thread - cannot reliably process 'merge' call] with root cause

jakarta.persistence.TransactionRequiredException: No EntityManager with actual transaction available for current thread - cannot reliably process 'merge' call
	at org.springframework.orm.jpa.SharedEntityManagerCreator$SharedEntityManagerInvocationHandler.invoke(SharedEntityManagerCreator.java:295) ~[spring-orm-6.0.12.jar:6.0.12]
	at jdk.proxy4/jdk.proxy4.$Proxy122.merge(Unknown Source) ~[na:na]
```

Of course the auto enablement of transactions is gone (look at the
output `No need to create transaction`) and we did not specify a transactional context
on our own. But this exception is like the `LazyInitializationException` - it reminds us that we
have to configure an aspect of our database connectivity which might be not ideal in the default
case as shown above. So I would say this exception is positive :) (this also means that disabling
the default transaction creation could be a policy to comply with!!)

So a solution already discussed is the `@Transactional` annotation. It starts a transaction and
commits all the changes within the transaction context after the method exits.
When importing `@Transactional` use the Spring dependency.

```java
  @Transactional
public void createFakeMovies(Integer numberOfMovies){
    long nextId=this.movieRepository.count()+1;
    for(int i=0;i<numberOfMovies; i++){
    movieRepository.save(...);
    }
    }
```

Run the application and the test again:

```agsl
2023-11-15T23:10:43.045+01:00  INFO 15064 --- [nio-8080-exec-1] d.l.s.d.controller.MovieRESTController   : Try create a lot of fake movies  Thread: http-nio-8080-exec-1 Controller: de.lion5.spring.dvd.controller.MovieRESTController@69ba8881
2023-11-15T23:10:43.046+01:00 TRACE 15064 --- [nio-8080-exec-1] o.s.t.i.TransactionInterceptor           : Getting transaction for [de.lion5.spring.dvd.service.MovieService.createFakeMovies]
2023-11-15T23:10:43.046+01:00 TRACE 15064 --- [nio-8080-exec-1] o.s.t.i.TransactionInterceptor           : No need to create transaction for [org.springframework.data.jpa.repository.support.SimpleJpaRepository.count]: This method is not transactional.
2023-11-15T23:10:43.145+01:00 TRACE 15064 --- [nio-8080-exec-1] o.s.t.i.TransactionInterceptor           : No need to create transaction for [org.springframework.data.jpa.repository.support.SimpleJpaRepository.save]: This method is not transactional.
2023-11-15T23:10:43.149+01:00 TRACE 15064 --- [nio-8080-exec-1] o.s.t.i.TransactionInterceptor           : No need to create transaction for [org.springframework.data.jpa.repository.support.SimpleJpaRepository.save]: This method is not transactional.
2023-11-15T23:10:43.150+01:00 TRACE 15064 --- [nio-8080-exec-1] o.s.t.i.TransactionInterceptor           : No need to create transaction for [org.springframework.data.jpa.repository.support.SimpleJpaRepository.save]: This method is not transactional.
2023-11-15T23:10:43.152+01:00 TRACE 15064 --- [nio-8080-exec-1] o.s.t.i.TransactionInterceptor           : No need to create transaction for [org.springframework.data.jpa.repository.support.SimpleJpaRepository.save]: This method is not transactional.
2023-11-15T23:10:43.154+01:00 TRACE 15064 --- [nio-8080-exec-1] o.s.t.i.TransactionInterceptor           : No need to create transaction for [org.springframework.data.jpa.repository.support.SimpleJpaRepository.save]: This method is not transactional.
2023-11-15T23:10:43.156+01:00 TRACE 15064 --- [nio-8080-exec-1] o.s.t.i.TransactionInterceptor           : No need to create transaction for [org.springframework.data.jpa.repository.support.SimpleJpaRepository.save]: This method is not transactional.
2023-11-15T23:10:43.158+01:00 TRACE 15064 --- [nio-8080-exec-1] o.s.t.i.TransactionInterceptor           : No need to create transaction for [org.springframework.data.jpa.repository.support.SimpleJpaRepository.save]: This method is not transactional.
2023-11-15T23:10:43.160+01:00 TRACE 15064 --- [nio-8080-exec-1] o.s.t.i.TransactionInterceptor           : No need to create transaction for [org.springframework.data.jpa.repository.support.SimpleJpaRepository.save]: This method is not transactional.
2023-11-15T23:10:43.162+01:00 TRACE 15064 --- [nio-8080-exec-1] o.s.t.i.TransactionInterceptor           : No need to create transaction for [org.springframework.data.jpa.repository.support.SimpleJpaRepository.save]: This method is not transactional.
2023-11-15T23:10:43.163+01:00 TRACE 15064 --- [nio-8080-exec-1] o.s.t.i.TransactionInterceptor           : No need to create transaction for [org.springframework.data.jpa.repository.support.SimpleJpaRepository.save]: This method is not transactional.
2023-11-15T23:10:43.165+01:00 TRACE 15064 --- [nio-8080-exec-1] o.s.t.i.TransactionInterceptor           : Completing transaction for [de.lion5.spring.dvd.service.MovieService.createFakeMovies]
2023-11-15T23:10:43.215+01:00  INFO 15064 --- [nio-8080-exec-1] d.l.s.d.controller.MovieRESTController   : Created fake movies Thread: http-nio-8080-exec-1 Controller: de.lion5.spring.dvd.controller.MovieRESTController@69ba8881
2023-11-15T23:10:43.215+01:00  INFO 15064 --- [nio-8080-exec-1] d.l.s.d.controller.MovieRESTController   : Response is generated :) Thread: http-nio-8080-exec-1 Controller: de.lion5.spring.dvd.controller.MovieRESTController@69ba8881
```

A proper solution for our scenario making a batch update that the whole method acts as an ACID
transaction.

The last thing you can try is to have the `enableDefaultTransactions` set to `true` and use the
`@Transactional` also:

```agsl
2023-11-15T23:16:11.111+01:00  INFO 9372 --- [nio-8080-exec-1] d.l.s.d.controller.MovieRESTController   : Try create a lot of fake movies  Thread: http-nio-8080-exec-1 Controller: de.lion5.spring.dvd.controller.MovieRESTController@7a38deb5
2023-11-15T23:16:11.111+01:00 TRACE 9372 --- [nio-8080-exec-1] o.s.t.i.TransactionInterceptor           : Getting transaction for [de.lion5.spring.dvd.service.MovieService.createFakeMovies]
2023-11-15T23:16:11.113+01:00 TRACE 9372 --- [nio-8080-exec-1] o.s.t.i.TransactionInterceptor           : Getting transaction for [org.springframework.data.jpa.repository.support.SimpleJpaRepository.count]
2023-11-15T23:16:11.187+01:00 TRACE 9372 --- [nio-8080-exec-1] o.s.t.i.TransactionInterceptor           : Completing transaction for [org.springframework.data.jpa.repository.support.SimpleJpaRepository.count]
2023-11-15T23:16:11.187+01:00 TRACE 9372 --- [nio-8080-exec-1] o.s.t.i.TransactionInterceptor           : Getting transaction for [org.springframework.data.jpa.repository.support.SimpleJpaRepository.save]
2023-11-15T23:16:11.190+01:00 TRACE 9372 --- [nio-8080-exec-1] o.s.t.i.TransactionInterceptor           : Completing transaction for [org.springframework.data.jpa.repository.support.SimpleJpaRepository.save]
2023-11-15T23:16:11.191+01:00 TRACE 9372 --- [nio-8080-exec-1] o.s.t.i.TransactionInterceptor           : Getting transaction for [org.springframework.data.jpa.repository.support.SimpleJpaRepository.save]
2023-11-15T23:16:11.193+01:00 TRACE 9372 --- [nio-8080-exec-1] o.s.t.i.TransactionInterceptor           : Completing transaction for [org.springframework.data.jpa.repository.support.SimpleJpaRepository.save]
2023-11-15T23:16:11.193+01:00 TRACE 9372 --- [nio-8080-exec-1] o.s.t.i.TransactionInterceptor           : Getting transaction for [org.springframework.data.jpa.repository.support.SimpleJpaRepository.save]
2023-11-15T23:16:11.195+01:00 TRACE 9372 --- [nio-8080-exec-1] o.s.t.i.TransactionInterceptor           : Completing transaction for [org.springframework.data.jpa.repository.support.SimpleJpaRepository.save]
2023-11-15T23:16:11.195+01:00 TRACE 9372 --- [nio-8080-exec-1] o.s.t.i.TransactionInterceptor           : Getting transaction for [org.springframework.data.jpa.repository.support.SimpleJpaRepository.save]
2023-11-15T23:16:11.197+01:00 TRACE 9372 --- [nio-8080-exec-1] o.s.t.i.TransactionInterceptor           : Completing transaction for [org.springframework.data.jpa.repository.support.SimpleJpaRepository.save]
2023-11-15T23:16:11.198+01:00 TRACE 9372 --- [nio-8080-exec-1] o.s.t.i.TransactionInterceptor           : Getting transaction for [org.springframework.data.jpa.repository.support.SimpleJpaRepository.save]
2023-11-15T23:16:11.200+01:00 TRACE 9372 --- [nio-8080-exec-1] o.s.t.i.TransactionInterceptor           : Completing transaction for [org.springframework.data.jpa.repository.support.SimpleJpaRepository.save]
2023-11-15T23:16:11.200+01:00 TRACE 9372 --- [nio-8080-exec-1] o.s.t.i.TransactionInterceptor           : Getting transaction for [org.springframework.data.jpa.repository.support.SimpleJpaRepository.save]
2023-11-15T23:16:11.203+01:00 TRACE 9372 --- [nio-8080-exec-1] o.s.t.i.TransactionInterceptor           : Completing transaction for [org.springframework.data.jpa.repository.support.SimpleJpaRepository.save]
2023-11-15T23:16:11.203+01:00 TRACE 9372 --- [nio-8080-exec-1] o.s.t.i.TransactionInterceptor           : Getting transaction for [org.springframework.data.jpa.repository.support.SimpleJpaRepository.save]
2023-11-15T23:16:11.205+01:00 TRACE 9372 --- [nio-8080-exec-1] o.s.t.i.TransactionInterceptor           : Completing transaction for [org.springframework.data.jpa.repository.support.SimpleJpaRepository.save]
2023-11-15T23:16:11.205+01:00 TRACE 9372 --- [nio-8080-exec-1] o.s.t.i.TransactionInterceptor           : Getting transaction for [org.springframework.data.jpa.repository.support.SimpleJpaRepository.save]
2023-11-15T23:16:11.207+01:00 TRACE 9372 --- [nio-8080-exec-1] o.s.t.i.TransactionInterceptor           : Completing transaction for [org.springframework.data.jpa.repository.support.SimpleJpaRepository.save]
2023-11-15T23:16:11.208+01:00 TRACE 9372 --- [nio-8080-exec-1] o.s.t.i.TransactionInterceptor           : Getting transaction for [org.springframework.data.jpa.repository.support.SimpleJpaRepository.save]
2023-11-15T23:16:11.209+01:00 TRACE 9372 --- [nio-8080-exec-1] o.s.t.i.TransactionInterceptor           : Completing transaction for [org.springframework.data.jpa.repository.support.SimpleJpaRepository.save]
2023-11-15T23:16:11.210+01:00 TRACE 9372 --- [nio-8080-exec-1] o.s.t.i.TransactionInterceptor           : Getting transaction for [org.springframework.data.jpa.repository.support.SimpleJpaRepository.save]
2023-11-15T23:16:11.212+01:00 TRACE 9372 --- [nio-8080-exec-1] o.s.t.i.TransactionInterceptor           : Completing transaction for [org.springframework.data.jpa.repository.support.SimpleJpaRepository.save]
2023-11-15T23:16:11.212+01:00 TRACE 9372 --- [nio-8080-exec-1] o.s.t.i.TransactionInterceptor           : Completing transaction for [de.lion5.spring.dvd.service.MovieService.createFakeMovies]
2023-11-15T23:16:11.265+01:00  INFO 9372 --- [nio-8080-exec-1] d.l.s.d.controller.MovieRESTController   : Created fake movies Thread: http-nio-8080-exec-1 Controller: de.lion5.spring.dvd.controller.MovieRESTController@7a38deb5
2023-11-15T23:16:11.265+01:00  INFO 9372 --- [nio-8080-exec-1] d.l.s.d.controller.MovieRESTController   : Response is generated :) Thread: http-nio-8080-exec-1 Controller: de.lion5.spring.dvd.controller.MovieRESTController@7a38deb5
```

The transaction context of the `createFakeMovies` is reused for the (I want to call it) child `save`
operations.

This has an impact when the transaction is committed and flushed!
Performance-wise, you only use a single flush and save the overhead of many (in our case 10)
transaction overheads.
This can be seen when enabling the
`spring.jpa.properties.hibernate.generate_statistics=true` property.
The output you see is the following:

```agsl
2023-11-22T18:05:14.625+01:00  INFO 13340 --- [nio-8080-exec-1] i.StatisticalLoggingSessionEventListener : Session Metrics {
    47300 nanoseconds spent acquiring 1 JDBC connections;
    0 nanoseconds spent releasing 0 JDBC connections;
    391800 nanoseconds spent preparing 10 JDBC statements;
    2463800 nanoseconds spent executing 10 JDBC statements;
    0 nanoseconds spent executing 0 JDBC batches;
    0 nanoseconds spent performing 0 L2C puts;
    0 nanoseconds spent performing 0 L2C hits;
    0 nanoseconds spent performing 0 L2C misses;
    11649700 nanoseconds spent executing 1 flushes (flushing a total of 10 entities and 0 collections);
    0 nanoseconds spent executing 0 partial-flushes (flushing a total of 0 entities and 0 collections)
}
```

So we see that hibernate prepares 10 statement (the preparation of statements is also dependent on
the provider - not further discussed here), but we focus on the 10 executed statements.
Not batch processing we would like to see, so we have to make another configuration.
`spring.jpa.properties.hibernate.jdbc.batch_size=5` is to tell Hibernate to make batches of 5.
The output when enabling this in the `application.properties` is as follows:

```agsl
2023-11-22T18:11:00.092+01:00  INFO 8212 --- [nio-8080-exec-1] i.StatisticalLoggingSessionEventListener : Session Metrics {
    31300 nanoseconds spent acquiring 1 JDBC connections;
    0 nanoseconds spent releasing 0 JDBC connections;
    50500 nanoseconds spent preparing 1 JDBC statements;
    0 nanoseconds spent executing 0 JDBC statements;
    2151100 nanoseconds spent executing 2 JDBC batches;
    0 nanoseconds spent performing 0 L2C puts;
    0 nanoseconds spent performing 0 L2C hits;
    0 nanoseconds spent performing 0 L2C misses;
    12111000 nanoseconds spent executing 1 flushes (flushing a total of 10 entities and 0 collections);
    0 nanoseconds spent executing 0 partial-flushes (flushing a total of 0 entities and 0 collections)
}
```

We now see that 1 statement is prepared (as said dependent on the provider) and what is important
for us 2 batches are executed within a single flush where 10 entities are written to the db.

### Summary

Per default, each JpaRepository method starts its own, isolated transaction for every operation.
This can lead to inconsistent operations or inconsistencies when reading and updating data
within a single method. A recommendation is to disable this behavior as shown and use proper
transaction context. Additionally, when you want to perform batch processing, you have to specify
this!