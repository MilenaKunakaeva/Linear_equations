import java.util.Random;

public class Input {
    private int n;
    private int iter = 1000000;
    private double epsilon = 0.00000001;

    public void readInput(int[][] coefficients, int[] constants) {
        // Проверка соответствия размеров массивов
        if (coefficients.length != n || constants.length != n) {
            throw new IllegalArgumentException("Количество уравнений (" + n + ") не соответствует размеру введенных данных.");
        }

        double[] x = new double[n];
        Random random = new Random();

        for (int i = 0; i < n; i++) {
            x[i] = random.nextInt() * 10;
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j) {
                    coefficients[i][j] = 1;
                } else {
                    coefficients[i][j] = 0;
                }
            }
        }

        for (int i = n - 1; i >= 0; i--) {
            for (int j = i - 1; j >= 0; j--) {
                double factor = random.nextDouble() * 10;
                if (factor != 0) {
                    for (int k = 0; k < n; k++) {
                        coefficients[j][k] += factor * coefficients[i][k];
                    }
                    constants[j] += factor * constants[i];
                }
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                constants[i] += coefficients[i][j] * x[j];
            }
        }
    }

    public int readSize() {
        return n;
    }

    public void setSize(int size) {
        this.n = size;
    }

    public int readIterations() {
        return iter;
    }

    public double readEpsilon() {
        return epsilon;
    }
}
