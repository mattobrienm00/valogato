There is some data that has to be stored somewhere. The two groups of the data are:
  * real-time statistic information
    * how many requests are being served by the backend service
  * the number of the waiting requests
  * the queue of the waiting requests
    * administration data about the backend service [Backend service configuration XML](BackendServiceConfigXML.md)

There are two vital requirements how / where to store the data:
  * quick access
  * simulated services in different servers (JVMs) must see the same data

So a distributed cache engine (coherence, memcached, hazelcast or ehcache) is needed in the case if we expect a high number of client requests so more simulated services have to be used to serve these requests. These simulated services that pretend to be the same backend service are on different servers (JVMs) but a common cache space is needed where all the simulated services will see the same statistics data (e.g. how many requests are served by the real service at the moment).

If the simulated service(s) will be deployed to the same server then we don’t need a distributed cache to be deployed and maintained but the Valogato throttling system will take care of the caching (LocalCache). Important note: the web application has to be deployed on the same server too!