package jsm.reto4.reto4.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jsm.reto4.reto4.Model.Reservaciones;
import jsm.reto4.reto4.Repository.ReservacionesRepositorio;

@Service
public class ReservacionesServicio {
    @Autowired
    private ReservacionesRepositorio reservacionesRepositorio;

    public List<Reservaciones>getAll(){
        return reservacionesRepositorio.getAll();
    }
    public Optional<Reservaciones>getReservation(int reservationId){
        return reservacionesRepositorio.getReservation(reservationId);
    }
    public Reservaciones save (Reservaciones reservation){
        if(reservation.getIdReservation()==null){
            return reservacionesRepositorio.save(reservation);
        }else{
            Optional<Reservaciones> consulta=reservacionesRepositorio.getReservation(reservation.getIdReservation());
            if(consulta.isEmpty()){
                return reservacionesRepositorio.save(reservation);
            }else{
                return reservation;
            }
        }
    }
    public Reservaciones update(Reservaciones reservation){
        if(reservation.getIdReservation()!=null){
            Optional<Reservaciones> consulta= reservacionesRepositorio.getReservation(reservation.getIdReservation());
            if(!consulta.isEmpty()){

                if(reservation.getStartDate()!=null){
                    consulta.get().setStartDate(reservation.getStartDate());
                }
                if(reservation.getDevolutionDate()!=null){
                    consulta.get().setDevolutionDate(reservation.getDevolutionDate());
                }
                if(reservation.getStatus()!=null){
                    consulta.get().setStatus(reservation.getStatus());
                }
                reservacionesRepositorio.save(consulta.get());
                return consulta.get();
            }else{
                return reservation;
            }
        }else{
            return reservation;
        }
    }

    public boolean deleteReservation(int reservationId) {
        Boolean aBoolean = getReservation(reservationId).map(reservation -> {
            reservacionesRepositorio.delete(reservation);
            return true;
        }).orElse(false);
        return aBoolean;
    }
    
}

