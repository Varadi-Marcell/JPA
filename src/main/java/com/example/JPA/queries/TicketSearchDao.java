package com.example.JPA.queries;

import com.example.JPA.dto.ticket.TicketDto;
import com.example.JPA.dto.ticket.TicketDtoResponse;
import com.example.JPA.model.Ticket;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class TicketSearchDao {

    private final EntityManager em;

    public TicketDtoResponse ticketQuery(TicketSearchDto dto) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Ticket> cq = cb.createQuery(Ticket.class);
        Root<Ticket> ticket = cq.from(Ticket.class);
        List<Predicate> predicates = new ArrayList<>();

        System.out.println(dto.toString());

        if (dto.getLocation().isPresent() && dto.getLocation().get().length() !=0 ) {
            System.out.println("LOCATION");
            predicates.add(cb.equal(ticket.get("location"), dto.getLocation().get()));
        }

        if (dto.getMinPrice().isPresent() && dto.getMinPrice().get() >= 0) {
            predicates.add(cb.greaterThanOrEqualTo(ticket.get("price"), dto.getMinPrice().get()));
        }

        if (dto.getMaxPrice().isPresent() ) {
            System.out.println(dto.getMaxPrice().get()+":"+"ASD");
            predicates.add(cb.lessThanOrEqualTo(ticket.get("price"), dto.getMaxPrice().get()));

        }

        if (dto.getStartDate().isPresent() && dto.getStartDate().get().length()!=0) {
            predicates.add(cb.greaterThanOrEqualTo(ticket.get("startDate"), LocalDate.parse(dto.getStartDate().get())));

        }

        if (dto.getEndDate().isPresent() && dto.getEndDate().get().length()!=0) {
            predicates.add(cb.lessThanOrEqualTo(ticket.get("endDate"), LocalDate.parse(dto.getEndDate().get())));
        }

        if (dto.getGenre().isPresent()) {
            predicates.add(cb.equal(ticket.get("genre"), dto.getGenre().get()));

        }

        if (!predicates.isEmpty()) {
            cq.where(predicates.toArray(new Predicate[predicates.size()]));
        }
        TypedQuery<Ticket> fullSizeQuery = em.createQuery(cq);


        int fullSizeOfTheResult = fullSizeQuery.getResultList().size();

        TypedQuery<Ticket> query = em.createQuery(cq);


        Pageable pageable = PageRequest.of(dto.getPage(), dto.getSize());
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        List<Ticket> tickets = query.getResultList();

        List<TicketDto> ticketDtos = tickets.stream()
                .map(t -> t.ticketToTicketDto(t))
                .toList();

        TicketDtoResponse ticketDtoResponse = new TicketDtoResponse(ticketDtos, pageable.getPageSize());
        System.out.println(ticketDtoResponse);

        return new TicketDtoResponse(ticketDtos, fullSizeOfTheResult);

    }
}
