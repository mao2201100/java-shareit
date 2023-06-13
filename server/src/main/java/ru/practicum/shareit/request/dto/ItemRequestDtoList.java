package ru.practicum.shareit.request.dto;

import ru.practicum.shareit.request.Request;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ItemRequestDtoList {
    private List<Request> request;
}
