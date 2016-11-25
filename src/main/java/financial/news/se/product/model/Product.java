package financial.news.se.product.model;

/**
 * Created by Nelson on 25.11.16.
 */

import org.springframework.data.annotation.Id;
//import org.springframework.data.solr.core.geo.GeoLocation;
import org.springframework.data.solr.core.mapping.Indexed;
import org.springframework.data.solr.core.mapping.SolrDocument;

import financial.news.se.product.model.SearchableProductDefinition;

import java.util.List;

@SolrDocument(solrCoreName = "collection1")
public class Product implements SearchableProductDefinition {

    private
    @Id
    @Indexed
    String id;

    private
    @Indexed(NAME_FIELD_NAME)
    String name;

    private
    @Indexed(AVAILABLE_FIELD_NAME)
    boolean available;

    private
    @Indexed
    List<String> features;

    private
    @Indexed(PRICE_FIELD_NAME)
    Float price;

    private
    @Indexed(CATEGORIES_FIELD_NAME)
    List<String> categories;

    private
    @Indexed
    Integer popularity;

//    private
//    @Indexed(LOCATION_FIELD_NAME)
//    GeoLocation location;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public List<String> getFeatures() {
        return features;
    }

    public void setFeatures(List<String> features) {
        this.features = features;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public Integer getPopularity() {
        return popularity;
    }

    public void setPopularity(Integer popularity) {
        this.popularity = popularity;
    }

//    public GeoLocation getLocation() {
//        return location;
//    }
//
//    public void setLocation(GeoLocation location) {
//        this.location = location;
//    }

    @Override
    public String toString() {
        return "Product [id=" + id + ", name=" + name + "]";
    }

}