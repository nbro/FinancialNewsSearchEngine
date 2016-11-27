package financial.news.se.page.model;

/**
 * Created by Nelson on 25.11.16.
 */

import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.Indexed;
import org.springframework.data.solr.core.mapping.SolrDocument;

import java.util.List;

import static com.sun.tools.doclint.Entity.lang;

/*
* Non-indexed and non-stored fields are not specified.
* */

@SolrDocument(solrCoreName = "collection1")
public class WebPageDocument {

    @Id
    @Indexed(required = true)
    private String id;

    @Indexed(type = "url")
    private String url;

    /* stored=true for highlighting, use term vectors  and positions for fast highlighting */
    @Indexed(type = "text_general")
    private String content;

    /*
     * Multivalued attribute
     */
    @Indexed(type = "text_general")
    private List<String> title;

    /*
     * fields for index-anchor plugin
     *
     * Multivalued attribute.
     */
    @Indexed(type = "text_general")
    private List<String> anchor;

    /* fields for index-more plugin */
    @Indexed
    private List<String> type;


    /* GETTERS AND SETTERS */

    @Override
    public String toString() {
        return "WebPageDocument{" +
                "id='" + id + '\'' +
                ",\ncontent='" + content + '\'' +
                ",\ntitle=" + title +
                ",\nanchor=" + anchor +
                ",\ntype=" + type +
                ",\nlang='" + lang + '\'' +
                "\n\n";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getTitle() {
        return title;
    }

    public void setTitle(List<String> title) {
        this.title = title;
    }

    public List<String> getAnchor() {
        return anchor;
    }

    public void setAnchor(List<String> anchor) {
        this.anchor = anchor;
    }

    public List<String> getType() {
        return type;
    }

    public void setType(List<String> type) {
        this.type = type;
    }


}