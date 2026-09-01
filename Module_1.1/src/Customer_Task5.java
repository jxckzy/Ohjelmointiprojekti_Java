import java.util.LinkedList;

public class Customer_Task5 {
    private static int nextId = 1;

    private int id;
    private long arrivalTime;
    private long endTime;

    public Customer_Task5() {
        this.id = nextId;
        nextId++;
    }

    public int getId() {
        return id;
    }

    public long getStartTime() {
        return arrivalTime;
    }

    public void setStartTime(long startTime) {
        this.arrivalTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public long getTimeSpent() {
        return endTime - arrivalTime;
    }

    @Override
    public String toString() {
        return "Customer #" + id;
    }
}


class ServicePoint {
    private LinkedList<Customer_Task5> queue = new LinkedList<>();

    public void addToQueueue(Customer_Task5 a) {
        a.setStartTime(System.nanoTime());
        queue.addFirst(a);
    }

    public Customer_Task5 removeFromQueueue() {
        if (queue.isEmpty()) {
            return null;
        }
        return queue.removeLast();
    }

    public int getQueueSize() {
        return queue.size();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public void serve() {
        System.out.println("Service point starting");

        Customer_Task5 customer;
        while ((customer = removeFromQueueue()) != null) {

            long removalTime = System.nanoTime();
            long waitingTimeNanos = removalTime - customer.getStartTime();

            // Simulate service time, e.g. random between 500ms and 2000ms
            int serviceTimeMillis = 500 + (int) (Math.random() * 1500);

            try {
                Thread.sleep(serviceTimeMillis);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Service interrupted for " + customer);
            }

            customer.setEndTime(System.nanoTime());
            long responseTimeNanos = customer.getTimeSpent(); // waiting + service

            double waitingMs = waitingTimeNanos / 1_000_000.0;
            double responseMs = responseTimeNanos / 1_000_000.0;

            System.out.printf("%s served.%n", customer);
            System.out.printf("Waiting time : %.2f ms%n", waitingMs);
            System.out.printf("Service time : %d ms%n", serviceTimeMillis);
            System.out.printf("Response time: %.2f ms%n", responseMs);
            System.out.println();
        }

        System.out.println("Queue is empty. Service point stopping.");
    }
}


class CustomerGenerator {
    public CustomerGenerator(ServicePoint servicePoint, int count) {
        for (int i = 0; i < count; i++) {
            Customer_Task5 customer = new Customer_Task5();
            servicePoint.addToQueueue(customer);
            System.out.println(customer + " added to queue.");
        }
    }
}


class Test {
    public static void main(String[] args) {
        ServicePoint servicePoint = new ServicePoint();
        // Testaus
        int numberOfCustomers = 5;
        System.out.println("Generating " + numberOfCustomers + " customers...\n");
        new CustomerGenerator(servicePoint, numberOfCustomers);

        System.out.println("\nQueue size before serving: " + servicePoint.getQueueSize() + "\n");
        servicePoint.serve();
        System.out.println("\nQueue size after serving: " + servicePoint.getQueueSize());
    }
}