package ru.practicum.shareit.item.validation;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;

class ItemValidationTest {
    private ItemValidation itemValidation = new ItemValidation();
    private static Item item = new Item();
    private static User owner = new User();

    @BeforeAll
    static void setup() {
        item.setId(1L);
        item.setOwner("testUser");
        item.setOwnerId(3L);

        owner.setId(1L);
        owner.setName("testUser");
        owner.setEmail("jepp@ebrilo.test");
    }

    @Test
    void item() {

        Assert.assertThrows(ValidationException.class, () -> itemValidation.item(item));
        try {
            itemValidation.item(item);
        } catch (Exception e) {
            Assert.assertEquals("не указан статус вещи",
                    e.getMessage());
        }

        item.setAvailable(true);

        Assert.assertThrows(ValidationException.class, () -> itemValidation.item(item));
        try {
            itemValidation.item(item);
        } catch (Exception e) {
            Assert.assertEquals("не указано описание",
                    e.getMessage());
        }

        item.setDescription("test");

        Assert.assertThrows(ValidationException.class, () -> itemValidation.item(item));
        try {
            itemValidation.item(item);
        } catch (Exception e) {
            Assert.assertEquals("нет указано название вещи",
                    e.getMessage());
        }
    }

    @Test
    void searchItem() {
        Assert.assertThrows(NotFoundException.class, () -> itemValidation.searchItem());
        try {
            itemValidation.searchItem();
        } catch (Exception e) {
            Assert.assertEquals("вещь не найдена",
                    e.getMessage());
        }
    }

    @Test
    void ownerItem() {
        Assert.assertThrows(NotFoundException.class, () -> itemValidation.ownerItem(owner.getId(), item.getOwnerId()));
        try {
            itemValidation.ownerItem(owner.getId(), item.getOwnerId());
        } catch (Exception e) {
            Assert.assertEquals("не правильно указан id хозяина вещи",
                    e.getMessage());
        }
    }

    @Test
    void checkItemAvailable() {
        Assert.assertThrows(ValidationException.class, () -> itemValidation.checkItemAvailable());
        try {
            itemValidation.checkItemAvailable();
        } catch (Exception e) {
            Assert.assertEquals("Вещь недоступна для бронирования",
                    e.getMessage());
        }
    }

    @Test
    void commentValidation() {
        Assert.assertThrows(ValidationException.class, () -> itemValidation.commentValidation());
        try {
            itemValidation.commentValidation();
        } catch (Exception e) {
            Assert.assertEquals("Вы не можете написать отзыв, бронирование не закончено либо указан не правильный id вещи",
                    e.getMessage());
        }
    }

    @Test
    void commenTextValidation() {
        Assert.assertThrows(ValidationException.class, () -> itemValidation.commenTextValidation());
        try {
            itemValidation.commenTextValidation();
        } catch (Exception e) {
            Assert.assertEquals("Комментарий не может быть пустым",
                    e.getMessage());
        }
    }
}