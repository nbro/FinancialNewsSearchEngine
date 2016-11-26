package financial.news.se;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.embedded.EmbeddedSolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.solr.core.SolrOperations;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.repository.config.EnableSolrRepositories;
import org.springframework.data.solr.server.support.EmbeddedSolrServerFactory;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

@SpringBootApplication
public class FinancialNewsSearchEngineApplication {

    public static void main(String[] args) {
        SpringApplication.run(FinancialNewsSearchEngineApplication.class, args);
    }

}

@Configuration
@EnableSpringDataWebSupport
@EnableSolrRepositories(basePackages = {"financial.news.se.page"}, multicoreSupport = true)
class SearchContext {

    @Bean
    public SolrClient solrClient(@Value("${solr.host}") String solrHost) {
        return new HttpSolrClient(solrHost);
    }

}


//@Configuration
//@EnableSolrRepositories
//class ApplicationConfig {
//
//    @Bean
//    public EmbeddedSolrServer solrServer() {
//
//        EmbeddedSolrServerFactory factory = null;
//
//        try {
//            factory = new EmbeddedSolrServerFactory("classpath:solr");
//            return factory.getSolrClient();
//        } catch (ParserConfigurationException | IOException | SAXException e) {
//            e.printStackTrace();
//        }
//
//        return null;
//    }
//
//    @Bean
//    public SolrOperations solrTemplate() {
//        return new SolrTemplate(solrServer());
//    }
//}