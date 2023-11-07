# JPA Issues - Open-Session in View (OSIV)

Caution: OSIV is enabled by default!! Disable it as soon as possible!!

Important when starting the JVM is to add VM parameters to turn on housekeeping of hikari at a
regular basis to see different behavior of OSIV, Transactions etc...
`-Dcom.zaxxer.hikari.housekeeping.periodMs=1000` to run the housekeeping every second.

When starting the JVM with the above mentioned command and waiting until the tomcat is started, we
will see the hikari pool messages.
Then you can execute the Concurrency Test and will get round about this output:

```agsl
2023-11-07T19:53:22.303+01:00  INFO 3880 --- [io-8080-exec-10] d.l.s.d.controller.MovieRESTController   : Try to access the DB  Thread: http-nio-8080-exec-10 Controller: de.lion5.spring.dvd.controller.MovieRESTController@47150bbf
2023-11-07T19:53:22.304+01:00  INFO 3880 --- [io-8080-exec-13] d.l.s.d.controller.MovieRESTController   : Try to access the DB  Thread: http-nio-8080-exec-13 Controller: de.lion5.spring.dvd.controller.MovieRESTController@47150bbf
2023-11-07T19:53:22.304+01:00  INFO 3880 --- [io-8080-exec-24] d.l.s.d.controller.MovieRESTController   : Try to access the DB  Thread: http-nio-8080-exec-24 Controller: de.lion5.spring.dvd.controller.MovieRESTController@47150bbf
2023-11-07T19:53:22.304+01:00  INFO 3880 --- [nio-8080-exec-1] d.l.s.d.controller.MovieRESTController   : Try to access the DB  Thread: http-nio-8080-exec-1 Controller: de.lion5.spring.dvd.controller.MovieRESTController@47150bbf
2023-11-07T19:53:22.304+01:00 DEBUG 3880 --- [nio-8080-exec-1] com.zaxxer.hikari.pool.HikariPool        : HikariPool-1 - Add connection elided, waiting=1, adders pending/running=2
2023-11-07T19:53:22.304+01:00  INFO 3880 --- [io-8080-exec-19] d.l.s.d.controller.MovieRESTController   : Try to access the DB  Thread: http-nio-8080-exec-19 Controller: de.lion5.spring.dvd.controller.MovieRESTController@47150bbf
2023-11-07T19:53:22.304+01:00 DEBUG 3880 --- [io-8080-exec-19] com.zaxxer.hikari.pool.HikariPool        : HikariPool-1 - Add connection elided, waiting=1, adders pending/running=2
2023-11-07T19:53:22.303+01:00  INFO 3880 --- [nio-8080-exec-3] d.l.s.d.controller.MovieRESTController   : Try to access the DB  Thread: http-nio-8080-exec-3 Controller: de.lion5.spring.dvd.controller.MovieRESTController@47150bbf
2023-11-07T19:53:22.303+01:00  INFO 3880 --- [io-8080-exec-32] d.l.s.d.controller.MovieRESTController   : Try to access the DB  Thread: http-nio-8080-exec-32 Controller: de.lion5.spring.dvd.controller.MovieRESTController@47150bbf
2023-11-07T19:53:22.307+01:00  INFO 3880 --- [io-8080-exec-33] d.l.s.d.controller.MovieRESTController   : Try to access the DB  Thread: http-nio-8080-exec-33 Controller: de.lion5.spring.dvd.controller.MovieRESTController@47150bbf
2023-11-07T19:53:22.308+01:00 DEBUG 3880 --- [io-8080-exec-33] com.zaxxer.hikari.pool.HikariPool        : HikariPool-1 - Add connection elided, waiting=2, adders pending/running=3
2023-11-07T19:53:22.309+01:00  INFO 3880 --- [io-8080-exec-10] d.l.s.d.controller.MovieRESTController   : My request to DB was executed Thread: http-nio-8080-exec-10 Controller: de.lion5.spring.dvd.controller.MovieRESTController@47150bbf
2023-11-07T19:53:22.303+01:00  INFO 3880 --- [io-8080-exec-16] d.l.s.d.controller.MovieRESTController   : Try to access the DB  Thread: http-nio-8080-exec-16 Controller: de.lion5.spring.dvd.controller.MovieRESTController@47150bbf
2023-11-07T19:53:22.310+01:00 DEBUG 3880 --- [io-8080-exec-16] com.zaxxer.hikari.pool.HikariPool        : HikariPool-1 - Add connection elided, waiting=2, adders pending/running=3
2023-11-07T19:53:22.312+01:00  INFO 3880 --- [io-8080-exec-24] d.l.s.d.controller.MovieRESTController   : My request to DB was executed Thread: http-nio-8080-exec-24 Controller: de.lion5.spring.dvd.controller.MovieRESTController@47150bbf
2023-11-07T19:53:22.314+01:00  INFO 3880 --- [io-8080-exec-32] d.l.s.d.controller.MovieRESTController   : My request to DB was executed Thread: http-nio-8080-exec-32 Controller: de.lion5.spring.dvd.controller.MovieRESTController@47150bbf
2023-11-07T19:53:22.307+01:00  INFO 3880 --- [io-8080-exec-28] d.l.s.d.controller.MovieRESTController   : Try to access the DB  Thread: http-nio-8080-exec-28 Controller: de.lion5.spring.dvd.controller.MovieRESTController@47150bbf
2023-11-07T19:53:22.315+01:00 DEBUG 3880 --- [io-8080-exec-28] com.zaxxer.hikari.pool.HikariPool        : HikariPool-1 - Add connection elided, waiting=2, adders pending/running=3
2023-11-07T19:53:22.315+01:00  INFO 3880 --- [io-8080-exec-33] d.l.s.d.controller.MovieRESTController   : My request to DB was executed Thread: http-nio-8080-exec-33 Controller: de.lion5.spring.dvd.controller.MovieRESTController@47150bbf
2023-11-07T19:53:22.316+01:00  INFO 3880 --- [io-8080-exec-34] d.l.s.d.controller.MovieRESTController   : Try to access the DB  Thread: http-nio-8080-exec-34 Controller: de.lion5.spring.dvd.controller.MovieRESTController@47150bbf
2023-11-07T19:53:22.316+01:00  INFO 3880 --- [io-8080-exec-23] d.l.s.d.controller.MovieRESTController   : Try to access the DB  Thread: http-nio-8080-exec-23 Controller: de.lion5.spring.dvd.controller.MovieRESTController@47150bbf
2023-11-07T19:53:22.307+01:00 DEBUG 3880 --- [nio-8080-exec-3] com.zaxxer.hikari.pool.HikariPool        : HikariPool-1 - Add connection elided, waiting=1, adders pending/running=2
2023-11-07T19:53:22.317+01:00  INFO 3880 --- [io-8080-exec-16] d.l.s.d.controller.MovieRESTController   : My request to DB was executed Thread: http-nio-8080-exec-16 Controller: de.lion5.spring.dvd.controller.MovieRESTController@47150bbf
2023-11-07T19:53:22.317+01:00  INFO 3880 --- [io-8080-exec-20] d.l.s.d.controller.MovieRESTController   : Try to access the DB  Thread: http-nio-8080-exec-20 Controller: de.lion5.spring.dvd.controller.MovieRESTController@47150bbf
2023-11-07T19:53:22.318+01:00 DEBUG 3880 --- [io-8080-exec-20] com.zaxxer.hikari.pool.HikariPool        : HikariPool-1 - Add connection elided, waiting=4, adders pending/running=5
2023-11-07T19:53:22.318+01:00  INFO 3880 --- [nio-8080-exec-7] d.l.s.d.controller.MovieRESTController   : Try to access the DB  Thread: http-nio-8080-exec-7 Controller: de.lion5.spring.dvd.controller.MovieRESTController@47150bbf
2023-11-07T19:53:22.316+01:00  INFO 3880 --- [io-8080-exec-19] d.l.s.d.controller.MovieRESTController   : My request to DB was executed Thread: http-nio-8080-exec-19 Controller: de.lion5.spring.dvd.controller.MovieRESTController@47150bbf
2023-11-07T19:53:22.318+01:00  INFO 3880 --- [io-8080-exec-25] d.l.s.d.controller.MovieRESTController   : Try to access the DB  Thread: http-nio-8080-exec-25 Controller: de.lion5.spring.dvd.controller.MovieRESTController@47150bbf
2023-11-07T19:53:22.319+01:00  INFO 3880 --- [io-8080-exec-30] d.l.s.d.controller.MovieRESTController   : Try to access the DB  Thread: http-nio-8080-exec-30 Controller: de.lion5.spring.dvd.controller.MovieRESTController@47150bbf
2023-11-07T19:53:22.319+01:00  INFO 3880 --- [nio-8080-exec-2] d.l.s.d.controller.MovieRESTController   : Try to access the DB  Thread: http-nio-8080-exec-2 Controller: de.lion5.spring.dvd.controller.MovieRESTController@47150bbf
2023-11-07T19:53:22.319+01:00  INFO 3880 --- [nio-8080-exec-8] d.l.s.d.controller.MovieRESTController   : Try to access the DB  Thread: http-nio-8080-exec-8 Controller: de.lion5.spring.dvd.controller.MovieRESTController@47150bbf
2023-11-07T19:53:22.320+01:00  INFO 3880 --- [nio-8080-exec-4] d.l.s.d.controller.MovieRESTController   : Try to access the DB  Thread: http-nio-8080-exec-4 Controller: de.lion5.spring.dvd.controller.MovieRESTController@47150bbf
2023-11-07T19:53:22.320+01:00  INFO 3880 --- [nio-8080-exec-1] d.l.s.d.controller.MovieRESTController   : My request to DB was executed Thread: http-nio-8080-exec-1 Controller: de.lion5.spring.dvd.controller.MovieRESTController@47150bbf
2023-11-07T19:53:22.320+01:00  INFO 3880 --- [io-8080-exec-15] d.l.s.d.controller.MovieRESTController   : Try to access the DB  Thread: http-nio-8080-exec-15 Controller: de.lion5.spring.dvd.controller.MovieRESTController@47150bbf
2023-11-07T19:53:22.320+01:00  INFO 3880 --- [nio-8080-exec-9] d.l.s.d.controller.MovieRESTController   : Try to access the DB  Thread: http-nio-8080-exec-9 Controller: de.lion5.spring.dvd.controller.MovieRESTController@47150bbf
2023-11-07T19:53:22.321+01:00  INFO 3880 --- [io-8080-exec-17] d.l.s.d.controller.MovieRESTController   : Try to access the DB  Thread: http-nio-8080-exec-17 Controller: de.lion5.spring.dvd.controller.MovieRESTController@47150bbf
2023-11-07T19:53:22.321+01:00  INFO 3880 --- [io-8080-exec-18] d.l.s.d.controller.MovieRESTController   : Try to access the DB  Thread: http-nio-8080-exec-18 Controller: de.lion5.spring.dvd.controller.MovieRESTController@47150bbf
2023-11-07T19:53:22.321+01:00  INFO 3880 --- [io-8080-exec-21] d.l.s.d.controller.MovieRESTController   : Try to access the DB  Thread: http-nio-8080-exec-21 Controller: de.lion5.spring.dvd.controller.MovieRESTController@47150bbf
2023-11-07T19:53:22.321+01:00  INFO 3880 --- [nio-8080-exec-6] d.l.s.d.controller.MovieRESTController   : Try to access the DB  Thread: http-nio-8080-exec-6 Controller: de.lion5.spring.dvd.controller.MovieRESTController@47150bbf
2023-11-07T19:53:22.321+01:00  INFO 3880 --- [io-8080-exec-12] d.l.s.d.controller.MovieRESTController   : Try to access the DB  Thread: http-nio-8080-exec-12 Controller: de.lion5.spring.dvd.controller.MovieRESTController@47150bbf
2023-11-07T19:53:22.321+01:00  INFO 3880 --- [io-8080-exec-22] d.l.s.d.controller.MovieRESTController   : Try to access the DB  Thread: http-nio-8080-exec-22 Controller: de.lion5.spring.dvd.controller.MovieRESTController@47150bbf
2023-11-07T19:53:22.321+01:00  INFO 3880 --- [io-8080-exec-35] d.l.s.d.controller.MovieRESTController   : Try to access the DB  Thread: http-nio-8080-exec-35 Controller: de.lion5.spring.dvd.controller.MovieRESTController@47150bbf
2023-11-07T19:53:22.321+01:00  INFO 3880 --- [io-8080-exec-14] d.l.s.d.controller.MovieRESTController   : Try to access the DB  Thread: http-nio-8080-exec-14 Controller: de.lion5.spring.dvd.controller.MovieRESTController@47150bbf
2023-11-07T19:53:22.322+01:00  INFO 3880 --- [nio-8080-exec-5] d.l.s.d.controller.MovieRESTController   : Try to access the DB  Thread: http-nio-8080-exec-5 Controller: de.lion5.spring.dvd.controller.MovieRESTController@47150bbf
2023-11-07T19:53:22.322+01:00  INFO 3880 --- [io-8080-exec-27] d.l.s.d.controller.MovieRESTController   : Try to access the DB  Thread: http-nio-8080-exec-27 Controller: de.lion5.spring.dvd.controller.MovieRESTController@47150bbf
2023-11-07T19:53:22.322+01:00  INFO 3880 --- [io-8080-exec-11] d.l.s.d.controller.MovieRESTController   : Try to access the DB  Thread: http-nio-8080-exec-11 Controller: de.lion5.spring.dvd.controller.MovieRESTController@47150bbf
2023-11-07T19:53:22.322+01:00  INFO 3880 --- [io-8080-exec-31] d.l.s.d.controller.MovieRESTController   : Try to access the DB  Thread: http-nio-8080-exec-31 Controller: de.lion5.spring.dvd.controller.MovieRESTController@47150bbf
2023-11-07T19:53:22.322+01:00  INFO 3880 --- [io-8080-exec-29] d.l.s.d.controller.MovieRESTController   : Try to access the DB  Thread: http-nio-8080-exec-29 Controller: de.lion5.spring.dvd.controller.MovieRESTController@47150bbf
2023-11-07T19:53:22.323+01:00  INFO 3880 --- [io-8080-exec-28] d.l.s.d.controller.MovieRESTController   : My request to DB was executed Thread: http-nio-8080-exec-28 Controller: de.lion5.spring.dvd.controller.MovieRESTController@47150bbf
2023-11-07T19:53:22.325+01:00  INFO 3880 --- [io-8080-exec-26] d.l.s.d.controller.MovieRESTController   : Try to access the DB  Thread: http-nio-8080-exec-26 Controller: de.lion5.spring.dvd.controller.MovieRESTController@47150bbf
2023-11-07T19:53:22.325+01:00  INFO 3880 --- [nio-8080-exec-3] d.l.s.d.controller.MovieRESTController   : My request to DB was executed Thread: http-nio-8080-exec-3 Controller: de.lion5.spring.dvd.controller.MovieRESTController@47150bbf
2023-11-07T19:53:22.332+01:00  INFO 3880 --- [io-8080-exec-13] d.l.s.d.controller.MovieRESTController   : My request to DB was executed Thread: http-nio-8080-exec-13 Controller: de.lion5.spring.dvd.controller.MovieRESTController@47150bbf
2023-11-07T19:53:23.053+01:00 DEBUG 3880 --- [l-1 housekeeper] com.zaxxer.hikari.pool.HikariPool        : HikariPool-1 - Pool stats (total=10, active=10, idle=0, waiting=25)
2023-11-07T19:53:23.053+01:00 DEBUG 3880 --- [l-1 housekeeper] com.zaxxer.hikari.pool.HikariPool        : HikariPool-1 - Fill pool skipped, pool has sufficient level or currently being filled (queueDepth=0).
2023-11-07T19:53:24.062+01:00 DEBUG 3880 --- [l-1 housekeeper] com.zaxxer.hikari.pool.HikariPool        : HikariPool-1 - Pool stats (total=10, active=10, idle=0, waiting=25)
2023-11-07T19:53:24.062+01:00 DEBUG 3880 --- [l-1 housekeeper] com.zaxxer.hikari.pool.HikariPool        : HikariPool-1 - Fill pool skipped, pool has sufficient level or currently being filled (queueDepth=0).
2023-11-07T19:53:25.077+01:00 DEBUG 3880 --- [l-1 housekeeper] com.zaxxer.hikari.pool.HikariPool        : HikariPool-1 - Pool stats (total=10, active=10, idle=0, waiting=25)
2023-11-07T19:53:25.077+01:00 DEBUG 3880 --- [l-1 housekeeper] com.zaxxer.hikari.pool.HikariPool        : HikariPool-1 - Fill pool skipped, pool has sufficient level or currently being filled (queueDepth=0).
2023-11-07T19:53:26.087+01:00 DEBUG 3880 --- [l-1 housekeeper] com.zaxxer.hikari.pool.HikariPool        : HikariPool-1 - Pool stats (total=10, active=10, idle=0, waiting=25)
2023-11-07T19:53:26.087+01:00 DEBUG 3880 --- [l-1 housekeeper] com.zaxxer.hikari.pool.HikariPool        : HikariPool-1 - Fill pool skipped, pool has sufficient level or currently being filled (queueDepth=0).
2023-11-07T19:53:27.103+01:00 DEBUG 3880 --- [l-1 housekeeper] com.zaxxer.hikari.pool.HikariPool        : HikariPool-1 - Pool stats (total=10, active=10, idle=0, waiting=25)
2023-11-07T19:53:27.103+01:00 DEBUG 3880 --- [l-1 housekeeper] com.zaxxer.hikari.pool.HikariPool        : HikariPool-1 - Fill pool skipped, pool has sufficient level or currently being filled (queueDepth=0).
2023-11-07T19:53:28.114+01:00 DEBUG 3880 --- [l-1 housekeeper] com.zaxxer.hikari.pool.HikariPool        : HikariPool-1 - Pool stats (total=10, active=10, idle=0, waiting=25)
2023-11-07T19:53:28.114+01:00 DEBUG 3880 --- [l-1 housekeeper] com.zaxxer.hikari.pool.HikariPool        : HikariPool-1 - Fill pool skipped, pool has sufficient level or currently being filled (queueDepth=0).
2023-11-07T19:53:29.117+01:00 DEBUG 3880 --- [l-1 housekeeper] com.zaxxer.hikari.pool.HikariPool        : HikariPool-1 - Pool stats (total=10, active=10, idle=0, waiting=25)
2023-11-07T19:53:29.117+01:00 DEBUG 3880 --- [l-1 housekeeper] com.zaxxer.hikari.pool.HikariPool        : HikariPool-1 - Fill pool skipped, pool has sufficient level or currently being filled (queueDepth=0).
2023-11-07T19:53:30.125+01:00 DEBUG 3880 --- [l-1 housekeeper] com.zaxxer.hikari.pool.HikariPool        : HikariPool-1 - Pool stats (total=10, active=10, idle=0, waiting=25)
2023-11-07T19:53:30.125+01:00 DEBUG 3880 --- [l-1 housekeeper] com.zaxxer.hikari.pool.HikariPool        : HikariPool-1 - Fill pool skipped, pool has sufficient level or currently being filled (queueDepth=0).
2023-11-07T19:53:31.139+01:00 DEBUG 3880 --- [l-1 housekeeper] com.zaxxer.hikari.pool.HikariPool        : HikariPool-1 - Pool stats (total=10, active=10, idle=0, waiting=25)
2023-11-07T19:53:31.139+01:00 DEBUG 3880 --- [l-1 housekeeper] com.zaxxer.hikari.pool.HikariPool        : HikariPool-1 - Fill pool skipped, pool has sufficient level or currently being filled (queueDepth=0).
2023-11-07T19:53:32.143+01:00 DEBUG 3880 --- [l-1 housekeeper] com.zaxxer.hikari.pool.HikariPool        : HikariPool-1 - Pool stats (total=10, active=10, idle=0, waiting=25)
2023-11-07T19:53:32.143+01:00 DEBUG 3880 --- [l-1 housekeeper] com.zaxxer.hikari.pool.HikariPool        : HikariPool-1 - Fill pool skipped, pool has sufficient level or currently being filled (queueDepth=0).
2023-11-07T19:53:32.312+01:00  INFO 3880 --- [io-8080-exec-10] d.l.s.d.controller.MovieRESTController   : Response is generated :) Thread: http-nio-8080-exec-10 Controller: de.lion5.spring.dvd.controller.MovieRESTController@47150bbf
2023-11-07T19:53:32.313+01:00  INFO 3880 --- [io-8080-exec-24] d.l.s.d.controller.MovieRESTController   : Response is generated :) Thread: http-nio-8080-exec-24 Controller: de.lion5.spring.dvd.controller.MovieRESTController@47150bbf
2023-11-07T19:53:32.332+01:00  INFO 3880 --- [io-8080-exec-33] d.l.s.d.controller.MovieRESTController   : Response is generated :) Thread: http-nio-8080-exec-33 Controller: de.lion5.spring.dvd.controller.MovieRESTController@47150bbf
2023-11-07T19:53:32.338+01:00  INFO 3880 --- [io-8080-exec-32] d.l.s.d.controller.MovieRESTController   : Response is generated :) Thread: http-nio-8080-exec-32 Controller: de.lion5.spring.dvd.controller.MovieRESTController@47150bbf
2023-11-07T19:53:32.339+01:00  INFO 3880 --- [io-8080-exec-19] d.l.s.d.controller.MovieRESTController   : Response is generated :) Thread: http-nio-8080-exec-19 Controller: de.lion5.spring.dvd.controller.MovieRESTController@47150bbf
2023-11-07T19:53:32.342+01:00  INFO 3880 --- [io-8080-exec-16] d.l.s.d.controller.MovieRESTController   : Response is generated :) Thread: http-nio-8080-exec-16 Controller: de.lion5.spring.dvd.controller.MovieRESTController@47150bbf
2023-11-07T19:53:32.343+01:00  INFO 3880 --- [io-8080-exec-13] d.l.s.d.controller.MovieRESTController   : Response is generated :) Thread: http-nio-8080-exec-13 Controller: de.lion5.spring.dvd.controller.MovieRESTController@47150bbf
2023-11-07T19:53:32.344+01:00  INFO 3880 --- [nio-8080-exec-3] d.l.s.d.controller.MovieRESTController   : Response is generated :) Thread: http-nio-8080-exec-3 Controller: de.lion5.spring.dvd.controller.MovieRESTController@47150bbf
2023-11-07T19:53:32.346+01:00  INFO 3880 --- [io-8080-exec-28] d.l.s.d.controller.MovieRESTController   : Response is generated :) Thread: http-nio-8080-exec-28 Controller: de.lion5.spring.dvd.controller.MovieRESTController@47150bbf
2023-11-07T19:53:32.350+01:00  INFO 3880 --- [nio-8080-exec-1] d.l.s.d.controller.MovieRESTController   : Response is generated :) Thread: http-nio-8080-exec-1 Controller: de.lion5.spring.dvd.controller.MovieRESTController@47150bbf
2023-11-07T19:53:32.497+01:00  INFO 3880 --- [io-8080-exec-23] d.l.s.d.controller.MovieRESTController   : My request to DB was executed Thread: http-nio-8080-exec-23 Controller: de.lion5.spring.dvd.controller.MovieRESTController@47150bbf
2023-11-07T19:53:32.507+01:00  INFO 3880 --- [io-8080-exec-20] d.l.s.d.controller.MovieRESTController   : My request to DB was executed Thread: http-nio-8080-exec-20 Controller: de.lion5.spring.dvd.controller.MovieRESTController@47150bbf
2023-11-07T19:53:32.510+01:00  INFO 3880 --- [io-8080-exec-34] d.l.s.d.controller.MovieRESTController   : My request to DB was executed Thread: http-nio-8080-exec-34 Controller: de.lion5.spring.dvd.controller.MovieRESTController@47150bbf
2023-11-07T19:53:32.519+01:00  INFO 3880 --- [io-8080-exec-25] d.l.s.d.controller.MovieRESTController   : My request to DB was executed Thread: http-nio-8080-exec-25 Controller: de.lion5.spring.dvd.controller.MovieRESTController@47150bbf
2023-11-07T19:53:32.521+01:00  INFO 3880 --- [nio-8080-exec-7] d.l.s.d.controller.MovieRESTController   : My request to DB was executed Thread: http-nio-8080-exec-7 Controller: de.lion5.spring.dvd.controller.MovieRESTController@47150bbf
2023-11-07T19:53:32.530+01:00  INFO 3880 --- [io-8080-exec-30] d.l.s.d.controller.MovieRESTController   : My request to DB was executed Thread: http-nio-8080-exec-30 Controller: de.lion5.spring.dvd.controller.MovieRESTController@47150bbf
2023-11-07T19:53:32.618+01:00  INFO 3880 --- [nio-8080-exec-8] d.l.s.d.controller.MovieRESTController   : My request to DB was executed Thread: http-nio-8080-exec-8 Controller: de.lion5.spring.dvd.controller.MovieRESTController@47150bbf
2023-11-07T19:53:32.631+01:00  INFO 3880 --- [nio-8080-exec-2] d.l.s.d.controller.MovieRESTController   : My request to DB was executed Thread: http-nio-8080-exec-2 Controller: de.lion5.spring.dvd.controller.MovieRESTController@47150bbf
2023-11-07T19:53:32.661+01:00  INFO 3880 --- [nio-8080-exec-4] d.l.s.d.controller.MovieRESTController   : My request to DB was executed Thread: http-nio-8080-exec-4 Controller: de.lion5.spring.dvd.controller.MovieRESTController@47150bbf
2023-11-07T19:53:32.725+01:00  INFO 3880 --- [io-8080-exec-15] d.l.s.d.controller.MovieRESTController   : My request to DB was executed Thread: http-nio-8080-exec-15 Controller: de.lion5.spring.dvd.controller.MovieRESTController@47150bbf
2023-11-07T19:53:33.159+01:00 DEBUG 3880 --- [l-1 housekeeper] com.zaxxer.hikari.pool.HikariPool        : HikariPool-1 - Pool stats (total=10, active=10, idle=0, waiting=15)
2023-11-07T19:53:33.159+01:00 DEBUG 3880 --- [l-1 housekeeper] com.zaxxer.hikari.pool.HikariPool        : HikariPool-1 - Fill pool skipped, pool has sufficient level or currently being filled (queueDepth=0).
2023-11-07T19:53:34.170+01:00 DEBUG 3880 --- [l-1 housekeeper] com.zaxxer.hikari.pool.HikariPool        : HikariPool-1 - Pool stats (total=10, active=10, idle=0, waiting=15)
2023-11-07T19:53:34.170+01:00 DEBUG 3880 --- [l-1 housekeeper] com.zaxxer.hikari.pool.HikariPool        : HikariPool-1 - Fill pool skipped, pool has sufficient level or currently being filled (queueDepth=0).
2023-11-07T19:53:35.184+01:00 DEBUG 3880 --- [l-1 housekeeper] com.zaxxer.hikari.pool.HikariPool        : HikariPool-1 - Pool stats (total=10, active=10, idle=0, waiting=15)
2023-11-07T19:53:35.184+01:00 DEBUG 3880 --- [l-1 housekeeper] com.zaxxer.hikari.pool.HikariPool        : HikariPool-1 - Fill pool skipped, pool has sufficient level or currently being filled (queueDepth=0).
2023-11-07T19:53:36.198+01:00 DEBUG 3880 --- [l-1 housekeeper] com.zaxxer.hikari.pool.HikariPool        : HikariPool-1 - Pool stats (total=10, active=10, idle=0, waiting=15)
2023-11-07T19:53:36.198+01:00 DEBUG 3880 --- [l-1 housekeeper] com.zaxxer.hikari.pool.HikariPool        : HikariPool-1 - Fill pool skipped, pool has sufficient level or currently being filled (queueDepth=0).
2023-11-07T19:53:37.200+01:00 DEBUG 3880 --- [l-1 housekeeper] com.zaxxer.hikari.pool.HikariPool        : HikariPool-1 - Pool stats (total=10, active=10, idle=0, waiting=15)
2023-11-07T19:53:37.200+01:00 DEBUG 3880 --- [l-1 housekeeper] com.zaxxer.hikari.pool.HikariPool        : HikariPool-1 - Fill pool skipped, pool has sufficient level or currently being filled (queueDepth=0).
2023-11-07T19:53:38.213+01:00 DEBUG 3880 --- [l-1 housekeeper] com.zaxxer.hikari.pool.HikariPool        : HikariPool-1 - Pool stats (total=10, active=10, idle=0, waiting=15)
2023-11-07T19:53:38.213+01:00 DEBUG 3880 --- [l-1 housekeeper] com.zaxxer.hikari.pool.HikariPool        : HikariPool-1 - Fill pool skipped, pool has sufficient level or currently being filled (queueDepth=0).
2023-11-07T19:53:39.226+01:00 DEBUG 3880 --- [l-1 housekeeper] com.zaxxer.hikari.pool.HikariPool        : HikariPool-1 - Pool stats (total=10, active=10, idle=0, waiting=15)
2023-11-07T19:53:39.226+01:00 DEBUG 3880 --- [l-1 housekeeper] com.zaxxer.hikari.pool.HikariPool        : HikariPool-1 - Fill pool skipped, pool has sufficient level or currently being filled (queueDepth=0).
2023-11-07T19:53:40.240+01:00 DEBUG 3880 --- [l-1 housekeeper] com.zaxxer.hikari.pool.HikariPool        : HikariPool-1 - Pool stats (total=10, active=10, idle=0, waiting=15)
2023-11-07T19:53:40.240+01:00 DEBUG 3880 --- [l-1 housekeeper] com.zaxxer.hikari.pool.HikariPool        : HikariPool-1 - Fill pool skipped, pool has sufficient level or currently being filled (queueDepth=0).
2023-11-07T19:53:41.256+01:00 DEBUG 3880 --- [l-1 housekeeper] com.zaxxer.hikari.pool.HikariPool        : HikariPool-1 - Pool stats (total=10, active=10, idle=0, waiting=15)
2023-11-07T19:53:41.256+01:00 DEBUG 3880 --- [l-1 housekeeper] com.zaxxer.hikari.pool.HikariPool        : HikariPool-1 - Fill pool skipped, pool has sufficient level or currently being filled (queueDepth=0).
2023-11-07T19:53:42.272+01:00 DEBUG 3880 --- [l-1 housekeeper] com.zaxxer.hikari.pool.HikariPool        : HikariPool-1 - Pool stats (total=10, active=10, idle=0, waiting=15)
2023-11-07T19:53:42.272+01:00 DEBUG 3880 --- [l-1 housekeeper] com.zaxxer.hikari.pool.HikariPool        : HikariPool-1 - Fill pool skipped, pool has sufficient level or currently being filled (queueDepth=0).
2023-11-07T19:53:42.498+01:00  INFO 3880 --- [io-8080-exec-23] d.l.s.d.controller.MovieRESTController   : Response is generated :) Thread: http-nio-8080-exec-23 Controller: de.lion5.spring.dvd.controller.MovieRESTController@47150bbf
2023-11-07T19:53:42.509+01:00  INFO 3880 --- [io-8080-exec-20] d.l.s.d.controller.MovieRESTController   : Response is generated :) Thread: http-nio-8080-exec-20 Controller: de.lion5.spring.dvd.controller.MovieRESTController@47150bbf
2023-11-07T19:53:42.511+01:00  INFO 3880 --- [nio-8080-exec-9] d.l.s.d.controller.MovieRESTController   : My request to DB was executed Thread: http-nio-8080-exec-9 Controller: de.lion5.spring.dvd.controller.MovieRESTController@47150bbf
2023-11-07T19:53:42.524+01:00  INFO 3880 --- [io-8080-exec-17] d.l.s.d.controller.MovieRESTController   : My request to DB was executed Thread: http-nio-8080-exec-17 Controller: de.lion5.spring.dvd.controller.MovieRESTController@47150bbf
2023-11-07T19:53:42.524+01:00  INFO 3880 --- [io-8080-exec-34] d.l.s.d.controller.MovieRESTController   : Response is generated :) Thread: http-nio-8080-exec-34 Controller: de.lion5.spring.dvd.controller.MovieRESTController@47150bbf
2023-11-07T19:53:42.525+01:00  INFO 3880 --- [io-8080-exec-25] d.l.s.d.controller.MovieRESTController   : Response is generated :) Thread: http-nio-8080-exec-25 Controller: de.lion5.spring.dvd.controller.MovieRESTController@47150bbf
2023-11-07T19:53:42.549+01:00  INFO 3880 --- [io-8080-exec-21] d.l.s.d.controller.MovieRESTController   : My request to DB was executed Thread: http-nio-8080-exec-21 Controller: de.lion5.spring.dvd.controller.MovieRESTController@47150bbf
2023-11-07T19:53:42.555+01:00  INFO 3880 --- [io-8080-exec-18] d.l.s.d.controller.MovieRESTController   : My request to DB was executed Thread: http-nio-8080-exec-18 Controller: de.lion5.spring.dvd.controller.MovieRESTController@47150bbf
2023-11-07T19:53:42.558+01:00  INFO 3880 --- [nio-8080-exec-7] d.l.s.d.controller.MovieRESTController   : Response is generated :) Thread: http-nio-8080-exec-7 Controller: de.lion5.spring.dvd.controller.MovieRESTController@47150bbf
2023-11-07T19:53:42.572+01:00  INFO 3880 --- [io-8080-exec-30] d.l.s.d.controller.MovieRESTController   : Response is generated :) Thread: http-nio-8080-exec-30 Controller: de.lion5.spring.dvd.controller.MovieRESTController@47150bbf
2023-11-07T19:53:42.579+01:00  INFO 3880 --- [nio-8080-exec-6] d.l.s.d.controller.MovieRESTController   : My request to DB was executed Thread: http-nio-8080-exec-6 Controller: de.lion5.spring.dvd.controller.MovieRESTController@47150bbf
2023-11-07T19:53:42.598+01:00  INFO 3880 --- [io-8080-exec-12] d.l.s.d.controller.MovieRESTController   : My request to DB was executed Thread: http-nio-8080-exec-12 Controller: de.lion5.spring.dvd.controller.MovieRESTController@47150bbf
2023-11-07T19:53:42.630+01:00  INFO 3880 --- [nio-8080-exec-8] d.l.s.d.controller.MovieRESTController   : Response is generated :) Thread: http-nio-8080-exec-8 Controller: de.lion5.spring.dvd.controller.MovieRESTController@47150bbf
2023-11-07T19:53:42.636+01:00  INFO 3880 --- [nio-8080-exec-2] d.l.s.d.controller.MovieRESTController   : Response is generated :) Thread: http-nio-8080-exec-2 Controller: de.lion5.spring.dvd.controller.MovieRESTController@47150bbf
2023-11-07T19:53:42.644+01:00  INFO 3880 --- [io-8080-exec-22] d.l.s.d.controller.MovieRESTController   : My request to DB was executed Thread: http-nio-8080-exec-22 Controller: de.lion5.spring.dvd.controller.MovieRESTController@47150bbf
2023-11-07T19:53:42.648+01:00  INFO 3880 --- [io-8080-exec-35] d.l.s.d.controller.MovieRESTController   : My request to DB was executed Thread: http-nio-8080-exec-35 Controller: de.lion5.spring.dvd.controller.MovieRESTController@47150bbf
2023-11-07T19:53:42.664+01:00  INFO 3880 --- [nio-8080-exec-4] d.l.s.d.controller.MovieRESTController   : Response is generated :) Thread: http-nio-8080-exec-4 Controller: de.lion5.spring.dvd.controller.MovieRESTController@47150bbf
2023-11-07T19:53:42.682+01:00  INFO 3880 --- [io-8080-exec-14] d.l.s.d.controller.MovieRESTController   : My request to DB was executed Thread: http-nio-8080-exec-14 Controller: de.lion5.spring.dvd.controller.MovieRESTController@47150bbf
2023-11-07T19:53:42.727+01:00  INFO 3880 --- [io-8080-exec-15] d.l.s.d.controller.MovieRESTController   : Response is generated :) Thread: http-nio-8080-exec-15 Controller: de.lion5.spring.dvd.controller.MovieRESTController@47150bbf
2023-11-07T19:53:42.742+01:00  INFO 3880 --- [nio-8080-exec-5] d.l.s.d.controller.MovieRESTController   : My request to DB was executed Thread: http-nio-8080-exec-5 Controller: de.lion5.spring.dvd.controller.MovieRESTController@47150bbf
2023-11-07T19:53:43.285+01:00 DEBUG 3880 --- [l-1 housekeeper] com.zaxxer.hikari.pool.HikariPool        : HikariPool-1 - Pool stats (total=10, active=10, idle=0, waiting=5)
2023-11-07T19:53:43.286+01:00 DEBUG 3880 --- [l-1 housekeeper] com.zaxxer.hikari.pool.HikariPool        : HikariPool-1 - Fill pool skipped, pool has sufficient level or currently being filled (queueDepth=0).
2023-11-07T19:53:44.287+01:00 DEBUG 3880 --- [l-1 housekeeper] com.zaxxer.hikari.pool.HikariPool        : HikariPool-1 - Pool stats (total=10, active=10, idle=0, waiting=5)
2023-11-07T19:53:44.287+01:00 DEBUG 3880 --- [l-1 housekeeper] com.zaxxer.hikari.pool.HikariPool        : HikariPool-1 - Fill pool skipped, pool has sufficient level or currently being filled (queueDepth=0).
2023-11-07T19:53:45.290+01:00 DEBUG 3880 --- [l-1 housekeeper] com.zaxxer.hikari.pool.HikariPool        : HikariPool-1 - Pool stats (total=10, active=10, idle=0, waiting=5)
2023-11-07T19:53:45.290+01:00 DEBUG 3880 --- [l-1 housekeeper] com.zaxxer.hikari.pool.HikariPool        : HikariPool-1 - Fill pool skipped, pool has sufficient level or currently being filled (queueDepth=0).
2023-11-07T19:53:46.302+01:00 DEBUG 3880 --- [l-1 housekeeper] com.zaxxer.hikari.pool.HikariPool        : HikariPool-1 - Pool stats (total=10, active=10, idle=0, waiting=5)
2023-11-07T19:53:46.302+01:00 DEBUG 3880 --- [l-1 housekeeper] com.zaxxer.hikari.pool.HikariPool        : HikariPool-1 - Fill pool skipped, pool has sufficient level or currently being filled (queueDepth=0).
2023-11-07T19:53:47.315+01:00 DEBUG 3880 --- [l-1 housekeeper] com.zaxxer.hikari.pool.HikariPool        : HikariPool-1 - Pool stats (total=10, active=10, idle=0, waiting=5)
2023-11-07T19:53:47.315+01:00 DEBUG 3880 --- [l-1 housekeeper] com.zaxxer.hikari.pool.HikariPool        : HikariPool-1 - Fill pool skipped, pool has sufficient level or currently being filled (queueDepth=0).
2023-11-07T19:53:48.329+01:00 DEBUG 3880 --- [l-1 housekeeper] com.zaxxer.hikari.pool.HikariPool        : HikariPool-1 - Pool stats (total=10, active=10, idle=0, waiting=5)
2023-11-07T19:53:48.329+01:00 DEBUG 3880 --- [l-1 housekeeper] com.zaxxer.hikari.pool.HikariPool        : HikariPool-1 - Fill pool skipped, pool has sufficient level or currently being filled (queueDepth=0).
2023-11-07T19:53:49.331+01:00 DEBUG 3880 --- [l-1 housekeeper] com.zaxxer.hikari.pool.HikariPool        : HikariPool-1 - Pool stats (total=10, active=10, idle=0, waiting=5)
2023-11-07T19:53:49.331+01:00 DEBUG 3880 --- [l-1 housekeeper] com.zaxxer.hikari.pool.HikariPool        : HikariPool-1 - Fill pool skipped, pool has sufficient level or currently being filled (queueDepth=0).
2023-11-07T19:53:50.332+01:00 DEBUG 3880 --- [l-1 housekeeper] com.zaxxer.hikari.pool.HikariPool        : HikariPool-1 - Pool stats (total=10, active=10, idle=0, waiting=5)
2023-11-07T19:53:50.332+01:00 DEBUG 3880 --- [l-1 housekeeper] com.zaxxer.hikari.pool.HikariPool        : HikariPool-1 - Fill pool skipped, pool has sufficient level or currently being filled (queueDepth=0).
2023-11-07T19:53:51.334+01:00 DEBUG 3880 --- [l-1 housekeeper] com.zaxxer.hikari.pool.HikariPool        : HikariPool-1 - Pool stats (total=10, active=10, idle=0, waiting=5)
2023-11-07T19:53:51.334+01:00 DEBUG 3880 --- [l-1 housekeeper] com.zaxxer.hikari.pool.HikariPool        : HikariPool-1 - Fill pool skipped, pool has sufficient level or currently being filled (queueDepth=0).
2023-11-07T19:53:52.332+01:00 DEBUG 3880 --- [io-8080-exec-26] com.zaxxer.hikari.pool.HikariPool        : HikariPool-1 - Timeout failure stats (total=10, active=10, idle=0, waiting=3)
2023-11-07T19:53:52.332+01:00 DEBUG 3880 --- [io-8080-exec-29] com.zaxxer.hikari.pool.HikariPool        : HikariPool-1 - Timeout failure stats (total=10, active=10, idle=0, waiting=3)
2023-11-07T19:53:52.334+01:00  WARN 3880 --- [io-8080-exec-29] o.h.engine.jdbc.spi.SqlExceptionHelper   : SQL Error: 0, SQLState: null
2023-11-07T19:53:52.334+01:00 ERROR 3880 --- [io-8080-exec-29] o.h.engine.jdbc.spi.SqlExceptionHelper   : HikariPool-1 - Connection is not available, request timed out after 30009ms.
2023-11-07T19:53:52.334+01:00  WARN 3880 --- [io-8080-exec-26] o.h.engine.jdbc.spi.SqlExceptionHelper   : SQL Error: 0, SQLState: null
2023-11-07T19:53:52.334+01:00 ERROR 3880 --- [io-8080-exec-26] o.h.engine.jdbc.spi.SqlExceptionHelper   : HikariPool-1 - Connection is not available, request timed out after 30007ms.
2023-11-07T19:53:52.335+01:00 DEBUG 3880 --- [io-8080-exec-31] com.zaxxer.hikari.pool.HikariPool        : HikariPool-1 - Timeout failure stats (total=10, active=10, idle=0, waiting=2)
2023-11-07T19:53:52.335+01:00  WARN 3880 --- [io-8080-exec-31] o.h.engine.jdbc.spi.SqlExceptionHelper   : SQL Error: 0, SQLState: null
2023-11-07T19:53:52.335+01:00 ERROR 3880 --- [io-8080-exec-31] o.h.engine.jdbc.spi.SqlExceptionHelper   : HikariPool-1 - Connection is not available, request timed out after 30012ms.
2023-11-07T19:53:52.335+01:00 DEBUG 3880 --- [io-8080-exec-11] com.zaxxer.hikari.pool.HikariPool        : HikariPool-1 - Timeout failure stats (total=10, active=10, idle=0, waiting=1)
2023-11-07T19:53:52.335+01:00  WARN 3880 --- [io-8080-exec-11] o.h.engine.jdbc.spi.SqlExceptionHelper   : SQL Error: 0, SQLState: null
2023-11-07T19:53:52.335+01:00 ERROR 3880 --- [io-8080-exec-11] o.h.engine.jdbc.spi.SqlExceptionHelper   : HikariPool-1 - Connection is not available, request timed out after 30012ms.
2023-11-07T19:53:52.335+01:00 DEBUG 3880 --- [io-8080-exec-27] com.zaxxer.hikari.pool.HikariPool        : HikariPool-1 - Timeout failure stats (total=10, active=10, idle=0, waiting=0)
2023-11-07T19:53:52.335+01:00  WARN 3880 --- [io-8080-exec-27] o.h.engine.jdbc.spi.SqlExceptionHelper   : SQL Error: 0, SQLState: null
2023-11-07T19:53:52.335+01:00 ERROR 3880 --- [io-8080-exec-27] o.h.engine.jdbc.spi.SqlExceptionHelper   : HikariPool-1 - Connection is not available, request timed out after 30012ms.
2023-11-07T19:53:52.348+01:00 DEBUG 3880 --- [l-1 housekeeper] com.zaxxer.hikari.pool.HikariPool        : HikariPool-1 - Pool stats (total=10, active=10, idle=0, waiting=0)
2023-11-07T19:53:52.348+01:00 DEBUG 3880 --- [l-1 housekeeper] com.zaxxer.hikari.pool.HikariPool        : HikariPool-1 - Fill pool skipped, pool has sufficient level or currently being filled (queueDepth=0).
2023-11-07T19:53:52.345+01:00 ERROR 3880 --- [io-8080-exec-27] o.a.c.c.C.[.[.[/].[dispatcherServlet]    : Servlet.service() for servlet [dispatcherServlet] in context with path [] threw exception [Request processing failed: org.springframework.transaction.CannotCreateTransactionException: Could not open JPA EntityManager for transaction] with root cause

java.sql.SQLTransientConnectionException: HikariPool-1 - Connection is not available, request timed out after 30012ms.
... (the other 5 stack traces ommitted)
```

