package cfvbaibai.cardfantasy.test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import cfvbaibai.cardfantasy.C;

public class CsvWriter {
    private FileWriter writer; 
    public CsvWriter(File file) throws IOException {
        writer = new FileWriter(file);
    }
    
    public void writeFields(Object[] fields) throws IOException {
        writeFields(C.toList(fields));
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
