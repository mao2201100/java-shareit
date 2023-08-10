package ru.practicum.shareit.item.dto;

import lombok.Data;
import ru.practicum.shareit.booking.dto.BookingDtoOut;

import java.util.List;

/**
 * TODO Sprint add-controllers.
 */
@Data
public class ItemDto {
    private Long id;  // уникальный идентификатор вещ
    private String name; //  краткое название
    private String description; //  развёрнутое описание
    private Boolean available; // статус о том, доступна или нет вещь для аренды
    private String owner; // владелец вещи
    private Long ownerId;
    private Long requestId;

    private BookingDtoOut lastBooking;
    private BookingDtoOut nextBooking;
    private List<CommentDto> comments;

    public ItemDto(Long id, String name, String description, Boolean available,
                   Long ownerId, Long requestId,
                   BookingDtoOut lastBooking, BookingDtoOut nextBooking,
                   List<CommentDto> comments) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.available = available;
        this.ownerId = ownerId;
        this.requestId = requestId;
        this.lastBooking = lastBooking;
        this.nextBooking = nextBooking;
        this.comments = comments;
    }
}
