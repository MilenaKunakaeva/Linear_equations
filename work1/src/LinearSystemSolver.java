import java.util.Arrays;

import static java.lang.Math.abs;

class LinearSystemSolver {
    Input input;
    Output output;

    // Конструктор, принимает объекты для ввода и вывода данных
    LinearSystemSolver(Input input, Output output) {
        this.input = input;
        this.output = output;
    }

    // Основной метод для решения системы линейных уравнений
    public void run() {
        int n = input.readSize(); // Считываем размер системы уравнений

        int[][] coefficients = new int[n][n]; // Матрица коэффициентов
        int[] constants = new int[n]; // Вектор правых частей
        int[][] Astar = new int[n][n + 1]; // Расширенная матрица (для проверки ранга)
        input.readInput(coefficients, constants); // Чтение данных

        // Формируем расширенную матрицу A*
        for (int i = 0; i < n; i++) {
            for (int j = 0; j <= n; j++) {
                if (j == n)
                    Astar[i][j] = constants[i];
                else
                    Astar[i][j] = coefficients[i][j];
            }
        }

        // Вычисляем ранги матриц для проверки совместности системы
        int rank = rank(coefficients); // Ранг матрицы коэффициентов
        int rankAdd = rank(Astar); // Ранг расширенной матрицы

        // Проверяем условия совместности: ранги должны совпадать и быть равны размеру
        if (!(rankAdd == rank && rankAdd == n)) {
            System.out.println("Rank: " + rank);
            System.out.println("RankAdd: " + rankAdd);
            return; // Если условие не выполняется, решение невозможно
        }

        double epsilon = input.readEpsilon(); // Читаем точность решения
        int maxIterations = input.readIterations(); // Читаем максимальное количество итераций

        // Преобразуем матрицу коэффициентов в формат double для дальнейших расчетов
        double[][] coefficients_double = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                coefficients_double[i][j] = coefficients[i][j];
            }
        }

        // Проверяем, является ли матрица диагонально-доминирующей
        boolean isDiagonalDominant = checkDiagonalDominance(coefficients_double);
        if (!isDiagonalDominant) {
            // Преобразуем матрицу в диагонально-доминирующую
            coefficients_double = makeDiagonalDominant(coefficients_double, constants);
        }

        // Решаем систему методом последовательных итераций
        double[] result = solve(coefficients_double, constants, epsilon, maxIterations);

        // Выводим результат
        output.printOutput(result);
    }

    // Преобразует матрицу в диагонально-доминирующую (если возможно)
    private static double[][] makeDiagonalDominant(double[][] matrix, int[] constants) {
        int n = matrix.length;

        for (int i = 0; i < n; i++) {
            int maxRowIndex = findMaxRow(i, matrix); // Находим строку с наибольшим значением

            if (maxRowIndex != i) {
                swapRows(matrix, i, maxRowIndex, constants); // Меняем строки местами
            }
        }

        return matrix;
    }

    // Находит строку с наибольшей суммой внедиагональных элементов
    private static int findMaxRow(int row, double[][] matrix) {
        int maxRowIndex = row;
        double maxSum = sumOffDiagonal(row, matrix[row]);

        for (int i = row + 1; i < matrix.length; i++) {
            double currentSum = sumOffDiagonal(i, matrix[i]);
            if (currentSum > maxSum) {
                maxSum = currentSum;
                maxRowIndex = i;
            }
        }

        return maxRowIndex;
    }

    // Вычисляет сумму внедиагональных элементов строки
    private static double sumOffDiagonal(int row, double[] array) {
        double sum = 0;
        for (int j = 0; j < array.length; j++) {
            if (j != row) {
                sum += Math.abs(array[j]);
            }
        }
        return sum;
    }

    // Меняет строки матрицы местами
    private static void swapRows(double[][] matrix, int row1, int row2, int[] constants) {
        double[] temp = Arrays.copyOf(matrix[row1], matrix[row1].length);
        matrix[row1] = Arrays.copyOf(matrix[row2], matrix[row2].length);
        matrix[row2] = temp;

        // Меняем соответствующие значения вектора правых частей
        int tmp_const = constants[row1];
        constants[row1] = constants[row2];
        constants[row2] = tmp_const;
    }

    // Проверяет, является ли матрица диагонально-доминирующей
    private static boolean checkDiagonalDominance(double[][] matrix) {
        int n = matrix.length;

        for (int i = 0; i < n; i++) {
            double diagonalElement = Math.abs(matrix[i][i]);
            double offDiagonalSum = sumOffDiagonal(i, matrix[i]);

            if (diagonalElement <= offDiagonalSum) {
                return false;
            }
        }

        return true;
    }

    // Вычисляет ранг матрицы
    private int rank(int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;

        int[][] tempMatrix = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            System.arraycopy(matrix[i], 0, tempMatrix[i], 0, cols);
        }

        int rank = 0;

        for (int row = 0; row < rows; row++) {
            if (row >= cols) {
                break;
            }
            if (tempMatrix[row][row] == 0) {
                boolean found = false;
                for (int i = row + 1; i < rows; i++) {
                    if (tempMatrix[i][row] != 0) {
                        // Меняем строки местами
                        int[] temp = tempMatrix[row];
                        tempMatrix[row] = tempMatrix[i];
                        tempMatrix[i] = temp;
                        found = true;
                        break;
                    }
                }

                if (!found) {
                    continue;
                }
            }

            // Приведение строки к каноническому виду
            for (int i = 0; i < rows; i++) {
                if (i != row) {
                    double factor = (double) tempMatrix[i][row] / tempMatrix[row][row];
                    for (int j = row; j < cols; j++) {
                        tempMatrix[i][j] -= factor * tempMatrix[row][j];
                    }
                }
            }
            rank++;
        }

        return rank;
    }

    // Реализация метода последовательных итераций
    public double[] solve(double[][] A, int[] b, double epsilon, int maxIterations) {
        int n = A.length;

        double[] xPrev = new double[n]; // Вектор предыдущих значений
        double[] xCur = new double[n]; // Вектор текущих значений

        // Инициализация начального приближения
        for (int i = 0; i < n; i++) {
            xPrev[i] = (double) b[i] / A[i][i];
        }

        // Итерационный процесс
        for (int k = 0; k < maxIterations; ++k) {
            for (int i = 0; i < n; ++i) {
                xCur[i] = (double) b[i] / A[i][i]; // Начальное значение для xCur[i]
                for (int j = 0; j < n; ++j) {
                    if (i == j) continue; // Пропускаем диагональный элемент
                    xCur[i] -= A[i][j] / A[i][i] * xPrev[j]; // Вычисляем новое значение
                }
            }

            // Проверяем сходимость
            boolean flag = true;
            for (int i = 0; i < n; i++) {
                if (abs(xCur[i] - xPrev[i]) > epsilon) {
                    flag = false;
                    break;
                }
            }

            // Обновляем xPrev для следующей итерации
            for (int i = 0; i < n; i++) {
                xPrev[i] = xCur[i];
            }

            // Если решение сошлось, возвращаем результат
            if (flag)
                return xPrev;
        }

        // Если решение не найдено за maxIterations, возвращаем null
        return null;
    }
}
