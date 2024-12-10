import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class form implements ActionListener {
    private JRadioButton InputFileRadioButton;
    private JRadioButton InputTerminalRadioButton;
    private JRadioButton OutputFileRadioButton;
    private JRadioButton OutputTerminalRadioButton;
    private JRadioButton OutputPrinterRadioButton;
    private JButton resultButton;
    private JPanel mainPanel;
    private JRadioButton InputUIradioButton;
    private JTextField textFieldN;
    private JTextField textFieldEpsilon;
    private JTextField textFieldIter;
    private JTextArea textAreaCoef;
    private JRadioButton OutputUiRadioButton;
    JFrame frame;

    private String inputFilePath = null;
    private String outputFilePath = null;

    public form() {
        frame = new JFrame();
        frame.add(mainPanel);
        frame.setSize(900, 800);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ButtonGroup groupInp = new ButtonGroup();
        ButtonGroup groupOut = new ButtonGroup();
        InputTerminalRadioButton.setSelected(true);
        OutputTerminalRadioButton.setSelected(true);
        groupInp.add(InputFileRadioButton);
        groupInp.add(InputTerminalRadioButton);
        groupInp.add(InputUIradioButton);
        groupOut.add(OutputFileRadioButton);
        groupOut.add(OutputTerminalRadioButton);
        groupOut.add(OutputPrinterRadioButton);
        groupOut.add(OutputUiRadioButton);


        InputFileRadioButton.addActionListener(e -> selectInputFile());
        OutputFileRadioButton.addActionListener(e -> selectOutputFile());

        resultButton.addActionListener(this);
    }

    private void selectInputFile() {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            inputFilePath = fileChooser.getSelectedFile().getAbsolutePath();
        } else {
            InputTerminalRadioButton.setSelected(true);
            JOptionPane.showMessageDialog(frame, "Файл не выбран.");
        }
    }

    private void selectOutputFile() {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            outputFilePath = fileChooser.getSelectedFile().getAbsolutePath();
        } else {
            OutputTerminalRadioButton.setSelected(true);
            JOptionPane.showMessageDialog(frame, "Файл не выбран.");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Input input;
        Output output;

        if (InputFileRadioButton.isSelected() && inputFilePath != null) {
            input = new InputFile(inputFilePath);
        } else if (InputTerminalRadioButton.isSelected()) {
            input = new InputTerminal();
        } else if (InputUIradioButton.isSelected()) {
            input = new InputTextField(textFieldN.getText(), textFieldEpsilon.getText(),
                    textFieldIter.getText(), textAreaCoef.getText());
        } else {
            JOptionPane.showMessageDialog(frame, "Выберите корректный тип ввода.");
            return;
        }

        if (OutputFileRadioButton.isSelected() && outputFilePath != null) {
            output = new OutputFile(outputFilePath);
        } else if (OutputTerminalRadioButton.isSelected()) {
            output = new OutputTerminal();
        } else if (OutputPrinterRadioButton.isSelected()) {
            output = new OutputPrinter();
        } else if (OutputUiRadioButton.isSelected()) {
            output = new OutputTextField();
        } else {
            JOptionPane.showMessageDialog(frame, "Выберите корректный тип вывода.");
            return;
        }

        LinearSystemSolver solver = new LinearSystemSolver(input, output);
        solver.run();
    }

    public static void main(String[] args) {
        new form();
    }
}
