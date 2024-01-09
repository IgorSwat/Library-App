package library.proj.service;

import library.proj.model.Book;
import library.proj.model.Person;
import library.proj.model.Reservation;
import library.proj.repository.ReservationsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationsService {
    private final ReservationsRepository reservationsRepository;

    public ReservationsService(ReservationsRepository reservationsRepository) {
        this.reservationsRepository = reservationsRepository;
    }

    public Reservation createReservation(Reservation reservation){
        return reservationsRepository.save(reservation);
    }

    public Reservation updateReservationStatus(int reservationId, boolean isActive) {
        Reservation reservationEntity = reservationsRepository.findById(reservationId);
        if (reservationEntity != null) {
            reservationEntity.setActive(isActive);
            return reservationsRepository.save(reservationEntity);
        }
        return null;
    }

    public List<Reservation> getAllReservationsByPerson(Person person){
        return  reservationsRepository.findAllByPerson(person);
    }

    public List<Reservation> getAllActiveReservationsByPerson(Person person){
        return  reservationsRepository.findAllByPerson(person).stream().filter(Reservation::isActive).toList();
    }

    public void updateReservationActiveness(Reservation reservation, boolean isActive){
        Reservation reservationEntity = reservationsRepository.findById(reservation.getId());
        reservationEntity.setActive(isActive);
        reservationsRepository.save(reservationEntity);
    }

    public List<Reservation> getAllReservationsByBook(Book book){
        return  reservationsRepository.findAllByBook(book);
    }

    public List<Reservation> getAllActiveReservationsByBook(Book book){
        return  reservationsRepository.findAllByBook(book).stream().filter(Reservation::isActive).toList();
    }

}
