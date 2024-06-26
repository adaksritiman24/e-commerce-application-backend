package com.sritiman.ecommerce.ecommerceapplication.utilities;

import com.sritiman.ecommerce.ecommerceapplication.entity.*;
import com.sritiman.ecommerce.ecommerceapplication.repository.CategoryRepository;
import com.sritiman.ecommerce.ecommerceapplication.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;

import java.util.*;

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
        Product product2 = getRealme();
        Product product3 = getSamsungM13();
        Product product4 = getPocoX5();

        //first stub
        try {
            product.setAssociatedProducts(List.of(new AssociatedProduct(2L)));
            productRepository.save(product);
        } catch (Exception e) {
            System.out.println("[FAIL]: " + e.getMessage());
        }

        //second stub
        try {
            product1.setAssociatedProducts(List.of(new AssociatedProduct(1L), new AssociatedProduct(5L), new AssociatedProduct(4L)));
            productRepository.save(product1);
        } catch (Exception e) {
            System.out.println("[FAIL]: " + e.getMessage());
        }

        //third stub
        try {
            product2.setAssociatedProducts(List.of(new AssociatedProduct(1L), new AssociatedProduct(2L)));
            productRepository.save(product2);
        } catch (Exception e) {
            System.out.println("[FAIL]: " + e.getMessage());
        }
        //fourth stub
        try {
            product3.setAssociatedProducts(List.of(new AssociatedProduct(1L), new AssociatedProduct(2L), new AssociatedProduct(5L), new AssociatedProduct(3L)));
            productRepository.save(product3);
        } catch (Exception e) {
            System.out.println("[FAIL]: " + e.getMessage());
        }

        //fifth stub
        try {
            product4.setAssociatedProducts(List.of(new AssociatedProduct(1L), new AssociatedProduct(2L), new AssociatedProduct(4L)));
            productRepository.save(product4);
        } catch (Exception e) {
            System.out.println("[FAIL]: " + e.getMessage());
        }
    }

    private Product getMoto() {
        String name = "Moto edge 70 (Midnight Blue, 4GB, 64GB Storage)";
        double normalPrice = 52000;
        double discountedPrice = 48599;
        String brand = "Motorola";
        String seller = "Buzz Smartphones";
        List<ProductImage> productImages = List.of(
                new ProductImage("/buzz/imgs/products/motorola-edge-70.jpeg")
        );
        Product product = new Product();
        restoreExistingReviews(product, 1L);

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
        product.setSeller(seller);
        product.setImages(productImages);
        product.setKeywords(keywords);
        product.setCategory(
                categoryRepository.findByName("smartphones")
        );
        product.setProductDetails(productDetails);
        return product;
    }

    private void restoreExistingReviews(Product product, long pid) {
        Optional<Product> productOPT = productRepository.findById(pid);
        if (productOPT.isPresent()) {
            List<Review> newReviews = productOPT.get().getReviews();

            Integer productRating = newReviews.size() > 0 ? newReviews.stream().map(Review::getRating).reduce(0, Integer::sum) / newReviews.size() : 0;
            product.setRating(productRating);
            product.setReviews(newReviews);
        }
    }

    private Product getSamsung() {
        String name = "Samsung Galaxy s22 ultra (Black, 8GB, 256GB Storage)";
        double normalPrice = 92699;
        double discountedPrice = 87699;
        String brand = "Samsung";
        String seller = "Buzz Smartphones";
        List<ProductImage> productImages = List.of(
                new ProductImage("/buzz/imgs/products/s22-ultra.jpg"),
                new ProductImage("/buzz/imgs/products/s22-ultra-2.jpg")
        );
        Product product = new Product();
        restoreExistingReviews(product, 2L);

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
        product.setSeller(seller);
        product.setImages(productImages);
        product.setKeywords(keywords);
        product.setCategory(
                categoryRepository.findByName("smartphones")
        );
        product.setProductDetails(productDetails);
        return product;
    }

    private Product getRealme() {
        String name = "Realme Narzo 7";
        double normalPrice = 11099;
        double discountedPrice = 7699;
        String brand = "Realme";
        String seller = "LMN Smartphones India";
        List<ProductImage> productImages = List.of(
                new ProductImage("/buzz/imgs/products/realme-narzo-7.jpg"),
                new ProductImage("/buzz/imgs/products/realme-narzo-7-2.jpg")
        );
        Product product = new Product();
        restoreExistingReviews(product, 3L);

        List<Keyword> keywords = Arrays.asList(
                new Keyword("realme"),
                new Keyword("realme narzo"),
                new Keyword("realme narzo 7"),
                new Keyword("narzo 7"),
                new Keyword("narzo"),
                new Keyword("mobile"),
                new Keyword("smartphone"),
                new Keyword("smartphones"),
                new Keyword("phone"),
                new Keyword("phones")
        );
        List<ProductDetail> productDetails = Arrays.asList(
                new ProductDetail("6500mAh lithium-ion battery, 1 year manufacturer warranty for device and 6 months manufacturer warranty for in-box accessories including batteries from the date of purchase"),
                new ProductDetail("Upto 4GB RAM with RAM Plus | 64GB internal memory expandable up to 1TB| Dual Sim (Nano)"),
                new ProductDetail("50MP+5MP+2MP Triple camera setup- True 50MP (F1.8) main camera +5MP(F2.2)+ 2MP (F2.4) | 8MP (F2.2) front cam"),
                new ProductDetail("Android 12,One UI Core 4 with a powerful Octa Core Processor"),
                new ProductDetail("16.72 centimeters (6.6-inch) FHD+ LCD - infinity O Display, FHD+ resolution with 1080 x 2408 pixels resolution, 401 PPI with 16M color")
        );
        product.setId(3L);
        product.setName(name);
        product.setNormalPrice(normalPrice);
        product.setDiscountedPrice(discountedPrice);
        product.setBrand(brand);
        product.setSeller(seller);
        product.setImages(productImages);
        product.setKeywords(keywords);
        product.setCategory(
                categoryRepository.findByName("smartphones")
        );
        product.setProductDetails(productDetails);
        return product;
    }

    private Product getSamsungM13() {
        String name = "Samsung Galaxy M13";
        double normalPrice = 23099;
        double discountedPrice = 13299;
        String brand = "Samsung";
        String seller = "LMN Smartphones India";
        List<ProductImage> productImages = List.of(
                new ProductImage("/buzz/imgs/products/m13-galaxy.jpg"),
                new ProductImage("/buzz/imgs/products/m13-galaxy-2.jpg")
        );
        Product product = new Product();
        restoreExistingReviews(product, 4L);

        List<Keyword> keywords = Arrays.asList(
                new Keyword("samsung"),
                new Keyword("samsung galaxy"),
                new Keyword("galaxy"),
                new Keyword("m13"),
                new Keyword("m 13"),
                new Keyword("13"),
                new Keyword("mobile"),
                new Keyword("mobiles"),
                new Keyword("smartphone"),
                new Keyword("smartphones"),
                new Keyword("phone"),
                new Keyword("phones")
        );
        List<ProductDetail> productDetails = Arrays.asList(
                new ProductDetail("6500mAh lithium-ion battery, 1 year manufacturer warranty for device and 6 months manufacturer warranty for in-box accessories including batteries from the date of purchase"),
                new ProductDetail("Upto 4GB RAM with RAM Plus | 64GB internal memory expandable up to 1TB| Dual Sim (Nano)"),
                new ProductDetail("50MP+5MP+2MP Triple camera setup- True 50MP (F1.8) main camera +5MP(F2.2)+ 2MP (F2.4) | 8MP (F2.2) front cam"),
                new ProductDetail("Android 12,One UI Core 4 with a powerful Octa Core Processor"),
                new ProductDetail("16.72 centimeters (6.6-inch) FHD+ LCD - infinity O Display, FHD+ resolution with 1080 x 2408 pixels resolution, 401 PPI with 16M color")
        );
        product.setId(4L);
        product.setName(name);
        product.setNormalPrice(normalPrice);
        product.setDiscountedPrice(discountedPrice);
        product.setBrand(brand);
        product.setSeller(seller);
        product.setImages(productImages);
        product.setKeywords(keywords);
        product.setCategory(
                categoryRepository.findByName("smartphones")
        );
        product.setProductDetails(productDetails);
        return product;
    }

    private Product getPocoX5() {
        String name = "Poco X5";
        double normalPrice = 17099;
        double discountedPrice = 11199;
        String brand = "Poco";
        String seller = "POCO India LTD.";
        List<ProductImage> productImages = List.of(
                new ProductImage("/buzz/imgs/products/poco-x5.jpg")
        );
        Product product = new Product();
        restoreExistingReviews(product, 5L);

        List<Keyword> keywords = Arrays.asList(
                new Keyword("poco"),
                new Keyword("samsung"),
                new Keyword("galaxy"),
                new Keyword("poco x5"),
                new Keyword("x5"),
                new Keyword("poco phones"),
                new Keyword("poco mobiles"),
                new Keyword("mobile"),
                new Keyword("mobiles"),
                new Keyword("smartphone"),
                new Keyword("smartphones"),
                new Keyword("phone"),
                new Keyword("phones")
        );
        List<ProductDetail> productDetails = Arrays.asList(
                new ProductDetail("6500mAh lithium-ion battery, 1 year manufacturer warranty for device and 6 months manufacturer warranty for in-box accessories including batteries from the date of purchase"),
                new ProductDetail("Upto 4GB RAM with RAM Plus | 64GB internal memory expandable up to 1TB| Dual Sim (Nano)"),
                new ProductDetail("50MP+5MP+2MP Triple camera setup- True 50MP (F1.8) main camera +5MP(F2.2)+ 2MP (F2.4) | 8MP (F2.2) front cam"),
                new ProductDetail("Android 12,One UI Core 4 with a powerful Octa Core Processor"),
                new ProductDetail("16.72 centimeters (6.6-inch) FHD+ LCD - infinity O Display, FHD+ resolution with 1080 x 2408 pixels resolution, 401 PPI with 16M color")
        );
        product.setId(5L);
        product.setName(name);
        product.setNormalPrice(normalPrice);
        product.setDiscountedPrice(discountedPrice);
        product.setBrand(brand);
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
