import javax.print.*;
import java.awt.*;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class OutputPrinter extends Output{
    @Override
    public void printOutput(double[] result){

        String textToPrint="";

        if (result != null) {
            textToPrint+="Решение системы уравнений:\n";
            for (int i = 0; i < result.length; ++i) {
                textToPrint+=String.format("x%d = %d\n", i + 1, Math.round(result[i]));

            }

        } else {
            textToPrint = "Не удалось найти решение.";
        }


        try {

            File tempFile = File.createTempFile("Print", ".txt");
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
                writer.write(textToPrint);
            }


            DocFlavor flavor = DocFlavor.STRING.TEXT_PLAIN;
            Doc doc = new SimpleDoc(textToPrint, flavor, null);


            PrintService[] printServices = PrintServiceLookup.lookupPrintServices(flavor, null);
            if (printServices.length > 0) {

                PrinterJob job = PrinterJob.getPrinterJob();
                String finalTextToPrint = textToPrint;
                job.setPrintable(new Printable() {
                    public int print(Graphics g, PageFormat pf, int pageIndex) throws PrinterException {
                        if (pageIndex > 0) {
                            return NO_SUCH_PAGE;
                        }
                        g.drawString(finalTextToPrint, 100, 100);
                        return PAGE_EXISTS;
                    }
                });

                if (job.printDialog()) {
                    try {
                        job.print();
                    } catch (PrinterException e) {
                        System.err.println("Ошибка печати: " + e.getMessage());
                    }
                }
            } else {
                System.err.println("Нет доступных принтеров.");
            }


            tempFile.deleteOnExit();
        } catch (IOException e) {
            System.err.println("Ошибка работы с файлом: " + e.getMessage());
        }
    }
}
