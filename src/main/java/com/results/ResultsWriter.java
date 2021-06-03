package com.results;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.List;

public class ResultsWriter {

    public static void writeToFile(ResultsCollection results,
                                   String dirName, String filename) throws IOException {
        File file = createFile(dirName, filename);
        writeToFile(file, results);
    }

    public static void writeToFile(File file, ResultsCollection results) throws IOException {
        FileWriter writer = new FileWriter(file);
        writer.write(results.toString());
        writer.flush();
        writer.close();

        System.out.println("Written results to " + file.getAbsolutePath());
    }

    public static File createFile(String dirName, String fileName) throws IOException {
        File directory = createUniqueResultsDirectory(dirName, fileName);
        File f = new File(directory, fileName + ".dat");
        return f;
    }

    public static File createUniqueResultsDirectory(String parentDir,
                                                    String resultsDirectoryName) throws IOException {
        File dir = new File(parentDir);
        if (!dir.exists()) {
            dir.mkdir();
        }
        NumberFormat format = NumberFormat.getIntegerInstance();
        format.setMinimumIntegerDigits(2);
        Calendar c = Calendar.getInstance();

        File dateDir = new File(dir, c.get(Calendar.YEAR) + "_"
                + format.format(c.get(Calendar.MONTH) + 1) + "_"
                + format.format(c.get(Calendar.DAY_OF_MONTH)));

        if (!dateDir.exists()) {
            dateDir.mkdir();
        }

        NumberFormat format3 = NumberFormat.getIntegerInstance();
        format3.setMinimumIntegerDigits(3);

        File f;
        int counter = 1;
        do {
            f = new File(dateDir, format.format(c.get(Calendar.HOUR_OF_DAY))
                    + format.format(c.get(Calendar.MINUTE)) + "_"
                    + format3.format(counter) + "_" + resultsDirectoryName);
            counter++;
        } while (f.exists());

        f.mkdir();

        return f;
    }
}