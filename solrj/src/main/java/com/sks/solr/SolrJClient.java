package com.sks.solr;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.CloudSolrClient;

public class SolrJClient {
	
	private final SolrClient _solrClient;
	
	private SolrJClient() {
		List<String> zkHosts=new ArrayList<String>();  
		zkHosts.add("zmc-zookeeper:2181");
		Optional<String> empty = Optional.empty();
		_solrClient = new CloudSolrClient.Builder(zkHosts,empty).build();
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
