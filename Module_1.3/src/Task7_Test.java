import eduni.distributions.ContinuousGenerator;
import eduni.distributions.Normal;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

// Represents the simulation clock singleton/utility
class Clock_Task7 {
    private static Clock_Task7 instance = null;
    private double time;

    private Clock_Task7() {
        time = 0.0;
    }

    public static synchronized Clock_Task7 getInstance() {
        if (instance == null) {
            instance = new Clock_Task7();
        }
        return instance;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }
}

// Enum for event types
enum EventType_Task7 {
    ARRIVAL
}

// Represents a Customer containing their arrival time
class Customer_Task7 {
    private double arrivalTime;
    private double departureTime;

    public Customer_Task7(double arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public double getArrivalTime() {
        return arrivalTime;
    }

    public void setDepartureTime(double departureTime) {
        this.departureTime = departureTime;
    }

    public double getDepartureTime() {
        return departureTime;
    }
}

// Represents an Event in the event list, ordered by time
class Event_Task7 implements Comparable<Event_Task7> {
    private EventType_Task7 type;
    private double time;

    public Event_Task7(EventType_Task7 type, double time) {
        this.type = type;
        this.time = time;
    }

    public EventType_Task7 getType() {
        return type;
    }

    public double getTime() {
        return time;
    }

    @Override
    public int compareTo(Event_Task7 other) {
        return Double.compare(this.time, other.time);
    }
}

// Manages the event list queue
class EventList_Task7 {
    private PriorityQueue<Event_Task7> events = new PriorityQueue<>();

    public Event_Task7 remove() {
        return events.poll();
    }

    public void add(Event_Task7 event) {
        events.add(event);
    }

    public boolean isEmpty() {
        return events.isEmpty();
    }
}

// Generates arrival events using a distribution generator
class ArrivalProcess_Task7 {
    private ContinuousGenerator generator;
    private EventType_Task7 eventType;

    public ArrivalProcess_Task7(ContinuousGenerator generator, EventType_Task7 eventType) {
        this.generator = generator;
        this.eventType = eventType;
    }

    public void generateNextEvent(EventList_Task7 eventList) {
        Clock_Task7 clock = Clock_Task7.getInstance();
        double interval = generator.sample();
        double newTime = clock.getTime() + interval;
        clock.setTime(newTime);

        Event_Task7 newEvent = new Event_Task7(eventType, newTime);
        eventList.add(newEvent);
    }
}

// Represents a service point handling a queue of customers with a random service time generator
class ServicePoint_Task7 {
    private Queue<Customer_Task7> queue = new LinkedList<>();
    private ContinuousGenerator serviceGenerator;

    public ServicePoint_Task7(ContinuousGenerator serviceGenerator) {
        this.serviceGenerator = serviceGenerator;
    }

    public void addToQueue(Customer_Task7 customer) {
        queue.add(customer);
    }

    public void serve() {
        while (!queue.isEmpty()) {
            Customer_Task7 customer = queue.poll();
            Clock_Task7 clock = Clock_Task7.getInstance();

            // Generate service time and calculate departure
            double serviceTime = serviceGenerator.sample();
            double departureTime = clock.getTime() + serviceTime;
            clock.setTime(departureTime);
            customer.setDepartureTime(departureTime);

            // Calculate time spent in the system (departure time - arrival time)
            double timeInSystem = customer.getDepartureTime() - customer.getArrivalTime();
            System.out.printf("Customer processed. Arrival: %.2f, Departure: %.2f, Time in system: %.2f\n",
                    customer.getArrivalTime(), customer.getDepartureTime(), timeInSystem);
        }
    }
}

public class Task7_Test {
    public static void main(String[] args) {
        Clock_Task7 clock = Clock_Task7.getInstance();
        EventList_Task7 eventList = new EventList_Task7();

        // Setup arrival process (e.g., Normal distribution for arrivals)
        ContinuousGenerator arrivalGenerator = new Normal(5.0, 1.0, 42);
        ArrivalProcess_Task7 arrivalProcess = new ArrivalProcess_Task7(arrivalGenerator, EventType_Task7.ARRIVAL);

        // Setup service point (e.g., Normal distribution for service times)
        ContinuousGenerator serviceGenerator = new Normal(3.0, 0.5, 123);
        ServicePoint_Task7 servicePoint = new ServicePoint_Task7(serviceGenerator);

        System.out.println("--- Generating 10 Arrival Events ---");
        for (int i = 0; i < 10; i++) {
            arrivalProcess.generateNextEvent(eventList);
        }

        System.out.printf("Clock time after generating all events: %.2f\n\n", clock.getTime());

        System.out.println("--- Processing Events into Service Point Queue ---");
        Event_Task7 event;
        while ((event = eventList.remove()) != null) {
            // Each event time is marked as a customer arrival time
            Customer_Task7 customer = new Customer_Task7(event.getTime());
            servicePoint.addToQueue(customer);
        }

        // Move the clock forward slightly (e.g., by 5 time units)
        double advancedTime = clock.getTime() + 5.0;
        clock.setTime(advancedTime);
        System.out.printf("Clock moved forward by 5 units. Current clock time: %.2f\n\n", clock.getTime());

        System.out.println("--- Clearing Service Point ---");
        servicePoint.serve();
    }
}