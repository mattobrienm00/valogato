The configuration of the backend services is stored in XML files. If it is needed to change them then the content of the file must be modified and reloaded the file. It would make pretty difficult to change the configuration.

Other problem is that the statistic information is stored in the cache which can be seen only with the tool of the specific cache implementation.

To ease the configuration and to give a tool to check the value of the statistic data a web application can be used that can help
  * checking and modifying the statistic data
  * administering the backend configuration data
  * (re)loading the config XML file

When the throttling system is used the first time (e.g. after server restart) the config XML must be loaded with the web application. The loaded data can be changed with the application but the data will be modified only in the cache and not in the XML file.
The data can be exported to XML file from the web application so it is not needed to maintain the data in two different places. However it is worth to export the data regularly preventing the loss of data.

On the top of the pages a path can be found to ease the navigation in the application. (e.g. 'Backend Service Configuration / List / Update (BigSystem)')