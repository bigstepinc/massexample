#MASS Storage example - Bigstep

This class has examples that show you how to use the MASS storage system that Bigstep provides to store files. 

Version 0.1 (alpha) 
 
Written by: Alex Bordei Bigstep
(alex at bigstep dt com)

##Dependencies:
* hadoop (2.0)

##How to use - HDFS get file

Compile
```
mvn package
```
Execute. MASS storage system uses KERBEROS authentication method and so you will need to aquire a ticket from the server. Make sure you have activated the product. KERBEROS is a system wide authentication system so it happens outside of the java classes so you might want to automate it using a keytab (see: https://kb.iu.edu/d/aumh).  
```
kinit replaceme@BIGSTEP.IO
mvn exec:java -Dexec.mainClass="com.bigstep.HDFSExample" -Djava.security.krb5.kdc=kdc.bigstep.io  -Djava.security.krb5.realm=BIGSTEP.IO -Dexec.args="hdfs://mass.bigstep.io/user/replaceme/file.pub"
```

