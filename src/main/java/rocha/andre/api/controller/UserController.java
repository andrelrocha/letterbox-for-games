package rocha.andre.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;
import rocha.andre.api.domain.user.DTO.*;
import rocha.andre.api.infra.security.TokenJwtDto;
import rocha.andre.api.service.UserService;

import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    @Transactional
    public ResponseEntity performLogin(@RequestBody @Valid UserLoginDTO data) {
        TokenJwtDto tokenJwt = userService.performLogin(data);
        return ResponseEntity.ok(tokenJwt);
    }

    @PostMapping("/create")
    @Transactional
    public ResponseEntity createUser(@RequestBody @Valid UserDTO data) {
        var newUser = userService.createUser(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    @GetMapping("/bytokenjwt/{tokenJWT}")
    public ResponseEntity getUserByTokenJWT(@PathVariable String tokenJWT) {
        var user = userService.getUserByTokenJWT(tokenJWT);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/forgot_password")
    @Transactional
    public ResponseEntity forgotPassword(@RequestBody UserOnlyLoginDTO data) {
        var dtoReturn= userService.forgotPassword(data);
        return ResponseEntity.ok(dtoReturn);
    }

    @PostMapping("/reset_password")
    @Transactional
    public ResponseEntity resetPassword(@RequestBody UserResetPassDTO data) {
        var stringSuccess= userService.resetPassword(data);
        return ResponseEntity.ok(stringSuccess);
    }

    @GetMapping("/getuserid/{tokenJwt}")
    public ResponseEntity<UserIdDTO> getUserId(@PathVariable String tokenJwt) {
        var userId = userService.getUserIdByJWT(tokenJwt);
        return ResponseEntity.ok(userId);
    }

    @PutMapping("/update/{tokenJWT}")
    public ResponseEntity updateUser(@PathVariable String tokenJWT, @RequestBody UserUpdateDTO data) {
        var updatedUser = userService.updateUserInfo(data, tokenJWT);
        return ResponseEntity.ok(updatedUser);
    }
}
