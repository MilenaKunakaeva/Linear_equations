import javax.swing.*;

public class OutputTextField  extends Output{
    @Override
    public void printOutput(double[] result){
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        JLabel label = new JLabel();
        panel.add(label);
        if (result != null) {
            label.setText("<html> Решение системы уравнений:<br/>");

            for (int i = 0; i < result.length; ++i) {
                label.setText(label.getText()+String.format("x%d = %d<br/>", i + 1, Math.round(result[i])));
            }
            label.setText(label.getText()+"</html>");
        } else {
            label.setText("Не удалось найти решение.");
        }
        frame.add(panel);
        frame.setSize(500, 500);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
