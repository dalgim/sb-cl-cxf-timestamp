# SpringBoot, Contract Last, CXF Client & Server with WSS Timestamp

### About
This projects demonstrates how to configure ws security timestamp with Apache CXF on client and server side. Both projects providing support
for wss signature to incoming and outgoing request.

* [sb-cl-cxf-timestamp-server](https://github.com/dalgim/sb-cl-cxf-timestamp/tree/master/sb-cl-cxf-wss-timestamp-server) - Server side
* [sb-cl-cxf-timestamp-client](https://github.com/dalgim/sb-cl-cxf-timestamp/tree/master/sb-cl-cxf-wss-timestamp-client) - Client side

More helpful info you can find on this websites:
* Apache CXF WS Security introduction - [Link](http://cxf.apache.org/docs/ws-security.html)
* WS Security in CXF - IBM - [Link](https://www.ibm.com/developerworks/library/j-jws13/)
* JBossWS - [Link](https://developer.jboss.org/wiki/Jbossws-stackcxfUserGuide#jive_content_id_WSSecurity)
* Advanced User Guide JBoss Community - [Link](https://docs.jboss.org/author/display/JBWS/Advanced+User+Guide)


### Signature basic parameters
Below parameters are using in client and server configuration to get wss signature connection
<br/><b>ACTION</b> - One on supported security actions (e.g. Encrypt, Signature, UsernameToken, Timestamp)
<br/><b>TTL_TIMESTAMP</b> - The time difference between creation and expiry time in seconds in the WSS Timestamp. The default is "300".
<br/><b>TIMESTAMP_PRECISION</b> - Set whether outbound timestamps have precision in milliseconds. Default is "true".
<br/><b>MUST_UNDERSTAND</b> - True if client/server must process ws security actions otherwise false
<br/><b>TTL_FUTURE_TIMESTAMP</b> - The time in seconds in the future within which the Created time of an incoming Timestamp is valid. The default is "60". 
<br/><b>REQUIRE_TIMESTAMP_EXPIRES</b> - Set the value of this parameter to true to require that a Timestamp must have an "Expires" Element. The default is "false".
<br/><b>TIMESTAMP_STRICT</b> - 	Set whether to enable strict Timestamp handling, i.e. throw an exception if the current receiver time is past the Expires time of the Timestamp. Default is "true".

More about cxf configuration - [Link](https://ws.apache.org/wss4j/config.html)

### Running and testing

If you have any problems with your implementation you can enable precise exception message using below properties. It is very helpful option because in default cxf hides exception messages for security reason. 

```java
endpoint.getProperties().put(Message.EXCEPTION_MESSAGE_CAUSE_ENABLED, "true");
endpoint.getProperties().put(Message.FAULT_STACKTRACE_ENABLED, "true");
```

//TODO