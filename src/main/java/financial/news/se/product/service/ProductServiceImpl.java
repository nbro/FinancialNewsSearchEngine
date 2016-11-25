package financial.news.se.product.service;

import financial.news.se.product.model.Product;
import financial.news.se.product.repository.ProductRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.query.result.FacetPage;
import org.springframework.data.solr.core.query.result.SolrResultPage;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by Nelson on 25.11.16.
 */
@Service
class ProductServiceImpl implements ProductService {

    private static final Pattern IGNORED_CHARS_PATTERN = Pattern.compile("\\p{Punct}");

    private ProductRepository productRepository;

    @Override
    public Page<Product> findByName(String searchTerm, Pageable pageable) {

        if (StringUtils.isBlank(searchTerm)) {
            return productRepository.findAll(pageable);
        }

        return productRepository.findByNameIn(splitSearchTermAndRemoveIgnoredCharacters(searchTerm), pageable);
    }

    @Override
    public Product findById(String id) {
        return productRepository.findOne(id);
    }

    @Override
    public FacetPage<Product> autocompleteNameFragment(String fragment, Pageable pageable) {

        if (StringUtils.isBlank(fragment)) {
            return new SolrResultPage<Product>(Collections.<Product>emptyList());
        }

        return productRepository.findByNameStartsWith(splitSearchTermAndRemoveIgnoredCharacters(fragment), pageable);
    }

    private Collection<String> splitSearchTermAndRemoveIgnoredCharacters(String searchTerm) {

        String[] searchTerms = StringUtils.split(searchTerm, " ");
        List<String> result = new ArrayList<String>(searchTerms.length);

        for (String term : searchTerms) {
            if (StringUtils.isNotEmpty(term)) {
                result.add(IGNORED_CHARS_PATTERN.matcher(term).replaceAll(" "));
            }
        }

        return result;
    }

    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

}
