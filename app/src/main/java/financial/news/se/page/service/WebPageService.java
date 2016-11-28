package financial.news.se.page.service;

import financial.news.se.page.model.WebPageDocument;
import financial.news.se.page.repository.WebPageDocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.query.result.HighlightPage;
import org.springframework.stereotype.Service;

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

        if (bodyData == null || bodyData.isEmpty()) {
            return null;
        }

        String q = bodyData.get("query").trim();
        List<String> query = splitQuery(q);

        /* this is only to prevent query with an empty query*/
        if (query.isEmpty()) {
            return null;
        }

        System.out.println("Query: " + query);

        return repository.findByContentContainingIgnoreCase(query, pageable);
    }

    private List<String> splitQuery(String query) {
        List<String> rawQueryList = Arrays.asList(query.split(" "));

        try {
            rawQueryList.removeAll(Arrays.asList("", null));
        } catch (UnsupportedOperationException e) {
        }

        return rawQueryList;
    }

}
