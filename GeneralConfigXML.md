The general config file is for storing the general configuration.

You can add this file to the classpath by:
  * bundling it to the simulated service package (JAR/WAR)
  * putting it in a shared library folder of the application server

Three things can be defined in this configuration file:
  1. the source type of the backend configuration - the value is usually CACHE
  1. the source type of the statistic information - the value is usually CACHE
  1. the name of the cache implementation and the name of the cache - this property is the most important one in this config file and being explained hereinafter

The possible cache implementations are:
  * **Hazelcast** - no need to add any parameter; the hazelcast-client.properties should be added to the classpath (see: [CacheImplementation](CacheImplementation.md))
  * **Terracotta** - a parameter named 'distributedCacheName' should be added as a parameter (see the sample below), this value will be used as the name of the cache in ehcace, this name must be the same as in ehcache.xml
> > Sample:
```
<ehcache name="THROTTLING_DISTRIBUTED_STORE" monitoring="autodetect" dynamicConfig="true">
  <cache ...>
    ...
  </cache>
</ehcache>
```
> > The ehcache.xml and tc-config.xml should be added to the classpath (see: [CacheImplementation](CacheImplementation.md)).
  * **Dummy** - only for testing the web application
  * **Coherence** - a parameter named 'distributedCacheName' should be added as a parameter (see the sample below), this value will be used as the name of the cache in Coherence
> > The distributedCacheName is used in the Coherence cache config XML (xmlns="http://xmlns.oracle.com/coherence/coherence-cache-config").
> > Sample:
```
<cache-config xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns="http://xmlns.oracle.com/coherence/coherence-cache-config" xsi:schemaLocation="http://xmlns.oracle.com/coherence/coherence-cache-config coherence-cache-config.xsd">
  <caching-scheme-mapping>
    <cache-mapping>
      <cache-name>THROTTLING_DISTRIBUTED_STORE</cache-name>
      <scheme-name>distributed</scheme-name>
    </cache-mapping>
    ... 
```
> > The Coherence cache config XML (xmlns="http://xmlns.oracle.com/coherence/coherence-cache-config") and operational config XML (xmlns="http://xmlns.oracle.com/coherence/coherence-operational-config") have to be added to the classpath. See more information: [Cache Implementation](CacheImplementation.md)
  * **Memcached** - the next parameters has to be configured:
    * servers: the used memcache servers
    * hashingAlg: Sets the hashing algorithm we will use; values: CONSISTENT\_HASH (MD5 Based - Stops thrashing when a server added or removed), OLD\_COMPAT\_HASH (original compatibility hashing algorithm (works with other clients)), NEW\_COMPAT\_HASH (new CRC32 based compatibility hashing algorithm (works with other clients)), NATIVE\_HASH (native String.hashCode)
    * failover: turn off auto-failover in event of server down
    * initConn: sets the initial number of connections per server in the available pool
    * minConn: sets the minimum number of spare connections to maintain in our available pool
    * maxConn: Sets the maximum number of spare connections allowed in our available pool
    * maintSleep: Set the sleep time between runs of the pool maintenance thread. If set to 0, then the maint thread will not be started.
    * socketTO: Sets the socket timeout for reads.
    * aliveCheck: Sets the aliveCheck flag for the pool. When true, this will attempt to talk to the server on every connection checkout to make sure the connection is still valid. This adds extra network chatter and thus is defaulted off. May be useful if you want to ensure you do not have any problems talking to the server on a dead connection.
> > > Sample:
```
<cache type="Memcached">
  <param name="servers">localhost:11241,localhost:11242</param>
  <param name="hashingAlg">CONSISTENT_HASH</param>
  <param name="failover">true</param>
  <param name="initConn">10</param>
  <param name="minConn">5</param>
  <param name="maxConn">1000</param>
  <param name="maintSleep">30</param>
  <param name="nagle">true</param>
  <param name="socketTO">3000</param>
  <param name="aliveCheck">false</param>
</cache>
```

  * **LocalCache** - no need to add any parameter

Sample XML:
```
<generalConfiguration>
    
    <!-- DUMMY, CACHE -->
    <backendserviceConfigSource>CACHE</backendserviceConfigSource>
    
    <!-- for usage + waiting request lists -->
    <!-- DUMMY, CACHE -->
    <statisticsSource>CACHE</statisticsSource>
    
    <!--cache type="Hazelcast"/-->
    <!--cache type="Terracotta">
        <param name="distributedCacheName">THROTTLING_DISTRIBUTED_STORE</param>
    </cache-->
    <!--cache type="Dummy"/-->
    <!--cache type="Coherence">
        <param name="distributedCacheName">THROTTLING_DISTRIBUTED_STORE</param>
    </cache-->
    <!--cache type="Memcached">
        <param name="servers">localhost:11241,localhost:11242</param>
        <param name="hashingAlg">CONSISTENT_HASH</param>
        <param name="failover">true</param>
        <param name="initConn">10</param>
        <param name="minConn">5</param>
        <param name="maxConn">1000</param>
        <param name="maintSleep">30</param>
        <param name="nagle">true</param>
        <param name="socketTO">3000</param>
        <param name="aliveCheck">false</param>
    </cache-->
    <cache type="LocalCache" />
    
</generalConfiguration>
```

Sample configuration files: [Downloads](Downloads.md)