package com.sks.solr;

import java.util.ArrayList;
import java.util.List;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.CloudSolrClient;

public class SolrJClient {
	
	private final SolrClient _solrClient;
	
	private SolrJClient() {
		List<String> zkHosts=new ArrayList<String>();  
		zkHosts.add("192.168.204.181:2181");    
		zkHosts.add(",192.168.204.182:2181");
		zkHosts.add("192.168.204.183:2181");
		_solrClient = new CloudSolrClient.Builder(zkHosts).build();
	};
	
	private static SolrJClient _instance;
	
	public static SolrJClient getInstance() {
		if (_instance == null) {
			synchronized (SolrJClient.class) {
				if (_instance == null) {
					System.out.println("SolrJClient- going to create new instance.");
					_instance = new SolrJClient();
				}				
			}
		}
		System.out.println("SolrJClient - returning instance");
		return _instance;
	}
	
	public SolrClient getClient()
	{
		return _solrClient;
	}
}
