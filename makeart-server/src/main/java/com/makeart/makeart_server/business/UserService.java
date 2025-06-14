package com.makeart.makeart_server.business;

import com.makeart.makeart_server.business.converter.UserConverter;
import com.makeart.makeart_server.business.dto.UserDTO;
import com.makeart.makeart_server.infrastructure.entity.User;
import com.makeart.makeart_server.infrastructure.exceptions.ConflictException;
import com.makeart.makeart_server.infrastructure.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserConverter userConverter;

    public UserDTO registerUser(UserDTO userDTO) {

        try {
            User user = userConverter.toUserEntity(userDTO);

            return userConverter.toUserDTO(userRepository.save(user));


        } catch (ConflictException e) {
            throw new ConflictException(e.getMessage());
        }
    }

}
