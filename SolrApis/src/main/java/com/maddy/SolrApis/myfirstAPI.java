package com.maddy.SolrApis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.LinkedHashSet;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.request.CoreAdminRequest;
import org.apache.solr.client.solrj.request.QueryRequest;
import org.apache.solr.client.solrj.response.CoreAdminResponse;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.CoreAdminParams.CoreAdminAction;

public class myfirstAPI  {

	public myfirstAPI() {

	}

	public String[] fieldNames(String coreName) {
		String s = getlist(coreName);
		System.out.println("myfirstAPI.fieldNames()");
		System.out.println(s);
		String[] uq;
		String[] arr = s.split(" ");
		Set<String> temp = new LinkedHashSet<String>(Arrays.asList(arr));
		uq = temp.toArray(new String[temp.size()]);
		return uq;
	}

	public List<String> getCoresList() {
		// List of the cores
		List<String> coreList = new ArrayList<String>();
		try {
			SolrClient solrClient = new HttpSolrClient("http://localhost:8983/solr");
			CoreAdminRequest request = new CoreAdminRequest();
			request.setAction(CoreAdminAction.STATUS);
			CoreAdminResponse cores;

			cores = request.process(solrClient);

			for (int i = 0; i < cores.getCoreStatus().size(); i++) {
				coreList.add(cores.getCoreStatus().getName(i));
				System.out.println("Core " + i + " : " + cores.getCoreStatus().getName(i));

			}
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return coreList;
	}

	private String getlist(String coreName) {
		SolrClient solrServer = new HttpSolrClient("http://localhost:8983/solr/" + coreName);
		SolrQuery qry = new SolrQuery("*:*");
		String solrFields = "";
		qry.setIncludeScore(true);
		qry.setShowDebugInfo(true);
		qry.setRows(100);
		QueryRequest qryReq = new QueryRequest(qry);
		QueryResponse resp;
		try {
			resp = qryReq.process(solrServer);

			SolrDocumentList results = resp.getResults();
			for (int i = 0; i < results.size(); i++) {
				SolrDocument hitDoc = results.get(i);
				for (Iterator<Entry<String, Object>> flditer = hitDoc.iterator(); flditer.hasNext();) {
					Entry<String, Object> entry = flditer.next();
					solrFields += entry.getKey() + " ";
				}
			}
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return solrFields.replaceAll("_version_ score ", "");
	}
}
