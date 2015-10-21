package cfvbaibai.cardfantasy.web;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import cfvbaibai.cardfantasy.web.beans.Logger;

public class SimpleUsageRecorder {
    private String dataFolderPath;
    private String dataFilePrefix;
    private Logger logger;
    public SimpleUsageRecorder(String dataFolderPath, String dataFilePrefix, Logger logger) throws IOException {
        if (dataFolderPath == null) {
            throw new IllegalArgumentException("dataFolderPath cannot be null");
        }
        if (dataFilePrefix == null) {
            throw new IllegalArgumentException("dataFilePrefix cannot be null");
        }
        this.logger = logger;
        this.dataFilePrefix = dataFilePrefix;
        this.dataFolderPath = dataFolderPath;
        File dataFolder = new File(dataFolderPath);
        if (dataFolder.exists()) {
            if (dataFolder.isFile()) {
                throw new IOException("dataFolderPath " + dataFolderPath + " points to a file!");
            }
        } else {
            dataFolder.mkdirs();
        }
    }

    public synchronized void recordIncrement(String key) {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        String currentDate = df.format(new Date());
        String currentDataFilePath = this.dataFolderPath + "/" + this.dataFilePrefix + "_" + currentDate + ".csv";
        File file = new File(currentDataFilePath);
        boolean fileExists = file.exists();
        try {
            FileOutputStream out = new FileOutputStream(currentDataFilePath, true);
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(out, "UTF-8"));
            if (!fileExists) {
                // UTF-8 BOM
                out.write(new byte[] { (byte) 0xEF, (byte) 0xBB, (byte) 0xBF });
            }
            writer.println(key);
            writer.close();
        } catch (Exception e) {
            logger.error(e);
        }
    }
}
