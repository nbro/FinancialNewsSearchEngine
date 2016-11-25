package financial.news.se.controller;

import financial.news.se.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Nelson on 25.11.16.
 */
@Controller
@Component
@Scope("prototype")
public class SearchController {

    private ProductService productService;

    @RequestMapping(value = "/search")
    @ResponseBody
    public String search(Model model,
                         @RequestParam(value = "q", required = false) String query,
                         @PageableDefault(page = 0, size = ProductService.DEFAULT_PAGE_SIZE) Pageable pageable,
                         HttpServletRequest request) {

//        model.addAttribute("page", productService.findByName(query, pageable));
//        model.addAttribute("pageable", pageable);
//        model.addAttribute("query", query);

        return "nice to know";
    }

    /*
    @ResponseBody
    @RequestMapping(value = "/autocomplete", produces = "application/json")
    public Set<String> autoComplete(Model model, @RequestParam("term") String query,
                                    @PageableDefault(page = 0, size = 1) Pageable pageable) {
        if (!StringUtils.hasText(query)) {
            return Collections.emptySet();
        }

        FacetPage<Product> result = productService.autocompleteNameFragment(query, pageable);

        Set<String> titles = new LinkedHashSet<String>();
        for (Page<FacetFieldEntry> page : result.getFacetResultPages()) {
            for (FacetFieldEntry entry : page) {
                if (entry.getValue().contains(query)) { // we have to do this as we do not use terms vector or a string field
                    titles.add(entry.getValue());
                }
            }
        }
        return titles;
    } */

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

}