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
    private static final String solrCollection = "sks-test";

    public static void indexDocument() {
        System.out.println("SolrOperations - indexDocument - started!");
        SolrInputDocument document1 = new SolrInputDocument();
        document1.addField("id", "101");
        document1.addField("name", "Albert Einstein");
        document1.addField("price", "599.99");

        SolrInputDocument document2 = new SolrInputDocument();
        document2.addField("id", "102");
        document2.addField("name", "Isaac Newton");
        document2.addField("price", "599.99");

        SolrClient client = SolrJClient.getInstance().getClient();
        try {
            client.add(solrCollection, document1);
            client.add(solrCollection, document2);
            client.commit(solrCollection);
        } catch (SolrServerException e) {
            System.out.println("SolrOperations - IndexDocument - SolrServerException " + e.getMessage());
        } catch (IOException e) {
            System.out.println("SolrOperations - IndexDocument - IOException " + e.getMessage());
        }
        System.out.println("SolrOperations - indexDocument - finished!");
    }

    /**
     * One collection sks-test has been created with numShards = 2 replicationFactor
     * = 2 Zookeeper will redirect the client to following address:
     * http://zmc-solr-1.zmc-solr:8983/solr/sks-test_shard1_replica_n1
     */
    public static void searchDocuments() {
        System.out.println("SolrOperations - searchDocuments - started!");
        SolrQuery query = new SolrQuery();
        query.set("q", "price:599.99");
        SolrClient client = SolrJClient.getInstance().getClient();
        QueryResponse response;
        try {
            response = client.query(solrCollection, query);
            SolrDocumentList docList = response.getResults();
            if (docList.size() == 2) {
                System.out.println("SolrOperations - searchDocuments - test passed ");
                for (SolrDocument doc : docList) {
                    System.out.println("Id:" + doc.getFieldValue("id").toString());
                    System.out.println("Name:" + doc.getFieldValue("name").toString());
                    System.out.println("Price:" + doc.getFieldValue("price").toString());
                }
            } else {
                System.out.println("SolrOperations - searchDocuments - test failed ");
            }
        } catch (SolrServerException e) {
            System.out.println("SolrOperations - searchDocuments - SolrServerException " + e.getMessage());
        } catch (IOException e) {
            System.out.println("SolrOperations - searchDocuments - IOException " + e.getMessage());
        }
        System.out.println("SolrOperations - searchDocuments - finished!");
    }

    public static boolean searchDocument(String id) {
        boolean success = false;
        System.out.println("SolrOperations - searchDocument - started!");
        SolrClient client = SolrJClient.getInstance().getClient();
        try {
            SolrDocument doc = client.getById(solrCollection, id);
            if (doc != null) {
                System.out.println("SolrOperations - searchDocument - test passed ");
                System.out.println("Id:" + doc.getFieldValue("id").toString());
                System.out.println("Name:" + doc.getFieldValue("name").toString());
                System.out.println("Price:" + doc.getFieldValue("price").toString());
                success = true;
            } else {
                System.out.println("SolrOperations - searchDocument - test failed ");
                success = false;
            }

        } catch (SolrServerException e) {
            System.out.println("SolrOperations - searchDocument - SolrServerException " + e.getMessage());
            success = false;
        } catch (IOException e) {
            System.out.println("SolrOperations - searchDocument - IOException " + e.getMessage());
            success = false;
        }
        System.out.println("SolrOperations - searchDocument - finished!");
        return success;
    }

    public static void deleteDocumentById() {
        System.out.println("SolrOperations - deleteDocumentById - started!");
        SolrClient client = SolrJClient.getInstance().getClient();
        try {
            client.deleteById(solrCollection, "101");
            client.commit(solrCollection);
            SolrQuery query = new SolrQuery();
            query.set("q", "id:101");
            QueryResponse response = client.query(solrCollection, query);
            SolrDocumentList docList = response.getResults();
            if (docList.getNumFound() == 0) {
                System.out.println("SolrOperations - deleteDocumentById - test passed ");
                System.out.println("SolrOperations - deleteDocument - Document deleted successfully");
            } else {
                System.out.println("SolrOperations - deleteDocumentById - test failed ");
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
            
            if (searchDocument("102") == true)
            {
                client.deleteByQuery(solrCollection, "name:Isaac Newton");
                client.commit(solrCollection);
                SolrQuery query = new SolrQuery();
                query.set("q", "id:102");
                QueryResponse response = client.query(solrCollection, query);
                SolrDocumentList docList = response.getResults();

                if (docList.getNumFound() == 0) {
                    System.out.println("SolrOperations - deleteDocumentByQuery - test passed ");
                    System.out.println("SolrOperations - deleteDocument - Document deleted successfully");
                } else {
                    System.out.println("SolrOperations - deleteDocumentByQuery - test failed ");
                }
            } else {
                System.out.println("SolrOperations - deleteDocumentByQuery - test failed ");
                System.out.println("SolrOperations - deleteDocumentByQuery - id not found to delete ");
            }
        } catch (SolrServerException e) {
            System.out.println("SolrOperations - deleteDocumentByQuery - SolrServerException " + e.getMessage());
        } catch (IOException e) {
            System.out.println("SolrOperations - deleteDocumentByQuery - IOException " + e.getMessage());
        }
        System.out.println("SolrOperations - deleteDocumentByQuery - finished!");
    }
}