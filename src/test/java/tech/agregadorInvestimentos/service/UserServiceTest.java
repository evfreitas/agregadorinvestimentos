package tech.agregadorInvestimentos.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
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

    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;

    @Nested
    class createUser{

        @Test
        @DisplayName("Deve criar usuário com sucesso")
        void deveCriarUsuario(){

            //Arrange
            var input = new User(UUID.randomUUID(), "username",
                                                    "email@email.com",
                                                    "123", Instant.now(),null);
            doReturn(input).when(userRepository).save(userArgumentCaptor.capture());

            //Act
            UUID output = userService.createUser(input);

            //Assert
            assertNotNull(output);
            assertEquals(input.getUsername(), userArgumentCaptor.getValue().getUsername());

        }

        @Test
        @DisplayName("Deve retornar exception quando houver erro")
        void deveRetornarRetornarExcecaoQuandoHouverErro(){

            //Arrange
            var input = new User(UUID.randomUUID(), "username",
                                                    "email@email.com",
                                                    "123", Instant.now(),null);
            doReturn(new RuntimeException()).when(userRepository).save(userArgumentCaptor.capture());

            //Act & Assert
            assertThrows(RuntimeException.class, () -> userService.createUser(input)); // () -> função quando executar
            assertEquals(input.getUsername(), userArgumentCaptor.getValue().getUsername());
        }
    }


}