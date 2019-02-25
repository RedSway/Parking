public class Car {
    private String number;
    private Ticket ticket;

    public Car(String number) {
        this.number = number;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public Ticket getTicket() {
        return ticket;
    }

    @Override
    public String toString() {
        return "Number: " + number + ", Ticket №: " + ticket.getTicketId();
    }
}
