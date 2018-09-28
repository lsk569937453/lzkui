Requirements
====
Requires Java 7 to run.

Start quickly
====
git clone git@github.com:lsk569937453/lzkui.git<br>
mvn install <br>
cd target <br>
java -jar -Dspring.config.location=/Users/user/Code/GitHub/lzkui/src/main/resources/conf/env/zkconfig.properties lzkui.jar<br>

Configuration
====
You can do configuration in the zkconfig.properties,inclue the connectionString and the password.The default zkconfig.properties is the 
resources/conf/env/zkconfig.properties.

Backend Technology Stack
====
1、Springboot<br>
2、Java<br>
3、Apache.curator<br>

Frontend  Technology Stack
====
Antd<br>
Dva<br>
React<br>
The frontend project is [here](https://github.com/lsk569937453/lzkuiFrontend).
