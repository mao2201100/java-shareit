package ru.practicum.shareit.request;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {

    List<Request> findByRequestorIdNot(Long requestorId, Pageable pageable);

    @Query(value = "SELECT *  \n" +
            "            FROM  REQUESTS req\n" +
            "            where exists(select * from ITEM it where it.REQUEST_ID = :id)", nativeQuery = true)
    List<Request> fetchRequestById(Long id);

    @Query(value = "SELECT *  \n" +
            "            FROM  REQUESTS req\n" +
            "             where req.REQUESTOR_ID = :id", nativeQuery = true)
    List<Request> ownerRequest(Long id);

}
