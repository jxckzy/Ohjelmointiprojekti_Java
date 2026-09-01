import java.util.LinkedList;
import java.util.Scanner;

public class Customer_Task4 {
    private static int nextId = 1;

    private int id;
    private long startTime;
    private long endTime;

    public Customer_Task4() {
        this.id = nextId;
        nextId++;
    }

    public int getId() {
        return id;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public long getTimeSpent() {
        return endTime - startTime;
    }

    @Override
    public String toString() {
        return "Customer #" + id;
    }
}


class CustomerQueue_Task4 {
    public static void main(String[] args) {
        LinkedList<Customer_Task4> queue = new LinkedList<>();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Customer Queue Simulation");
        System.out.println("Commands: [q] queue new customer, [d] dequeue customer, [l] list queue, [e] exit");

        boolean running = true;
        while (running) {
            System.out.print("\nEnter command (q/d/l/e): ");
            String command = scanner.nextLine().trim().toLowerCase();

            switch (command) {
                case "q":
                    queueCustomer(queue);
                    break;
                case "d":
                    dequeueCustomer(queue);
                    break;
                case "l":
                    listQueue(queue);
                    break;
                case "e":
                    running = false;
                    System.out.println("Exiting program. Goodbye!");
                    break;
                default:
                    System.out.println("Unknown command. Please enter q, d, l, or e.");
            }
        }

        scanner.close();
    }

    // Lisää uusi asiakas "q" kommennolla
    private static void queueCustomer(LinkedList<Customer_Task4> queue) {
        Customer_Task4 customer = new Customer_Task4();
        customer.setStartTime(System.nanoTime());
        queue.addFirst(customer);

        System.out.println("Customer #" + customer.getId() + " has joined the queue.");
        System.out.println("Current queue size: " + queue.size());
    }

    // Poista asiakas "d" kommennolla
    private static void dequeueCustomer(LinkedList<Customer_Task4> queue) {
        if (queue.isEmpty()) {
            System.out.println("The queue is empty. No customer to dequeue.");
            return;
        }

        Customer_Task4 customer = queue.removeLast();
        customer.setEndTime(System.nanoTime());

        long nanosSpent = customer.getTimeSpent();
        double millisSpent = nanosSpent / 1_000_000.0;
        double secondsSpent = nanosSpent / 1_000_000_000.0;

        System.out.println("Customer #" + customer.getId() + " has been dequeued.");
        System.out.printf("Time spent in queue: %d ns (%.3f ms / %.6f s)%n",
                nanosSpent, millisSpent, secondsSpent);
        System.out.println("Current queue size: " + queue.size());
    }

    // Näyttää asiakkaiden lista
    private static void listQueue(LinkedList<Customer_Task4> queue) {
        if (queue.isEmpty()) {
            System.out.println("The queue is currently empty.");
            return;
        }

        System.out.println("Customers currently in queue (front to back):");
        for (int i = queue.size() - 1; i >= 0; i--) {
            System.out.println(" - " + queue.get(i));
        }
    }
}