Ingests AWS CloudTrail logs into MongoDB for use by policy mining tools.

Requirements
----
* Java 8
* MongoDB 3.6

Getting Started
----
1. Obtain AWS CloudTrail logs to be used for mining
2. Obtain GeoLiteDB: The GeoLiteDB is used to convert IP Addresses to geo locaitons for binning.  It is distributed under a separate license.
Download GeoLite2 City level in MaxMindDB format from https://dev.maxmind.com/geoip/geoip2/geolite2/
3. Start Mongo: 'brew install mongodb' and ''brew services start mongodb' in OSX
4. Update the resources/application.conf settings to match the file locations and Mongo address set above
5. Run "mvn clean compile" to build the code
6. Run "mvn exec:java  -Dexec.mainClass=edu.mines.ingest.ServiceMapIngestor" to run the ServiceMapIngestor which builds a collection of all AWS services in Mongo
7. Run "mvn exec:java  -Dexec.mainClass=edu.mines.CloudTrailIngestor" to ingest audit log events into Mongo


Reference
----
Mining Least Privilege Attribute Based Access Control Policies.  By Matthew Sanders and Chuan Yue.  
Annual Computer Security Applications Conference (ACSAC), 2019.

