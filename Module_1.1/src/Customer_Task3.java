public class Customer_Task3 {
    private static int nextId = 1;

    private int id;
    private long startTime;
    private long endTime;

    public Customer_Task3() {
        this.id = nextId;
        nextId++;
    }

    public Customer_Task3(long startTime, long endTime) {
        this();
        this.startTime = startTime;
        this.endTime = endTime;
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
        return "Customer{" + "id=" + id + ", startTime=" + startTime + ", endTime=" + endTime + ", timeSpent=" + getTimeSpent() + '}';
    }
}


class CustomerTest {
    public static void main(String[] args) throws InterruptedException {

        // Testaus 1: manuaalliset asetukset
        Customer_Task3 c1 = new Customer_Task3();
        c1.setStartTime(1000);
        c1.setEndTime(5000);

        Customer_Task3 c2 = new Customer_Task3();
        c2.setStartTime(2000);
        c2.setEndTime(3500);

        System.out.println("Manual timestamps");
        System.out.println(c1);
        System.out.println("Time spent by customer " + c1.getId() + ": " + c1.getTimeSpent());
        System.out.println(c2);
        System.out.println("Time spent by customer " + c2.getId() + ": " + c2.getTimeSpent());

        System.out.println("\nExpected: c1 id = 1, c2 id = 2");
        System.out.println("Actual: c1 id = " + c1.getId() + ", c2 id = " + c2.getId());

        // Testaus 2 käyttäen currentTimeMillis
        System.out.println("\nReal timestamps (in milliseconds)");

        Customer_Task3 c3 = new Customer_Task3();
        c3.setStartTime(System.currentTimeMillis());

        Thread.sleep(1500); // 1.5 sek

        c3.setEndTime(System.currentTimeMillis());

        System.out.println(c3);
        System.out.println("Time spent by customer " + c3.getId() + ": " + c3.getTimeSpent() + " ms");

        // Testaus 3
        Customer_Task3 c4 = new Customer_Task3(System.currentTimeMillis(), System.currentTimeMillis() + 800);
        System.out.println("\nAnother customer");
        System.out.println(c4);
        System.out.println("Time spent by customer " + c4.getId() + ": " + c4.getTimeSpent() + " ms");
    }
}