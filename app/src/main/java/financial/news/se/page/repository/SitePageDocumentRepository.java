package financial.news.se.page.repository;

import financial.news.se.page.model.SitePageDocument;
import org.springframework.data.solr.repository.SolrCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Nelson on 26.11.16.
 */
@Repository
public interface SitePageDocumentRepository extends SolrCrudRepository<SitePageDocument, String> {

    List<SitePageDocument> findByIdOrUrlOrTitleOrContentContaining(String id, String url, String title, String content);

}


