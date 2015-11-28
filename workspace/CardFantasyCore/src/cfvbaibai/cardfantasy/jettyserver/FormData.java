package cfvbaibai.cardfantasy.jettyserver;

import javax.servlet.http.HttpServletRequest;

public class FormData {
    private HttpServletRequest request;
    public FormData(HttpServletRequest request) {
        this.request = request;
    }
    
    public String getNonNullString(String name) {
        String value = request.getParameter(name);
        if (value == null) {
            throw new IllegalArgumentException("The parameter " + name + " does not exist");
        }
        return value;
    }

    public String getString(String name) {
        return request.getParameter(name);
    }

    public int getInt(String name) {
        String value = request.getParameter(name);
        if (value == null) {
            throw new IllegalArgumentException("The parameter " + name + " does not exist");
        }
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("The parameter " + name + " does not contain an integer value", e);
        }
    }
}
