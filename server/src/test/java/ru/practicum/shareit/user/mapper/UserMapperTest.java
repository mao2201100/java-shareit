package ru.practicum.shareit.user.mapper;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.dto.UserDto;

class UserMapperTest {

    @Test
    void toUserDto() {
        User user = new User();
        user.setId(1);
        user.setEmail("test@mail.ru");
        user.setName("Test");
        UserDto userDtTest = UserMapper.toUserDto(user);
        Assert.assertEquals((long) 1, (long) userDtTest.getId());
        Assert.assertEquals("test@mail.ru", userDtTest.getEmail());
        Assert.assertEquals("Test",  userDtTest.getName());
    }
}