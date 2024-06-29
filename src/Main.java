import java.util.*;

public class Main {
    private static final int TOTAL_SEATS = 8;
    private static final int WAITING_LIST_LIMIT = 2;
    private List<Ticket> confirmedTickets = new ArrayList<>();
    private List<Ticket> waitingList = new ArrayList<>();
    private boolean[] seats = new boolean[TOTAL_SEATS];

    public static void main(String[] args) {
        Main system = new Main();
        Scanner scanner = new Scanner(System.in);
        String input;

        while (true) {
            System.out.println("Enter command (book <source> <destination> <seats> / cancel <PNR> <seats> / chart / exit):");
            input = scanner.nextLine();
            String[] parts = input.split(" ");

            if (parts[0].equals("exit")) {
                break;
            }

            switch (parts[0]) {
                case "book":
                    String source = parts[1];
                    String destination = parts[2];
                    int seats = Integer.parseInt(parts[3]);
                    system.bookTicket(source, destination, seats);
                    break;
                case "cancel":
                    int pnr = Integer.parseInt(parts[1]);
                    int seatsToCancel = Integer.parseInt(parts[2]);
                    system.cancelTicket(pnr, seatsToCancel);
                    break;
                case "chart":
                    system.printSummary();
                    break;
                default:
                    System.out.println("Invalid command. Please try again.");
            }
        }

        scanner.close();
    }

    private List<Integer> allocateSeats(int numberOfSeats) {
        List<Integer> allocatedSeats = new ArrayList<>();
        for (int i = 0; i < seats.length && numberOfSeats > 0; i++) {
            if (!seats[i]) {
                seats[i] = true;
                allocatedSeats.add(i + 1);
                numberOfSeats--;
            }
        }
        return allocatedSeats;
    }

    public void bookTicket(String source, String destination, int seats) {
        int availableSeats = 0;
        for (int i = 0; i < TOTAL_SEATS; i++) {
            if (!this.seats[i]) availableSeats++;
        }

        if (availableSeats >= seats) {
            List<Integer> allocatedSeats = allocateSeats(seats);
            Ticket ticket = new Ticket(source, destination, seats, "Confirmed", allocatedSeats);
            confirmedTickets.add(ticket);
            System.out.println("Booking successful: " + ticket);
        } else if (waitingList.size() < WAITING_LIST_LIMIT) {
            Ticket ticket = new Ticket(source, destination, seats, "Waiting", new ArrayList<>());
            waitingList.add(ticket);
            System.out.println("Booking added to waiting list: " + ticket);
        } else {
            System.out.println("Booking failed. Not enough seats available.");
        }
    }

    public void cancelTicket(int pnr, int seatsToCancel) {
        Ticket ticket = findTicketByPNR(pnr);
        if (ticket != null) {
            if (ticket.status.equals("Confirmed")) {
                if (seatsToCancel >= ticket.seats) {
                    ticket.seatNumbers.forEach(seat -> seats[seat - 1] = false);
                    confirmedTickets.remove(ticket);
                    System.out.println("Ticket fully cancelled: " + ticket);
                    moveWaitingListToConfirmed();
                } else {
                    List<Integer> cancelledSeats = ticket.seatNumbers.subList(0, seatsToCancel);
                    cancelledSeats.forEach(seat -> seats[seat - 1] = false);
                    ticket.cancelledSeats.addAll(cancelledSeats);
                    ticket.seatNumbers = ticket.seatNumbers.subList(seatsToCancel, ticket.seatNumbers.size());
                    ticket.seats -= seatsToCancel;
                    System.out.println("Ticket partially cancelled: " + ticket);
                    moveWaitingListToConfirmed();
                }
            } else if (ticket.status.equals("Waiting")) {
                waitingList.remove(ticket);
                System.out.println("Ticket cancelled from waiting list: " + ticket);
            }
        } else {
            System.out.println("PNR not found.");
        }
    }

    private void moveWaitingListToConfirmed() {
        Iterator<Ticket> iterator = waitingList.iterator();
        while (iterator.hasNext()) {
            Ticket waitingTicket = iterator.next();
            int availableSeats = 0;
            for (boolean seat : seats) {
                if (!seat) availableSeats++;
            }
            if (availableSeats >= waitingTicket.seats) {
                List<Integer> allocatedSeats = allocateSeats(waitingTicket.seats);
                waitingTicket.status = "Confirmed";
                waitingTicket.seatNumbers = allocatedSeats;
                confirmedTickets.add(waitingTicket);
                System.out.println("Moved from waiting to confirmed: " + waitingTicket);
                iterator.remove();
            }
        }
    }

    private Ticket findTicketByPNR(int pnr) {
        for (Ticket ticket : confirmedTickets) {
            if (ticket.pnr == pnr) {
                return ticket;
            }
        }
        for (Ticket ticket : waitingList) {
            if (ticket.pnr == pnr) {
                return ticket;
            }
        }
        return null;
    }

    public void printSummary() {
        System.out.println("Booking Summary:");
        for (Ticket ticket : confirmedTickets) {
            System.out.println(ticket);
        }
        for (Ticket ticket : waitingList) {
            System.out.println(ticket);
        }
    }
}
