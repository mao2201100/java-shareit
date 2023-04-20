package ru.practicum.shareit.booking;

import lombok.Data;
import org.springframework.boot.context.properties.ConstructorBinding;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * TODO Sprint add-bookings.
 */
@Entity
@Table(name = "booking")
@Data
@ConstructorBinding
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;//уникальный идентификатор бронирования;

    @Column(name = "start_date")
    private LocalDateTime start; // дата и время начала бронирования;
    @Column(name = "end_date")
    private LocalDateTime end; // дата и время конца бронирования;
    @ManyToOne
    private User booker; // пользователь, который осуществляет бронирование;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status; //статус бронирования. Может принимать одно из следующих значений:

    //WAITING — новое бронирование, ожидает одобрения, APPROVED — бронирование
    //подтверждено владельцем, REJECTED — бронирование отклонено владельцем,
    //CANCELED — бронирование отменено создателем.
    public enum Status {
        WAITING, APPROVED, REJECTED, CANCELED;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    private Item item;
}
