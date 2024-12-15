package com.sritiman.ecommerce.ecommerceapplication.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class OauthUserDetails {
    private String lastName;
    private String firstName;
    private String profilePicture;
}
