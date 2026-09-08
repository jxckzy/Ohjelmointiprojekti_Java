class Clock {
    private static Clock instance;
    private double time;

    private Clock() {
        time = 0.0;
    }

    public static Clock getInstance() {
        if (instance == null) {
            instance = new Clock();
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


public class ClockTest {
    public static void main(String[] args) {
        Clock clock1 = Clock.getInstance();

        clock1.setTime(10.5);
        System.out.println("clock1 time = " + clock1.getTime());

        Clock clock2 = Clock.getInstance();

        System.out.println("clock2 time = " + clock2.getTime());

        System.out.println("Same object: " + (clock1 == clock2));

        clock2.setTime(25.0);

        System.out.println("clock1 time after change = " + clock1.getTime());
    }
}
