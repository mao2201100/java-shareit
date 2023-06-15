package ru.practicum.shareit.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.shareit.client.BaseClient;
import ru.practicum.shareit.comments.Comments;
import ru.practicum.shareit.item.dto.Item;

import java.util.Map;

@Service
public class ItemClient extends BaseClient {
    private static final String API_PREFIX = "/items";

    @Autowired
    public ItemClient(@Value("${shareit-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }


    public ResponseEntity<Object> findItemById(Long itemId, long ownerId) {
        return get("/" + itemId, ownerId);
    }

    public ResponseEntity<Object> itemAllOwnerId(long ownerId) {
        return get("", ownerId);
    }

    public ResponseEntity<Object> itemSearch(String text, long ownerId) {
        Map<String, Object> parameters = Map.of(
                "text", text
        );
        return get("/search/?text=" + text, ownerId);
    }

    public ResponseEntity<Object> create(Item item, long idUser) {
        return post("", idUser, item);
    }

    public ResponseEntity<Object> createComments(Comments comments, long idUser, Long itemId) {
        return post("/" + itemId + "/comment", idUser, comments);
    }

    public ResponseEntity<Object> update(Item item, Long itemId, long ownerId) {
        return patch("/" + itemId, ownerId, item);
    }
}