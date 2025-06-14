package com.makeart.makeart_server.business.converter;

import com.makeart.makeart_server.business.dto.AddressDTO;
import com.makeart.makeart_server.business.dto.PhoneDTO;
import com.makeart.makeart_server.business.dto.UserDTO;
import com.makeart.makeart_server.infrastructure.entity.Address;
import com.makeart.makeart_server.infrastructure.entity.Phone;
import com.makeart.makeart_server.infrastructure.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UserConverter {

    private final PhoneConverter phoneConverter;
    private final AddressConverter addressConverter;

    public List<UserDTO> toUserDTOList(List<User> userList) {
        List<UserDTO> userDTOList = new ArrayList<UserDTO>();

        for (User user: userList) {
            userDTOList.add(toUserDTO(user));
        }

        return userDTOList;
    }


    public UserDTO toUserDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .lastname(user.getLastname())
                .rg(user.getRg())
                .cpf(user.getCpf())
                .email(user.getEmail())
                .password(user.getPassword())
                .access(user.getAccess())
                .phone(phoneConverter.toListPhoneDTO(user.getPhone()))
                .address(addressConverter.toListAddressDTO(user.getAddress()))
                .build();
    }

    public List<User> toUserList(List<UserDTO> userDTOList) {
        List<User> userList = new ArrayList<User>();

        for (UserDTO userDTO: userDTOList) {
            userList.add(toUserEntity(userDTO));
        }

        return userList;
    }

    public User toUserEntity(UserDTO userDTO) {
        User user = User.builder()
                .id(userDTO.getId())
                .name(userDTO.getName())
                .lastname(userDTO.getLastname())
                .rg(userDTO.getRg())
                .cpf(userDTO.getCpf())
                .email(userDTO.getEmail())
                .password(userDTO.getPassword())
                .access(userDTO.getAccess())
                .build();

        if (userDTO.getPhone() != null) {
            List<Phone> phones = new ArrayList<Phone>();

            for (PhoneDTO phoneDTO: userDTO.getPhone()) {
                Phone phone = phoneConverter.toPhoneEntity(phoneDTO);
                phone.setUser(user);
                phones.add(phone);
            }

            user.setPhone(phones);
        }

        if (userDTO.getAddress() != null) {
            List<Address> addresses = new ArrayList<Address>();

            for (AddressDTO addressDTO: userDTO.getAddress()) {
                Address address = addressConverter.toAddressEntity(addressDTO);
                address.setUser(user);
                addresses.add(address);
            }

            user.setAddress(addresses);

        }

        return user;
    }
}
