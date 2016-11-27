package financial.news.se.controller;

import financial.news.se.page.model.WebPageDocument;
import financial.news.se.page.repository.WebPageDocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.query.result.HighlightEntry.Highlight;
import org.springframework.data.solr.core.query.result.HighlightPage;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by Nelson on 26.11.16.
 */
@RestController
public class SearchController {

    @Autowired
    WebPageDocumentRepository repository;

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public HighlightPage<WebPageDocument> search(@RequestBody Map<String, String> bodyData, Pageable pageable) {

        if (bodyData == null || bodyData.isEmpty()) {
            return null;
        }

        String query = bodyData.get("query").trim();

        /* this is only to prevent query with an empty query*/
        if (query.isEmpty()) {
            return null;
        }

        System.out.println("Query: " + query);

        // testHighlights(query, pageable);

        return repository.findByIdOrTitleOrContentContainingIgnoreCase(query, query, query, pageable);
    }


    private void testHighlights(String query, Pageable pageable) {

        HighlightPage<WebPageDocument> highlightPages = repository.findByContentContainingIgnoreCase(query, pageable);

        for (WebPageDocument e : highlightPages.getContent()) {

            System.out.println("Document: ... " + e);

            List<Highlight> highlights = highlightPages.getHighlights(e);

            for (Highlight h : highlights) {
                System.out.println(h.getSnipplets());
            }

            System.out.println("------------------------------------");

        }
    }

    private List<String> splitQuery(String query) {
        List<String> rawQueryList = Arrays.asList(query.split(" "));
        rawQueryList.removeAll(Arrays.asList("", null));
        return rawQueryList;
    }

}
