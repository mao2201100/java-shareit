package ru.practicum.shareit.request.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.shareit.request.Request;

import java.util.List;

@Data
@AllArgsConstructor
public class ItemRequestDtoList {
    private List<Request> request;
}
