package financial.news.se;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.solr.repository.config.EnableSolrRepositories;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@SpringBootApplication
public class FinancialNewsSearchEngineApplication {

    public static void main(String[] args) {
        SpringApplication.run(FinancialNewsSearchEngineApplication.class, args);
    }

}

@Configuration
@EnableSpringDataWebSupport
@EnableSolrRepositories(basePackages = {"financial.news.se.page"}, multicoreSupport = true)
class SolrConfig {

    @Bean
    public SolrClient solrClient(@Value("${solr.host}") String solrHost) {
        return new HttpSolrClient(solrHost);
    }

}