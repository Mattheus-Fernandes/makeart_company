package com.makeart.makeart_server.business.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressDTO {

    private Long id;
    private String street;
    private String number;
    private String neighborhood;
    private String city;
    private String cep;
    private String uf;

}
