package burst.web.requestbuilder;

import burst.web.IRequest;
import burst.web.framework.RawContext;

/**
 * @author Burst
 *         13-12-4 上午12:07
 */
public interface IRequestBuilder {

    <REQUEST extends IRequest> REQUEST build(Class<REQUEST> requestClass, RawContext rawContext) throws Throwable;
}
