package financial.news.se.controller;

import financial.news.se.page.model.WebPageDocument;
import financial.news.se.page.repository.WebPageDocumentRepository;
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
    WebPageDocumentRepository repository;

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public List<WebPageDocument> search(@RequestBody String searchInput) {

        System.out.println("Query: " + searchInput + "\n");
        System.out.println(repository.findByIdOrUrlOrTitleOrContentContaining(searchInput, searchInput, searchInput, searchInput));

        return repository.findByIdOrUrlOrTitleOrContentContaining(searchInput, searchInput, searchInput, searchInput);

    }

}
