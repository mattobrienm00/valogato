## Valogato throttling system ##

_New version (31/12/2013) -> v1.1 : [Whats's new](WhatsNew.md)_

We keep hearing "Throttling is very important to protect our backend systems!". Ok, perhaps it is but why is it important and what is the throtttling at all??

Just imagine a situation that we want to pour wine into a bottle with the help of a funnel. If we pour too much wine into the funnel then it cannot flow into the bottle but the funnel will be full and we will be (very-very) sad that a lot of wine will get to floor…
So there is a specific velocity how the wine can be poured otherwise a catastrophe happens. There is no backend system / service that can serve infinite amount of requests. On the other hand the backend system must be prepared to serve higher amount of requests than usual.
It is not allowed to overwhelm the backend systems with requests so the number of calls must be controlled.

As the number of the client requests increase the danger of backend service not being able to serve these requests can rise and eventually crashing the system and therefore increasing the response time (it gets more than expected).

![http://wiki.valogato.googlecode.com/hg/images/9-start.jpg](http://wiki.valogato.googlecode.com/hg/images/9-start.jpg)

The basic idea is to put an intermediate station (a Valogato simulated service) between the client and the service (the throttling can be used directly in your client code too). If the number of the served requests is within a specific limit the intermediate station will allow the request to go through otherwise a logic (feature) will be executed by the simulated service.
See more information about the features: http://javaisourfriend.blogspot.co.uk/2013/08/java-throttling-valogato.html

The Valogato throttling system can help you to protect your services / systems against the unexpected numbers of requests by
  * putting the requests (above a specific limit) in a queue and make them wait for a small amount of time
  * using a different endpoint(s) for the call
  * letting the client handle the situation.
You can view real-time statistic information in a web application about how the throttling system works and you can modify the system access properties too. See more: [Using the Administration Web Application](UsingWebApplication.md)

So the basic keyword of the Valogato is **lightweight**! You just have to deploy a service to an existing application server and that is! No need any new infrastructure or engine. Valogato is so lightweight that you even don't need an intermediate server as it can be used directly in the client so the throttling will happen on the client side.

The supported cache engines: ehcache with Terracotta Server Array (http://ehcache.org/), hazelcast (http://www.hazelcast.com/), Oracle Coherence (http://coherence.oracle.com/), memcached (http://memcached.org/) and local cache (not a distributed cache!).
More information see: [Why is cache needed](WhyCacheNeeded.md).

Maven can be used for developing. More information: [How to use Maven](HowToUseMaven.md)

It is pretty easy to implement a simulated service and create a cache for storing the data. A brief description can be found here: [Basic steps for starting the work with the system](Steps.md)

Downloading artifacts: [Downloads](Downloads.md)

See more information:
https://code.google.com/p/valogato/wiki/Introduction
http://javaisourfriend.blogspot.co.uk/2013/08/java-throttling-valogato.html

Any question: ric.flair.wcw@gmail.com