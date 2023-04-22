package ru.practicum.shareit.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
//    List<Booking> findByBooker_IdAndEndIsBefore(Long bookerId, LocalDateTime end, Sort sort); //осуществлять
//    // поиск всех записей по переданному bookerId с датой окончания (поле end ) раньше переданной.
//    List<Booking> findByBookerId(Long bookerId);

    @Query(value = "select * from booking book where item_id = :itemId and exists((select bookSub1.id from booking bookSub1  inner join item iSub1 on iSub1.id = bookSub1.item_id where bookSub1.item_id = :itemId and iSub1.owner_id = :ownerId and bookSub1.status = 'APPROVED' order by bookSub1.start_date desc limit 1)) and book.booker_id not in (select bookSub2.booker_id from booking bookSub2  inner join item isub2 on isub2.id = bookSub2.item_id where bookSub2.item_id = :itemId and isub2.owner_id = :ownerId  and bookSub2.status = 'APPROVED' order by bookSub2.start_date desc limit 1) order by book.start_date desc limit 1", nativeQuery = true)
    Booking fetchLastBookerByItem(long itemId,  long ownerId);

    Booking getFirstByItemIdAndStartBeforeOrderByStartDesc(Long idItem, LocalDateTime now);

    @Query(value = "select * from booking book where item_id = :itemId and book.id not in (select bookSub1.id from booking bookSub1  inner join item iSub1 on iSub1.id = bookSub1.item_id where bookSub1.status = 'APPROVED' and bookSub1.item_id = :itemId and iSub1.owner_id = :ownerId and bookSub1.status = 'APPROVED' order by bookSub1.start_date desc limit 1) and exists((select bookSub1.id from booking bookSub1  inner join item iSub1 on iSub1.id = bookSub1.item_id where bookSub1.item_id = :itemId and iSub1.owner_id = :ownerId and bookSub1.status = 'APPROVED' order by bookSub1.start_date desc limit 1)) and book.booker_id not in (select bookSub2.booker_id from booking bookSub2  inner join item isub2 on isub2.id = bookSub2.item_id where bookSub2.item_id = :itemId and isub2.owner_id = :ownerId  and bookSub2.status = 'APPROVED' order by bookSub2.start_date desc limit 1) order by book.start_date desc limit 1", nativeQuery = true)
    Booking fetchNextBookerByItem(long itemId, long ownerId);

    Booking findTopByItemIdAndStartAfterAndEndAfterOrderByStartAsc(Long itemId, LocalDateTime now, LocalDateTime start);


    @Query(value = "select * from booking book inner join item i on i.id = book.item_id where item_id = :itemId and i.owner_id = :ownerId and book.status = 'APPROVED'", nativeQuery = true)
    List<Booking> fetchLastBookerByItemForAll(long itemId, long ownerId);

    @Query(value = "select * from booking book where item_id = :itemId and book.id not in (select bookSub1.id from booking bookSub1  inner join item iSub1 on iSub1.id = bookSub1.item_id where bookSub1.status = 'APPROVED' and bookSub1.item_id = :itemId and iSub1.owner_id = :ownerId and bookSub1.status = 'APPROVED' order by bookSub1.id asc limit 1) and exists((select bookSub1.id from booking bookSub1  inner join item iSub1 on iSub1.id = bookSub1.item_id where bookSub1.item_id = :itemId and iSub1.owner_id = :ownerId and bookSub1.status = 'APPROVED' order by bookSub1.id asc limit 1)) and book.booker_id not in (select bookSub2.booker_id from booking bookSub2  inner join item isub2 on isub2.id = bookSub2.item_id where bookSub2.item_id = :itemId and isub2.owner_id = :ownerId  and bookSub2.status = 'APPROVED' order by bookSub2.id asc limit 1) order by book.id asc limit 1", nativeQuery = true)
    Booking fetchNextBookerByItemForAll(long itemId, long ownerId);


    List<Booking> findByBookerIdAndItemIdAndEndBefore(Long bookerId, Long itemId, LocalDateTime now);

    @Query(value = "select * from booking book where book.item_id = :itemId and book.booker_id = :bookerId and book.end_date < now() and book.status = 'APPROVED'", nativeQuery = true)
    List<Booking> commentsCheck(Long bookerId, Long itemId);


    @Query(value = "select * from booking book where book.booker_id = :bookerId and book.end_date >= now() and book.start_date <= now() order by book.id asc", nativeQuery = true)
    List<Booking> fetchBookingByStateCurrentByBookerId(Long bookerId);

    @Query(value = "select * from booking book where book.booker_id = :bookerId and book.end_date <= now() order by book.id desc", nativeQuery = true)
    List<Booking> fetchBookingByStatePastByBookerId(Long bookerId);

    @Query(value = "select * from booking book where book.booker_id = :bookerId and cast(book.start_date as date) >= cast(now() as date) order by book.id desc", nativeQuery = true)
    List<Booking> fetchBookingByStateFutureByBookerId(Long bookerId);

    @Query(value = "select * from booking book where book.status = :status and book.booker_id = :bookerId order by book.id asc", nativeQuery = true)
    List<Booking> fetchBookingByStatusByBookerId(Long bookerId, String status);

    @Query(value = "select * from booking book where book.booker_id = :bookerId order by book.id desc", nativeQuery = true)
    List<Booking> fetchBookingByBookerId(Long bookerId);// все бронирования текущего пользователя


    @Query(value = "select * from booking book inner join item it on it.id = book.item_id and it.owner_id = :ownerId where  book.end_date >= now() and book.start_date <= now() order by book.id asc", nativeQuery = true)
    List<Booking> fetchBookingByStateCurrentByOwnerId(Long ownerId);


    @Query(value = "select * from booking book inner join item it on it.id = book.item_id and it.owner_id = :ownerId where book.end_date < now() order by book.id desc", nativeQuery = true)
    List<Booking> fetchBookingByStatePastByOwnerId(Long ownerId);

    @Query(value = "select * from booking book inner join item it on it.id = book.item_id and it.owner_id = :ownerId where cast(book.start_date as date) >= cast(now() as date) order by book.id desc", nativeQuery = true)
    List<Booking> fetchBookingByStateFutureByOwnerId(Long ownerId);

    @Query(value = "select * from booking book inner join item it on it.id = book.item_id and it.owner_id = :ownerId where book.status = :status order by book.id asc", nativeQuery = true)
    List<Booking> fetchBookingByStatusByOwnerId(Long ownerId, String status);

    @Query(value = "select * from booking book inner join item it on it.id = book.item_id and it.owner_id = :ownerId order by book.id desc", nativeQuery = true)
    List<Booking> fetchBookingOwnerId(Long ownerId);// все бронирования текущего пользователя

}
