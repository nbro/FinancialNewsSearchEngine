package financial.news.se.product.service;

import financial.news.se.product.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.query.result.FacetPage;

/**
 * Created by Nelson on 25.11.16.
 */
public interface ProductService {

    int DEFAULT_PAGE_SIZE = 3;

    Page<Product> findByName(String searchTerm, Pageable pageable);

    Product findById(String id);

    FacetPage<Product> autocompleteNameFragment(String fragment, Pageable pageable);

}
