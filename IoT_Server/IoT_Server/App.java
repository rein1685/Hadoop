package org.SampleHBase.UserHbase;

import java.util.HashMap;

import org.eclipse.californium.core.CoapServer;

public class App {
	public static HBase hb;
	
	public static void main(String[] args) throws Exception{
		
		hb = new HBase("164.125.234.62" , "PowerTest");
		
		/*for(HashMap<String, String> data : datas)
		{
			System.out.println(data.get("created_at"));
		}*/
		
		CoapServer server = new CoapServer();
		server.add(new MyResource("hello" , hb));
		server.start();
	}
}
