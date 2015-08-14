package cfvbaibai.cardfantasy.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.junit.Assert;
import org.junit.Test;

import cfvbaibai.cardfantasy.Base64Encoder;
import cfvbaibai.cardfantasy.Compressor;

public class VideoRecordTest {
    @Test
    public void testZip() throws IOException {
        String s = "Test Zip String中文";
        byte[] data = Compressor.compress(s);
        String decompressed = Compressor.decompress(data);
        System.out.println("Decompressed = " + decompressed);
        Assert.assertEquals(s, decompressed);
    }
    
    @Test
    public void testBase64Encoding() throws IOException {
        String s = "Test 中文";
        byte[] bin = s.getBytes();
        String encoded = Base64Encoder.encode(bin);
        System.out.println("Encoded: " + encoded);
        byte[] decoded = Base64Encoder.decode(encoded);
        String decodedText = new String(decoded);
        System.out.println("Decoded Text: " + decodedText);
        Assert.assertEquals(s, decodedText);
    }

    @Test
    public void testZipAndEncode() throws IOException {
        BufferedReader reader = new BufferedReader(
            new InputStreamReader(
                VideoRecordTest.class.getResourceAsStream("/cfvbaibai/cardfantasy/test/sample-video.txt")));
        StringBuffer sb = new StringBuffer();
        String line = null;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        String videoText = sb.toString();
        String encodedText = Base64Encoder.encode(Compressor.compress(videoText));
        System.out.println("Original length: " + videoText.length());
        System.out.println("Encoded length: " + encodedText.length());
        System.out.println("Encoded: " + encodedText);
        String decodedVideoText = Compressor.decompress(Base64Encoder.decode(encodedText));
        Assert.assertEquals(videoText, decodedVideoText);
    }
}
