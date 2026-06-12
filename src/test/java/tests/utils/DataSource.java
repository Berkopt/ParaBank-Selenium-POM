package tests.utils;

import tests.datatypes.Saving;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataSource {

    // --- EZ A MEGLÉVŐ BEOLVASÁSOD (VÁLTOZATLANUL) ---
    public static List<Saving> loadSavings() {
        List<Saving> savingsList = new ArrayList<>();
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

    // --- EZ AZ ÚJ MENTÉS FUNKCIÓ (EZT MÁSOLD BE ALÁ) ---
    public static void saveSavings(List<Saving> savings) {
        // Egy külön fájlba mentünk (savings_exported.csv), hogy ne írjuk felül az eredeti tesztadatodat
        String filePath = "src/test/resources/testdata/savings_exported.csv";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            // Fejléc kiírása (pontosan úgy, ahogy a load-nál van elvárva: pontosvesszővel elválasztva)
            writer.write("accountNumber;balance");
            writer.newLine();

            // Végigmegyünk a kapott listán
            for (Saving s : savings) {
                // Itt ellenőrizd a getterek nevét a te Saving.java fájlodban!
                // Ha pl. s.getAccountNumber() vagy s.getAccount() van, azt használd!
                String row = s.getName() + ";" + s.getInitialDeposit();
                writer.write(row);
                writer.newLine();
            }
            System.out.println("Adatok sikeresen kimentve: " + filePath);
        } catch (IOException e) {
            System.err.println("Hiba történt a CSV mentése közben: " + e.getMessage());
        }
    }
}