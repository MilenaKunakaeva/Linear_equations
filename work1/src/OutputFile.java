import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class OutputFile extends Output{
    private String filename;
    public OutputFile(String filename) {
        this.filename = filename;
    }
    @Override
    public void printOutput(double[] result){
        Path fileName = Path.of(filename);

        try {
            String fileResult="";

            if (result != null) {
                fileResult+="Решение системы уравнений:\n";
                for (int i = 0; i < result.length; ++i) {
                    fileResult+=String.format("x%d = %d\n", i + 1, Math.round(result[i]));

                }

            } else {
                fileResult = "Не удалось найти решение.";
            }
            Files.writeString(fileName,fileResult);
        } catch (IOException e) {
            System.out.println("Проблема с файлом вывода");
            System.exit(0);
        }
    }
}
