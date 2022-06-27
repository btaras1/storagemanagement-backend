package com.management.storage.dto.response;

import com.management.storage.model.Receipt;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BuyerResponseDto {

    private Long id;
    private String firstname;
    private String lastname;
    private String address;
    private String city;
    private String mobile;

    private List<Receipt> receipts;
}
