package burst.web.errorhandle;

import burst.web.framework.RawContext;

import javax.servlet.RequestDispatcher;

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
    public void handle(Object errorContext, RawContext rawContext) {
        try {
            RequestDispatcher dispatcher = rawContext.getRequest().getRequestDispatcher(targetUrl);
            dispatcher.forward(rawContext.getRequest(), rawContext.getResponse());
        } catch (Throwable ex) { }
    }
}
