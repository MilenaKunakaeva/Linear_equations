public class Testing {
    public static void main(String[] args) {
        Input input = new Input();
        Output output = new Output();

        int N=100000;
        for(int i = 1; i<=N;i++){

            long startTime = System.nanoTime();

            input.setSize(i);
            LinearSystemSolver solver = new LinearSystemSolver(input, output);
            solver.run();

            long endTime = System.nanoTime();

            long executionTime = (endTime - startTime) / 1000000;

            System.out.println("Size = "+i+" time: " + executionTime + " ms");
        }

        for(int i = 1; i<=N;i++){

            input.setSize(i);
            LinearSystemSolver solver = new LinearSystemSolver(input, output);
            solver.run();

            Runtime runtime = Runtime.getRuntime();
            runtime.gc();
            long memory = runtime.totalMemory() - runtime.freeMemory();
            System.out.println("Size = "+i+" bytes: " + memory);
        }

    }
}
