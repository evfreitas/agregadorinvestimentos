package tech.agregadorInvestimentos.service;

import org.springframework.stereotype.Service;
import tech.agregadorInvestimentos.controller.UpdateUserDto;
import tech.agregadorInvestimentos.entity.User;
import tech.agregadorInvestimentos.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private UserRepository  userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public UUID createUser (User user) {

        var userSaved = userRepository.save(user);

        return userSaved.getUserId();
    };

    public Optional<User> getUserById (UUID userId) {

        Optional<User> user = userRepository.findById(userId);

        return user;
    };

    public List<User> userList () {

        List<User> users = userRepository.findAll();

        return users;
    };

    public Boolean updateUserById (UUID userId, UpdateUserDto updateUserDto) {

        Optional<User> userEntity = userRepository.findById(userId);

        Boolean userExists = false;

        if (userEntity.isPresent()){
            var user = userEntity.get();

            if(updateUserDto.username() != null){
                user.setUsername(updateUserDto.username());
            }

            if(updateUserDto.password() != null){
                user.setPassword(updateUserDto.password());
            }

            userRepository.save(user);
            userExists = true;
        }

        return userExists;

    };

    public Boolean deleteById (UUID userId) {

        Boolean userExist = userRepository.existsById(userId);

        if (userExist){
         userRepository.deleteById(userId);
        }

        return userExist;

    };

}
