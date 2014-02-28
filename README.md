ubiengine
=========

A Java engine for ubiquitous games, made with the uOS middleware.

Four steps to create an Eclipse Maven project with this library:
* Clone ubiengine, uos_socket_plugin and uos_core (**links** below);
* Import these three existing Maven projects;
* Create a Maven project and add the tags below in the **pom.xml**;
* Right-click on project -> Properties -> Java Build Path -> Libraries -> Maven Dependencies arrow -> Native library location -> Edit... -> Insert Location path as replace-with-project-name/target/natives -> OK -> OK.

Check the [Wiki](https://github.com/matheuscscp/ubiengine/wiki) for tutorials.

Links
=====

* [ubiengine](https://github.com/matheuscscp/ubiengine)
* [uos_socket_plugin](https://github.com/UnBiquitous/uos_socket_plugin)
* [uos_core](https://github.com/UnBiquitous/uos_core)

pom.xml
=======

```xml
  <dependencies>
    <dependency>
      <groupId>org.unbiquitous</groupId>
      <artifactId>ubi-engine</artifactId>
      <version>1.0-SNAPSHOT</version>
    </dependency>
  </dependencies>
  
  <build>
    <plugins>
      <plugin>
        
        <groupId>com.googlecode.mavennatives</groupId>
        <artifactId>maven-nativedependencies-plugin</artifactId>
        <version>0.0.7</version>
        
        <executions>
          <execution>
            <id>unpacknatives</id>
            <goals>
              <goal>copy</goal>
            </goals>
          </execution>
        </executions>
        
      </plugin>
    </plugins>
  </build>
```
