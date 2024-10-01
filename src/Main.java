import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;
        while (!exit){
            System.out.println("1)Book Ticket\n2)Cancel Ticket\n3)Print Summary\n4)Exit");
            int choice = scanner.nextInt();
            switch (choice){
                case 1:
                    System.out.println("Enter Source");
                    char sc = Character.toUpperCase(scanner.next().charAt(0));
                    System.out.println("Enter Destincation");
                    char dn = Character.toUpperCase(scanner.next().charAt(0));
                    System.out.println("Enter no of seats");
                    int seats = scanner.nextInt();
                    TicketBooking booking = new TicketBooking(sc,dn,seats);
                    booking.execute();
                    break;
                case 2:
                    System.out.println("Enter pnr number");
                    int pnrNo = scanner.nextInt();
                    System.out.println("Number of seats to cancel");
                    int seat = scanner.nextInt();
                    TicketCanceling canceling = new TicketCanceling(pnrNo,seat);
                    canceling.execute();
                    break;
                case 3:
                    TicketSystem.getInstance().printChart();
                    break;
                case 4:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid Choice");
                    break;
            }
        }
    }
}