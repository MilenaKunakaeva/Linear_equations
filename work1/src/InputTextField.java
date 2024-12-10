import javax.swing.*;

public class InputTextField extends Input {
    private String textFieldN;
    private String textFieldEpsilon;
    private String textFieldIter;
    private String textFieldCoef;

    public InputTextField(String textFieldN, String textFieldEpsilon, String textFieldIter, String textFieldCoef) {
        this.textFieldN = textFieldN;
        this.textFieldEpsilon = textFieldEpsilon;
        this.textFieldIter = textFieldIter;
        this.textFieldCoef = textFieldCoef;
    }

    @Override
    public void readInput(int[][] coefficients, int[] constants) {
        try {
            int n = Integer.parseInt(textFieldN);
            String[] lines = textFieldCoef.trim().split("\n");

            if (lines.length != n) {
                throw new IllegalArgumentException("Количество строк в коэффициентах должно быть равно " + n + ".");
            }

            for (int i = 0; i < n; i++) {
                String[] line = lines[i].trim().split(" ");
                if (line.length != n + 1) {
                    throw new IllegalArgumentException("В строке " + (i + 1) + " должно быть " + (n + 1) + " чисел.");
                }

                for (int j = 0; j < n; j++) {
                    coefficients[i][j] = Integer.parseInt(line[j]);
                }
                constants[i] = Integer.parseInt(line[n]);
            }
        } catch (NumberFormatException e) {
            showError("Некорректный ввод чисел.");
            throw new RuntimeException(e);
        } catch (IllegalArgumentException e) {
            showError(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(null, message, "Ошибка ввода", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public int readIterations() {
        return Integer.parseInt(textFieldIter);
    }

    @Override
    public double readEpsilon() {
        return Double.parseDouble(textFieldEpsilon);
    }

    @Override
    public int readSize() {
        return Integer.parseInt(textFieldN);
    }
}
