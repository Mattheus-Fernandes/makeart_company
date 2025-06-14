package com.makeart.makeart_server.business.converter;

import com.makeart.makeart_server.business.dto.PhoneDTO;
import com.makeart.makeart_server.infrastructure.entity.Phone;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PhoneConverter {

    public List<PhoneDTO> toListPhoneDTO(List<Phone> phoneList) {
        List<PhoneDTO> phones = new ArrayList<PhoneDTO>();

        for (Phone phone: phoneList) {
            phones.add(toPhoneDTO(phone));
        }

        return phones;

    }

    public PhoneDTO toPhoneDTO(Phone phone) {
        return PhoneDTO.builder()
                .ddd(phone.getDdd())
                .number(phone.getNumber())
                .build();
    }

    public List<Phone> toListPhoneEntity(List<PhoneDTO> phoneList) {
        List<Phone> phones = new ArrayList<Phone>();

        for (PhoneDTO phone: phoneList) {
            phones.add(toPhoneEntity(phone));
        }

        return phones;

    }

    public Phone toPhoneEntity(PhoneDTO phone) {
        return Phone.builder()
                .ddd(phone.getDdd())
                .number(phone.getNumber())
                .build();
    }
}
