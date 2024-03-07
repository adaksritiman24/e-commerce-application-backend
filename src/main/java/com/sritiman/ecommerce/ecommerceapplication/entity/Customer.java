package com.sritiman.ecommerce.ecommerceapplication.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "customer")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    @Column(unique = true)
    private String username;

    @Column(unique = true)
    private String email;

    @OneToOne(targetEntity = Address.class, cascade = {CascadeType.ALL})
    private Address address;

    @Column(name = "phone_number")
    private String phoneNumber;

    private String password;

    @OneToOne(targetEntity = Cart.class, cascade = {CascadeType.ALL})
    private Cart cart;

    @OneToMany
    private List<Order> orders = new ArrayList<>();

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", address=" + address +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
