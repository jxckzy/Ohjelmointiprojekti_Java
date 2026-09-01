import java.util.PriorityQueue;
import java.util.Random;

enum EventType {
    ARRIVAL, EXIT
}


public class Event_Task3 implements Comparable<Event_Task3> {
    private double time;
    private EventType type;
    private String description;

    public Event_Task3(double time, EventType type, String description) {
        this.time = time;
        this.type = type;
        this.description = description;
    }

    public double getTime() {
        return time;
    }

    public EventType getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public int compareTo(Event_Task3 other) {
        return Double.compare(this.time, other.time);
    }

    @Override
    public String toString() {
        return String.format("t=%.2f [%s] %s", time, type, description);
    }
}


class EventList_Task3 {
    private PriorityQueue<Event_Task3> events;

    public EventList_Task3() {
        events = new PriorityQueue<>();
    }

    public void insert(Event_Task3 e) {
        events.add(e);
    }

    public Event_Task3 next() {
        return events.poll();
    }

    public Event_Task3 peek() {
        return events.peek();
    }

    public boolean isEmpty() {
        return events.isEmpty();
    }

    public int size() {
        return events.size();
    }

    public void printOrdered() {
        PriorityQueue<Event_Task3> copy = new PriorityQueue<>(events);
        System.out.println("EventList contents (chronological order):");
        while (!copy.isEmpty()) {
            System.out.println("  " + copy.poll());
        }
    }
}


class Test3 {
    public static void main(String[] args) {
        EventList_Task3 list = new EventList_Task3();
        Random rand = new Random();

        EventType[] types = EventType.values();

        for (int i = 0; i < 6; i++) {
            double time = rand.nextDouble() * 100;
            EventType type = types[rand.nextInt(types.length)];
            String desc = (type == EventType.ARRIVAL)
                    ? "Customer " + i + " arrives"
                    : "Customer " + i + " exits";

            Event_Task3 e = new Event_Task3(time, type, desc);
            list.insert(e);
            System.out.println("Inserted: " + e);
        }

        System.out.println();

        Event_Task3 next = list.next();
        System.out.println("Removed next event to process: " + next);
        System.out.println();

        list.printOrdered();
    }
}