It is pretty simple to start the work with the Valogato throttling system. Here are the basic steps:

  1. Setting up the cache engine
    * creating the configuration file of the cache server (no need in case of local cache) - more information: [Cache Implementation](CacheImplementation.md)
    * starting the cache server (no need in case of local cache) - more information: [Cache Implementation](CacheImplementation.md)
  1. Creating the simulated service
    * coding - more information: [Creating the simulated service interface](CreatingTheSimulatedServiceInterface.md)
    * creating the client-side cache config file - more information: [Cache Implementation](CacheImplementation.md)
  1. Adding the configuration files to the classpath (besides the client-side cache config file)
> > [Setting other configuration files](OtherConfigFiles.md)
> > [Setting the General Configuration XML file](GeneralConfigXML.md)
> > [Setting the Backend Service Configuration XML file](BackendServiceConfigXML.md)
  1. Deploying the simulated service to the application server
  1. Deploying the web application to the application server and loading the backend service configuration - more information: [(Re)load the config XML](LoadConfigXML.md)