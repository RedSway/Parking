import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    private static Parking parking;
    private static AtomicInteger uniqIdCar;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        uniqIdCar = new AtomicInteger(1);

        System.out.print("Enter the number of parking spaces: ");
        parking = new Parking(scanner.nextInt());

        while (true) {
            String str = scanner.next();

            if (str.matches("p:\\d+")) {
                int countCar = Integer.parseInt(str.substring(2));
                if (countCar <= parking.getFreeCountPlace())
                    runParkThread(countCar);
                else if (countCar > parking.getFreeCountPlace() && parking.getFreeCountPlace() != 0)
                    runParkThread(parking.getFreeCountPlace());
                else
                    System.out.println("No free places");
            }

            else if (str.matches("u:\\d+")) {
                int ticketId = Integer.parseInt(str.substring(2));
                runUnparkingThread(ticketId);
            }

            else if (str.indexOf('[') == 2) {
                String[] s = str.substring(3, str.length() - 1).split(",");
                for (int i = 0; i < s.length; i++) {
                    runUnparkingThread(Integer.parseInt(s[i]));
                }
            }

            else if (str.equals("c"))
                System.out.println("Count of free places: " + parking.getFreeCountPlace());

            else if (str.equals("l")) {
                if (parking.getListCar().size() == 0 || parking.getListCar() == null)
                    System.out.println("All places are free");
                else {
                    for (int i = 0; i < parking.getListCar().size(); i++) {
                        System.out.println(i + 1 + ". " + parking.getListCar().get(i).toString());
                    }
                }
            }

            else if (str.equals("e"))
                System.exit(1);
        }
    }

    private static void runParkThread(int countCar) {
        Random random = new Random();
        Thread parkThread = new Thread(() -> {
            for (int i = 0; i < countCar; i++) {
                try {
                    int tsleep = random.nextInt(5);
                    parking.parkCar(new Car("" + uniqIdCar.getAndIncrement()));
                    Thread.sleep((tsleep + 1) * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        parkThread.start();
    }

    private static void runUnparkingThread(int ticketId) {
        if (parking.checkTicketId(ticketId)) {
            Thread unparkThread = new Thread(() -> {
                parking.unparkCar(ticketId);
            });
            unparkThread.start();
        } else
            System.out.println("Ticket № " + ticketId + " is free");
    }
}
