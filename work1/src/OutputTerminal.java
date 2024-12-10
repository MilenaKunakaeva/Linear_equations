public class OutputTerminal extends Output {
    @Override
    public void printOutput(double[] result){
        if (result != null) {
            System.out.println("Решение системы уравнений:");
            for (int i = 0; i < result.length; ++i) {
                System.out.printf("x%d = %d\n", i + 1, Math.round(result[i]));
            }
        } else {
            System.out.println("Не удалось найти решение.");
        }
    }
}
