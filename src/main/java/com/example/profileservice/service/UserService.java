package com.example.profileservice.service;

import com.example.profileservice.domain.User;
import com.example.profileservice.dto.UserDto;
import com.example.profileservice.dto.UserLoginDto;
import com.example.profileservice.dto.UserSignUpResultDto;
import com.example.profileservice.exceptions.DuplicateUserException;
import com.example.profileservice.exceptions.NotFoundUserException;
import com.example.profileservice.mapper.UserRowMapper;
import com.example.profileservice.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserRowMapper userRowMapper;
    private WebClientService webClientService;

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public UserSignUpResultDto insert(UserDto userDto) {
        if (!userRepository.existsByLogin(userDto.getLogin())) {
            User user = userRowMapper.toEntity(userDto);
            if (user.getId() != null) {
                throw new IllegalArgumentException("You shouldn't pass id for new user");
            }
            User saved = userRepository.save(user);
            webClientService.createCredentials(UserLoginDto.builder()
                    .login(userDto.getLogin())
                    .password(userDto.getPassword())
                    .build());
            return UserSignUpResultDto.builder()
                    .id(saved.getId())
                    .isSuccess(true)
                    .build();
        } else {
            throw new DuplicateUserException("User with login=%s already exists ");
        }
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void update(UserDto userDto, String oldLogin) {
        if (userRepository.existsByLogin(oldLogin)) {
            User user = userRowMapper.toEntity(userDto);
            userRepository.save(user);
            if (userDto.getPassword() != null || !oldLogin.equals(userDto.getLogin())) {
                webClientService.updateCredentials(UserLoginDto.builder()
                        .login(userDto.getLogin())
                        .password(userDto.getPassword())
                        .build(), oldLogin);
            }
        } else {
            throw new NotFoundUserException(String.format("User with login=%s not found in database for update", oldLogin));
        }
    }

    public UserDto findByLogin(String login) {
        return userRepository.findByLogin(login).map(userRowMapper::toDto)
                .orElseThrow(() -> new NotFoundUserException(String.format("Not found user for login=%s", login)));
    }
}
