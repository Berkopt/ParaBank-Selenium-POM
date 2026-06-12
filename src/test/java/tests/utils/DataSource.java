package tests.utils; // A tests mappán belüli utils csomag

import tests.datatypes.Saving; // Most már a jó helyről importáljuk!
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataSource {

    public static List<Saving> loadSavings() {
        List<Saving> savingsList = new ArrayList<>();

        // Ha a korábbi lépés alapján a testdata mappába raktad:
        String csvFile = "src/test/resources/testdata/savings.csv";

        String line;
        String cvsSplitBy = ";";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            br.readLine(); // Fejléc átugrása

            while ((line = br.readLine()) != null) {
                String[] data = line.split(cvsSplitBy);
                if (data.length >= 2) {
                    Saving saving = new Saving(data[0].trim(), data[1].trim());
                    savingsList.add(saving);
                }
            }
        } catch (IOException e) {
            System.err.println("Hiba a CSV fájl olvasása közben: " + e.getMessage());
        }
        return savingsList;
    }
}