package com.project_1_ecommerce.demo.Controller;

import com.project_1_ecommerce.demo.Model.Product;

import com.project_1_ecommerce.demo.Services.ProductService;
import jakarta.persistence.GeneratedValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
//to integrate frontend address with backend address for security
@CrossOrigin
public class ProductController {
    @Autowired
    private ProductService productService;

    @RequestMapping("/")
    public String getMsg() {
        return "hello world";
    }

    //read
    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts() {
        return new ResponseEntity<>(productService.getAllProduct(), HttpStatus.OK);
    }

    //readbyid
    @GetMapping("/product/{id}")
    public ResponseEntity<?> getProductById(@PathVariable int id) {

        if (productService.getProductByID(id) != null) {
            return new ResponseEntity<>(productService.getProductByID(id), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //create
    @PostMapping("/product")
    public ResponseEntity<?> addProduct(@RequestPart Product product,
                                        @RequestPart MultipartFile imageFile) {
        try {
            Product product1 = productService.addProduct(product, imageFile);
            return new ResponseEntity<>(product1, HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

    }

    //getimage by id
    @GetMapping("product/{productId}/image")
    public ResponseEntity<?> getProductImage(@PathVariable int productId) {

        Product product = productService.getProductByID(productId);
        if (product != null) {
            byte[] imageFile = product.getImageData();
            return ResponseEntity.ok()
                    .contentType(MediaType.valueOf(product.getImageType()))
                    .body(imageFile);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @PutMapping("product/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable int id, @RequestPart Product product,
                                           @RequestPart MultipartFile imageFile) {
        Product product1 = null;
        try {
            product1 = productService.updateProduct(id, product, imageFile);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (product1 != null) {
            return new ResponseEntity<>(product1, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("product/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable int id) {
        Product product = productService.getProductByID(id);

        if (product != null) {
            productService.deleteProductByID(id);
            return new ResponseEntity<>("Deleted", HttpStatus.OK);
        } else
            return new ResponseEntity<>("Product Not Found", HttpStatus.NOT_FOUND);
    }


    @GetMapping("products/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam String key){
    List<Product> products=productService.searchProduct(key);
    if(products!=null){
        return new  ResponseEntity<>(products,HttpStatus.OK);
    }
        return new  ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


}



//    @PostMapping("/test")
//    public ResponseEntity<?> test(@RequestBody String body) {
//        System.out.println(body);
//        return ResponseEntity.ok(body);
//    }
