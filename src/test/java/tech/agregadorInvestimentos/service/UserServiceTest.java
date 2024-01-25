package tech.agregadorInvestimentos.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.agregadorInvestimentos.controller.CreateUserDto;
import tech.agregadorInvestimentos.entity.User;
import tech.agregadorInvestimentos.repository.UserRepository;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Nested
    class createUser{

        @Test
        @DisplayName("Deve criar usuário com sucesso")
        void deveCriarUsuario(){

            //Arrange
            var input = new User(UUID.randomUUID(), "username", "email@email.com", "123", Instant.now(),null);
            doReturn(input).when(userRepository).save(any());

            //var userDto = new CreateUserDto("", "", "");
            //var input = new User();

            //Act
            UUID output = userService.createUser(input);

            //Assert
            assertNotNull(output);

        }

        @Test
        @DisplayName("Deve retornar exception quando houver erro")
        void deveRetornarRetornarExcecaoQuandoHouverErro(){

            //Arrange
            var input = new User(UUID.randomUUID(), "username", "email@email.com", "123", Instant.now(),null);
            doReturn(new RuntimeException()).when(userRepository).save(any());

            //var userDto = new CreateUserDto("", "", "");
            //var input = new User();

            //Act
            UUID output = userService.createUser(input);

            //Assert
            assertNotNull(output);

        }
    }


}