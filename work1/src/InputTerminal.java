import java.util.Scanner;

public class InputTerminal extends Input {
    private Scanner scanner = new Scanner(System.in);

    @Override
    public void readInput(int[][] coefficients, int[] constants) {
        try {
            System.out.println("Введите количество уравнений:");
            int n = readSize();

            System.out.println("Введите коэффициенты системы уравнений:");
            for (int i = 0; i < n; i++) {
                System.out.println("Уравнение " + (i + 1) + ":");
                for (int j = 0; j < n; j++) {
                    coefficients[i][j] = scanner.nextInt();
                }
                constants[i] = scanner.nextInt();
            }

            if (coefficients.length != n || constants.length != n) {
                throw new IllegalArgumentException("Размеры массивов не соответствуют заданному количеству уравнений.");
            }
        } catch (Exception e) {
            System.out.println("Некорректный ввод данных.");
            System.exit(0);
        }
    }

    @Override
    public int readIterations() {
        try {
            System.out.print("Введите максимальное количество итераций: ");
            return scanner.nextInt();
        } catch (Exception e) {
            System.out.println("Некорректный ввод максимального количества итераций.");
            System.exit(0);
        }
        return 0;
    }

    @Override
    public double readEpsilon() {
        try {
            System.out.print("Введите точность (ε): ");
            return scanner.nextDouble();
        } catch (Exception e) {
            System.out.println("Некорректный ввод точности.");
            System.exit(0);
        }
        return 0;
    }

    @Override
    public int readSize() {
        try {
            System.out.print("Введите количество уравнений в системе: ");
            int n = scanner.nextInt();
            if (n <= 0) {
                throw new IllegalArgumentException("Количество уравнений должно быть положительным.");
            }
            return n;
        } catch (Exception e) {
            System.out.println("Некорректный ввод количества уравнений.");
            System.exit(0);
        }
        return 0;
    }
}
