package com.sritiman.ecommerce.ecommerceapplication.entity;

import lombok.Getter;
import lombok.Value;

import javax.persistence.*;

@Embeddable
@Getter
public class AssociatedProduct {

    long id;
}
