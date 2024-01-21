package tech.agregadorInvestimentos.controller;

import org.mapstruct.control.MappingControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.agregadorInvestimentos.entity.User;
import tech.agregadorInvestimentos.mapper.UserMapper;
import tech.agregadorInvestimentos.service.UserService;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody CreateUserDto createUserDto){

        User userEntity = UserMapper.INSTANCE.mapFrom(createUserDto);

        var userId = userService.createUser(userEntity);

        return ResponseEntity.created(URI.create("v1/users/" + userId.toString())).build();

    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUser(@PathVariable("userId") UUID userId){

        var user = userService.getUserById(userId);

        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping()
    public ResponseEntity<List<User>> userList(){

        List<User> users = userService.userList();

        return users.isEmpty() ?
                ResponseEntity.noContent().build() :
                ResponseEntity.ok(users);
    }

    @PutMapping("/{userId}")
    public ResponseEntity updateUserById(@PathVariable ("userId") UUID userId,
                                         @RequestBody UpdateUserDto updateUserDto){

        Boolean userExist = userService.updateUserById(userId, updateUserDto);

        return userExist ?
                ResponseEntity.noContent().build() :
                ResponseEntity.notFound().build();

        // TODO: 22/12/2023 retornar objeto alterado
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity deleteUser(@PathVariable ("userId") UUID userId){

        Boolean userExist = userService.deleteById(userId);

        return userExist.booleanValue() ?
                ResponseEntity.ok().build() :
                ResponseEntity.notFound().build();
    }
}
