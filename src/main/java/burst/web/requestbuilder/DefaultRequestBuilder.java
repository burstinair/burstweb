package burst.web.requestbuilder;

import burst.web.IRequest;
import burst.web.enums.HttpMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Burst
 *         13-12-4 上午12:10
 */
public class DefaultRequestBuilder implements IRequestBuilder {

    public static final IRequestBuilder INSTANCE = new DefaultRequestBuilder();

    private DefaultRequestBuilder() { }

    @Override
    public IRequest build(HttpServletRequest rawRequest, HttpServletResponse rawResponse, HttpMethod method) {
        return null;
    }
}
