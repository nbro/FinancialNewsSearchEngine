package financial.news.se.page.repository;

import financial.news.se.page.model.WebPageDocument;
import org.springframework.data.solr.repository.SolrCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Nelson on 26.11.16.
 */
@Repository
public interface WebPageDocumentRepository extends SolrCrudRepository<WebPageDocument, String> {

    List<WebPageDocument> findByIdOrUrlOrTitleOrContentContaining(String id, String url, String title, String content);

    List<WebPageDocument> findByBoost(Float boost);

    List<WebPageDocument> findByUrl(String url);

    List<WebPageDocument> findByContent(String content);

    List<WebPageDocument> findByTitle(String title);

    List<WebPageDocument> findByAnchor(List<String> anchors);

    List<WebPageDocument> findByType(List<String> types);

    List<WebPageDocument> findByDate(String date);

    List<WebPageDocument> findByMeta(List<String> meta);

    List<WebPageDocument> findByLang(String lang);

    List<WebPageDocument> findBySubCollection(List<String> subCollection);

    List<WebPageDocument> findByAuthor(String author);

    List<WebPageDocument> findByTag(List<String> tags);

    List<WebPageDocument> findByFeed(String feed);

    List<WebPageDocument> findByPublishedDate(String publishedDate);

    List<WebPageDocument> findByUpdatedDate(String updatedDate);

    List<WebPageDocument> findByCc(List<String> cc);

}


