package com.sritiman.ecommerce.ecommerceapplication.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String house;
    private String locality;
    private String city;
    private String country;
    private String pincode;
    private String phone;
    private String email;
    private String name;

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", house='" + house + '\'' +
                ", locality='" + locality + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", pincode='" + pincode + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
