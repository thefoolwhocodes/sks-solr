package com.sks.solr;


public class solr 
{
    public static void main( String[] args )
    {
        System.out.println("sks - Solr demonstrator started!");
        try {
            SolrOperations.indexDocument();
            SolrOperations.searchDocument();
            SolrOperations.searchDocuments();
            SolrOperations.deleteDocumentById();
            SolrOperations.deleteDocumentByQuery();
        	
        } catch (RuntimeException e ) {
        	System.out.println("solr - main - RuntimeException" + e.getMessage());
        	e.printStackTrace();
        }
        System.out.println("sks - Solr demonstrator finished!");
    }
}
