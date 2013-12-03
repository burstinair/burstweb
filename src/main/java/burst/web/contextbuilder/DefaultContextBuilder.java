package burst.web.contextbuilder;

import burst.web.IContext;
import burst.web.enums.HttpMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Burst
 *         13-12-4 上午12:32
 */
public class DefaultContextBuilder implements IContextBuilder {

    public static final DefaultContextBuilder INSTANCE = new DefaultContextBuilder();

    private DefaultContextBuilder() { }

    @Override
    public IContext build(HttpServletRequest request, HttpServletResponse response, HttpMethod httpMethod) {
        return null;
    }
}
