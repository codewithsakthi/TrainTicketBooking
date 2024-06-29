import java.util.ArrayList;
import java.util.List;

class Ticket {
    static int pnrCounter = 1;
    int pnr;
    String source;
    String destination;
    int seats;
    String status;
    List<Integer> seatNumbers;
    List<Integer> cancelledSeats;

    public Ticket(String source, String destination, int seats, String status, List<Integer> seatNumbers) {
        this.pnr = pnrCounter++;
        this.source = source;
        this.destination = destination;
        this.seats = seats;
        this.status = status;
        this.seatNumbers = seatNumbers;
        this.cancelledSeats = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "PNR: " + pnr + ", Source: " + source + ", Destination: " + destination + ", Seats: " + seats + ", Status: " + status + ", Seat Numbers: " + seatNumbers + (cancelledSeats.isEmpty() ? "" : ", Cancelled Seats: " + cancelledSeats);
    }
}