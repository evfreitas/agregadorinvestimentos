package tech.agregadorInvestimentos.mapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import tech.agregadorInvestimentos.controller.CreateUserDto;
import tech.agregadorInvestimentos.entity.User;

@Mapper(uses = UserMapper.class)
public abstract class UserMapper {

    public static final UserMapper INSTANCE = Mappers.getMapper( UserMapper.class);

    public abstract User mapFrom(CreateUserDto createUserDto);



}
