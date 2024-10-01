import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TicketSystem {
    private static TicketSystem instance;
    private Map<Integer,Ticket> bookedList = new HashMap<>();
    private Map<Integer,Ticket> cancelledList = new HashMap<>();
    private Map<Integer,Ticket> waitingList = new ConcurrentHashMap<>();
    private Map<Integer,Integer> partiallyCanceledList = new HashMap<>();
    private int[] seatsAvailability = new int[5];
    static int waitingSeatsAvailable = 2;

    public int getWaitingSeatsAvailable() {
        return waitingSeatsAvailable;
    }
    public Ticket getTicket(int pnr){
        Ticket ticket = bookedList.get(pnr);
        return ticket == null ? waitingList.get(pnr) : ticket;
    }
    private TicketSystem(){
        Arrays.fill(seatsAvailability,8);
    }
    public static TicketSystem getInstance(){
        if(instance == null)
            instance = new TicketSystem();
        return instance;
    }

    public void increaseAvailability(char sc ,char dn,int seat){
        for(int i= sc-'A';i<=dn-'A';i++)
            seatsAvailability[i] += seat;
    }
    public void decreaseAvailability(char sc,char dn,int seat){
            for(int i= sc-'A';i<dn-'A';i++)
                seatsAvailability[i] -= seat;
    }
    public boolean checkAvailability(char sc,char dn,int seat){
        for(int i= sc-'A';i<=dn-'A';i++){
            if(seatsAvailability[i] < seat)
                return false;
        }
        return true;
    }
    public void addToBookingList(int pnr ,Ticket ticket){
        bookedList.put(pnr,ticket);
    }
    public void addToWaitingList(int pnr ,Ticket ticket){
        waitingList.put(pnr,ticket);
    }
    public void addToCancelList(int pnr ,Ticket ticket){
        cancelledList.put(pnr,ticket);
    }
    public void removeFromBookingList(int pnr){
        bookedList.remove(pnr);
    }
    public void addToParitiallyList(int pnr,int seats){
        partiallyCanceledList.merge(pnr,seats,Integer::sum);
    }
    public void printChart(){
        System.out.println("Booked List");
        bookedList.values().forEach(System.out::println);
        System.out.println();
        System.out.println("Waiting List");
        waitingList.values().forEach(System.out::println);
        System.out.println();
        System.out.println("Cancelled List");
        cancelledList.values().forEach(System.out::println);

        System.out.println("\t");
        for (int i=0;i<seatsAvailability.length;i++){
            System.out.print("\t"+(char) ('A'+i)+"\t");
        }
        System.out.println();
        for (int i=1;i<=8;i++){
            System.out.print(i);
            for(int j=0;j<seatsAvailability.length;j++){
                if(8 - seatsAvailability[j] >= i){
                    System.out.print("\t[X]"+"\t");
                }
                else
                    System.out.print("\t[ ]"+"\t");
            }
            System.out.println();
        }
    }

    public void removeFromWaitingList(int pnrNo) {
        waitingList.remove(pnrNo);
    }
    public Map<Integer,Ticket> getWaitingList(){
        return waitingList;
    }

    public void processPartiallyCancelled(Ticket ticket,int pnrNo) {
        if(partiallyCanceledList.get(pnrNo)!=null){
            int seat = partiallyCanceledList.get(pnrNo);
            ticket.setSeatsBooked(seat+ticket.getSeatsBooked());
            partiallyCanceledList.remove(pnrNo);
        }
    }
}