Okay, we are somehow "blocked" for the whole request-response interaction - thanks OSIV for this...

You can solve this problem with the following:

0. Move the heavy computation before the first DB access.
   The first DB interactions keeps the connection due to OSIV.
1. Solution (which is not a real solution...):
   Increase the pool size via spring properties `spring.datasource.hikari.maximum-pool-size=20` in
   our artificial
   example with 35 concurrent users.
2. Next solution (an obvious): Disable OSIV in spring properties `spring.jpa.open-in-view=false`. (
   Change pool to 10 again :)
   What happens now?

```agsl
2023-11-07T20:22:25.909+01:00  INFO 6676 --- [io-8080-exec-27] d.l.s.d.controller.MovieRESTController   : Try to access the DB  Thread: http-nio-8080-exec-27 Controller: de.lion5.spring.dvd.controller.MovieRESTController@66c3cb9b
2023-11-07T20:22:25.909+01:00  INFO 6676 --- [io-8080-exec-14] d.l.s.d.controller.MovieRESTController   : Try to access the DB  Thread: http-nio-8080-exec-14 Controller: de.lion5.spring.dvd.controller.MovieRESTController@66c3cb9b
2023-11-07T20:22:25.909+01:00  INFO 6676 --- [io-8080-exec-33] d.l.s.d.controller.MovieRESTController   : Try to access the DB  Thread: http-nio-8080-exec-33 Controller: de.lion5.spring.dvd.controller.MovieRESTController@66c3cb9b
2023-11-07T20:22:25.909+01:00  INFO 6676 --- [io-8080-exec-19] d.l.s.d.controller.MovieRESTController   : Try to access the DB  Thread: http-nio-8080-exec-19 Controller: de.lion5.spring.dvd.controller.MovieRESTController@66c3cb9b
2023-11-07T20:22:25.909+01:00  INFO 6676 --- [nio-8080-exec-8] d.l.s.d.controller.MovieRESTController   : Try to access the DB  Thread: http-nio-8080-exec-8 Controller: de.lion5.spring.dvd.controller.MovieRESTController@66c3cb9b
2023-11-07T20:22:25.911+01:00  INFO 6676 --- [io-8080-exec-34] d.l.s.d.controller.MovieRESTController   : Try to access the DB  Thread: http-nio-8080-exec-34 Controller: de.lion5.spring.dvd.controller.MovieRESTController@66c3cb9b
2023-11-07T20:22:25.909+01:00  INFO 6676 --- [io-8080-exec-22] d.l.s.d.controller.MovieRESTController   : Try to access the DB  Thread: http-nio-8080-exec-22 Controller: de.lion5.spring.dvd.controller.MovieRESTController@66c3cb9b
2023-11-07T20:22:25.911+01:00 DEBUG 6676 --- [io-8080-exec-34] com.zaxxer.hikari.pool.HikariPool        : HikariPool-1 - Add connection elided, waiting=2, adders pending/running=3
2023-11-07T20:22:25.911+01:00 DEBUG 6676 --- [io-8080-exec-22] com.zaxxer.hikari.pool.HikariPool        : HikariPool-1 - Add connection elided, waiting=2, adders pending/running=3
2023-11-07T20:22:25.911+01:00 DEBUG 6676 --- [nio-8080-exec-8] com.zaxxer.hikari.pool.HikariPool        : HikariPool-1 - Add connection elided, waiting=1, adders pending/running=3
2023-11-07T20:22:25.911+01:00  INFO 6676 --- [nio-8080-exec-9] d.l.s.d.controller.MovieRESTController   : Try to access the DB  Thread: http-nio-8080-exec-9 Controller: de.lion5.spring.dvd.controller.MovieRESTController@66c3cb9b
2023-11-07T20:22:25.911+01:00  INFO 6676 --- [io-8080-exec-24] d.l.s.d.controller.MovieRESTController   : Try to access the DB  Thread: http-nio-8080-exec-24 Controller: de.lion5.spring.dvd.controller.MovieRESTController@66c3cb9b
2023-11-07T20:22:25.911+01:00 DEBUG 6676 --- [nio-8080-exec-9] com.zaxxer.hikari.pool.HikariPool        : HikariPool-1 - Add connection elided, waiting=1, adders pending/running=3
2023-11-07T20:22:25.911+01:00 DEBUG 6676 --- [io-8080-exec-24] com.zaxxer.hikari.pool.HikariPool        : HikariPool-1 - Add connection elided, waiting=2, adders pending/running=3
2023-11-07T20:22:25.911+01:00  INFO 6676 --- [io-8080-exec-29] d.l.s.d.controller.MovieRESTController   : Try to access the DB  Thread: http-nio-8080-exec-29 Controller: de.lion5.spring.dvd.controller.MovieRESTController@66c3cb9b
2023-11-07T20:22:25.912+01:00  INFO 6676 --- [io-8080-exec-28] d.l.s.d.controller.MovieRESTController   : Try to access the DB  Thread: http-nio-8080-exec-28 Controller: de.lion5.spring.dvd.controller.MovieRESTController@66c3cb9b
2023-11-07T20:22:25.912+01:00  INFO 6676 --- [nio-8080-exec-7] d.l.s.d.controller.MovieRESTController   : Try to access the DB  Thread: http-nio-8080-exec-7 Controller: de.lion5.spring.dvd.controller.MovieRESTController@66c3cb9b
2023-11-07T20:22:25.912+01:00 DEBUG 6676 --- [io-8080-exec-29] com.zaxxer.hikari.pool.HikariPool        : HikariPool-1 - Add connection elided, waiting=1, adders pending/running=3
2023-11-07T20:22:25.912+01:00  INFO 6676 --- [io-8080-exec-20] d.l.s.d.controller.MovieRESTController   : Try to access the DB  Thread: http-nio-8080-exec-20 Controller: de.lion5.spring.dvd.controller.MovieRESTController@66c3cb9b
2023-11-07T20:22:25.912+01:00  INFO 6676 --- [io-8080-exec-32] d.l.s.d.controller.MovieRESTController   : Try to access the DB  Thread: http-nio-8080-exec-32 Controller: de.lion5.spring.dvd.controller.MovieRESTController@66c3cb9b
2023-11-07T20:22:25.912+01:00 DEBUG 6676 --- [io-8080-exec-20] com.zaxxer.hikari.pool.HikariPool        : HikariPool-1 - Add connection elided, waiting=4, adders pending/running=5
2023-11-07T20:22:25.912+01:00  INFO 6676 --- [nio-8080-exec-5] d.l.s.d.controller.MovieRESTController   : Try to access the DB  Thread: http-nio-8080-exec-5 Controller: de.lion5.spring.dvd.controller.MovieRESTController@66c3cb9b
2023-11-07T20:22:25.912+01:00  INFO 6676 --- [io-8080-exec-26] d.l.s.d.controller.MovieRESTController   : Try to access the DB  Thread: http-nio-8080-exec-26 Controller: de.lion5.spring.dvd.controller.MovieRESTController@66c3cb9b
2023-11-07T20:22:25.912+01:00  INFO 6676 --- [nio-8080-exec-1] d.l.s.d.controller.MovieRESTController   : Try to access the DB  Thread: http-nio-8080-exec-1 Controller: de.lion5.spring.dvd.controller.MovieRESTController@66c3cb9b
2023-11-07T20:22:25.912+01:00  INFO 6676 --- [io-8080-exec-15] d.l.s.d.controller.MovieRESTController   : Try to access the DB  Thread: http-nio-8080-exec-15 Controller: de.lion5.spring.dvd.controller.MovieRESTController@66c3cb9b
2023-11-07T20:22:25.913+01:00  INFO 6676 --- [io-8080-exec-13] d.l.s.d.controller.MovieRESTController   : Try to access the DB  Thread: http-nio-8080-exec-13 Controller: de.lion5.spring.dvd.controller.MovieRESTController@66c3cb9b
2023-11-07T20:22:25.913+01:00  INFO 6676 --- [io-8080-exec-11] d.l.s.d.controller.MovieRESTController   : Try to access the DB  Thread: http-nio-8080-exec-11 Controller: de.lion5.spring.dvd.controller.MovieRESTController@66c3cb9b
2023-11-07T20:22:25.913+01:00  INFO 6676 --- [io-8080-exec-16] d.l.s.d.controller.MovieRESTController   : Try to access the DB  Thread: http-nio-8080-exec-16 Controller: de.lion5.spring.dvd.controller.MovieRESTController@66c3cb9b
2023-11-07T20:22:25.913+01:00  INFO 6676 --- [io-8080-exec-35] d.l.s.d.controller.MovieRESTController   : Try to access the DB  Thread: http-nio-8080-exec-35 Controller: de.lion5.spring.dvd.controller.MovieRESTController@66c3cb9b
2023-11-07T20:22:25.913+01:00  INFO 6676 --- [io-8080-exec-23] d.l.s.d.controller.MovieRESTController   : Try to access the DB  Thread: http-nio-8080-exec-23 Controller: de.lion5.spring.dvd.controller.MovieRESTController@66c3cb9b
2023-11-07T20:22:25.915+01:00  INFO 6676 --- [io-8080-exec-18] d.l.s.d.controller.MovieRESTController   : Try to access the DB  Thread: http-nio-8080-exec-18 Controller: de.lion5.spring.dvd.controller.MovieRESTController@66c3cb9b
2023-11-07T20:22:25.915+01:00  INFO 6676 --- [io-8080-exec-21] d.l.s.d.controller.MovieRESTController   : Try to access the DB  Thread: http-nio-8080-exec-21 Controller: de.lion5.spring.dvd.controller.MovieRESTController@66c3cb9b
2023-11-07T20:22:25.914+01:00  INFO 6676 --- [nio-8080-exec-2] d.l.s.d.controller.MovieRESTController   : Try to access the DB  Thread: http-nio-8080-exec-2 Controller: de.lion5.spring.dvd.controller.MovieRESTController@66c3cb9b
2023-11-07T20:22:25.915+01:00 DEBUG 6676 --- [io-8080-exec-18] com.zaxxer.hikari.pool.HikariPool        : HikariPool-1 - Add connection elided, waiting=14, adders pending/running=15
2023-11-07T20:22:25.915+01:00  INFO 6676 --- [nio-8080-exec-4] d.l.s.d.controller.MovieRESTController   : Try to access the DB  Thread: http-nio-8080-exec-4 Controller: de.lion5.spring.dvd.controller.MovieRESTController@66c3cb9b
2023-11-07T20:22:25.915+01:00  INFO 6676 --- [nio-8080-exec-6] d.l.s.d.controller.MovieRESTController   : Try to access the DB  Thread: http-nio-8080-exec-6 Controller: de.lion5.spring.dvd.controller.MovieRESTController@66c3cb9b
2023-11-07T20:22:25.915+01:00  INFO 6676 --- [io-8080-exec-12] d.l.s.d.controller.MovieRESTController   : Try to access the DB  Thread: http-nio-8080-exec-12 Controller: de.lion5.spring.dvd.controller.MovieRESTController@66c3cb9b
2023-11-07T20:22:25.915+01:00  INFO 6676 --- [io-8080-exec-17] d.l.s.d.controller.MovieRESTController   : Try to access the DB  Thread: http-nio-8080-exec-17 Controller: de.lion5.spring.dvd.controller.MovieRESTController@66c3cb9b
2023-11-07T20:22:25.915+01:00  INFO 6676 --- [nio-8080-exec-3] d.l.s.d.controller.MovieRESTController   : Try to access the DB  Thread: http-nio-8080-exec-3 Controller: de.lion5.spring.dvd.controller.MovieRESTController@66c3cb9b
2023-11-07T20:22:25.915+01:00  INFO 6676 --- [io-8080-exec-25] d.l.s.d.controller.MovieRESTController   : Try to access the DB  Thread: http-nio-8080-exec-25 Controller: de.lion5.spring.dvd.controller.MovieRESTController@66c3cb9b
2023-11-07T20:22:25.915+01:00  INFO 6676 --- [io-8080-exec-10] d.l.s.d.controller.MovieRESTController   : Try to access the DB  Thread: http-nio-8080-exec-10 Controller: de.lion5.spring.dvd.controller.MovieRESTController@66c3cb9b
2023-11-07T20:22:25.915+01:00  INFO 6676 --- [io-8080-exec-30] d.l.s.d.controller.MovieRESTController   : Try to access the DB  Thread: http-nio-8080-exec-30 Controller: de.lion5.spring.dvd.controller.MovieRESTController@66c3cb9b
2023-11-07T20:22:25.915+01:00  INFO 6676 --- [io-8080-exec-31] d.l.s.d.controller.MovieRESTController   : Try to access the DB  Thread: http-nio-8080-exec-31 Controller: de.lion5.spring.dvd.controller.MovieRESTController@66c3cb9b
2023-11-07T20:22:25.968+01:00 DEBUG 6676 --- [l-1 housekeeper] com.zaxxer.hikari.pool.HikariPool        : HikariPool-1 - Pool stats (total=10, active=10, idle=0, waiting=25)
2023-11-07T20:22:25.968+01:00 DEBUG 6676 --- [l-1 housekeeper] com.zaxxer.hikari.pool.HikariPool        : HikariPool-1 - Fill pool skipped, pool has sufficient level or currently being filled (queueDepth=0).
2023-11-07T20:22:26.172+01:00 ERROR 6676 --- [io-8080-exec-25] o.a.c.c.C.[.[.[/].[dispatcherServlet]    : Servlet.service() for servlet [dispatcherServlet] in context with path [] threw exception [Request processing failed: org.hibernate.LazyInitializationException: failed to lazily initialize a collection of role: de.lion5.spring.dvd.model.Movie.actors: could not initialize proxy - no Session] with root cause

org.hibernate.LazyInitializationException: failed to lazily initialize a collection of role: de.lion5.spring.dvd.model.Movie.actors: could not initialize proxy - no Session
	at org.hibernate.collection.spi.AbstractPersistentCollection.throwLazyInitializationException(AbstractPersistentCollection.java:635) ~[hibernate-core-6.2.9.Final.jar:6.2.9.Final]
	at org.hibernate.collection.spi.AbstractPersistentCollection.withTemporarySessionIfNeeded(AbstractPersistentCollection.java:218) ~[hibernate-core-6.2.9.Final.jar:6.2.9.Final]
	at org.hibernate.collection.spi.AbstractPersistentCollection.readSize(AbstractPersistentCollection.java:148) ~[hibernate-core-6.2.9.Final.jar:6.2.9.Final]
	at org.hibernate.collection.spi.PersistentBag.size(PersistentBag.java:355) ~[hibernate-core-6.2.9.Final.jar:6.2.9.Final]
	at de.lion5.spring.dvd.service.MovieService.lambda$findAllWithMoreThanNActors$0(MovieService.java:23) ~[main/:na]
	at java.base/java.util.stream.ReferencePipeline$2$1.accept(ReferencePipeline.java:178) ~[na:na]
	at java.base/java.util.ArrayList$ArrayListSpliterator.forEachRemaining(ArrayList.java:1625) ~[na:na]
	at java.base/java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:509) ~[na:na]
	at java.base/java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:499) ~[na:na]
	at java.base/java.util.stream.ReduceOps$ReduceOp.evaluateSequential(ReduceOps.java:921) ~[na:na]
	at java.base/java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:234) ~[na:na]
	at java.base/java.util.stream.ReferencePipeline.collect(ReferencePipeline.java:682) ~[na:na]
	at de.lion5.spring.dvd.service.MovieService.findAllWithMoreThanNActors(MovieService.java:24) ~[main/:na]
	at de.lion5.spring.dvd.controller.MovieRESTController.getMovies(MovieRESTController.java:27) ~[main/:na]
	at java.base/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:104) ~[na:na]
	at java.base/java.lang.reflect.Method.invoke(Method.java:577) ~[na:na]
	at org.springframework.web.method.support.InvocableHandlerMethod.doInvoke(InvocableHandlerMethod.java:205) ~[spring-web-6.0.12.jar:6.0.12]
	at org.springframework.web.method.support.InvocableHandlerMethod.invokeForRequest(InvocableHandlerMethod.java:150) ~[spring-web-6.0.12.jar:6.0.12]
	at org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod.invokeAndHandle(ServletInvocableHandlerMethod.java:118) ~[spring-webmvc-6.0.12.jar:6.0.12]
	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.invokeHandlerMethod(RequestMappingHandlerAdapter.java:884) ~[spring-webmvc-6.0.12.jar:6.0.12]
	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.handleInternal(RequestMappingHandlerAdapter.java:797) ~[spring-webmvc-6.0.12.jar:6.0.12]
	at org.springframework.web.servlet.mvc.method.AbstractHandlerMethodAdapter.handle(AbstractHandlerMethodAdapter.java:87) ~[spring-webmvc-6.0.12.jar:6.0.12]
	at org.springframework.web.servlet.DispatcherServlet.doDispatch(DispatcherServlet.java:1081) ~[spring-webmvc-6.0.12.jar:6.0.12]
	at org.springframework.web.servlet.DispatcherServlet.doService(DispatcherServlet.java:974) ~[spring-webmvc-6.0.12.jar:6.0.12]
	at org.springframework.web.servlet.FrameworkServlet.processRequest(FrameworkServlet.java:1011) ~[spring-webmvc-6.0.12.jar:6.0.12]
	at org.springframework.web.servlet.FrameworkServlet.doGet(FrameworkServlet.java:903) ~[spring-webmvc-6.0.12.jar:6.0.12]
	at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:564) ~[tomcat-embed-core-10.1.13.jar:6.0]
	at org.springframework.web.servlet.FrameworkServlet.service(FrameworkServlet.java:885) ~[spring-webmvc-6.0.12.jar:6.0.12]
	at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:658) ~[tomcat-embed-core-10.1.13.jar:6.0]
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:205) ~[tomcat-embed-core-10.1.13.jar:10.1.13]
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:149) ~[tomcat-embed-core-10.1.13.jar:10.1.13]
	at org.apache.tomcat.websocket.server.WsFilter.doFilter(WsFilter.java:51) ~[tomcat-embed-websocket-10.1.13.jar:10.1.13]
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:174) ~[tomcat-embed-core-10.1.13.jar:10.1.13]
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:149) ~[tomcat-embed-core-10.1.13.jar:10.1.13]
	at org.springframework.web.filter.RequestContextFilter.doFilterInternal(RequestContextFilter.java:100) ~[spring-web-6.0.12.jar:6.0.12]
	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:116) ~[spring-web-6.0.12.jar:6.0.12]
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:174) ~[tomcat-embed-core-10.1.13.jar:10.1.13]
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:149) ~[tomcat-embed-core-10.1.13.jar:10.1.13]
	at org.springframework.web.filter.FormContentFilter.doFilterInternal(FormContentFilter.java:93) ~[spring-web-6.0.12.jar:6.0.12]
	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:116) ~[spring-web-6.0.12.jar:6.0.12]
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:174) ~[tomcat-embed-core-10.1.13.jar:10.1.13]
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:149) ~[tomcat-embed-core-10.1.13.jar:10.1.13]
	at org.springframework.web.filter.CharacterEncodingFilter.doFilterInternal(CharacterEncodingFilter.java:201) ~[spring-web-6.0.12.jar:6.0.12]
	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:116) ~[spring-web-6.0.12.jar:6.0.12]
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:174) ~[tomcat-embed-core-10.1.13.jar:10.1.13]
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:149) ~[tomcat-embed-core-10.1.13.jar:10.1.13]
	at org.apache.catalina.core.StandardWrapperValve.invoke(StandardWrapperValve.java:167) ~[tomcat-embed-core-10.1.13.jar:10.1.13]
	at org.apache.catalina.core.StandardContextValve.invoke(StandardContextValve.java:90) ~[tomcat-embed-core-10.1.13.jar:10.1.13]
	at org.apache.catalina.authenticator.AuthenticatorBase.invoke(AuthenticatorBase.java:482) ~[tomcat-embed-core-10.1.13.jar:10.1.13]
	at org.apache.catalina.core.StandardHostValve.invoke(StandardHostValve.java:115) ~[tomcat-embed-core-10.1.13.jar:10.1.13]
	at org.apache.catalina.valves.ErrorReportValve.invoke(ErrorReportValve.java:93) ~[tomcat-embed-core-10.1.13.jar:10.1.13]
	at org.apache.catalina.core.StandardEngineValve.invoke(StandardEngineValve.java:74) ~[tomcat-embed-core-10.1.13.jar:10.1.13]
	at org.apache.catalina.connector.CoyoteAdapter.service(CoyoteAdapter.java:341) ~[tomcat-embed-core-10.1.13.jar:10.1.13]
	at org.apache.coyote.http11.Http11Processor.service(Http11Processor.java:391) ~[tomcat-embed-core-10.1.13.jar:10.1.13]
	at org.apache.coyote.AbstractProcessorLight.process(AbstractProcessorLight.java:63) ~[tomcat-embed-core-10.1.13.jar:10.1.13]
	at org.apache.coyote.AbstractProtocol$ConnectionHandler.process(AbstractProtocol.java:894) ~[tomcat-embed-core-10.1.13.jar:10.1.13]
	at org.apache.tomcat.util.net.NioEndpoint$SocketProcessor.doRun(NioEndpoint.java:1740) ~[tomcat-embed-core-10.1.13.jar:10.1.13]
	at org.apache.tomcat.util.net.SocketProcessorBase.run(SocketProcessorBase.java:52) ~[tomcat-embed-core-10.1.13.jar:10.1.13]
	at org.apache.tomcat.util.threads.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1191) ~[tomcat-embed-core-10.1.13.jar:10.1.13]
	at org.apache.tomcat.util.threads.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:659) ~[tomcat-embed-core-10.1.13.jar:10.1.13]
	at org.apache.tomcat.util.threads.TaskThread$WrappingRunnable.run(TaskThread.java:61) ~[tomcat-embed-core-10.1.13.jar:10.1.13]
	at java.base/java.lang.Thread.run(Thread.java:833) ~[na:na]
(ommitted the other 34 stacks which look exactly the same...)
```

