At the moment 5 cache implementations are supported by the Valogato throttling system:
  * Ehcache
  * Hazelcast
  * memcached
  * Oracle Coherence
  * local cache.

The 4 of the 5 caches support to create distributed cache and the local cache is for using cache only in one JVM (not supporting distributed cache!).

Please be aware of what JARs you need to put to the classpath! -> [Third Party Jars](ThirdPartyJars.md)

## Terracotta Ehcache 3.7.5 ##

To implement a distributed cache with ehcache we need the Terracotta Server Array (http://terracotta.org/downloads/open-source/catalog). After installing it you can start the server by executing this command:

`terracotta-3.7.5/bin/start-tc-server.bat -f tc-config.xml`

The file tc-config.xml contains the configuration data of the server.
Sample configuration:
```
<tc:tc-config xmlns:tc="http://www.terracotta.org/config" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://www.terracotta.org/schema/terracotta-5.xsd">
   <servers>
      <server name="Server1" host="localhost">
         <data>d:/utils/terracotta-3.7.5/terracotta/server1-data</data>
         <dso-port>8510</dso-port>
         <jmx-port>8520</jmx-port>
         <l2-group-port>8530</l2-group-port>
         <dso>
            <client-reconnect-window>120</client-reconnect-window>
            <persistence>
               <mode>permanent-store</mode>
            </persistence>
         </dso>
      </server>
      <ha>
         <mode>networked-active-passive</mode>
         <networked-active-passive>
            <election-time>5</election-time>
         </networked-active-passive>
      </ha>
   </servers>
</tc:tc-config>
```

You also have to define an other configuration file on the client side (simulated services, web application). A sample ehcache.xml configuration file:

```
<ehcache name="THROTTLING_DISTRIBUTED_STORE" monitoring="autodetect" dynamicConfig="true">
   <cache maxElementsInMemory="500" eternal="true" memoryStoreEvictionPolicy="LRU" name="THROTTLING_DISTRIBUTED_STORE">
      <persistence strategy="distributed" synchronousWrites="true"/>
      <terracotta clustered="true" synchronousWrites="true" consistency="strong">
         <nonstop enabled="true" immediateTimeout="true"/>
      </terracotta>
   </cache>
   <terracottaConfig url="localhost:8510" rejoin="true" />
</ehcache>
```

More information: http://terracotta.org/downloads/open-source/catalog


## Hazelcast 2.6 ##

In order to use Hazelcast no server is needed to install or start. All you have to do is to add the hazelcast-2.6.jar to the classpath.
One thing is important to note: if you want to use only one running Hazelcast per application server instance (we need only one) then you have to copy the JAR into the lib folder of your app server. If the Hazelcast JAR is copied to the JAR or WAR file then you will have as many active Hazelcast instances as many simulated services or applications are deployed to the application server. For example: if the web application and 4 simulated services are deployed to the same server then we will have 5 Hazelcast instances.

A sample config file for Hazelcast client (hazelcast-client.properties):

```
hazelcast.client.group.name                 = dev
hazelcast.client.group.pass                 = dev-pass
hazelcast.client.connection.timeout         = 30000
hazelcast.client.connection.attempts.limit  = 3
hazelcast.client.reconnection.timeout       = 5000
hazelcast.client.reconnection.attempts.limit= 5
hazelcast.client.shuffle.addresses          = false
hazelcast.client.update.automatic           = true
hazelcast.client.addresses                  = 127.0.0.1:3701
```

More information: http://www.hazelcast.com/


## memcahced ##

After installing the memcached server (e.g. Linux: https://www.digitalocean.com/community/articles/how-to-install-and-use-memcache-on-ubuntu-12-04, Windows: http://zurmo.org/wiki/installing-memcache-on-windows; by the way the memcached install on windows is not a supported way) you can start it with the next command (I used the windows version of the memcached.):

```
memcached-win-1.4.4-14/memcached.exe -p 11242 -vvv
```

You can configure the memcached client in the general config XML file ([Setting the General Configuration XML file](GeneralConfigXML.md)).
It is important to note that the memcached client determines which server will be used to store a specific key (by using a hashing algorythm). That's why it is vital to use the same server list (
```
<param name="servers">server1:111,server0:112,...</param>
```
) on each clients! For example: if a specific server is used only in one client then that server cannot be accessed fom the other clients.


## Oracle Coherence ##

A config folder should be created in the Coherence home directory and two configuration file should be put there:
  * Coherence cache config XML (where xmlns="http://xmlns.oracle.com/coherence/coherence-cache-config")
  * operational config XML (xmlns="http://xmlns.oracle.com/coherence/coherence-operational-config")

**sample Coherence cache config XML**

```
<cache-config xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns="http://xmlns.oracle.com/coherence/coherence-cache-config" xsi:schemaLocation="http://xmlns.oracle.com/coherence/coherence-cache-config coherence-cache-config.xsd">
  <caching-scheme-mapping>
    <cache-mapping>
      <cache-name>THROTTLING_DISTRIBUTED_STORE</cache-name>
      <scheme-name>distributed</scheme-name>
    </cache-mapping>
  </caching-scheme-mapping>
  <caching-schemes>
    <distributed-scheme>
      <scheme-name>distributed</scheme-name>
      <service-name>DistributedCache</service-name>
      <backing-map-scheme>
        <local-scheme/>
      </backing-map-scheme>
      <autostart>true</autostart>
    </distributed-scheme>
  </caching-schemes>
</cache-config>
```

**sample Coherence operational config XML**

```
<coherence xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns="http://xmlns.oracle.com/coherence/coherence-operational-config" xsi:schemaLocation="http://xmlns.oracle.com/coherence/coherence-operational-config coherence-operational-config.xsd">
  <cluster-config>
    <member-identity>
      <cluster-name system-property="tangosol.coherence.cluster">valogato-throttling-cluster</cluster-name>
    </member-identity>
  </cluster-config>
  <configurable-cache-factory-config>
    <init-params>
      <init-param>
        <param-type>java.lang.String</param-type>
        <param-value system-property="tangosol.coherence.cacheconfig">valogato-coherence-cache-config.xml</param-value>
      </init-param>
    </init-params>
  </configurable-cache-factory-config>  
</coherence>
```

You have to make sure that that the content of the **config** folder is on the classpath when starting the Oracle Coherence server. For example: modify the **cache-server.bat** bat:
```
%java_exec% -server -showversion %java_opts% -cp "%coherence_home%\config;%coherence_home%\lib\coherence.jar" com.tangosol.net.DefaultCacheServer %1
```

You can start the Oracle Coherence cache server by executing the file **cache-server.bat**.

The operational and the cache config XMLs are used by the clients too so they have to be added to the classpath of the Valogato Throttling System (e.g. copying them to the lib of the application server).


## local cache ##

If you don't need distributed cache (just some reason why not having distributed cache: no need to prepare high load of requests, the simulated services that simulate the same backend service will be installed on the same JVM) then you can use the local cache.
In the background ehcache 2.6 is used for caching.

The obvious advantage of the local cache that you don't have build any infrastructure to have a cache (no need the install and run a cache server). The only task you have to do is to put the necessary JAR file to the classpath ([Third Party Jars](ThirdPartyJars.md)) and that's it.

The disadvantage is that you have to deploy all the simulated service that uses the same backend service to the same JVM.

Let's see an example: there are two backend service: BigSystem and FragileSystem. There are 3 simulated services: BigSimulatedSystem\_FaultBack, BigSimulatedSystem\_Waiting and FragileSimulatedSystem\_Forwarder. Two servers are used: server0 and server1 and the local cache is used. In this situation you have to decide what backend service's data will be stored on which server. If the server0 is used to store the cache data of the BigSystem then all the simulated services that simulate the BigSystem must be deployed to server0 i.e. BigSimulatedSystem\_Waiting and BigSimulatedSystem\_FaultBack (because a simulated service simulating the BigSystem and deployed to server1 cannot access the cache on server0 but it can only use the cache on server1).