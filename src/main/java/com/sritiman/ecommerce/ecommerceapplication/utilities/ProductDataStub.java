package com.sritiman.ecommerce.ecommerceapplication.utilities;

import com.sritiman.ecommerce.ecommerceapplication.entity.*;
import com.sritiman.ecommerce.ecommerceapplication.repository.CategoryRepository;
import com.sritiman.ecommerce.ecommerceapplication.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
@Transactional
public class ProductDataStub implements CommandLineRunner {

    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;

    @Autowired

    public ProductDataStub(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(String... args) {

        Product product = getMoto();
        Product product1 = getSamsung();

        //first stub
        try {
            product.setAssociatedProducts(List.of(new AssociatedProduct(2L)));
            productRepository.save(product);
        }
        catch (Exception e) {
            System.out.println("[FAIL]: " + e.getMessage());
        }

        //second stub
        try {
            product1.setAssociatedProducts(List.of(new AssociatedProduct(1L)));
            productRepository.save(product1);
        }
        catch (Exception e) {
            System.out.println("[FAIL]: " + e.getMessage());
        }
    }

    private Product getMoto() {
        String name = "Moto edge 70 (Midnight Blue, 4GB, 64GB Storage)";
        double normalPrice = 52000;
        double discountedPrice = 48599;
        String brand = "Motorola";
        int rating = 0;
        String seller = "Buzz Smartphones";
        List<ProductImage> productImages= List.of(
                new ProductImage("/buzz/imgs/products/motorola-edge-70.jpeg")
        );
        Product product = new Product();

        List<Keyword> keywords = Arrays.asList(
                new Keyword("moto"),
                new Keyword("motorola"),
                new Keyword("edge 70"),
                new Keyword("edge"),
                new Keyword("mobiles"),
                new Keyword("mobile"),
                new Keyword("smartphone"),
                new Keyword("smartphones"),
                new Keyword("phone"),
                new Keyword("phones")
        );
        List<ProductDetail> productDetails = Arrays.asList(
                new ProductDetail("6000mAh lithium-ion battery, 1 year manufacturer warranty for device and 6 months manufacturer warranty for in-box accessories including batteries from the date of purchase"),
                new ProductDetail("Upto 12GB RAM with RAM Plus | 64GB internal memory expandable up to 1TB| Dual Sim (Nano)"),
                new ProductDetail("50MP+5MP+2MP Triple camera setup- True 50MP (F1.8) main camera +5MP(F2.2)+ 2MP (F2.4) | 8MP (F2.2) front cam"),
                new ProductDetail("Android 12,One UI Core 4 with a powerful Octa Core Processor"),
                new ProductDetail("16.72 centimeters (6.6-inch) FHD+ LCD - infinity O Display, FHD+ resolution with 1080 x 2408 pixels resolution, 401 PPI with 16M color")
        );
        product.setId(1L);
        product.setName(name);
        product.setNormalPrice(normalPrice);
        product.setDiscountedPrice(discountedPrice);
        product.setBrand(brand);
        product.setRating(rating);
        product.setSeller(seller);
        product.setImages(productImages);
        product.setKeywords(keywords);
        product.setCategory(
                categoryRepository.findByName("smartphones")
        );
        product.setProductDetails(productDetails);
        return product;
    }

    private Product getSamsung() {
        String name = "Samsung Galaxy s22 ultra (Black, 8GB, 256GB Storage)";
        double normalPrice = 92699;
        double discountedPrice = 87699;
        String brand = "Samsung";
        int rating = 0;
        String seller = "Buzz Smartphones";
        List<ProductImage> productImages= List.of(
                new ProductImage("/buzz/imgs/products/s22-ultra.jpg"),
                new ProductImage("/buzz/imgs/products/s22-ultra-2.jpg")
        );
        Product product = new Product();

        List<Keyword> keywords = Arrays.asList(
                new Keyword("samsung"),
                new Keyword("galaxy"),
                new Keyword("s22"),
                new Keyword("s22 ultra"),
                new Keyword("ultra"),
                new Keyword("mobile"),
                new Keyword("smartphone"),
                new Keyword("smartphones"),
                new Keyword("phone"),
                new Keyword("phones")
        );
        List<ProductDetail> productDetails = Arrays.asList(
                new ProductDetail("6000mAh lithium-ion battery, 1 year manufacturer warranty for device and 6 months manufacturer warranty for in-box accessories including batteries from the date of purchase"),
                new ProductDetail("Upto 12GB RAM with RAM Plus | 64GB internal memory expandable up to 1TB| Dual Sim (Nano)"),
                new ProductDetail("50MP+5MP+2MP Triple camera setup- True 50MP (F1.8) main camera +5MP(F2.2)+ 2MP (F2.4) | 8MP (F2.2) front cam"),
                new ProductDetail("Android 12,One UI Core 4 with a powerful Octa Core Processor"),
                new ProductDetail("16.72 centimeters (6.6-inch) FHD+ LCD - infinity O Display, FHD+ resolution with 1080 x 2408 pixels resolution, 401 PPI with 16M color")
        );
        product.setId(2L);
        product.setName(name);
        product.setNormalPrice(normalPrice);
        product.setDiscountedPrice(discountedPrice);
        product.setBrand(brand);
        product.setRating(rating);
        product.setSeller(seller);
        product.setImages(productImages);
        product.setKeywords(keywords);
        product.setCategory(
                categoryRepository.findByName("smartphones")
        );
        product.setProductDetails(productDetails);
        return product;
    }
}
