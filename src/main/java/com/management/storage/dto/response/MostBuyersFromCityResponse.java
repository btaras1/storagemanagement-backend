package com.management.storage.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class MostBuyersFromCityResponse {
    private String city;
    private Long count;
}
