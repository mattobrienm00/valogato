One way of using the Valogato Throttling System is to create a simulated service of the real backend service.
Any kind of backend service protocol is supported. So the backend service can be :
  * web service
  * XML over HTTP service
  * REST service
  * and so on

There are two configuration XML file that must be created and put to the classpath:
  * one for general configuration  - [General configuration XML](GeneralConfigXML.md)
  * other one for backend service configuration - [Backend service configuration XML](BackendServiceConfigXML.md)
Configuration files must be put to the classpath for the specific cache implementation, logback and Apache Shiro.

Third-party jars must be added to the classpath too. See the detailed list: [Third party JARs](ThirdPartyJars.md)

Maven can be used for developing. More information: [How to use Maven?](HowToUseMaven.md)

Advantage:
  * no impact on the clients (except modifying the endpoint URL)
Disadvantage:
  * the request must travel through an extra station => it can slow down the communication (e.g. additional XML un/marshalling)