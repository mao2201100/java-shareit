package ru.practicum.shareit.request.validation;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.shareit.request.Request;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
@SpringBootTest
class ItemRequestValidationTest {
    @Mock
    Request mokRequest;

    @Test
    void itemRequest(Request mokRequest) {


    }

    @Test
    void itemRequestIdIsEmpty() {
    }

    @Test
    void itemRequestIdIsFirstAndSizeIndex() {
    }
}