package cfvbaibai.cardfantasy.test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Table<T> {
    private List<List<T>> rows;

    public Table() {
        this.rows = new ArrayList<List<T>>();
    }

    public void setCell(int r, int c, T value) {
        while (rows.size() <= r) {
            rows.add(new ArrayList<T>());
        }
        List<T> row = rows.get(r);
        while (row.size() <= c) {
            row.add(null);
        }
        row.set(c, value);
    }

    public void outputToCsv(File file) throws IOException {
        CsvWriter writer = new CsvWriter(file);
        try {
            for (List<T> row : rows) {
                writer.writeFields(row);
            }
        } finally {
            writer.close();
        }
    }
}
