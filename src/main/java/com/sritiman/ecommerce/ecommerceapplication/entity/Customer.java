package com.sritiman.ecommerce.ecommerceapplication.entity;


import lombok.*;

import javax.persistence.*;
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

    @OneToMany(targetEntity = Address.class, cascade = {CascadeType.ALL})
    private List<Address> address;

    @Column(name = "phone_number")
    private String phoneNumber;

    private String password;

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
