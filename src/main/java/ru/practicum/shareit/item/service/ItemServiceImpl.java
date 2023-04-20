package ru.practicum.shareit.item.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.comments.CommentRepository;
import ru.practicum.shareit.comments.Comments;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.maper.CommentsMapper;
import ru.practicum.shareit.item.maper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.validation.ItemValidation;
import ru.practicum.shareit.user.service.UserServiceImpl;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ItemServiceImpl implements ItemService {
    private ItemRepository itemRepository;
    private CommentRepository commentRepository;
    private BookingRepository bookingRepository;
    private final ItemValidation validation;
    private final UserServiceImpl userService;
    private final ItemMapper mapper;
    private final CommentsMapper commentsMapper;

    public ItemServiceImpl(ItemValidation validation, UserServiceImpl userService, ItemRepository itemRepository, CommentRepository commentRepository, BookingRepository bookingRepository, ItemMapper mapper, CommentsMapper commentsMapper) {
        this.validation = validation;
        this.userService = userService;
        this.itemRepository = itemRepository;
        this.commentRepository = commentRepository;
        this.bookingRepository = bookingRepository;
        this.mapper = mapper;
        this.commentsMapper = commentsMapper;
    }

    @Override
    public ItemDto findItemById(Long itemId, Long ownerId) {
        if (itemRepository.findById(itemId).isEmpty()) {
            validation.searchItem();
        }
        var result = mapper.toItemDto(itemRepository.getById(itemId), ownerId);
        return result;
    }

    @Override
    public Collection<ItemDto> itemAllOwnerId(Long ownerId) { ///получение списка всех вещей пользователя
        return itemRepository.fetchItemByOwnerId(ownerId)
                .stream()
                .map((x) -> mapper.toItemDtoForAll(x, ownerId))
                .collect(Collectors.toList());

    }

    @Override
    public Collection<ItemDto> itemSearch(String text, long ownerId) {
        if (text.isEmpty()) {
            return List.of();
        }
        List<Item> items = itemRepository.search(text);
        log.info("По запросу " + text + " Найдено: " + items.size() + " вещей");
        return items.stream().map((x) -> mapper.toItemDto(x, ownerId)).collect(Collectors.toList());
    }

    @Override
    public ItemDto create(Item item, long ownerId) {  //+++
        validation.item(item);
        userService.searchUser(ownerId);
        item.setOwnerId(ownerId);
        itemRepository.save(item);
        log.info("Пользователь id: " + ownerId + " добавил вещь: " + item.getName());
        return mapper.toItemDto(itemRepository.getById(item.getId()), ownerId);
    }

    @Override
    public ItemDto update(Item item, Long itemId, Long ownerId) {   // изменить ващь +++
        checkItem(itemId);
        userService.searchUser(ownerId);
        Item item1 = itemRepository.getById(itemId);
        validation.ownerItem(item1.getOwnerId(), ownerId);
        if (item.getName() != null) {
            item1.setName(item.getName());
            itemRepository.save(item1);
            log.info("Изменена вещь id:" + itemId + " название: " + item.getName());
        }
        if (item.getDescription() != null) {
            item1.setDescription(item.getDescription());
            itemRepository.save(item1);
            log.info("Изменена вещь id:" + itemId + " описание: " + item.getDescription());
        }
        if (item.getAvailable() != null) {
            item1.setAvailable(item.getAvailable());
            itemRepository.save(item1);
            log.info("Изменена вещь id:" + itemId + " статус: " + item.getAvailable());
        }

        return mapper.toItemDto(itemRepository.getById(itemId), ownerId);
    }

    @Override
    public CommentDto createComments(Comments comments, long authorId, long itemId) { // добавление комментария
        validation.item(itemRepository.getById(itemId));
        userService.searchUser(authorId);
        if (comments.getText().equals("")) {
            validation.commenTextValidation();
        }

        List<Booking> bookings = bookingRepository.commentsCheck(authorId, itemId);
        if (bookings != null && bookings.size() > 0) {
            Comments comment = new Comments();
            comment.setText(comments.getText());
            comment.setItemId(itemId);
            comment.setAuthorId(authorId);
            comment.setCreated(Timestamp.from(Instant.now()));
            commentRepository.saveAndFlush(comment);
            return commentsMapper.toCommentsDto(commentRepository.findById(comment.getId()).get());

        } else {
            validation.commentValidation();
        }
        return null;
    }

    public void checkItem(Long itemId) {  // поиск вещи ++
        if (itemRepository.findById(itemId).isEmpty()) {
            validation.searchItem();
        }
    }

    public void checkItemAvailable(Long itemId) {
        if (!itemRepository.findById(itemId).get().getAvailable()) {
            validation.checkItemAvailable();
        }
    }

    public void checkItemOwner(Long itemId, Long ownerId) {
        if (itemRepository.findById(itemId).get().getOwnerId() == ownerId) {
            throw new NotFoundException("");
        }
    }

}
