package cfvbaibai.cardfantasy.test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvWriter {
    private static <T> List<T> toList(T[] array) {
        List <T> result = new ArrayList <T>();
        for (int i = 0; i < array.length; ++i) {
            result.add(array[i]);
        }
        return result;
    }
    
    private FileWriter writer; 
    public CsvWriter(File file) throws IOException {
        writer = new FileWriter(file);
    }
    
    public void writeFields(Object[] fields) throws IOException {
        writeFields(toList(fields));
    }
    
    public void writeFields(List <? extends Object> fields) throws IOException {
        for (Object field : fields) {
            if (field == null) {
                field = "<NULL>";
            }
            String text = field.toString().replace("\"", "\"\"");
            writer.write("\"");
            writer.write(text);
            writer.write("\"");
            writer.write(",");
        }
        writer.write(System.lineSeparator());
    }
    
    public void close() throws IOException {
        this.writer.close();
    }
}
