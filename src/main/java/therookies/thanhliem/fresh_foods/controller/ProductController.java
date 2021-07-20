package therookies.thanhliem.fresh_foods.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

import therookies.thanhliem.fresh_foods.dto.ProductDTO;
import therookies.thanhliem.fresh_foods.dto.pageable.OutputDTO;
import therookies.thanhliem.fresh_foods.repository.UserRepository;
import therookies.thanhliem.fresh_foods.service.IProductService;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@RestController
@RequestMapping("/api")
public class ProductController{
    @Autowired
    private IProductService productService;
    @Autowired
    private UserRepository userRepository;

    @PostMapping(value="/product")
    public ProductDTO createProduct(@Valid @RequestBody ProductDTO model){
        model.setId(0L);
        return productService.save(model);
    }

    @PutMapping(value="/product/{id}")
    public ProductDTO updateProduct(@Valid @RequestBody ProductDTO model, @PathVariable("id") long id){
        model.setId(id);
        return productService.save(model);
    }
    @GetMapping(value="/product/{id}")
    public ProductDTO getProduct(@PathVariable("id") long id){
        return productService.findById(id);
    }

    @GetMapping(value="/product/all")
    public List<ProductDTO> getAllProduct(){
        return productService.findAll();
    }

    @GetMapping(value="/product")
    public List<ProductDTO> getByCategoryId(@RequestParam(name="categoryId") Long id) {
        return productService.findByCategoryId(id);
    }
    @DeleteMapping(value="/product/{id}")
    public Map<String,String> deleteProduct(@PathVariable(value = "id") Long id) {
        return productService.delete(id);
    }

    @GetMapping(value = "/productlist")
    public OutputDTO getProductPage(@RequestParam(name= "page",defaultValue = "1") @Min(value =1) int page,
                                    @RequestParam(name = "limit", defaultValue = "3") @Min(value =1) int limit,
                                    @RequestParam(name = "category", required = false)Long id,
                                    @RequestParam(name = "sort",defaultValue = "createdDate") String sortType,
                                    @RequestParam(name = "type",defaultValue = "1") int desc) {
        OutputDTO result = new OutputDTO();
        result.setPage(page);
        if(id==null) { //all product
            result.setTotalPage((int) Math.ceil((double) (productService.totalProduct())/limit));

            Pageable pageable;
            if(desc==1) pageable =  PageRequest.of(page-1,limit,Sort.by(sortType).descending());
            else  pageable =  PageRequest.of(page-1,limit,Sort.by(sortType).ascending());
            result.setListProduct(productService.findAll(pageable));
        }
        else { //product in category
            result.setTotalPage((int) Math.ceil((double) (productService.totalProduct(id))/limit));

            Pageable pageable;
            if(desc==1) pageable =  PageRequest.of(page-1,limit,Sort.by(sortType).descending());
            else  pageable =  PageRequest.of(page-1,limit,Sort.by(sortType).ascending());
            result.setListProduct(productService.findByCategoryId(id,pageable));
        }

        return result;
    }
}
