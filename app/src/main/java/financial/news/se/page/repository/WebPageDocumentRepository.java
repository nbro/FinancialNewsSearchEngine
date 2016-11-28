package financial.news.se.page.repository;

import financial.news.se.page.model.WebPageDocument;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.query.result.HighlightPage;
import org.springframework.data.solr.repository.Highlight;
import org.springframework.data.solr.repository.SolrCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Nelson on 26.11.16.
 */
@Repository
public interface WebPageDocumentRepository extends SolrCrudRepository<WebPageDocument, String> {

    @Highlight(prefix = "<mark>", postfix = "</mark>")
    HighlightPage<WebPageDocument> findByContentContainingIgnoreCase(List<String> query, Pageable pageable);

    @Highlight(prefix = "<mark>", postfix = "</mark>")
    HighlightPage<WebPageDocument> findByTitleContainingIgnoreCase(List<String> query, Pageable pageable);
}


