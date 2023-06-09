package ru.practicum.shareit.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    @Query(value = "select * from item i where i.owner_id = :ownerId order by i.id asc", nativeQuery = true)
    List<Item> fetchItemByOwnerId(Long ownerId);

    @Query(" select i from Item i " +
            "where i.available = true and (upper(i.name) like upper(concat('%', ?1, '%')) " +
            " or upper(i.description) like upper(concat('%', ?1, '%')))")
    List<Item> search(String text);

    @Query(value = "select * from item i where i.request_id = :id", nativeQuery = true)
    List<Item> itemsForRequestId(Long id);

}