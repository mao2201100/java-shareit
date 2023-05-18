package ru.practicum.shareit.request;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.service.ItemRequestService;

import java.util.Collection;
import java.util.List;

/**
 * TODO Sprint add-item-requests.
 */
@Slf4j
@RestController
@RequestMapping(path = "/requests")
public class ItemRequestController {
    public final ItemRequestService requestService;

    public ItemRequestController(ItemRequestService requestService) {
        this.requestService = requestService;
    }

    @PostMapping
    public ItemRequestDto createItemRequest(@RequestBody Request request, @RequestHeader("X-Sharer-User-Id") long userId) { // создать запрос вещи
        log.info("Executing Post createItemRequest");
        return requestService.create(request, userId);
    }

    @GetMapping
    public List<ItemRequestDto> findAllItemRequestOwner(@RequestHeader("X-Sharer-User-Id") long ownerId) { //получить список своих запросов вместе с данными об ответах на них
        log.info("Executing Get findAllItemRequestOwner");
        return requestService.findAllItemRequestOwner(ownerId);
    }
    // Для каждого запроса должны указываться описание, дата и время создания и список ответов в формате: id вещи,
    // название, id владельца. Так в дальнейшем, используя указанные id вещей, можно будет получить подробную
    // информацию о каждой вещи. Запросы должны возвращаться в отсортированном порядке от более новых к более
    // старым.

    @GetMapping("/all") // получить список запросов, созданных другими пользователями.
    public List<ItemRequestDto> findAllItemRequestUsers(@RequestParam(required = false) Long from,
                                                              @RequestParam(required = false) Long size,
                                                              @RequestHeader("X-Sharer-User-Id") long userId) {
        log.info("Executing Get findAllItemRequestUsers");
        return requestService.findAllItemRequestUsers(from, size, userId);
    }

    @GetMapping("/{requestId}")
    // получить данные об одном конкретном запросе вместе с данными об ответах на него в том же формате, что и в эндпоинте
    public ItemRequestDto findItemRequest(@PathVariable("requestId") long requestId, @RequestHeader("X-Sharer-User-Id") long userId) {
        log.info("Executing Get findItemRequest");
        return requestService.findItemRequest(requestId, userId);
    }

}
