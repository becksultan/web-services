package main.controller;

import main.entity.Product;
import main.exception.ProductNotFoundException;
import main.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "product")
public class ProductController {
    @Autowired
    private ProductRepository repository;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<Product> index() {
        return repository.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Product get(@PathVariable(name = "id") Long id) {
        return repository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public Product insert(@RequestBody Product product) {
        return repository.save(product);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Product edit(@RequestBody Product product, @PathVariable Long id) {
        return repository.findById(id).map(product1 -> {
            product1.setName(product.getName());
            product1.setPrice(product.getPrice());
            return repository.save(product1);
        })
                .orElseGet(()->{
                    product.setId(id);
                    return repository.save(product);
                });
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id) {
        repository.deleteById(id);
    }
}