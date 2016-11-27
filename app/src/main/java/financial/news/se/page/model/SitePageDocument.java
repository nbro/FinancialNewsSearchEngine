package financial.news.se.page.model;

/**
 * Created by Nelson on 25.11.16.
 */

import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.Indexed;
import org.springframework.data.solr.core.mapping.SolrDocument;

@SolrDocument(solrCoreName = "collection1")
public class SitePageDocument {

    @Id
    @Indexed(required = true)
    private String id;

    @Indexed(name = "_version_")
    private Long version;

    /* CORE FIELDS */
    @Indexed
    private String segment;

    @Indexed
    private String digest;

    @Indexed
    private Float boost;

    /* FIELDS FOR INDEX-BASIC PLUGIN */

    @Indexed(type = "url", stored = false)
    private String host;

    @Indexed(type = "url")
    private String url;

    @Indexed(type = "text_general")
    private String content;

    @Indexed(type = "text_general")
    private String title;

    @Indexed
    private String cache;

    @Indexed(type = "date", name = "tstamp")
    private String timestamp;

    /* GETTERS AND SETTERS */

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getSegment() {
        return segment;
    }

    public void setSegment(String segment) {
        this.segment = segment;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public Float getBoost() {
        return boost;
    }

    public void setBoost(Float boost) {
        this.boost = boost;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCache() {
        return cache;
    }

    public void setCache(String cache) {
        this.cache = cache;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "SitePageDocument{" +
                "id='" + id + '\'' +
                ", version=" + version +
                ", segment='" + segment + '\'' +
                ", digest='" + digest + '\'' +
                ", boost=" + boost +
                ", host='" + host + '\'' +
                ", url='" + url + '\'' +
                ", content='" + content + '\'' +
                ", title='" + title + '\'' +
                ", cache='" + cache + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
}