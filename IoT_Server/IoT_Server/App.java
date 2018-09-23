package org.SampleHBase.UserHbase;

import java.util.HashMap;

import org.eclipse.californium.core.CoapServer;

public class App {
	public static HBase hb;
	
	public static void main(String[] args) throws Exception{
		
		hb = new HBase("164.125.234.62" , "PowerTest");
		
		
		HashMap hm = new HashMap();
		/*hm.put("hello1", "value1");
		hm.put("hello2", "value2");
		
		hb.putData(hm);*/
		
		hb.scanDatas();
		
		//CoapServer server = new CoapServer();
		//server.add(new MyResource("hello"));
		//server.start();
	}
}
