package com.sritiman.ecommerce.ecommerceapplication.model.response;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrderHistoryResponseDTO {
    int totalPages;
    int requestedPageNumber;
    List<OrderHistoryItemDTO> orders;
}
