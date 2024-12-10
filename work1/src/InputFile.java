import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class InputFile extends Input {
    private int maxIterarions = 0;
    private int n;
    private double epsilon;
    private int[][] coefficients;
    private int[] constants;

    public InputFile(String filename) {
        super();
        try {
            File myObj = new File(filename);
            Scanner myReader = new Scanner(myObj);
            String data = myReader.nextLine();
            n = Integer.parseInt(data);

            coefficients = new int[n][n];
            constants = new int[n];

            for (int i = 0; i < n; i++) {
                data = myReader.nextLine();
                String[] tmp = data.split(" ");
                if (tmp.length != n + 1) {
                    throw new IllegalArgumentException("В строке " + (i + 1) + " должно быть " + (n + 1) + " чисел.");
                }
                for (int j = 0; j < n; j++) {
                    coefficients[i][j] = Integer.parseInt(tmp[j]);
                }
                constants[i] = Integer.parseInt(tmp[n]);
            }

            data = myReader.nextLine();
            epsilon = Double.parseDouble(data);

            data = myReader.nextLine();
            maxIterarions = Integer.parseInt(data);
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Нет файла " + filename);
            System.exit(0);
        } catch (NumberFormatException e) {
            System.out.println("Не корректный ввод данных, введите целочисленные значения.");
            System.exit(0);
        } catch (NoSuchElementException | IllegalArgumentException e) {
            System.out.println("Файл пуст или содержит несоответствие данных.");
            System.exit(0);
        }
    }

    @Override
    public void readInput(int[][] coefficients, int[] constants) {
        for (int i = 0; i < this.coefficients.length; i++) {
            System.arraycopy(this.coefficients[i], 0, coefficients[i], 0, this.coefficients[i].length);
            constants[i] = this.constants[i];
        }
    }

    @Override
    public int readIterations() {
        return maxIterarions;
    }

    @Override
    public double readEpsilon() {
        return epsilon;
    }

    @Override
    public int readSize() {
        return n;
    }
}
