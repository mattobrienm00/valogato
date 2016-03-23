Maven can be used to develop the simulated service.
All you have to do is to add the Valogato Core dependency.
Sample:


```
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
  
  ...
  
  <repositories>
    <repository>
      <id>maven2</id>
      <url>http://repo1.maven.org/maven2</url>
    </repository>
  </repositories>
  
  <dependencies>
    <dependency>
      <groupId>com.googlecode.valogato</groupId>
      <artifactId>thr-core</artifactId>
      <version>1.1</version>
    </dependency>
    ...
  </dependencies>
</project>
```