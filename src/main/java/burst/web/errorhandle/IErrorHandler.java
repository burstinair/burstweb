package burst.web.errorhandle;

import burst.web.framework.RawContext;

/**
 * @author Burst
 *         13-12-4 上午12:12
 */
public interface IErrorHandler {

    void handle(Object errorContext, RawContext rawContext);
}