Okay now we have no session (as the error message implies), but where do we need a session? What is
actually the problem?
Our relations in `Movie` and `Actor` are defined as `LAZY`, so we read associated data only when we
need them
as in the case for the `getAuthors` in `MovieService`. To see this behavior that this is the problem
and what happens
on a SQL level
uncomment the `sql` properties for printing sql statements to the console
in `application.properties`. To better understand whats happening also change the number of
concurrent
users in the test to 1. The output is as follows:

```agsl
2023-11-07T20:53:02.345+01:00  INFO 17376 --- [nio-8080-exec-1] d.l.s.d.controller.MovieRESTController   : Try to access the DB  Thread: http-nio-8080-exec-1 Controller: de.lion5.spring.dvd.controller.MovieRESTController@70dbc340
2023-11-07T20:53:02.505+01:00 DEBUG 17376 --- [nio-8080-exec-1] org.hibernate.SQL                        : 
    select
        m1_0.id,
        m1_0.cover_image,
        m1_0.film_studio_id,
        m1_0.release_year,
        m1_0.title,
        m1_0.won_oscar 
    from
        movie m1_0
2023-11-07T20:53:02.507+01:00 DEBUG 17376 --- [nio-8080-exec-1] org.hibernate.SQL                        : 
    select
        f1_0.id,
        f1_0.name,
        f1_0.since 
    from
        film_studio f1_0 
    where
        f1_0.id=?
2023-11-07T20:53:02.507+01:00 TRACE 17376 --- [nio-8080-exec-1] org.hibernate.orm.jdbc.bind              : binding parameter [1] as [BIGINT] - [1]
2023-11-07T20:53:02.509+01:00 DEBUG 17376 --- [nio-8080-exec-1] org.hibernate.SQL                        : 
    select
        f1_0.id,
        f1_0.name,
        f1_0.since 
    from
        film_studio f1_0 
    where
        f1_0.id=?
2023-11-07T20:53:02.509+01:00 TRACE 17376 --- [nio-8080-exec-1] org.hibernate.orm.jdbc.bind              : binding parameter [1] as [BIGINT] - [2]
2023-11-07T20:53:02.519+01:00 ERROR 17376 --- [nio-8080-exec-1] o.a.c.c.C.[.[.[/].[dispatcherServlet]    : Servlet.service() for servlet [dispatcherServlet] in context with path [] threw exception [Request processing failed: org.hibernate.LazyInitializationException: failed to lazily initialize a collection of role: de.lion5.spring.dvd.model.Movie.actors: could not initialize proxy - no Session] with root cause

org.hibernate.LazyInitializationException: failed to lazily initialize a collection of role: de.lion5.spring.dvd.model.Movie.actors: could not initialize proxy - no Session
	at org.hibernate.collection.spi.AbstractPersistentCollection.throwLazyInitializationException(AbstractPersistentCollection.java:635) ~[hibernate-core-6.2.9.Final.jar:6.2.9.Final]
	at org.hibernate.collection.spi.AbstractPersistentCollection.withTemporarySessionIfNeeded(AbstractPersistentCollection.java:218) ~[hibernate-core-6.2.9.Final.jar:6.2.9.Final]
	at org.hibernate.collection.spi.AbstractPersistentCollection.readSize(AbstractPersistentCollection.java:148) ~[hibernate-core-6.2.9.Final.jar:6.2.9.Final]
	at org.hibernate.collection.spi.PersistentBag.size(PersistentBag.java:355) ~[hibernate-core-6.2.9.Final.jar:6.2.9.Final]
	at de.lion5.spring.dvd.service.MovieService.lambda$findAllWithMoreThanNActors$0(MovieService.java:23) ~[main/:na]
	at java.base/java.util.stream.ReferencePipeline$2$1.accept(ReferencePipeline.java:178) ~[na:na]
	at java.base/java.util.ArrayList$ArrayListSpliterator.forEachRemaining(ArrayList.java:1625) ~[na:na]
	at java.base/java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:509) ~[na:na]
	at java.base/java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:499) ~[na:na]
	at java.base/java.util.stream.ReduceOps$ReduceOp.evaluateSequential(ReduceOps.java:921) ~[na:na]
	at java.base/java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:234) ~[na:na]
	at java.base/java.util.stream.ReferencePipeline.collect(ReferencePipeline.java:682) ~[na:na]
	at de.lion5.spring.dvd.service.MovieService.findAllWithMoreThanNActors(MovieService.java:24) ~[main/:na]
	at de.lion5.spring.dvd.controller.MovieRESTController.getMovies(MovieRESTController.java:27) ~[main/:na]
	at java.base/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:104) ~[na:na]
	at java.base/java.lang.reflect.Method.invoke(Method.java:577) ~[na:na]
	at org.springframework.web.method.support.InvocableHandlerMethod.doInvoke(InvocableHandlerMethod.java:205) ~[spring-web-6.0.12.jar:6.0.12]
	at org.springframework.web.method.support.InvocableHandlerMethod.invokeForRequest(InvocableHandlerMethod.java:150) ~[spring-web-6.0.12.jar:6.0.12]
	at org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod.invokeAndHandle(ServletInvocableHandlerMethod.java:118) ~[spring-webmvc-6.0.12.jar:6.0.12]
	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.invokeHandlerMethod(RequestMappingHandlerAdapter.java:884) ~[spring-webmvc-6.0.12.jar:6.0.12]
	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.handleInternal(RequestMappingHandlerAdapter.java:797) ~[spring-webmvc-6.0.12.jar:6.0.12]
	at org.springframework.web.servlet.mvc.method.AbstractHandlerMethodAdapter.handle(AbstractHandlerMethodAdapter.java:87) ~[spring-webmvc-6.0.12.jar:6.0.12]
	at org.springframework.web.servlet.DispatcherServlet.doDispatch(DispatcherServlet.java:1081) ~[spring-webmvc-6.0.12.jar:6.0.12]
	at org.springframework.web.servlet.DispatcherServlet.doService(DispatcherServlet.java:974) ~[spring-webmvc-6.0.12.jar:6.0.12]
	at org.springframework.web.servlet.FrameworkServlet.processRequest(FrameworkServlet.java:1011) ~[spring-webmvc-6.0.12.jar:6.0.12]
	at org.springframework.web.servlet.FrameworkServlet.doGet(FrameworkServlet.java:903) ~[spring-webmvc-6.0.12.jar:6.0.12]
	at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:564) ~[tomcat-embed-core-10.1.13.jar:6.0]
	at org.springframework.web.servlet.FrameworkServlet.service(FrameworkServlet.java:885) ~[spring-webmvc-6.0.12.jar:6.0.12]
	at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:658) ~[tomcat-embed-core-10.1.13.jar:6.0]
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:205) ~[tomcat-embed-core-10.1.13.jar:10.1.13]
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:149) ~[tomcat-embed-core-10.1.13.jar:10.1.13]
	at org.apache.tomcat.websocket.server.WsFilter.doFilter(WsFilter.java:51) ~[tomcat-embed-websocket-10.1.13.jar:10.1.13]
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:174) ~[tomcat-embed-core-10.1.13.jar:10.1.13]
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:149) ~[tomcat-embed-core-10.1.13.jar:10.1.13]
	at org.springframework.web.filter.RequestContextFilter.doFilterInternal(RequestContextFilter.java:100) ~[spring-web-6.0.12.jar:6.0.12]
	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:116) ~[spring-web-6.0.12.jar:6.0.12]
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:174) ~[tomcat-embed-core-10.1.13.jar:10.1.13]
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:149) ~[tomcat-embed-core-10.1.13.jar:10.1.13]
	at org.springframework.web.filter.FormContentFilter.doFilterInternal(FormContentFilter.java:93) ~[spring-web-6.0.12.jar:6.0.12]
	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:116) ~[spring-web-6.0.12.jar:6.0.12]
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:174) ~[tomcat-embed-core-10.1.13.jar:10.1.13]
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:149) ~[tomcat-embed-core-10.1.13.jar:10.1.13]
	at org.springframework.web.filter.CharacterEncodingFilter.doFilterInternal(CharacterEncodingFilter.java:201) ~[spring-web-6.0.12.jar:6.0.12]
	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:116) ~[spring-web-6.0.12.jar:6.0.12]
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:174) ~[tomcat-embed-core-10.1.13.jar:10.1.13]
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:149) ~[tomcat-embed-core-10.1.13.jar:10.1.13]
	at org.apache.catalina.core.StandardWrapperValve.invoke(StandardWrapperValve.java:167) ~[tomcat-embed-core-10.1.13.jar:10.1.13]
	at org.apache.catalina.core.StandardContextValve.invoke(StandardContextValve.java:90) ~[tomcat-embed-core-10.1.13.jar:10.1.13]
	at org.apache.catalina.authenticator.AuthenticatorBase.invoke(AuthenticatorBase.java:482) ~[tomcat-embed-core-10.1.13.jar:10.1.13]
	at org.apache.catalina.core.StandardHostValve.invoke(StandardHostValve.java:115) ~[tomcat-embed-core-10.1.13.jar:10.1.13]
	at org.apache.catalina.valves.ErrorReportValve.invoke(ErrorReportValve.java:93) ~[tomcat-embed-core-10.1.13.jar:10.1.13]
	at org.apache.catalina.core.StandardEngineValve.invoke(StandardEngineValve.java:74) ~[tomcat-embed-core-10.1.13.jar:10.1.13]
	at org.apache.catalina.connector.CoyoteAdapter.service(CoyoteAdapter.java:341) ~[tomcat-embed-core-10.1.13.jar:10.1.13]
	at org.apache.coyote.http11.Http11Processor.service(Http11Processor.java:391) ~[tomcat-embed-core-10.1.13.jar:10.1.13]
	at org.apache.coyote.AbstractProcessorLight.process(AbstractProcessorLight.java:63) ~[tomcat-embed-core-10.1.13.jar:10.1.13]
	at org.apache.coyote.AbstractProtocol$ConnectionHandler.process(AbstractProtocol.java:894) ~[tomcat-embed-core-10.1.13.jar:10.1.13]
	at org.apache.tomcat.util.net.NioEndpoint$SocketProcessor.doRun(NioEndpoint.java:1740) ~[tomcat-embed-core-10.1.13.jar:10.1.13]
	at org.apache.tomcat.util.net.SocketProcessorBase.run(SocketProcessorBase.java:52) ~[tomcat-embed-core-10.1.13.jar:10.1.13]
	at org.apache.tomcat.util.threads.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1191) ~[tomcat-embed-core-10.1.13.jar:10.1.13]
	at org.apache.tomcat.util.threads.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:659) ~[tomcat-embed-core-10.1.13.jar:10.1.13]
	at org.apache.tomcat.util.threads.TaskThread$WrappingRunnable.run(TaskThread.java:61) ~[tomcat-embed-core-10.1.13.jar:10.1.13]
	at java.base/java.lang.Thread.run(Thread.java:833) ~[na:na]
```

