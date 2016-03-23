Three more configuration files has to be configured and put in the classpath.
<br />
## logback.xml: configuration for logback logging ##
Every request is identified by a unique id (which is unique for every JVM) and it is recommended to log this request id. It is possible to differentiate the requests with this id if analyzing the code is needed.

An example pattern:
```
<pattern>%d{HH:mm:ss.SSS}->%logger{35} - %-5level [%X{reqId}] - %msg %n</pattern>
```

Sample:

```
<configuration debug="true" scan="true" scanPeriod="2 seconds">

   <appender name="FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
      <file>Throttling_logback.log</file>
      <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
         <fileNamePattern>Throttling_logback_%d{yyyy-MM-dd}.log</fileNamePattern>
         <maxHistory>30</maxHistory>
      </rollingPolicy>
      <encoder>
         <pattern>%d{HH:mm:ss.SSS}->%logger{35} - %-5level [%X{reqId}] - %msg %n</pattern>
      </encoder>
   </appender>

   <appender name="FILE_WEB" class="ch.qos.logback.core.rolling.RollingFileAppender">
      <file>Throttling_Web_logback.log</file>
      <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
         <fileNamePattern>Throttling_Web_logback_%d{yyyy-MM-dd}.log</fileNamePattern>
         <maxHistory>30</maxHistory>
      </rollingPolicy>
      <encoder>
         <pattern>%d{HH:mm:ss.SSS}->%logger{35} - %-5level - %msg %n</pattern>
      </encoder>
   </appender>

   <logger name="Throttling-Web" level="DEBUG">
      <appender-ref ref="FILE_WEB" />
   </logger>
   
   <root level="TRACE">
      <appender-ref ref="FILE" />
   </root>

</configuration>
```
<br />

## shiro.ini: configuration file for configuring Apache Shiro ##
Apache Shire is used as a Java security framework to perform authentication, authorization.
More information: http://shiro.apache.org/

Sample:

```
[main]
# specify login page
authc.loginUrl = /jsp/login.jsp
authc.successUrl  = index.action

# name of request parameter with username; if not present filter assumes 'username'
authc.usernameParam=user
# name of request parameter with password; if not present filter assumes 'password'
authc.passwordParam=passw
# does the user wish to be remembered?; if not present filter assumes 'rememberMe'
authc.rememberMeParam = remember

# redirect after successful login
#authc.successUrl = /jsp/index.jsp
[urls]
# enable authc filter for all application pages
/** = authc

[users]
adminstrator=throttling,Administrator
```
<br />

## configuration file(s) for configuring the specific cache engine ##

See in [Cache Implementation](CacheImplementation.md)