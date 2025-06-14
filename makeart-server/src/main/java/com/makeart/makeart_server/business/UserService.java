package com.makeart.makeart_server.business;

import com.makeart.makeart_server.business.converter.PhoneConverter;
import com.makeart.makeart_server.business.converter.UserConverter;
import com.makeart.makeart_server.business.dto.PhoneDTO;
import com.makeart.makeart_server.business.dto.UserDTO;
import com.makeart.makeart_server.infrastructure.entity.User;
import com.makeart.makeart_server.infrastructure.exceptions.ConflictException;
import com.makeart.makeart_server.infrastructure.repository.PhoneRepository;
import com.makeart.makeart_server.infrastructure.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PhoneRepository phoneRepository;
    private final UserConverter userConverter;
    private final PhoneConverter phoneConverter;

    public UserDTO registerUser(UserDTO userDTO) {

        try {
            existsRg(userDTO);
            existsCpf(userDTO);
            existsEmail(userDTO);
            existsNumber(userDTO.getPhone());

            User user = userConverter.toUserEntity(userDTO);

            return userConverter.toUserDTO(userRepository.save(user));

        } catch (ConflictException e) {
            throw new ConflictException(e.getMessage());
        }
    }

    public void existsRg(UserDTO userDTO) {

        try {
            boolean exist = userRepository.existsByRg(userDTO.getRg());

            if (exist) {
                throw new ConflictException("RG já cadastrado " + userDTO.getRg());
            }
        } catch (ConflictException e) {
            throw new ConflictException(e.getMessage());
        }
    }

    public void existsCpf(UserDTO userDTO) {
        try {
            boolean exist = userRepository.existsByCpf(userDTO.getCpf());

            if (exist) {
                throw new ConflictException("CPF já cadastrado " + userDTO.getCpf());
            }
        } catch (ConflictException e) {
            throw new ConflictException(e.getMessage());
        }
    }

    public void existsEmail(UserDTO userDTO) {
        try {
            boolean exist = userRepository.existsByEmail(userDTO.getEmail());

            if (exist) {
                throw new ConflictException("E-mail já cadastrado " + userDTO.getEmail());
            }
        } catch (ConflictException e) {
            throw new ConflictException(e.getMessage());
        }
    }

    public void existsNumber(List<PhoneDTO> phoneDTOList) {
        try {

            for (PhoneDTO phoneDTO : phoneDTOList) {
                if (phoneRepository.existsByDddAndNumber(phoneDTO.getDdd(), phoneDTO.getNumber())) {
                    throw new ConflictException("Celular já cadastrado: (" + phoneDTO.getDdd() + ") " + phoneDTO.getNumber());

                }
            }

        } catch (ConflictException e) {
            throw new ConflictException(e.getMessage());
        }

    }

}
