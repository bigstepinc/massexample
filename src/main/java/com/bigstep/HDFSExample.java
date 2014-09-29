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
 
public class HDFSExample{
        public static void main (String [] args) throws Exception{
			if(args.length!=1)
			{
				System.err.println("Syntax: <path>");
				System.exit(0);
			}

		
			String p=args[0];

			Configuration conf=new Configuration();
			conf.set("hadoop.security.authentication","kerberos");
			conf.set("fs.default.name","instance-1868.instances.hdfs-proto.instance-arrays.couchbase-test-alex.7.fmc.bigstep.io:8020");
			conf.set("dfs.namenode.kerberos.principal","hdfs/_HOST@BIGSTEP.IO");
			conf.set("dfs.namenode.kerberos.internal.spnego.principal","HTTP/_HOST@BIGSTEP.IO");
			conf.set("dfs.datanode.kerberos.principal","hdfs/_HOST@BIGSTEP.IO");
	
			UserGroupInformation.setConfiguration(conf);

			if(!UserGroupInformation.isSecurityEnabled())
				throw new Exception("Security must be enabled!");

			System.out.println("User name:"+UserGroupInformation.getCurrentUser().getUserName());
			System.out.println("Auth method"+UserGroupInformation.getCurrentUser().getAuthenticationMethod().getAuthMethod().toString());

			System.out.println("Module:"+KerberosUtil.getKrb5LoginModuleName());
			System.out.println("Realm:"+KerberosUtil.getDefaultRealm());	


                        Path pt=new Path(p);
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
