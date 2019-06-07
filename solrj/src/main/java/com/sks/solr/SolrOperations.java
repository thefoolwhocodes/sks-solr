package com.sks.solr;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;

public class SolrOperations {
	public static void indexDocument() {
		System.out.println("SolrOperations - indexDocument - started!");
		SolrInputDocument document = new SolrInputDocument();
		document.addField("id", "123456");
		document.addField("name", "Kenmore Dishwasher");
		document.addField("price", "599.99");
		
		SolrClient client = SolrJClient.getInstance().getClient();
		try {
			client.add("sks-test",document);
			client.commit();
		} catch (SolrServerException e) {
			System.out.println("SolrOperations - IndexDocument - SolrServerException " + e.getMessage());
		} catch (IOException e) {
			System.out.println("SolrOperations - IndexDocument - IOException " + e.getMessage());
		}
		System.out.println("SolrOperations - indexDocument - finished!");
	}

	/**
	 * One collection sks-test has been created with
	 *     numShards = 2
	 *     replicationFactor = 2
	 * Zookeeper will redirect the client to following address: http://zmc-solr-1.zmc-solr:8983/solr/sks-test_shard1_replica_n1
	 */
	
	public static void searchDocuments() {
		System.out.println("SolrOperations - searchDocuments - started!");
		SolrQuery query = new SolrQuery();
		query.set("q", "price:599.99");
		SolrClient client = SolrJClient.getInstance().getClient();
		QueryResponse response;
		try {
			response = client.query("sks-test",query);
			SolrDocumentList docList = response.getResults();
			for (SolrDocument doc : docList) {
				System.out.println("Id:" + doc.getFieldValue("id").toString());
				System.out.println("Id:" + doc.getFieldValue("price").toString());
			}
		} catch (SolrServerException e) {
			System.out.println("SolrOperations - searchDocuments - SolrServerException " + e.getMessage());
		} catch (IOException e) {
			System.out.println("SolrOperations - searchDocuments - IOException " + e.getMessage());
		}
		System.out.println("SolrOperations - searchDocuments - finished!");
	}
	
	public static void searchDocument() {
		System.out.println("SolrOperations - searchDocument - started!");
		SolrClient client = SolrJClient.getInstance().getClient();
		try {
			SolrDocument doc = client.getById("sks-test","123456");
			if(doc != null) {
				System.out.println("Id:" + doc.getFieldValue("id").toString());
				System.out.println("Id:" + doc.getFieldValue("price").toString());
			}
		} catch (SolrServerException e) {
			System.out.println("SolrOperations - searchDocument - SolrServerException " + e.getMessage());
		} catch (IOException e) {
			System.out.println("SolrOperations - searchDocument - IOException " + e.getMessage());
		}
		System.out.println("SolrOperations - searchDocument - finished!");
	}
	
	public static void deleteDocumentById() {
		System.out.println("SolrOperations - deleteDocumentById - started!");
		SolrClient client = SolrJClient.getInstance().getClient();
		try {
			client.deleteById("sks-test","123456");
			client.commit();
			SolrQuery query = new SolrQuery();
			query.set("q", "id:123456");
			QueryResponse response = client.query("sks-test",query);
			SolrDocumentList docList = response.getResults();
			if(docList.getNumFound() == 0) {
				System.out.println("SolrOperations - deleteDocument - Document deleted successfully");
			}
		} catch (SolrServerException e) {
			System.out.println("SolrOperations - deleteDocument - SolrServerException " + e.getMessage());
		} catch (IOException e) {
			System.out.println("SolrOperations - deleteDocument - IOException " + e.getMessage());
		}
		System.out.println("SolrOperations - deleteDocumentById - finished!");
	}
	
	public static void deleteDocumentByQuery() {
		System.out.println("SolrOperations - deleteDocumentByQuery - started!");
		SolrClient client = SolrJClient.getInstance().getClient();
		try {
			client.deleteByQuery("name:Kenmore");
			client.commit();
			SolrQuery query = new SolrQuery();
			query.set("q", "id:123456");
			QueryResponse response = client.query("sks-test",query);
			SolrDocumentList docList = response.getResults();
			if(docList.getNumFound() == 0) {
				System.out.println("SolrOperations - deleteDocument - Document deleted successfully");
			}
		} catch (SolrServerException e) {
			System.out.println("SolrOperations - deleteDocumentByQuery - SolrServerException " + e.getMessage());
		} catch (IOException e) {
			System.out.println("SolrOperations - deleteDocumentByQuery - IOException " + e.getMessage());
		}
		System.out.println("SolrOperations - deleteDocumentByQuery - finished!");
	}	
}
