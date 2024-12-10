import javax.swing.*;
import java.io.FileNotFoundException;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {



        Scanner scanner = new Scanner(System.in);
        Input input;
        Output output;
        System.out.println("Выберите тип ввода: \n1 - терминал\n2 - файл");
        String rez = scanner.nextLine();
        switch (rez) {
            case "1":
                input = new InputTerminal();
                break;
            case "2":
                System.out.println("Введите полный путь до файла");
                String filename = scanner.nextLine();
                input = new InputFile(filename);
                break;
            default:
                input = new InputTerminal();
                System.out.println("Ввод не корректен\nБудет использован ввод с терминала");
        }
        System.out.println("Выберите тип вывода: \n1 - терминал\n2 - файл\n3 - принтер");
        rez = scanner.nextLine();
        switch (rez) {
            case "1":
                output = new OutputTerminal();
                break;
            case "2":
                System.out.println("Введите полный путь до файла вывода");
                String filename = scanner.nextLine();
                output = new OutputFile(filename);
                break;
            case "3":
                output = new OutputPrinter();
                break;
            default:
                output = new OutputTerminal();
                System.out.println("Ввод не корректен\nБудет использован вывод на терминал");
        }
        LinearSystemSolver solver = new LinearSystemSolver(input, output);
        solver.run();

    }

}
