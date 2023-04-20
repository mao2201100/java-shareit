package ru.practicum.shareit.user;

import lombok.Data;
import org.springframework.boot.context.properties.ConstructorBinding;

import javax.persistence.*;
import javax.validation.constraints.Email;

/**
 * TODO Sprint add-controllers.
 */
@Entity
@Table(name = "user", schema = "public")
@Data
@ConstructorBinding
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id; // уникальный идентификатор пользователя
    @Column(name = "name")
    private String name; // имя или логин пользователя
    @Column(name = "email", unique = true)
    @Email
    private String email; // адрес электронной почты (учтите, что два пользователя не могут
    //иметь одинаковый адрес электронной почты)

}
