import java.util.PriorityQueue;
import java.util.Random;

public class Event_Task2 implements Comparable<Event_Task2> {
    private double time;
    private String description;

    public Event_Task2(double time, String description) {
        this.time = time;
        this.description = description;
    }

    public double getTime() {
        return time;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public int compareTo(Event_Task2 other) {
        return Double.compare(this.time, other.time);
    }

    @Override
    public String toString() {
        return String.format("t=%.2f : %s", time, description);
    }
}


class EventList_Task2 {
    private PriorityQueue<Event_Task2> events;

    public EventList_Task2() {
        events = new PriorityQueue<>();
    }

    public void insert(Event_Task2 e) {
        events.add(e);
    }

    public Event_Task2 next() {
        return events.poll();
    }

    public Event_Task2 peek() {
        return events.peek();
    }

    public boolean isEmpty() {
        return events.isEmpty();
    }

    public int size() {
        return events.size();
    }

    public void printOrdered() {
        PriorityQueue<Event_Task2> copy = new PriorityQueue<>(events);
        System.out.println("EventList contents (chronological order):");
        while (!copy.isEmpty()) {
            System.out.println("  " + copy.poll());
        }
    }
}


class Test2 {
    public static void main(String[] args) {
        EventList_Task2 list = new EventList_Task2();
        Random rand = new Random();

        String[] descriptions = {
                "Customer arrives", "Service starts", "Service ends",
                "Machine breaks down", "Repair finished", "Break ends"
        };

        for (String desc : descriptions) {
            double time = rand.nextDouble() * 100;
            Event_Task2 e = new Event_Task2(time, desc);
            list.insert(e);
            System.out.println("Inserted: " + e);
        }

        System.out.println();

        Event_Task2 next = list.next();
        System.out.println("Removed next event to process: " + next);
        System.out.println();

        list.printOrdered();
    }
}