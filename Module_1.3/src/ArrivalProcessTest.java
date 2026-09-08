import eduni.distributions.ContinuousGenerator;
import eduni.distributions.Normal;

import java.util.PriorityQueue;


class Event implements Comparable<Event> {
    private String type;
    private double time;

    public Event(String type, double time) {
        this.type = type;
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public double getTime() {
        return time;
    }

    @Override
    public int compareTo(Event other) {
        return Double.compare(this.time, other.time);
    }

    @Override
    public String toString() {
        return "Event{type='" + type + "', time=" + time + "}";
    }
}


class ArrivalProcess {
    private ContinuousGenerator generator;
    private String eventType;
    private double clock;

    public ArrivalProcess(ContinuousGenerator generator, String eventType) {
        this.generator = generator;
        this.eventType = eventType;
        this.clock = 0.0;
    }

    public void generateNextEvent(PriorityQueue<Event> eventList) {
        double interval = generator.sample();
        clock += interval;

        Event newEvent = new Event(eventType, clock);
        eventList.add(newEvent);
    }

    public double getClock() {
        return clock;
    }
}


public class ArrivalProcessTest {
    public static void main(String[] args) {
        ContinuousGenerator generator = new Normal(10.0, 2.0, 42);
        ArrivalProcess arrivalProcess = new ArrivalProcess(generator, "ARRIV_CUSTOMER");

        PriorityQueue<Event> eventList = new PriorityQueue<>();

        System.out.println("Generating 10 arrival events...");
        for (int i = 0; i < 10; i++) {
            arrivalProcess.generateNextEvent(eventList);
        }

        System.out.println("\nCurrent Event List:");
        for (Event e : eventList) {
            System.out.println(e);
        }
    }
}