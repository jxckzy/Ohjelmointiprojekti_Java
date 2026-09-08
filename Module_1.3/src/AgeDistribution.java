public class AgeDistribution {

    public static void main(String[] args) {
        int[][] distributionA = {
                {16, 20},  // 16% for age 20
                {34, 21},  // 18% for age 21
                {52, 22},  // 18% for age 22
                {68, 23},  // 16% for age 23
                {82, 24},  // 14% for age 24
                {89, 25},  // 7% for age 25
                {94, 26},  // 5% for age 26
                {96, 28},  // 2% for age 28
                {98, 30},  // 2% for age 30
                {100, 35}  // 2% for age 35
        };

        // Mature class
        int[][] distributionB = {
                {10, 25},  // 10% for age 25
                {40, 30},  // 30% for age 30
                {75, 35},  // 35% for age 35
                {90, 40},  // 15% for age 40
                {100, 50}  // 10% for age 50
        };

        System.out.println("Test 1: original distribution, 1000 iterations");
        System.out.println("High variability in %-share");
        runSimulation(1000, distributionA, 35);

        System.out.println("\nTest 2: original distribution, 10000 iterations");
        System.out.println("Low variability in %-share because of more iterations.");
        runSimulation(1000, distributionA, 35);

        System.out.println("\nTest 3: custom situation(mature class), 10000 iterations");
        System.out.println("Testing a completely different array");
        runSimulation(1000, distributionB, 50);
    }


    public static void runSimulation(int iterations, int[][] distribution, int maxAge) {
        int[] generatedAges = new int[maxAge + 1];

        for (int i = 0; i < iterations; i++) {
            int x = (int)(Math.random() * 100) + 1;
            int j = 0;
            while (x > distribution[j][0]) {
                j++;
            }
            generatedAges[distribution[j][1]]++;
        }

        System.out.println("Age  count    %-share");
        for (int age = 0; age <= maxAge; age++) {
            if (generatedAges[age] != 0) {
                double percentage = ((double) generatedAges[age]) / iterations * 100;
                System.out.printf("%-4d %-8d %-8.2f\n", age, generatedAges[age], percentage);
            }
        }
    }
}