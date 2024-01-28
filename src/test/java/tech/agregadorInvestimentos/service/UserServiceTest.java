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
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;

    @Captor
    private ArgumentCaptor<UUID> uuidArgumentCaptor;

    @Nested
    class createUser{

        @Test
        @DisplayName("Deve criar usuário com sucesso")
        void deveCriarUsuario(){

            //Arrange
            var user = new User(UUID.randomUUID(), "teste",
                                                    "email@email.com",
                                                    "123", Instant.now(),null);
            doReturn(user).when(userRepository).save(userArgumentCaptor.capture()); //captura o que está passando no save para validar depois
            var input = new CreateUserDto("teste","email@email.com","123");

            //Act
            UUID output = userService.createUser(input);

            //Assert
            assertNotNull(output);
            assertEquals(input.username(), userArgumentCaptor.getValue().getUsername());
            assertEquals(input.email(), userArgumentCaptor.getValue().getEmail());
            assertEquals(input.password(), userArgumentCaptor.getValue().getPassword());

        }

        @Test
        @DisplayName("Deve retornar exception quando houver erro")
        void deveRetornarRetornarExcecaoQuandoHouverErro(){

            //Arrange
            doThrow(new RuntimeException()).when(userRepository).save(any());
            var input = new CreateUserDto("teste","email@email.com","123");

            //Act & Assert
            assertThrows(RuntimeException.class, () -> userService.createUser(input)); // () -> função quando executar
        }
    }

    @Nested
    class getUserById{

        @Test
        @DisplayName("Deve consultar um usuário pelo Id com sucesso")
        void deveConsultarUsuarioPeloId() {

            //Arrange
            var user = new User(UUID.randomUUID(), "teste",
                    "email@email.com",
                    "123", Instant.now(),null);
            doReturn(Optional.of(user)).when(userRepository).findById(uuidArgumentCaptor.capture()); //trocou o tipo do campo que vai ser capturado

            //act
            Optional<User> output = userService.getUserById(user.getUserId());

            //Assert
            assertTrue(output.isPresent());
            assertEquals(output.get().getUserId(), uuidArgumentCaptor.getValue());
        }
    }
}