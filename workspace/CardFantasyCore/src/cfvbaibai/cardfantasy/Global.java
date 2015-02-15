package cfvbaibai.cardfantasy;

import java.io.File;

public class Global {
    public static boolean isValidationDisabled() {
        return new File("C:\\Temp\\mkhx.validation.disable").exists();
    }
}
