# JPA Issues

Important when starting the JVM is to add VM parameters to turn on housekeeping of hikari at a
regular basis to see different behavior of OSIV, Transactions etc...
`-Dcom.zaxxer.hikari.housekeeping.periodMs=1000` to run the housekeeping every second.

. . . work in progress . . .