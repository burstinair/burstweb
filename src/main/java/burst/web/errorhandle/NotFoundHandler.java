package burst.web.errorhandle;

import burst.web.enums.HttpMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Burst
 *         13-12-4 上午12:13
 */
public class NotFoundHandler implements IErrorHandler {

    public static final NotFoundHandler INSTANCE = new NotFoundHandler();

    private NotFoundHandler() { }

    @Override
    public void handle(Object errorContext, HttpServletRequest request, HttpServletResponse response, HttpMethod httpMethod) {
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
    }
}
