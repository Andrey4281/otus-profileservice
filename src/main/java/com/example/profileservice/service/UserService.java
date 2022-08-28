package com.example.profileservice.service;

import com.example.profileservice.domain.User;
import com.example.profileservice.dto.UserDto;
import com.example.profileservice.exceptions.DuplicateUserException;
import com.example.profileservice.exceptions.NotFoundUserException;
import com.example.profileservice.mapper.UserRowMapper;
import com.example.profileservice.repository.UserRepository;
import com.example.profileservice.service.util.SecurityUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserRowMapper userRowMapper;
    private final SecurityUtil securityUtil;

    @Transactional(readOnly = false)
    public void insert(UserDto userDto) {
        if (!userRepository.existsByLogin(userDto.getLogin())) {
            User user = userRowMapper.toEntity(userDto);
            user.setPassword(securityUtil.getSecuredPassword(user.getPassword()));
            if (user.getId() != null) {
                throw new IllegalArgumentException("You shouldn't pass id for new user");
            }
            userRepository.save(user);
        } else {
            throw new DuplicateUserException("User with login=%s already exists ");
        }
    }

    @Transactional(readOnly = false)
    public void update(UserDto userDto, String login) {
        if (userRepository.existsByLogin(login)) {
            User user = userRowMapper.toEntity(userDto);
            user.setPassword(securityUtil.getSecuredPassword(user.getPassword()));
            userRepository.save(user);
        } else {
            throw new NotFoundUserException(String.format("User with login=%s not found in database for update", login));
        }
    }

    public UserDto findByLogin(String login) {
        return userRepository.findByLogin(login).map(userRowMapper::toDto)
                .orElseThrow(() -> new NotFoundUserException(String.format("Not found user for login=%s", login)));
    }
}
