package financial.news.se.controller;

import financial.news.se.page.model.SitePageDocument;
import financial.news.se.page.repository.SitePageDocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Nelson on 26.11.16.
 */
@RestController
public class SearchController {

    @Autowired
    SitePageDocumentRepository repository;

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public List<SitePageDocument> search(@RequestBody String searchInput) {

        System.out.println(repository.findByContent(searchInput));

        return repository.findByContent(searchInput);

    }


}
