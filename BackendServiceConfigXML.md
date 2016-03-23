This config file is for storing the configuration of the backend services.

You can add this file to the classpath by:
  * bundling it to the simulated service package (JAR/WAR): only those backend services must be defined that are used in the specific simulated service
  * putting it in a shared library folder of the application server: all the backend services which are used in the simulated services on that application server instance (in the JVM) must be put in the configuration XML

Five throttling attributes of the backend service can be defined here:
  1. name: the name of the backend service
  1. maxLoading: the number of requests which can be served without applying a feature
  1. averageResponseTime: the value of the average response time in milliseconds
  1. feature: the defined feature will be applied if no exception feature has been set
  1. exceptions: exceptions can be defined for specific simulated services in order to use a different feature

This is a sample rule how a feature will be chosen to use (the name of the backend service and simulated service can be defined in the code of the simulated service; see in GetCustomerByIdService\_V2.java in the sample project):
```
IF the backend service *BigSystem* has been called THEN

   IF the simulated service *BigSimulatedSystem_Waiting* has been called THEN the feature *WaitingFeature* will be applied

   ELSE the feature *SendBackFaultFeature* will be applied
```

Sample:
```
<backendServices>

   <!-- ############################# Backend Service Configuration ############################# -->
   <backendService name="BigSystem" maxLoading="2" averageResponseTime="8000">
      <!-- if the backend service is called this feature will be applied unless there is an exception feature defined for a specific simulated service-->
      <feature name="SendBackFaultFeature"/>
	
      <!-- exception features -->
	  <simulatedService name="BigSimulatedSystem_Waiting">
         <feature name="WaitingFeature">
            <param name="period">1000</param>
            <param name="waitingReqListMaxSize">200</param>
            <param name="maxNumberOfWaitingReqs">2000</param>
            <param name="strategy">REGISTERING_REQUESTS_INDIVIDUALLY</param>
         </feature>
      </simulatedService>
   </backendService>

   <backendService name="ImportantSystem" maxLoading="5" averageResponseTime="10000">
      <feature name="ForwarderFeature">
         <param name="endpoints">http://localhost:8029/mockGetCustomerByIdHttpBinding; http://localhost:8030/mockGetCustomerByIdHttpBinding</param>
      </feature>
   </backendService>

</backendServices>
```

Sample files: [Downloads](Downloads.md)