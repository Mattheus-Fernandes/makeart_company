package com.makeart.makeart_server.business.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {

    private Long id;
    private String name;
    private String lastname;
    private String rg;
    private String cpf;
    private String email;
    private String password;
    private Long access;

    private List<PhoneDTO> phone;
    private List<AddressDTO> address;
}
