The most important requirement against the simulated service is that its interface MUST be the same as the interface of the real interface.
It is vital because the client code mustn't be changed and usually the endpoint of the server service is stored in config files or in properties so it can be easily modified without building and releasing the backend service.
For example in case of web service we can use the same WSDL for implementing our service.

The Valogato Core dependency is needed to implement the simulated service. All we have to do is to add the dependency to our pom.xml as per the next link: [How to use Maven](HowToUseMaven.md). If maven is not used then the JAR can be donwloaded from [here](Downloads.md) and added to the classpath.

Our class must implement the `ISimulatedService` interface. Three classes must be defined for this interface:
  * request class
  * response class
  * exception class (when the backend system cannot be called and no feature can be applied any more then an exception will be thrown to indicate the client that the request won't be processed)

```
@WebService(...)
@SOAPBinding(...)
@Remote(...)
@Stateless
public class GetCustomerByIdService_V2 implements ThrottlingSimulatedPortType, ISimulatedService<GetByIdRequest, GetByIdResponse, GetByIdFault_Exception>
```

There are three methods of the interface ISimulatedService must be implemented:
  1. forwardRequest: It contains the implementation details how the backend service is called.
```
public GetByIdResponse forwardRequest(GetByIdRequest req) throws GetByIdFault_Exception {
   GetCustomerById service = new GetCustomerById(/*wsdlLocation */);
   ThrottlingSimulatedPortType port = service.getThrottlingSimulatedPort();
   BindingProvider bp = (BindingProvider)port;
   bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpoint);
   return port.getById(req);
}
```
  1. buildFault: It contains the implementation details how the exception must be thrown when the backend system cannot be called and no feature can be applied any more.
```
public GetByIdFault_Exception buildFault(String reason) {
   return new GetByIdFault_Exception("Fault from simulated service: The backend service cannot be called! - reason:" + reason);
}
```
  1. setEndpoint: How to set the endpoint of the backend service. (e.g. storing it in an instance variable...)

There is a sample simulated service project in the [Downloads](Downloads.md) section.