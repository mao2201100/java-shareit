package ru.practicum.shareit.user.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConstructorBinding;

/**
 * TODO Sprint add-controllers.
 */
@Getter
@Setter
@ConstructorBinding
public class UserRequestDto {
    private long id; // уникальный идентификатор пользователя
    private String name; // имя или логин пользователя
    private String email; // адрес электронной почты (учтите, что два пользователя не могут
    //иметь одинаковый адрес электронной почты)

}
