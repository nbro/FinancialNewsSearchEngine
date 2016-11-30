package financial.news.se.page.service;

import financial.news.se.page.model.WebPageDocument;
import financial.news.se.page.repository.WebPageDocumentRepository;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.SimpleTermsQuery;
import org.springframework.data.solr.core.query.result.HighlightPage;
import org.springframework.data.solr.core.query.result.SolrResultPage;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by Nelson on 28.11.16.
 */
@Service
public class WebPageService {

    private final WebPageDocumentRepository repository;

    @Autowired
    public WebPageService(WebPageDocumentRepository repository) {
        this.repository = repository;
    }

    public HighlightPage<WebPageDocument> processQuery(Map<String, String> bodyData, Pageable pageable) {

        if (bodyData == null || bodyData.get("query") == null) {
            return null;
        }

        String q = bodyData.get("query").trim();

        if (q.isEmpty()) {
            return null;
        }

        List<String> query = splitQuery(q);

        if (query.isEmpty()) {
            return null;
        }

        System.out.println("query: " + query);

        return repository.findByContentContainingIgnoreCase(query, pageable);
    }

    private List<String> splitQuery(String query) {
        List<String> rawQueryList = new ArrayList<>(Arrays.asList(query.split(" ")));

        // rawQueryList is mutable because I wrapped the result of Arrays.asList in an ArrayList
        rawQueryList.removeIf(item -> item == null || "".equals(item));

        return rawQueryList;
    }

}


interface SearchService {
    /**
     * Returns a page of search documents matching the given terms.
     */
    Page<WebPageDocument> search(String terms, Pageable page);
}

class SearchServiceImpl implements SearchService {

    private final SolrTemplate solrTemplate;

    public SearchServiceImpl(final SolrTemplate solrTemplate) {
        this.solrTemplate = solrTemplate;
    }

    @Override
    public Page<WebPageDocument> search(String terms, Pageable page) {

        /* create a netative SolrQuery */
        final SolrQuery query = new SolrQuery();

        /* setup e-dis-max query */
        query.set("q", terms);
        query.set("qf", "title^20.0 url^5.0 content^2.0");
        query.set("defType", "edismax");

        /* apply paging request */
        query.setStart(page.getOffset());
        query.setRows(page.getPageSize());

        /* executing the query and returning the result */
        return execute(query, page);
    }

    private Page<WebPageDocument> execute(final SolrQuery query, final Pageable page) {

        try {
            final QueryResponse resp = solrTemplate.getSolrClient().query(query);

            /* convert response to POJOs */
            final List<WebPageDocument> beans = solrTemplate.convertQueryResponseToBeans(resp, WebPageDocument.class);

            return new SolrResultPage<>(beans, page, resp.getResults().getNumFound(), resp.getResults().getMaxScore());

        } catch (IOException | SolrServerException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void something() {
        SimpleTermsQuery.queryBuilder("title").build();

    }

}