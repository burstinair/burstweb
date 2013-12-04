package burst.web.response;

import burst.web.IResponse;
import burst.web.enums.ForwardType;
import burst.web.framework.RawContext;

import javax.servlet.RequestDispatcher;

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
    public void response(RawContext rawContext) throws Throwable {

        if(forwardType == ForwardType.FORWARD) {
            RequestDispatcher dispatcher = rawContext.getRequest().getRequestDispatcher(targetUrl);
            dispatcher.forward(rawContext.getRequest(), rawContext.getResponse());
        } else if(forwardType == ForwardType.REDIRECT) {
            rawContext.getResponse().sendRedirect(targetUrl);
        }
    }

    @Override
    public String getErrorCode() {
        return null;
    }
}
