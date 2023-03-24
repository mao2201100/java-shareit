package ru.practicum.shareit.user.model;

import lombok.Data;
import org.springframework.boot.context.properties.ConstructorBinding;

import javax.validation.constraints.Email;

/**
 * TODO Sprint add-controllers.
 */
@Data
@ConstructorBinding
public class User {
    private long id = 1L; // уникальный идентификатор пользователя
    private String name; // имя или логин пользователя
    @Email
    private String email; // адрес электронной почты (учтите, что два пользователя не могут
            //иметь одинаковый адрес электронной почты)


}