Okay, now we see three sql statements and an exception - now I am totally confused :|

One for reading all the movies - okay this is understandable `select *`.
First question arises (which is a side aspect here) why are there two queries for film studios?
We know we have two movies and each movie is associated with a single film studio.
Per default, the film studio is read from the database based on the `ManyToOne` association in an
`EAGER` fashion (`EAGER`is the default fetch type for `ManyToOne`). So we read first N movies (1
query) and then for each movie the associated film
studio if not already present (N queries), which
results in an **N+1 problem**. Okay you may argue a few more queries, but it works. (Keep in mind
you don't need the film studio info, but still read it from the database - pressure on the DB
without
a usage).

- Solution A is not recommended: Changing `LAZY` to `EAGER` as often recommended in blogs: Let's try
  it and see what's happening:

```java
  @ManyToMany(fetch = FetchType.EAGER)
@JoinTable(name = "movie_actor", joinColumns = @JoinColumn(name = "movie_id"), inverseJoinColumns = @JoinColumn(name = "actor_id"))
private List<Actor> actors;
```

This is the result, even more sql statements, but no `LazyInitializationException`. So we solved the
problem or?

```agsl
2023-11-07T21:06:46.493+01:00  INFO 16964 --- [nio-8080-exec-1] d.l.s.d.controller.MovieRESTController   : Try to access the DB  Thread: http-nio-8080-exec-1 Controller: de.lion5.spring.dvd.controller.MovieRESTController@2fe946f
2023-11-07T21:06:46.534+01:00 DEBUG 16964 --- [l-1 housekeeper] com.zaxxer.hikari.pool.HikariPool        : HikariPool-1 - Pool stats (total=10, active=1, idle=9, waiting=0)
2023-11-07T21:06:46.534+01:00 DEBUG 16964 --- [l-1 housekeeper] com.zaxxer.hikari.pool.HikariPool        : HikariPool-1 - Fill pool skipped, pool has sufficient level or currently being filled (queueDepth=0).
2023-11-07T21:06:46.622+01:00 DEBUG 16964 --- [nio-8080-exec-1] org.hibernate.SQL                        : 
    select
        m1_0.id,
        m1_0.cover_image,
        m1_0.film_studio_id,
        m1_0.release_year,
        m1_0.title,
        m1_0.won_oscar 
    from
        movie m1_0
2023-11-07T21:06:46.623+01:00 DEBUG 16964 --- [nio-8080-exec-1] org.hibernate.SQL                        : 
    select
        f1_0.id,
        f1_0.name,
        f1_0.since 
    from
        film_studio f1_0 
    where
        f1_0.id=?
2023-11-07T21:06:46.624+01:00 TRACE 16964 --- [nio-8080-exec-1] org.hibernate.orm.jdbc.bind              : binding parameter [1] as [BIGINT] - [1]
2023-11-07T21:06:46.625+01:00 DEBUG 16964 --- [nio-8080-exec-1] org.hibernate.SQL                        : 
    select
        f1_0.id,
        f1_0.name,
        f1_0.since 
    from
        film_studio f1_0 
    where
        f1_0.id=?
2023-11-07T21:06:46.625+01:00 TRACE 16964 --- [nio-8080-exec-1] org.hibernate.orm.jdbc.bind              : binding parameter [1] as [BIGINT] - [2]
2023-11-07T21:06:46.626+01:00 DEBUG 16964 --- [nio-8080-exec-1] org.hibernate.SQL                        : 
    select
        a1_0.movie_id,
        a1_1.id,
        a1_1.birthday,
        a1_1.name,
        a1_1.won_oscar 
    from
        movie_actor a1_0 
    join
        actor a1_1 
            on a1_1.id=a1_0.actor_id 
    where
        a1_0.movie_id=?
2023-11-07T21:06:46.627+01:00 TRACE 16964 --- [nio-8080-exec-1] org.hibernate.orm.jdbc.bind              : binding parameter [1] as [BIGINT] - [2]
2023-11-07T21:06:46.628+01:00 DEBUG 16964 --- [nio-8080-exec-1] org.hibernate.SQL                        : 
    select
        a1_0.movie_id,
        a1_1.id,
        a1_1.birthday,
        a1_1.name,
        a1_1.won_oscar 
    from
        movie_actor a1_0 
    join
        actor a1_1 
            on a1_1.id=a1_0.actor_id 
    where
        a1_0.movie_id=?
2023-11-07T21:06:46.628+01:00 TRACE 16964 --- [nio-8080-exec-1] org.hibernate.orm.jdbc.bind              : binding parameter [1] as [BIGINT] - [1]
2023-11-07T21:06:46.631+01:00  INFO 16964 --- [nio-8080-exec-1] d.l.s.d.controller.MovieRESTController   : My request to DB was executed Thread: http-nio-8080-exec-1 Controller: de.lion5.spring.dvd.controller.MovieRESTController@2fe946f
2023-11-07T21:06:47.536+01:00 DEBUG 16964 --- [l-1 housekeeper] com.zaxxer.hikari.pool.HikariPool        : HikariPool-1 - Pool stats (total=10, active=0, idle=10, waiting=0)
2023-11-07T21:06:47.536+01:00 DEBUG 16964 --- [l-1 housekeeper] com.zaxxer.hikari.pool.HikariPool        : HikariPool-1 - Fill pool skipped, pool has sufficient level or currently being filled (queueDepth=0).
```

