package com.sritiman.ecommerce.ecommerceapplication.utilities;

import com.sritiman.ecommerce.ecommerceapplication.entity.Keyword;
import com.sritiman.ecommerce.ecommerceapplication.entity.Product;
import com.sritiman.ecommerce.ecommerceapplication.entity.ProductDetail;
import com.sritiman.ecommerce.ecommerceapplication.entity.ProductImage;
import com.sritiman.ecommerce.ecommerceapplication.repository.ProductRepository;
import org.hibernate.Hibernate;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
@Transactional
public class ProductDataStub implements CommandLineRunner {

    private ProductRepository productRepository;

    public ProductDataStub(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void run(String... args) throws Exception {


        return;
//
//        String name = "Samsung galaxy S10+ (12gb RAM, 256gb ROM)";
//        double normalPrice = 82000;
//        double discountedPrice = 71599;
//        String brand = "Samsung";
//        int rating = 0;
//        String seller = "ASIN Electronics";
//        List<ProductImage> productImages= Arrays.asList(
//                new ProductImage("/buzz/imgs/products/s10plus.jpg"),
//                new ProductImage("/buzz/imgs/products/s10plus-3.jpg"),
//                new ProductImage("/buzz/imgs/products/s10plus-2.jpg")
//        );
//
//        List<Keyword> keywords = Arrays.asList(
//                new Keyword("samsung"),
//                new Keyword("galaxy"),
//                new Keyword("smartphone"),
//                new Keyword("mobile"),
//                new Keyword("s10"),
//                new Keyword("s10+"),
//                new Keyword("phone")
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
//        product.setProductDetails(productDetails);
//
//        productRepository.save(product);
    }
}
