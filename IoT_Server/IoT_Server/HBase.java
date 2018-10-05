package org.SampleHBase.UserHbase;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.BinaryComparator;
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.PrefixFilter;
import org.apache.hadoop.hbase.filter.QualifierFilter;
import org.apache.hadoop.hbase.util.Bytes;

public class HBase {
	private Configuration config;
	private HTable hTable;
	HBaseAdmin hBaseAdmin;
	private int count = 0;
	
	public HBase(String HMasterAddress , String TableName) {
		config = HBaseConfiguration.create();
		config.set("hbase.master", HMasterAddress);
		
		try {
			hBaseAdmin = new HBaseAdmin(config);
			
			if (hBaseAdmin.isTableAvailable(TableName) == false) {
				HTableDescriptor tableDs = new HTableDescriptor(TableName);
				tableDs.addFamily(new HColumnDescriptor("cf"));
				hBaseAdmin.createTable(tableDs);
			} else {
				System.out.println("Already created: " + TableName);
				/*hBaseAdmin.disableTable(TableName);
				hBaseAdmin.deleteTable(TableName);

				HTableDescriptor tableDs = new HTableDescriptor(TableName);
				tableDs.addFamily(new HColumnDescriptor("cf"));
				hBaseAdmin.createTable(tableDs);*/
			}

			hTable = new HTable(config, TableName);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void putData(String data)  throws IOException{
		Put p = new Put(Bytes.toBytes(new Date().toString()));
		p.add(Bytes.toBytes("cf"), Bytes.toBytes(new Date().toString()), Bytes.toBytes(data));
		hTable.put(p);
	}
	
	public void putData(HashMap<String, String> hm) throws IOException{
		Put p = new Put(Bytes.toBytes(new Date().toString()));
		Iterator<String> keys = hm.keySet().iterator();
		
		while(keys.hasNext()) {
			String key = keys.next();
			String value = hm.get(key);
			p.add(Bytes.toBytes("cf"), Bytes.toBytes(key), Bytes.toBytes(value));
			System.out.println(key + ": " + value);
		}
		
		hTable.put(p);
	}
	
	public ArrayList scanDatas() throws Exception{
		Scan s = new Scan();
		ResultScanner scanner = hTable.getScanner(s);
		
		ArrayList return_list = new ArrayList();

		try {
			
			for (Result rowResult = scanner.next(); rowResult != null; rowResult = scanner.next()) {
				System.out.println("row: " + rowResult.toString());
				
				try {
					HashMap<String, String> map = new HashMap<String, String>();
					
					byte[] bcds = rowResult.getValue(Bytes.toBytes("cf"),Bytes.toBytes("cds"));
					byte[] bcreated_at = rowResult.getValue(Bytes.toBytes("cf"),Bytes.toBytes("created_at"));
					byte[] btemp = rowResult.getValue(Bytes.toBytes("cf"),Bytes.toBytes("temp"));
					byte[] bhumidity = rowResult.getValue(Bytes.toBytes("cf"),Bytes.toBytes("humidity"));
					byte[] bvol_bat = rowResult.getValue(Bytes.toBytes("cf"),Bytes.toBytes("vol_bat"));
					
					String cds = new String(bcds);
					String created_at = new String(bcreated_at);
					String temp = new String(btemp);
					String humidity = new String(bhumidity);
					String vol_bat = new String(bvol_bat);
					
					map.put("created_at" , created_at);
					map.put("temp" , temp);
					map.put("humidity" , humidity);
					map.put("cds" , cds);
					map.put("vol_bat" , vol_bat);
					
					return_list.add(map);
					
					System.out.println(cds + " , " + created_at);
				} catch(Exception e) {}
				
			}
		} finally {
			scanner.close();
			return return_list;
		}
	}
	
	public void close() throws Exception{
		hTable.close();
		hBaseAdmin.close();
	}
}
