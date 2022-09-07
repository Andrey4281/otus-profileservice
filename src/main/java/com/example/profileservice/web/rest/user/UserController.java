package com.example.profileservice.web.rest.user;

import com.example.profileservice.dto.UserDto;
import com.example.profileservice.dto.UserSignUpResultDto;
import com.example.profileservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @PostMapping(value = "users/signUp")
    public ResponseEntity<UserSignUpResultDto> insert(@RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.insert(userDto));
    }

    @PutMapping(value = "users/update/{login}")
    public ResponseEntity<Void> update(@RequestBody UserDto userDto, @PathVariable String login) {
        userService.update(userDto, login);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "users/{oldLogin}")
    public ResponseEntity<UserDto> getByLogin(@PathVariable String oldLogin) {
        return ResponseEntity.ok(userService.findByLogin(oldLogin));
    }
}
