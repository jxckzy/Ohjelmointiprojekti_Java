import java.util.LinkedList;

public class Customer_Task6 {
    private static int nextId = 1;

    private int id;
    private long arrivalTime;
    private long endTime;
    private long serviceTime;

    public Customer_Task6() {
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

    public long getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(long serviceTime) {
        this.serviceTime = serviceTime;
    }

    @Override
    public String toString() {
        return "Customer #" + id;
    }
}


class ServicePoint_Task6 {
    private LinkedList<Customer_Task6> queue = new LinkedList<>();

    private long totalServiceTime = 0;
    private int servedCount = 0;

    public void addToQueueue(Customer_Task6 a) {
        a.setStartTime(System.nanoTime());
        queue.addFirst(a);
    }

    public Customer_Task6 removeFromQueueue() {
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

        Customer_Task6 customer;
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
            customer.setServiceTime(serviceTimeMillis);

            totalServiceTime += serviceTimeMillis;
            servedCount++;

            long responseTimeNanos = customer.getTimeSpent();

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

    public double getAverageServiceTime() {
        if (servedCount == 0) {
            return 0.0;
        }
        return (double) totalServiceTime / servedCount;
    }

    public int getServedCount() {
        return servedCount;
    }

    public long getTotalServiceTime() {
        return totalServiceTime;
    }

    public void resetStatistics() {
        totalServiceTime = 0;
        servedCount = 0;
    }
}


class CustomerGenerator_Task6 {
    public CustomerGenerator_Task6(ServicePoint_Task6 servicePoint, int count) {
        for (int i = 0; i < count; i++) {
            Customer_Task6 customer = new Customer_Task6();
            servicePoint.addToQueueue(customer);
            System.out.println(customer + " added to queue.");
        }
    }
}


class Test_Task6 {
    public static void main(String[] args) {
        int numberOfRuns = 3;
        int customersPerRun = 5;

        double[] averagesPerRun = new double[numberOfRuns];

        for (int run = 1; run <= numberOfRuns; run++) {
            System.out.println("\nRun #" + run);

            ServicePoint_Task6 servicePoint = new ServicePoint_Task6();

            System.out.println("Generating " + customersPerRun + " customers...\n");
            new CustomerGenerator_Task6(servicePoint, customersPerRun);

            System.out.println("\nQueue size before serving: " + servicePoint.getQueueSize() + "\n");

            servicePoint.serve();

            double avgServiceTime = servicePoint.getAverageServiceTime();
            averagesPerRun[run - 1] = avgServiceTime;

            System.out.println("\nQueue size after serving: " + servicePoint.getQueueSize());
            System.out.printf("Customers served: %d%n", servicePoint.getServedCount());
            System.out.printf("Total service time: %d ms%n", servicePoint.getTotalServiceTime());
            System.out.printf("Average service time: %.2f ms%n", avgServiceTime);
        }

        System.out.println("\nSummary of runs:");

        double overallSum = 0;
        for (int i = 0; i < numberOfRuns; i++) {
            System.out.printf("Run %d average service time: %.2f ms%n", i + 1, averagesPerRun[i]);
            overallSum += averagesPerRun[i];
        }

        double overallAverage = overallSum / numberOfRuns;
        System.out.printf("%nOverall average service time across all runs: %.2f ms%n", overallAverage);
    }
}