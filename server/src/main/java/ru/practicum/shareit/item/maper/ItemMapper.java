package ru.practicum.shareit.item.maper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.booking.BookingStatus;
import ru.practicum.shareit.booking.mapper.BookingMapper;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ItemMapper {
    private BookingRepository bookingRepository;
    private BookingMapper bookingMapper;

    private CommentsMapper commentsMapper;

    private ItemMapper() {
    }

    public ItemDto toItemDto(Item item, Long ownerId) {
        List<Booking> bookings = bookingRepository.fetchLastBookerByItemForAll(item.getId(), ownerId);
        LocalDateTime now = LocalDateTime.now();
        var lastBooking = bookings
                .stream()
                .filter(b -> b.getEnd().isBefore(now) ^ (b.getStart().isBefore(now) && b.getEnd().isAfter(now)))
                .filter(b -> b.getStatus().equals(BookingStatus.APPROVED))
                .max(Comparator.comparing(Booking::getEnd)).orElse(null);

        Booking nextBooking = null;
        if (lastBooking != null) {
            nextBooking = bookingRepository.fetchNextBookerByItem(item.getId(), ownerId);
        }
        return new ItemDto(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.getAvailable(),
                item.getOwnerId(),
                item.getRequestId(),
                lastBooking != null ? bookingMapper.toBookingDtoOut(lastBooking) : null,
                nextBooking != null ? bookingMapper.toBookingDtoOut(nextBooking) : null,
                item.getComments() != null ?
                        item.getComments().stream().map(commentsMapper::toCommentsDto).collect(Collectors.toList()) : null
        );
    }

    @Autowired
    public void setBookingRepository(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @Autowired
    public void setBookingMapper(BookingMapper bookingMapper) {
        this.bookingMapper = bookingMapper;
    }

    @Autowired
    public void setCommentsMapper(CommentsMapper commentsMapper) {
        this.commentsMapper = commentsMapper;
    }
}
