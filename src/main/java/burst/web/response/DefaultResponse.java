package burst.web.response;

import burst.web.IResponse;
import burst.web.enums.HttpMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Burst
 *         13-12-4 上午12:59
 */
public class DefaultResponse implements IResponse {

    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    private static final String CONTENT_TYPE = "text/html";

    @Override
    public void response(HttpServletRequest request, HttpServletResponse response, HttpMethod httpMethod) {
        response.setContentType(CONTENT_TYPE);
        //TODO response.getWriter();
    }
}
