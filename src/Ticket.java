public class Ticket {
    public int getPnrNo() {
        return pnrNo;
    }

    public void setPnrNo(int pnrNo) {
        this.pnrNo = pnrNo;
    }

    public char getSource() {
        return source;
    }

    public void setSource(char source) {
        this.source = source;
    }

    public char getDestination() {
        return destination;
    }

    public void setDestination(char destination) {
        this.destination = destination;
    }

    public int getSeatsBooked() {
        return seatsBooked;
    }

    public void setSeatsBooked(int seatsBooked) {
        this.seatsBooked = seatsBooked;
    }

    public TicketStatus getStatus() {
        return status;
    }

    public void setStatus(TicketStatus status) {
        this.status = status;
    }

    private static int pnrGenerator = 1;
    private int  pnrNo;
    private char source;
    private char destination;
    private int seatsBooked;
    private TicketStatus status;

    @Override
    public String toString() {
        return "Ticket{" +
                "pnrNo=" + pnrNo +
                ", source=" + source +
                ", destination=" + destination +
                ", seatsBooked=" + seatsBooked +
                ", status=" + status +
                '}';
    }

    Ticket(char source, char destination, int seats, TicketStatus status){
        this.source = source;
        this.destination = destination;
        this.seatsBooked = seats;
        this.pnrNo = pnrGenerator++;
        this.status = status;
    }
}
