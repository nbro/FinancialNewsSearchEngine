package financial.news.se.page.repository;

import financial.news.se.page.model.WebPageDocument;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.query.result.HighlightPage;
import org.springframework.data.solr.repository.Highlight;
import org.springframework.data.solr.repository.SolrCrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Nelson on 26.11.16.
 */
@Repository
public interface WebPageDocumentRepository extends SolrCrudRepository<WebPageDocument, String> {

    @Highlight(prefix = "<mark>", postfix = "</mark>")
    HighlightPage<WebPageDocument> findByIdOrTitleOrContentContainingIgnoreCase(String id, String title, String content, Pageable pageable);

    @Highlight(prefix = "<mark>", postfix = "</mark>")
    HighlightPage<WebPageDocument> findByContentContainingIgnoreCase(String string, Pageable pageable);

    @Highlight(prefix = "<mark>", postfix = "</mark>")
    HighlightPage<WebPageDocument> findByTitleContainingIgnoreCase(String string, Pageable pageable);

    @Highlight(prefix = "<mark>", postfix = "</mark>")
    HighlightPage<WebPageDocument> findByAnchor(String anchors, Pageable pageable);

    @Highlight(prefix = "<mark>", postfix = "</mark>")
    HighlightPage<WebPageDocument> findByType(String type, Pageable pageable);

}


