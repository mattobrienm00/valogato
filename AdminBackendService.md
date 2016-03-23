The configuration of the backend services can be edited here.

The 4th column of the table displays the features assigned to the backend service. The text 'SendBackFaultFeature,[ForwarderFeature,SendBackFaultFeature,WaitingFeature]' means the feature SendBackFaultFeature is assigned directly to the backend service BigSystem and the features ForwarderFeature,SendBackFaultFeature,WaitingFeature are assigned to the simulated services of this backend service. The text '[.md](.md)' means that there is no simulated service configured to the backend services.
An XML representation of the actual backend service configuration (the file will contains all the services) can be downloaded by clicking on the button 'Export as XML'.

It is important to mention that a new backend service cannot be added and an existing one cannot be removed.

List:
![http://wiki.valogato.googlecode.com/hg/images/4-backendservice-list.png](http://wiki.valogato.googlecode.com/hg/images/4-backendservice-list.png)

Clicking on the link 'Update' the configuration details of a specific backend service can be edited. The descriptions of the fields can be found here [Setting the Backend Service Configuration XML file](BackendServiceConfigXML.md).
The feature combobox contains if the feature is a built-in feature (Builtin) or a new feature (New) -> developed by the you.
The parameter list can be different depending on the selected feature (the waiting feature has 4 parameters but the SendBackFaultFeature doesn't have any parameter at all). After selecting a feature the webpage is being refreshed automatically.

Update:
![http://wiki.valogato.googlecode.com/hg/images/5-backendservice-update.png](http://wiki.valogato.googlecode.com/hg/images/5-backendservice-update.png)

It is possible to define exceptions ([Setting the Backend Service Configuration XML file](BackendServiceConfigXML.md)) for each simulated service. The feature for the simulated feature can be set here.

Simulated service update:
![http://wiki.valogato.googlecode.com/hg/images/6-simulatedservice-update.png](http://wiki.valogato.googlecode.com/hg/images/6-simulatedservice-update.png)