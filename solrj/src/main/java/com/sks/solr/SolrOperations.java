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
		SolrInputDocument document = new SolrInputDocument();
		document.addField("id", "123456");
		document.addField("name", "Kenmore Dishwasher");
		document.addField("price", "599.99");
		
		SolrClient client = SolrJClient.getInstance().getClient();
		try {
			client.add(document);
			client.commit();
		} catch (SolrServerException e) {
			System.out.println("SolrOperations - IndexDocument - SolrServerException" + e.getMessage());
		} catch (IOException e) {
			System.out.println("SolrOperations - IndexDocument - IOException" + e.getMessage());
		}	
	}
	
	public static void searchDocuments() {
		SolrQuery query = new SolrQuery();
		query.set("q", "price:599.99");
		SolrClient client = SolrJClient.getInstance().getClient();
		QueryResponse response;
		try {
			response = client.query(query);
			SolrDocumentList docList = response.getResults();
			for (SolrDocument doc : docList) {
				System.out.println("Id:" + doc.getFieldValue("id").toString());
				System.out.println("Id:" + doc.getFieldValue("price").toString());
			}
		} catch (SolrServerException e) {
			System.out.println("SolrOperations - searchDocuments - SolrServerException" + e.getMessage());
		} catch (IOException e) {
			System.out.println("SolrOperations - searchDocuments - IOException" + e.getMessage());
		}
	}
	
	public static void searchDocument() {
		SolrClient client = SolrJClient.getInstance().getClient();
		try {
			SolrDocument doc = client.getById("123456");
			if(doc != null) {
				System.out.println("Id:" + doc.getFieldValue("id").toString());
				System.out.println("Id:" + doc.getFieldValue("price").toString());
			}
		} catch (SolrServerException e) {
			System.out.println("SolrOperations - searchDocument - SolrServerException" + e.getMessage());
		} catch (IOException e) {
			System.out.println("SolrOperations - searchDocument - IOException" + e.getMessage());
		}
	}
	
	public static void deleteDocumentById() {
		SolrClient client = SolrJClient.getInstance().getClient();
		try {
			client.deleteById("123456");
			client.commit();
			SolrQuery query = new SolrQuery();
			query.set("q", "id:123456");
			QueryResponse response = client.query(query);
			SolrDocumentList docList = response.getResults();
			if(docList.getNumFound() == 0) {
				System.out.println("SolrOperations - deleteDocument - Document deleted successfully");
			}
		} catch (SolrServerException e) {
			System.out.println("SolrOperations - deleteDocument - SolrServerException" + e.getMessage());
		} catch (IOException e) {
			System.out.println("SolrOperations - deleteDocument - IOException" + e.getMessage());
		}
	}
	
	public static void deleteDocumentByQuery() {
		SolrClient client = SolrJClient.getInstance().getClient();
		try {
			client.deleteByQuery("name:Kenmore");
			client.commit();
			SolrQuery query = new SolrQuery();
			query.set("q", "id:123456");
			QueryResponse response = client.query(query);
			SolrDocumentList docList = response.getResults();
			if(docList.getNumFound() == 0) {
				System.out.println("SolrOperations - deleteDocument - Document deleted successfully");
			}
		} catch (SolrServerException e) {
			System.out.println("SolrOperations - deleteDocumentByQuery - SolrServerException" + e.getMessage());
		} catch (IOException e) {
			System.out.println("SolrOperations - deleteDocumentByQuery - IOException" + e.getMessage());
		}

	}	
}
