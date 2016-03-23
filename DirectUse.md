The second way of using the Valogato throttling system is to modify the client code and use the throttling logic directly on the client side. There are two obvious facts coming from this use of the Valogato throttling system:
  * this method isn't transparent for the client because all the clients that want to use the throtling directly have to be changed
  * there isn't an intermediate station between the client and backend system so this way of applying the throttling is faster

Using the Valogato throttling system this way is pretty similar like developing a simulated service. The best practice is to put the web service calling logic into a separate class, implement the interface ISimulatedService, add the methods of the interface ISimulatedService to our class and instead of calling the webservice directly call the method processRequest(...) of the class ThrottlingProcessController.
For example:

```
public class WebserviceClient implements ISimulatedService<GetByIdRequest, GetByIdResponse, GetByIdFault_Exception> {
  
  private String endpoint = null;
  private String requestId = null;
  
  public void call() {
    GetByIdRequest request = createGetByIdRequest();
    
    IThrottlingController<GetByIdRequest, GetByIdResponse, GetByIdFault_Exception> controller = 
        new ThrottlingProcessController<GetByIdRequest, GetByIdResponse, GetByIdFault_Exception>();
    String nameBackendService = "BigSystem";
    String simulatedServiceName = "BigSimulatedSystem_Waiting";
    setEndpoint("http://localhost:8028/mockGetCustomerByIdHttpBinding");
    requestId = controller.getRequestId();
    
    try {
      controller.processRequest(request, this, nameBackendService, simulatedServiceName);
    } catch (GetByIdFault_Exception e) {
      System.out.println("Error at calling the service!");
      e.printStackTrace();
    }
  }

  private GetByIdRequest createGetByIdRequest() {
    GetByIdRequest request = new GetByIdRequest();
    request.setId("1111");
    
    return request;
  }

  /*
  * *********************************** Methods for the simulated service *********************************
  */
  public GetByIdResponse forwardRequest(GetByIdRequest req) throws GetByIdFault_Exception {
    // get the port
    GetCustomerById service = new GetCustomerById();
    ThrottlingSimulatedPortType port = service.getThrottlingSimulatedPort();
    
    // set the endpoint
    BindingProvider bp = (BindingProvider)port;
    bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpoint);
    
    System.out.println("### Calling the service...");
    GetByIdResponse response = port.getById(req);
    System.out.println("### Called the service!");
    
    return response;
  }

  public GetByIdFault_Exception buildFault(String reason) {
    return new GetByIdFault_Exception(String.format("Fault from simulated service: The backend service cannot be called! - request id: %s, reason: %s", 
        requestId, reason));
  }

  public void setEndpoint(String endpoint) {
    this.endpoint = endpoint;
  }

}
```

The same class would look without the Valogato throttling as follows:

```
public class WebserviceClient {

  public void call() {
    GetCustomerById service = new GetCustomerById();
    ThrottlingSimulatedPortType port = service.getThrottlingSimulatedPort();
    
    // set the endpoint
    BindingProvider bp = (BindingProvider)port;
    bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, "http://localhost:8028/mockGetCustomerByIdHttpBinding");
    
    System.out.println("### Calling the service...");
    GetByIdResponse response = port.getById(createGetByIdRequest());
    System.out.println("### Called the service!");
  }

  private GetByIdRequest createGetByIdRequest() {
    GetByIdRequest request = new GetByIdRequest();
    request.setId("1111");
    
    return request;
  }

}
```

It is very easy to switch the throttling off by using some configuration parameter on client side.

There is a sample project in the [Downloads](Downloads.md) section (standalone-builtin-client).

Advantage:
  * minimal performance impact as there is no additional station the request should travel through
Disadvantage:
  * code change is needed on the client side