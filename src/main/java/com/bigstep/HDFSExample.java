package com.bigstep;

import java.io.*;
import java.util.*;
import java.net.*;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.util.*;
import org.apache.hadoop.security.*;
import org.apache.hadoop.security.UserGroupInformation.AuthenticationMethod;
import org.apache.hadoop.security.authentication.util.*;
import org.apache.log4j.*;
 
public class HDFSExample{
	 static Logger log = Logger.getLogger(HDFSExample.class.getName());
	
        public static void main (String [] args) throws Exception{

			if(args.length!=1)
			{
				System.err.println("Syntax: <path>");
				System.exit(0);
			}

			String pathString=args[0];

			Configuration conf=new Configuration();
			
			//we need to know where to connect. You need not set them here if they are already set
			if(null==System.getProperty("java.security.krb5.kdc"))
				System.setProperty("java.security.krb5.kdc","mass.bigstep.io");

			if(null==System.getProperty("java.security.krb5.realm"))
				System.setProperty("java.security.krb5.realm","BIGSTEP.IO");
						
			if(null==System.getProperty("mass.keytab.file"))
				conf.set("mass.keytab.file","/Users/alexandrubordei/mass.kt");
			
			if(null==System.getProperty("mass.keytab.username"))
				conf.set("mass.keytab.username","alex@BIGSTEP.IO");

			boolean getCredentialsFromOS=true;
			if(null!=conf.get("mass.keytab.file") && null!=conf.get("mass.keytab.username"))
				getCredentialsFromOS=false;

			log.info("getCredentialsFromOS:"+getCredentialsFromOS);
			System.out.println("getCredentialsFromOS:"+getCredentialsFromOS);
			

			conf.set("hadoop.security.authentication","kerberos");
			conf.set("fs.default.name","instance-1868.instances.hdfs-proto.instance-arrays.couchbase-test-alex.7.fmc.bigstep.io:8020");
			conf.set("dfs.namenode.kerberos.principal","hdfs/_HOST@BIGSTEP.IO");
			conf.set("dfs.namenode.kerberos.internal.spnego.principal","HTTP/_HOST@BIGSTEP.IO");
			conf.set("dfs.datanode.kerberos.principal","hdfs/_HOST@BIGSTEP.IO");

			UserGroupInformation.setConfiguration(conf);

			//use this to login without any previous kinit call
			if(!getCredentialsFromOS)
			{
				log.info("Logging in with mass.keytab.file="+conf.get("mass.keytab.file")+" and mass.keytab.username="+conf.get("mass.keytab.username"));
				SecurityUtil.login(conf,"mass.keytab.file","mass.keytab.username");
			}
			
			log.info("Auth Module:"+KerberosUtil.getKrb5LoginModuleName());
			log.info("Realm:"+KerberosUtil.getDefaultRealm());	
			log.info("User name:"+UserGroupInformation.getCurrentUser().getUserName());
			log.info("Auth method:"+UserGroupInformation.getCurrentUser().getAuthenticationMethod().getAuthMethod().toString());

			//Open a path and get a file
                        Path pt=new Path(pathString);
			FileSystem fs = pt.getFileSystem(conf); 

                        BufferedReader br=new BufferedReader(new InputStreamReader(fs.open(pt)));
                        String line;
                        line=br.readLine();
                        while (line != null){
                                System.out.println(line);
                                line=br.readLine();
                        }
			fs.close();
        }
}
