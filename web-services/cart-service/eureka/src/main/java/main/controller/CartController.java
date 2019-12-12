package main.controller;

import main.entity.Cart;
import main.entity.Product;

import main.exception.CartNotFoundException;
import main.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping(value = "cart")
public class CartController {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private CartRepository repository;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Cart cart(@PathVariable Long id) {
        return repository.findById(id).orElseThrow(() -> new CartNotFoundException(id));
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public Cart create() {
        Cart cart = new Cart();
        return repository.save(cart);
    }

    @RequestMapping(value = "/{id}/add_product/{product_id}/{quantity}", method = RequestMethod.GET)
    public Cart addProduct(@PathVariable Long id, @PathVariable Long product_id, @PathVariable int quantity) {
        Cart cart = repository.findById(id).orElseThrow(() -> new CartNotFoundException(id));
        Product product = restTemplate.getForObject("http://product-service/product/" + product_id, Product.class);
        int total = cart.getTotal();
        for (int i = 0; i < quantity; i++) {
            total += product.getPrice();
        }
        cart.setTotal(total);
        return repository.save(cart);
    }
}
