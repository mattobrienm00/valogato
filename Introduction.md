# What is the throttling for? #

It is quite a common problem that the called service runs out of resources when the clients try to call it. This situation can occur even in a well-designed systems too, just imagine a not expected customer interest.

If the backend systems are overwhelmed with requests the possible outcome is that the average response time is getting (not rarely dramatically) bigger or it is even possible that the system will collapse while trying to serve the requests. It is easy to come to the conclusion that it is better to do something with the requests than let the backend system crash.

# How does it work? #

A pretty detailed description can be found in this blog: http://javaisourfriend.blogspot.co.uk/2013/08/java-throttling-valogato.html

Briefly how it works: There are two ways to apply the throttling framework:
  * Using the Valogato framework directly in the service client code, more information: [Using the Valogato framework in the client](DirectUse.md)
  * A fake service must be deployed in front of the service that must be protected. The endpoint URL of the real service has to be changed in the client(s) to refer to this simulated service.

When a client tries to call the backend service the request first arrives to the simulated service (in case of using a simulated service) or the Valogato framework will be applied before calling the backend service (in case of using direct use). Here a decisison will be made whether or not the backend service can be called and if the answer is yes then the request will be forwarded to the real service.
If the answer is no then a feature will be applied. There are three built-in features:

  * **Waiting feature** : waiting for a small period of time and checking again if the backend system can be called
  * **Sendback Fault feature**: sending back a SOAP fault so the client must deal with the situation
  * **Forwarder feature**: calling an other endpoint if the backend system is busy (load balancer function)

The decision being made is based on real-time statistics data how many requests are being processed at the moment. This data is collected for each backend services that are defined in the configuration XML. The condition can be for example:

```
IF max_loading - number_of_processed_requests > 0 
THEN backend can be called 
ELSE backend cannot be called
```

max\_loading : the value is defined in the configuration XML and it means how many requests can be processed maximum in a specific backend service at the same
number\_of\_processed\_requests: how many requests were sent to the backend system but their response haven't arrived back yet


**Architecture**

![http://wiki.valogato.googlecode.com/hg/images/8-architecture.png](http://wiki.valogato.googlecode.com/hg/images/8-architecture.png)