As before, we see the queries for getting all the movies and the two film studios (EAGER fetching
per default).
Now we configured to also fetch the actors eagerly (means whenever I read the movie list I read also
the film studio and the actors in separate queries..). Look also at the type of queries, joins over
several tables - a performance nightmare...
This effect is even transitively :( Could result in reading whole subtrees of the database without
needing it, e.g. also change the association in Actor

```java
  @ManyToMany(fetch = FetchType.EAGER, mappedBy = "actors", cascade = {MERGE, PERSIST})
private List<Movie> movies;
```

and you get the following result - even worse:

```agsl
2023-11-07T21:13:41.547+01:00  INFO 13888 --- [nio-8080-exec-1] d.l.s.d.controller.MovieRESTController   : Try to access the DB  Thread: http-nio-8080-exec-1 Controller: de.lion5.spring.dvd.controller.MovieRESTController@2e16664a
2023-11-07T21:13:41.679+01:00 DEBUG 13888 --- [nio-8080-exec-1] org.hibernate.SQL                        : 
    select
        m1_0.id,
        m1_0.cover_image,
        m1_0.film_studio_id,
        m1_0.release_year,
        m1_0.title,
        m1_0.won_oscar 
    from
        movie m1_0
2023-11-07T21:13:41.681+01:00 DEBUG 13888 --- [nio-8080-exec-1] org.hibernate.SQL                        : 
    select
        f1_0.id,
        f1_0.name,
        f1_0.since 
    from
        film_studio f1_0 
    where
        f1_0.id=?
2023-11-07T21:13:41.681+01:00 TRACE 13888 --- [nio-8080-exec-1] org.hibernate.orm.jdbc.bind              : binding parameter [1] as [BIGINT] - [1]
2023-11-07T21:13:41.682+01:00 DEBUG 13888 --- [nio-8080-exec-1] org.hibernate.SQL                        : 
    select
        f1_0.id,
        f1_0.name,
        f1_0.since 
    from
        film_studio f1_0 
    where
        f1_0.id=?
2023-11-07T21:13:41.683+01:00 TRACE 13888 --- [nio-8080-exec-1] org.hibernate.orm.jdbc.bind              : binding parameter [1] as [BIGINT] - [2]
2023-11-07T21:13:41.684+01:00 DEBUG 13888 --- [nio-8080-exec-1] org.hibernate.SQL                        : 
    select
        a1_0.movie_id,
        a1_1.id,
        a1_1.birthday,
        a1_1.name,
        a1_1.won_oscar 
    from
        movie_actor a1_0 
    join
        actor a1_1 
            on a1_1.id=a1_0.actor_id 
    where
        a1_0.movie_id=?
2023-11-07T21:13:41.684+01:00 TRACE 13888 --- [nio-8080-exec-1] org.hibernate.orm.jdbc.bind              : binding parameter [1] as [BIGINT] - [2]
2023-11-07T21:13:41.685+01:00 DEBUG 13888 --- [nio-8080-exec-1] org.hibernate.SQL                        : 
    select
        m1_0.actor_id,
        m1_1.id,
        m1_1.cover_image,
        f1_0.id,
        f1_0.name,
        f1_0.since,
        m1_1.release_year,
        m1_1.title,
        m1_1.won_oscar 
    from
        movie_actor m1_0 
    join
        movie m1_1 
            on m1_1.id=m1_0.movie_id 
    left join
        film_studio f1_0 
            on f1_0.id=m1_1.film_studio_id 
    where
        m1_0.actor_id=?
2023-11-07T21:13:41.685+01:00 TRACE 13888 --- [nio-8080-exec-1] org.hibernate.orm.jdbc.bind              : binding parameter [1] as [BIGINT] - [1]
2023-11-07T21:13:41.686+01:00 DEBUG 13888 --- [nio-8080-exec-1] org.hibernate.SQL                        : 
    select
        a1_0.movie_id,
        a1_1.id,
        a1_1.birthday,
        a1_1.name,
        a1_1.won_oscar 
    from
        movie_actor a1_0 
    join
        actor a1_1 
            on a1_1.id=a1_0.actor_id 
    where
        a1_0.movie_id=?
2023-11-07T21:13:41.687+01:00 TRACE 13888 --- [nio-8080-exec-1] org.hibernate.orm.jdbc.bind              : binding parameter [1] as [BIGINT] - [1]
2023-11-07T21:13:41.688+01:00 DEBUG 13888 --- [nio-8080-exec-1] org.hibernate.SQL                        : 
    select
        m1_0.actor_id,
        m1_1.id,
        m1_1.cover_image,
        f1_0.id,
        f1_0.name,
        f1_0.since,
        m1_1.release_year,
        m1_1.title,
        m1_1.won_oscar 
    from
        movie_actor m1_0 
    join
        movie m1_1 
            on m1_1.id=m1_0.movie_id 
    left join
        film_studio f1_0 
            on f1_0.id=m1_1.film_studio_id 
    where
        m1_0.actor_id=?
2023-11-07T21:13:41.688+01:00 TRACE 13888 --- [nio-8080-exec-1] org.hibernate.orm.jdbc.bind              : binding parameter [1] as [BIGINT] - [2]
2023-11-07T21:13:41.691+01:00  INFO 13888 --- [nio-8080-exec-1] d.l.s.d.controller.MovieRESTController   : My request to DB was executed Thread: http-nio-8080-exec-1 Controller: de.lion5.spring.dvd.controller.MovieRESTController@2e16664a
```

So I agree this is a functional solution, but not from a performance perspective.
Redo the changes in `Actor` and `Movie` and also change the fetch type of the FillStudio to `LAZY`.

```java
  @JsonIgnore
@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY) // default fetch type: EAGER
private FilmStudio filmStudio;
```

You should not be surprised - you get an exception as before, but the sql queries for the film
studios are gone:

```agsl
2023-11-07T21:21:53.825+01:00  INFO 5720 --- [nio-8080-exec-1] d.l.s.d.controller.MovieRESTController   : Try to access the DB  Thread: http-nio-8080-exec-1 Controller: de.lion5.spring.dvd.controller.MovieRESTController@213368be
2023-11-07T21:21:54.082+01:00 DEBUG 5720 --- [nio-8080-exec-1] org.hibernate.SQL                        : 
    select
        m1_0.id,
        m1_0.cover_image,
        m1_0.film_studio_id,
        m1_0.release_year,
        m1_0.title,
        m1_0.won_oscar 
    from
        movie m1_0
2023-11-07T21:21:54.113+01:00 ERROR 5720 --- [nio-8080-exec-1] o.a.c.c.C.[.[.[/].[dispatcherServlet]    : Servlet.service() for servlet [dispatcherServlet] in context with path [] threw exception [Request processing failed: org.hibernate.LazyInitializationException: failed to lazily initialize a collection of role: de.lion5.spring.dvd.model.Movie.actors: could not initialize proxy - no Session] with root cause

org.hibernate.LazyInitializationException: failed to lazily initialize a collection of role: de.lion5.spring.dvd.model.Movie.actors: could not initialize proxy - no Session
	at org.hibernate.collection.spi.AbstractPersistentCollection.throwLazyInitializationException(AbstractPersistentCollection.java:635) ~[hibernate-core-6.2.9.Final.jar:6.2.9.Final]
	at org.hibernate.collection.spi.AbstractPersistentCollection.withTemporarySessionIfNeeded(AbstractPersistentCollection.java:218) ~[hibernate-core-6.2.9.Final.jar:6.2.9.Final]
	at org.hibernate.collection.spi.AbstractPersistentCollection.readSize(AbstractPersistentCollection.java:148) ~[hibernate-core-6.2.9.Final.jar:6.2.9.Final]
	at org.hibernate.collection.spi.PersistentBag.size(PersistentBag.java:355) ~[hibernate-core-6.2.9.Final.jar:6.2.9.Final]
	at de.lion5.spring.dvd.service.MovieService.lambda$findAllWithMoreThanNActors$0(MovieService.java:23) ~[main/:na]
```

Okay what could we do next??

- Misusing Transactions... (short spoiler: also not recommended)

```java
  @Transactional
public List<Movie> findAllWithMoreThanNActors(final int noOfActors){
```

Changing the method in `MovieService` to be transactional - whatever this means right now. Execute
the test again.

```agsl
2023-11-07T21:27:19.715+01:00  INFO 5240 --- [nio-8080-exec-1] d.l.s.d.controller.MovieRESTController   : Try to access the DB  Thread: http-nio-8080-exec-1 Controller: de.lion5.spring.dvd.controller.MovieRESTController@64d511b8
2023-11-07T21:27:19.861+01:00 DEBUG 5240 --- [nio-8080-exec-1] org.hibernate.SQL                        : 
    select
        m1_0.id,
        m1_0.cover_image,
        m1_0.film_studio_id,
        m1_0.release_year,
        m1_0.title,
        m1_0.won_oscar 
    from
        movie m1_0
2023-11-07T21:27:19.879+01:00 DEBUG 5240 --- [nio-8080-exec-1] org.hibernate.SQL                        : 
    select
        a1_0.movie_id,
        a1_1.id,
        a1_1.birthday,
        a1_1.name,
        a1_1.won_oscar 
    from
        movie_actor a1_0 
    join
        actor a1_1 
            on a1_1.id=a1_0.actor_id 
    where
        a1_0.movie_id=?
2023-11-07T21:27:19.879+01:00 TRACE 5240 --- [nio-8080-exec-1] org.hibernate.orm.jdbc.bind              : binding parameter [1] as [BIGINT] - [1]
2023-11-07T21:27:19.881+01:00 DEBUG 5240 --- [nio-8080-exec-1] org.hibernate.SQL                        : 
    select
        a1_0.movie_id,
        a1_1.id,
        a1_1.birthday,
        a1_1.name,
        a1_1.won_oscar 
    from
        movie_actor a1_0 
    join
        actor a1_1 
            on a1_1.id=a1_0.actor_id 
    where
        a1_0.movie_id=?
2023-11-07T21:27:19.881+01:00 TRACE 5240 --- [nio-8080-exec-1] org.hibernate.orm.jdbc.bind              : binding parameter [1] as [BIGINT] - [2]
2023-11-07T21:27:19.886+01:00  INFO 5240 --- [nio-8080-exec-1] d.l.s.d.controller.MovieRESTController   : My request to DB was executed Thread: http-nio-8080-exec-1 Controller: de.lion5.spring.dvd.controller.MovieRESTController@64d511b8
```

Yippie - we have a working solution with only a single annotation - feels like magic, but why does
it work?
Like the OSIV, `@Transactional` keeps the DB session (spans a transaction) after executing the first
sql statement. So
LAZY associated
can be fetched as long as we are within this transaction context (within the method).
Uncomment the transcation related logging in `application.properties`. You may see something similar
like the following output:

```agsl
2023-11-07T21:32:55.542+01:00  INFO 4324 --- [nio-8080-exec-1] d.l.s.d.controller.MovieRESTController   : Try to access the DB  Thread: http-nio-8080-exec-1 Controller: de.lion5.spring.dvd.controller.MovieRESTController@39dbbe32
2023-11-07T21:32:55.543+01:00 TRACE 4324 --- [nio-8080-exec-1] o.s.t.i.TransactionInterceptor           : Getting transaction for [de.lion5.spring.dvd.service.MovieService.findAllWithMoreThanNActors]
2023-11-07T21:32:55.547+01:00 TRACE 4324 --- [nio-8080-exec-1] o.s.t.i.TransactionInterceptor           : Getting transaction for [org.springframework.data.jpa.repository.support.SimpleJpaRepository.findAll]
2023-11-07T21:32:55.668+01:00 DEBUG 4324 --- [nio-8080-exec-1] org.hibernate.SQL                        : 
    select
        m1_0.id,
        m1_0.cover_image,
        m1_0.film_studio_id,
        m1_0.release_year,
        m1_0.title,
        m1_0.won_oscar 
    from
        movie m1_0
2023-11-07T21:32:55.690+01:00 TRACE 4324 --- [nio-8080-exec-1] o.s.t.i.TransactionInterceptor           : Completing transaction for [org.springframework.data.jpa.repository.support.SimpleJpaRepository.findAll]
2023-11-07T21:32:55.691+01:00 DEBUG 4324 --- [nio-8080-exec-1] org.hibernate.SQL                        : 
    select
        a1_0.movie_id,
        a1_1.id,
        a1_1.birthday,
        a1_1.name,
        a1_1.won_oscar 
    from
        movie_actor a1_0 
    join
        actor a1_1 
            on a1_1.id=a1_0.actor_id 
    where
        a1_0.movie_id=?
2023-11-07T21:32:55.692+01:00 TRACE 4324 --- [nio-8080-exec-1] org.hibernate.orm.jdbc.bind              : binding parameter [1] as [BIGINT] - [1]
2023-11-07T21:32:55.693+01:00 DEBUG 4324 --- [nio-8080-exec-1] org.hibernate.SQL                        : 
    select
        a1_0.movie_id,
        a1_1.id,
        a1_1.birthday,
        a1_1.name,
        a1_1.won_oscar 
    from
        movie_actor a1_0 
    join
        actor a1_1 
            on a1_1.id=a1_0.actor_id 
    where
        a1_0.movie_id=?
2023-11-07T21:32:55.693+01:00 TRACE 4324 --- [nio-8080-exec-1] org.hibernate.orm.jdbc.bind              : binding parameter [1] as [BIGINT] - [2]
2023-11-07T21:32:55.694+01:00 TRACE 4324 --- [nio-8080-exec-1] o.s.t.i.TransactionInterceptor           : Completing transaction for [de.lion5.spring.dvd.service.MovieService.findAllWithMoreThanNActors]
2023-11-07T21:32:55.697+01:00  INFO 4324 --- [nio-8080-exec-1] d.l.s.d.controller.MovieRESTController   : My request to DB was executed Thread: http-nio-8080-exec-1 Controller: de.lion5.spring.dvd.controller.MovieRESTController@39dbbe32
2023-11-07T21:32:55.978+01:00 DEBUG 4324 --- [l-1 housekeeper] com.zaxxer.hikari.pool.HikariPool        : HikariPool-1 - Pool stats (total=10, active=0, idle=10, waiting=0)
```

We should be aware that we misuse transactions here to keep the db session and enable LAZY fetching.
And we still need three sql statements - Can we do better? YES WE CAN :)

- The gold standard and solution - `NamedEntityGraphs`