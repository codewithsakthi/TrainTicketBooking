public class TicketCanceling {
    private int pnrNo;
    private int seats;
    private TicketSystem system = TicketSystem.getInstance();
    TicketCanceling(int pnr,int seats){
        this.pnrNo = pnr;
        this.seats = seats;
    }
    private void cancel(){
        Ticket ticket = system.getTicket(pnrNo);
        if(ticket.getStatus()==TicketStatus.WaitingList){
            system.removeFromWaitingList(ticket.getPnrNo());
            TicketSystem.waitingSeatsAvailable += seats;
            return;
        }
        if(seats < ticket.getSeatsBooked()){
            system.addToParitiallyList(ticket.getPnrNo(),seats);
            ticket.setSeatsBooked(ticket.getSeatsBooked()-seats);
            system.increaseAvailability(ticket.getSource(),ticket.getDestination(),seats);
            System.out.println(seats+"Seats Partially Cancelled for pnr no "+ticket.getPnrNo());
        }
        else{
            system.removeFromBookingList(pnrNo);
            ticket.setStatus(TicketStatus.Cancelled);
            system.increaseAvailability(ticket.getSource(),ticket.getDestination(),seats);
            system.processPartiallyCancelled(ticket,pnrNo);
            system.addToCancelList(ticket.getPnrNo(),ticket);
            System.out.println("Tickets cancelled successfully");
        }
        WaitingListManager manager = WaitingListManager.getInstance();
        manager.processWaitingList();
    }
    public void execute(){
        cancel();
    }
}
