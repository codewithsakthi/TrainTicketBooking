public class TicketBooking {
    private TicketSystem system = TicketSystem.getInstance();
    private  char sc;
    private  char dn;
    private int seats;

    TicketBooking(char sc ,char dn ,int seats){
        this.sc = sc;
        this.dn = dn;
        this.seats = seats;
    }

    private void bookTicket(){
        if(system.checkAvailability(sc,dn,seats)){
            Ticket ticket = new Ticket(sc,dn,seats,TicketStatus.Booked);
            int pnr = ticket.getPnrNo();
            system.addToBookingList(pnr,ticket);
            system.decreaseAvailability(sc,dn,seats);
            System.out.println("Ticket Booked Successfully Pnr no is "+pnr);
        }
        else{
            if( seats <= system.getWaitingSeatsAvailable()){
                Ticket ticket = new Ticket(sc,dn,seats,TicketStatus.WaitingList);
                int pnr = ticket.getPnrNo();
                system.addToWaitingList(pnr,ticket);
                TicketSystem.waitingSeatsAvailable -= seats;
                System.out.println("Ticket Added to waitingList Pnr no is "+pnr);

            }
            else
                System.out.println("No tickets available");
        }
    }
    public void execute(){
        bookTicket();
    }
}
