package com.makeart.makeart_server.business.converter;

import com.makeart.makeart_server.business.dto.AddressDTO;
import com.makeart.makeart_server.infrastructure.entity.Address;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AddressConverter {

    public List<AddressDTO> toListAddressDTO(List<Address> addressList) {
        List<AddressDTO> addresses = new ArrayList<AddressDTO>();

        for (Address address : addressList) {
            addresses.add(toAddressDTO(address));
        }

        return addresses;
    }

    public AddressDTO toAddressDTO(Address address) {
        return AddressDTO.builder()
                .street(address.getStreet())
                .number(address.getNumber())
                .neighborhood(address.getNeighborhood())
                .city(address.getCity())
                .cep(address.getCep())
                .uf(address.getUf())
                .build();
    }

    public List<Address> toListAddressEntity(List<AddressDTO> addressDTOList) {
        List<Address> addresses = new ArrayList<Address>();

        for (AddressDTO address : addressDTOList) {
            addresses.add(toAddressEntity(address));
        }

        return addresses;
    }

    public Address toAddressEntity(AddressDTO addressDTO) {
        return Address.builder()
                .street(addressDTO.getStreet())
                .number(addressDTO.getNumber())
                .neighborhood(addressDTO.getNeighborhood())
                .city(addressDTO.getCity())
                .cep(addressDTO.getCep())
                .uf(addressDTO.getUf())
                .build();
    }

}
