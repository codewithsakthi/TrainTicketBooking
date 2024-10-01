public class WaitingListManager {
    private static WaitingListManager instance;
    private TicketSystem system = TicketSystem.getInstance();

    private WaitingListManager(){
    }
    public static  WaitingListManager getInstance(){
        return instance == null ? new WaitingListManager() : instance;
    }
    public void processWaitingList(){
        for (Ticket ticket: system.getWaitingList().values()){
            char sc = ticket.getSource();
            char dn = ticket.getDestination();
            int seat= ticket.getSeatsBooked();
            int pnrNo = ticket.getPnrNo();
            if(system.checkAvailability(sc,dn,seat)){
                system.addToBookingList(pnrNo,ticket);
                ticket.setStatus(TicketStatus.Booked);
                system.decreaseAvailability(sc,dn,seat);
                system.removeFromWaitingList(pnrNo);
                System.out.println("Pnr No"+pnrNo+"Added to Booking List ");
            }
        }
    }


}
