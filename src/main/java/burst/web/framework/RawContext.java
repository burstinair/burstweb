package burst.web.framework;

import burst.web.enums.HttpMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zhongkai.zhao
 *         13-12-4 下午7:10
 */
public class RawContext {

    private HttpServletRequest request;

    private HttpServletResponse response;

    private HttpMethod httpMethod;

    public RawContext() { }

    public RawContext(HttpServletRequest request, HttpServletResponse response, HttpMethod httpMethod) {
        this.request = request;
        this.response = response;
        this.httpMethod = httpMethod;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(HttpMethod httpMethod) {
        this.httpMethod = httpMethod;
    }
}
