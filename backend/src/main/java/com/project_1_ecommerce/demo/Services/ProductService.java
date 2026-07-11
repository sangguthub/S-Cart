package com.project_1_ecommerce.demo.Services;

import com.project_1_ecommerce.demo.Model.Product;
import com.project_1_ecommerce.demo.Repository.ProductRepo;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ProductService {

    private ProductRepo productRepository;


    ProductService(ProductRepo productRepository) {
        this.productRepository = productRepository;
    }


    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    public Product getProductByID(int id) {
        return productRepository.findById(id).orElse(null);
    }

    public Product addProduct(Product product, MultipartFile imageFile) throws IOException {
        product.setImageName(imageFile.getOriginalFilename());
        product.setImageType(imageFile.getContentType());
        product.setImageData(imageFile.getBytes());
        return productRepository.save(product);
    }

    public Product updateProduct(int id, Product product, MultipartFile imageFile) throws IOException {
        product.setImageName(imageFile.getOriginalFilename());
        product.setImageType(imageFile.getContentType());
        product.setImageData(imageFile.getBytes());
        return productRepository.save(product);
    }

    public void deleteProductByID(int id) {
        productRepository.deleteById(id);
    }
    public  List<Product> searchProduct(String key) {
        return productRepository.searchProduct(key);
    }

}
