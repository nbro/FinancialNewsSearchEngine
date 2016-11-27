package financial.news.se.page.model;

/**
 * Created by Nelson on 25.11.16.
 */

import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.Indexed;
import org.springframework.data.solr.core.mapping.SolrDocument;

import java.util.List;

@SolrDocument(solrCoreName = "collection1")
public class WebPageDocument {

    @Id
    @Indexed(required = true)
    private String id;

    /*
     * This field is used internally by Solr,
     * for example by features like partial update functionality and update log.
     * It is NOT required if updateLog is turned off in your updateHandler,
     * however it is advised to include it as performance improvements are minimal.
     */
    @Indexed(name = "_version_")
    private Long version;

    /* core fields */
    @Indexed
    private String batchId;

    @Indexed
    private String digest;

    @Indexed
    private Float boost;

    /* fields for index-basic plugin */

    @Indexed(type = "url", stored = false)
    private String host;

    @Indexed(type = "url")
    private String url;

    /* stored=true for highlighting, use term vectors  and positions for fast highlighting */
    @Indexed(type = "text_general")
    private String content;

    /* Multivalued attribute */
    @Indexed(type = "text_general")
    private List<String> title;

    @Indexed(searchable = false)
    private String cache;

    @Indexed(type = "date", name = "tstamp", searchable = false)
    private String timestamp;

    /*
     * catch-all field
     *
     * Multivalued attribute.
     */
    @Indexed(type = "text_general", stored = false)
    private List<String> text;

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

    @Indexed(searchable = false)
    private String contentLength;

    @Indexed(type = "date", searchable = false)
    private String lastModified;

    @Indexed(type = "tdate")
    private String date;

    /*
     * fields for index-metadata plugin
     *
     * Multivalued attribute.
     *
     * Dynamic field.
     */
    @Indexed
    private List<String> meta;


    /* fields for language identifier plugin */
    @Indexed
    private String lang;

    /*
     * fields for sub-collection plugin
     *
     * Multivalued attribute.
     */
    @Indexed(name = "subcollection")
    private List<String> subCollection;

    /* fields for feed plugin (tag is also used by microformats-reltag) */

    @Indexed
    private String author;

    /*
     * Multivalued attribute.
     */
    @Indexed
    private List<String> tag;

    @Indexed
    private String feed;

    @Indexed(type = "date")
    private String publishedDate;

    @Indexed(type = "date")
    private String updatedDate;

    /*
     * fields for creative-commons plugin
     *
     * Multivalued attribute.
     */
    @Indexed
    private List<String> cc;

    /* fields for tld plugin */
    @Indexed(stored = false, searchable = false)
    private String tld;

    /*
     * fields for index-html plugin
     *
     * Note: although raw document content may be binary,
     * index-html adds a String to the index field.
     */
    @Indexed(name = "rawcontent", searchable = false)
    private String rawContent;

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

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
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

    public List<String> getTitle() {
        return title;
    }

    public void setTitle(List<String> title) {
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

    public List<String> getText() {
        return text;
    }

    public void setText(List<String> text) {
        this.text = text;
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

    public String getContentLength() {
        return contentLength;
    }

    public void setContentLength(String contentLength) {
        this.contentLength = contentLength;
    }

    public String getLastModified() {
        return lastModified;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<String> getMeta() {
        return meta;
    }

    public void setMeta(List<String> meta) {
        this.meta = meta;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public List<String> getSubCollection() {
        return subCollection;
    }

    public void setSubCollection(List<String> subCollection) {
        this.subCollection = subCollection;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public List<String> getTag() {
        return tag;
    }

    public void setTag(List<String> tag) {
        this.tag = tag;
    }

    public String getFeed() {
        return feed;
    }

    public void setFeed(String feed) {
        this.feed = feed;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public List<String> getCc() {
        return cc;
    }

    public void setCc(List<String> cc) {
        this.cc = cc;
    }

    public String getTld() {
        return tld;
    }

    public void setTld(String tld) {
        this.tld = tld;
    }

    public String getRawContent() {
        return rawContent;
    }

    public void setRawContent(String rawContent) {
        this.rawContent = rawContent;
    }

    /* TO-STRING */

    @Override
    public String toString() {
        return "WebPageDocument{" +
                "id='" + id + '\'' +
                ", version=" + version +
                ", batchId='" + batchId + '\'' +
                ", digest='" + digest + '\'' +
                ", boost=" + boost +
                ", host='" + host + '\'' +
                ", url='" + url + '\'' +
                ", content='" + content + '\'' +
                ", title=" + title +
                ", cache='" + cache + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", text=" + text +
                ", anchor=" + anchor +
                ", type=" + type +
                ", contentLength='" + contentLength + '\'' +
                ", lastModified='" + lastModified + '\'' +
                ", date='" + date + '\'' +
                ", meta=" + meta +
                ", lang='" + lang + '\'' +
                ", subCollection=" + subCollection +
                ", author='" + author + '\'' +
                ", tag=" + tag +
                ", feed='" + feed + '\'' +
                ", publishedDate='" + publishedDate + '\'' +
                ", updatedDate='" + updatedDate + '\'' +
                ", cc=" + cc +
                ", tld='" + tld + '\'' +
                ", rawContent='" + rawContent + '\'' +
                "}\n";
    }


}