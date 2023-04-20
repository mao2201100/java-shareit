package ru.practicum.shareit.request;

import lombok.Data;
import org.springframework.boot.context.properties.ConstructorBinding;

import javax.persistence.*;

@Entity
@Table(name = "request")
@Data
@ConstructorBinding
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "description")
    private String description;
    @Column(name = "requestor_id")
    private long requestorId;
}
