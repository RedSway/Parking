import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Parking {
    private int maxCountPlaces;
    private List<Ticket> freeTikets;
    private List<Car> listCar;

    public Parking(int maxCountPlaces) {
        this.maxCountPlaces = maxCountPlaces;
        listCar = new LinkedList<>();
        initialFreeTickets();
    }

    private void initialFreeTickets() {
        freeTikets = new ArrayList<>();
        for (int i = maxCountPlaces; i != 0; i--) {
            freeTikets.add(new Ticket(i));
        }
    }

    public int getFreeCountPlace() {
        return freeTikets.size();
    }

    public List<Car> getListCar() {
        return listCar;
    }

    public void parkCar(Car car) {
        if (freeTikets.size() > 0)
            addCarInList(car);
    }

    public void unparkCar(int ticketId) {
        if (listCar.size() > 0)
            removeCarInList(ticketId);
    }

    private void addCarInList(Car car) {
        listCar.add(car);
        car.setTicket(freeTikets.get(freeTikets.size() - 1));
        freeTikets.remove(freeTikets.size() - 1);
    }

    private void removeCarInList(int ticketId) {
        for (int i = 0; i < listCar.size(); i++) {
            if (listCar.get(i).getTicket().getTicketId() == ticketId) {
                listCar.remove(listCar.get(i));
                freeTikets.add(new Ticket(ticketId));
            }
        }
    }

    public boolean checkTicketId(int ticketId) {
        for (int i = 0; i < listCar.size(); i++) {
            if (listCar.get(i).getTicket().getTicketId() == ticketId) {
                return true;
            }
        }
        return false;
    }
}
