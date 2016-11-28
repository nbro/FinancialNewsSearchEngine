package financial.news.se.controller;

import financial.news.se.page.model.WebPageDocument;
import financial.news.se.page.service.WebPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.query.result.HighlightPage;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created by Nelson on 26.11.16.
 */
@RestController
public class SearchController {

    WebPageService webPageService;

    @Autowired
    public SearchController(WebPageService webPageService) {
        this.webPageService = webPageService;
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public HighlightPage<WebPageDocument> search(@RequestBody Map<String, String> bodyData, Pageable pageable) {
        return webPageService.processQuery(bodyData, pageable);
    }


}
