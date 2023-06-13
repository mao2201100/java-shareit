package ru.practicum.shareit.booking.dto;

import ru.practicum.shareit.booking.UnsupportedStatus;

import java.util.Optional;

public enum BookingState {
	// Все
	ALL,
	// Текущие
	CURRENT,
	// Будущие
	FUTURE,
	// Завершенные
	PAST,
	// Отклоненные
	REJECTED,
	// Ожидающие подтверждения
	WAITING;

	public static Optional<BookingState> from(String stringState) {
		for (BookingState state : values()) {
			if (state.name().equalsIgnoreCase(stringState)) {
				return Optional.of(state);
//			} if (stringState == "UNSUPPORTED_STATUS"){
//				throw new UnsupportedStatus();
			}
		}
		return Optional.empty();
	}
}
