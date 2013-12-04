package burst.web.errorhandle;

import burst.web.enums.HttpMethod;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author zhongkai.zhao
 *         13-12-4 下午1:47
 */
public class ForwardHandler implements IErrorHandler {

    private String targetUrl;

    public String getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }

    @Override
    public void handle(Object errorContext, HttpServletRequest request, HttpServletResponse response, HttpMethod httpMethod) {
        try {
            RequestDispatcher dispatcher = request.getRequestDispatcher(targetUrl);
            dispatcher.forward(request, response);
        } catch (Throwable ex) { }
    }
}
