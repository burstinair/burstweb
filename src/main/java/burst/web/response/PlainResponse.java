package burst.web.response;

import burst.web.framework.RawContext;

/**
 * @author Burst
 *         13-12-4 上午12:59
 */
public class PlainResponse extends BaseResponse {

    private Object data;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    protected String contentType = "text/plain; charset=UTF-8";

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    protected String getResponseString() throws Throwable {
        return data.toString();
    }

    @Override
    public void response(RawContext rawContext) throws Throwable {
        rawContext.getResponse().setContentType(contentType);
        rawContext.getResponse().getWriter().write(getResponseString());
    }
}
