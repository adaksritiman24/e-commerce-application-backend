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

        try {
            productRepository.save(product);
        }
        catch (Exception e) {
            System.out.println("[FAIL]: " + e.getMessage());
        }

//        Product p1 = productRepository.findById(16L).orElse(null);
//        Product p1 = productRepository.findById(58L).orElse(null);
//        Product p1 = productRepository.findById(61L).orElse(null);
//        Product p4 = productRepository.findById(64L).orElse(null);
//        Product p1 = productRepository.findById(67L).orElse(null);
//        Product p1 = productRepository.findById(70L).orElse(null);
//        Product p1 = productRepository.findById(73L).orElse(null);
//        Product p1 = productRepository.findById(76L).orElse(null);
//        Product p9 = productRepository.findById(79L).orElse(null);
//
//        if(p1!=null){
//            List<AssociatedProduct> asp = new ArrayList<AssociatedProduct>();
//
//
//                asp.add(new AssociatedProduct(79L));
//                asp.add(new AssociatedProduct(73L));
//                asp.add(new AssociatedProduct(58L));
//                asp.add(new AssociatedProduct(61L));
//
//            p1.setAssociatedProducts(asp);
//        }
//        if(p1 != null)
//            productRepository.save(p1);

//        List<Category> categories = Arrays.asList(
//            new Category(1, "electronics"),
//            new Category(1, "home and kitchen"),
//            new Category(1, "fashion"),
//            new Category(1, "smartphones"),
//            new Category(1, "computers"),
//            new Category(1, "lifestyle"),
//            new Category(1, "beauty"),
//            new Category(1, "footwear"),
//            new Category(1, "baby products")
//        );
//
//        categoryRepository.saveAll(categories);

//
//        String name = "Moto edge 70 (Midnight Blue, 4GB, 64GB Storage)";
//        double normalPrice = 52000;
//        double discountedPrice = 48599;
//        String brand = "Motorola";
//        int rating = 0;
//        String seller = "Buzz Smartphones";
//        List<ProductImage> productImages= Arrays.asList(
//                new ProductImage("/buzz/imgs/products/motorola-edge-70.jpeg")
//        );
//
//        List<Keyword> keywords = Arrays.asList(
//                new Keyword("moto"),
//                new Keyword("motorola"),
//                new Keyword("edge 70"),
//                new Keyword("edge"),
//                new Keyword("mobiles"),
//                new Keyword("mobile"),
//                new Keyword("smartphone"),
//                new Keyword("smartphones"),
//                new Keyword("phone"),
//                new Keyword("phones")
//        );
//
//        List<ProductDetail> productDetails = Arrays.asList(
//                new ProductDetail("6000mAh lithium-ion battery, 1 year manufacturer warranty for device and 6 months manufacturer warranty for in-box accessories including batteries from the date of purchase"),
//                new ProductDetail("Upto 12GB RAM with RAM Plus | 64GB internal memory expandable up to 1TB| Dual Sim (Nano)"),
//                new ProductDetail("50MP+5MP+2MP Triple camera setup- True 50MP (F1.8) main camera +5MP(F2.2)+ 2MP (F2.4) | 8MP (F2.2) front cam"),
//                new ProductDetail("Android 12,One UI Core 4 with a powerful Octa Core Processor"),
//                new ProductDetail("16.72 centimeters (6.6-inch) FHD+ LCD - infinity O Display, FHD+ resolution with 1080 x 2408 pixels resolution, 401 PPI with 16M color")
//        );
//
//        Product product = new Product();
//
//        product.setName(name);
//        product.setNormalPrice(normalPrice);
//        product.setDiscountedPrice(discountedPrice);
//        product.setBrand(brand);
//        product.setRating(rating);
//        product.setSeller(seller);
//        product.setImages(productImages);
//        product.setKeywords(keywords);
//        product.setCategory(
//                categoryRepository.findByName("smartphones")
//        );
//        product.setProductDetails(productDetails);
//
//        productRepository.save(product);
//
//        Product product1 = productRepository.findByName("Samsung galaxy S10+ (12gb RAM, 256gb ROM)");
//        Product product2 = productRepository.findByName("Samsung S22 Ultra (Midnight Blue, 4GB, 64GB Storage)");
//
//        product1.setCategory(categoryRepository.findByName("smartphones"));
//        product2.setCategory(categoryRepository.findByName("smartphones"));
//
//        productRepository.save(product1);
//        productRepository.save(product2);
    }
}
