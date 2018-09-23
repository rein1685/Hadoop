package org.SampleHBase.UserHbase;

import static org.eclipse.californium.core.coap.CoAP.ResponseCode.*; // shortcuts

import java.io.IOException;
import java.util.HashMap;

import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.server.resources.CoapExchange;

public class MyResource extends CoapResource {
	public HBase hb;
	
	public MyResource(String start) {
		super(start);
		this.hb = new HBase("164.125.234.62" , "PowerTest");
	}
	
	@Override
	public void handleGET(CoapExchange exchange) {
		exchange.respond("hello world"); // reply with 2.05 payload (text/plain)
	}
	@Override	
	public void handlePOST(CoapExchange exchange) {
		System.out.println("Payload : " + exchange.getRequestPayload());
		System.out.println("Message : " + exchange.getRequestText());
		exchange.accept(); // make it a separate response
		
		String message = exchange.getRequestText().toString();
		
		HashMap hm = new HashMap();
		
		if (exchange.getRequestOptions() != null) {
			// do something specific to the request options
		}
		exchange.respond(CREATED); // reply with response code only (shortcut)
	}
	
	public void handlePUT(CoapExchange exchange){
		System.out.println("Payload : " + exchange.getRequestPayload());
		System.out.println("Message : " + exchange.getRequestText());
		
		String message = exchange.getRequestText().toString();
		String[] messages = message.split(",");
		String[] keywords = {"created_at","temp","humidity","cds","vol_solar","vol_bat","Amp_solar","Amp_bat","Cond1","Cond2","Cond3"};
		
		HashMap hm = new HashMap();
		
		for(int i=0; i<messages.length ; i++)
		{
			
			hm.put(keywords[i] , messages[i]);
			//System.out.println(keywords[i] + " : " + messages[i]);
		}
		
		try {
		hb.putData(hm);
		}catch(IOException e) {}
		
		exchange.accept(); // make it a separate response
		
		if (exchange.getRequestOptions() != null) {
			// do something specific to the request options
		}
		exchange.respond(CREATED); // reply with response code only (shortcut)
	}
}
