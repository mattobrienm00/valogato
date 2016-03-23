There is some third-party JAR is needed to use the Valogato throttling system.
First of all a cache engine is needed if distributed cache has to be used. You can see in the list below which JAR(s) must be put to the classpath depending on the selected cache engine.
You have to add only those cache JARs to the classpath that you want to use (e.g. if the cache engine is hazelcast then you need hazelcast-2.6.jar and hazelcast-client-2.6.jar). You have to copy the cache JARs to the lib directory of the WAR file.

Usually the Valogato sytem is used in application server. In these cases the JARs should add to the classpath to specific application server. It depends on the container how it should be done. There is short description how to do that on JBOSS 7: http://javaisourfriend.blogspot.co.uk/2013/08/using-valogato-throttling-system-on.html

The next third-party JARs are needed in the classpath:

  * hazelcast-2.6.jar (for Hazelcast)
  * hazelcast-client-2.6.jar (for Hazelcast)

  * ehcache-core-2.6.6.jar (for Ehcache)
  * ehcache-terracotta-2.6.6.jar (for Ehcache)
  * terracotta-toolkit-1.6-runtime-5.5.0.jar (for Ehcache)

  * Memcached-Java-Client-3.0.2.jar (for memcached)

  * coherence.jar (for Oracle Coherence)

  * ehcache-core-2.6.6.jar (for local caching)

  * logback-classic-1.0.13.jar (for Logback)
  * logback-core-1.0.13.jar (for Logback)
  * slf4j-api-1.7.5.jar (for SLF4J)
  * gson-2.2.3.jar (for Google Gson)
  * guava-14.0-rc1.jar (for Google Guava)
  * simple-xml-2.6.4.jar (for Simpleframework)