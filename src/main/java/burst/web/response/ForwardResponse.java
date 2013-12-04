package burst.web.response;

import burst.web.IResponse;
import burst.web.enums.ForwardType;
import burst.web.enums.HttpMethod;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zhongkai.zhao
 *         13-12-4 下午4:38
 */
public class ForwardResponse implements IResponse {

    private String targetUrl;

    private ForwardType forwardType;

    public String getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }

    public ForwardType getForwardType() {
        return forwardType;
    }

    public void setForwardType(ForwardType forwardType) {
        this.forwardType = forwardType;
    }

    public ForwardResponse(String targetUrl) {
        this.targetUrl = targetUrl;
        this.forwardType = ForwardType.FORWARD;
    }

    public ForwardResponse(String targetUrl, ForwardType forwardType) {
        this.targetUrl = targetUrl;
        this.forwardType = forwardType;
    }

    @Override
    public void response(HttpServletRequest request, HttpServletResponse response, HttpMethod httpMethod) throws Throwable {

        if(forwardType == ForwardType.FORWARD) {
            RequestDispatcher dispatcher = request.getRequestDispatcher(targetUrl);
            dispatcher.forward(request, response);
        } else if(forwardType == ForwardType.REDIRECT) {
            response.sendRedirect(targetUrl);
        }
    }

    @Override
    public String getErrorCode() {
        return null;
    }
}
