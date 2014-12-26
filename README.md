7DaysToDieVip
=============

A simple implementation of a VIP list and global ban for the game 7 Days to Die. With vip List you can define a number of slots reserved for non vip users.

To build, you need JDK8 and Maven.

Change file vip-users.xml to include vip users like this:

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<vipUsers>
    <slotsAvailable>15</slotsAvailable>
    <serverFullKickMessage>Server is full. Become a VIP!</serverFullKickMessage>
    <users>
        <user>
            <steamId>00000000000000001</steamId>
        </user>
        <user>
            <steamId>00000000000000002</steamId>
        </user>
    </users>
</vipUsers>
```

Change file options.xml to setup telnet server address, port, password, etc:

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<options>
    <server>
        <address>192.168.0.11</address>
        <port>8081</port>
        <password>teste</password>
    </server>
    
    <checkInterval>6000</checkInterval>
    <commandTimeout>10000</commandTimeout>
    
    <executors>
        <executor>br.com.sdtd.vip.ban.VipListExecution</executor>
    </executors>
</options>
```

To compile, you need to run (in the main directory with maven): mvn clean install
<br>
In target directory, you will find the compiled application.
To run, execute:

java -jar 7DaysToDieVip.jar

or

java -jar 7DaysToDieVip-jar-with-dependencies.jar
