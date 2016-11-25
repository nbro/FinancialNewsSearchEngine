package financial.news.se.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Angular, Bootstrap, Wro4j, Jquery uses based on:
 * https://github.com/spring-guides/tut-spring-security-and-angular-js/tree/master/basic
 * <p>
 * Created by Nelson on 25.11.16.
 */
@RestController
public class IndexController {

    @RequestMapping("/resource")
    public Map<String, Object> index() {

        Map<String, Object> model = new HashMap<String, Object>();

        model.put("id", UUID.randomUUID().toString());
        model.put("content", "Hello World");

        return model;
    }

}
