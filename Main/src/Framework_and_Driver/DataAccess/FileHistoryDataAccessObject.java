package Framework_and_Driver.DataAccess;

import Entity.History;
import UseCase.History.HistoryDataAccessInterface;

import java.io.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.time.LocalDateTime;
import java.util.ArrayList;


public class FileHistoryDataAccessObject implements HistoryDataAccessInterface {

    private final String filePath;

    public FileHistoryDataAccessObject(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void saveHistory(History history) {
        try (FileWriter writer = new FileWriter(filePath, true)) {
            writer.write(history.getUsername() + ","
                    + history.getDifficulty() + ","
                    + history.getCompletedTime().toString()
                    + System.lineSeparator());
        } catch (IOException e) {
            throw new RuntimeException("Failed to save history.", e);
        }
    }

    @Override
    public List<History> getHistory(String username) {
        List<History> histories = new ArrayList<>();

        if (!new File(filePath).exists()) {
            return histories;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");

                if (data.length == 3 && data[0].equals(username)) {
                    History history = new History(
                            data[0],
                            data[1],
                            LocalDateTime.parse(data[2])
                    );

                    histories.add(history);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load history.", e);
        }

        return histories;
    }
}



