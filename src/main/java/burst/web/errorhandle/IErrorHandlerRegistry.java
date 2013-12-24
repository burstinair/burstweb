package burst.web.errorhandle;

import java.util.Map;

/**
 * @author Burst
 *         13-12-4 上午12:14
 */
public interface IErrorHandlerRegistry {

    void setErrorHandlerMap(Map<String, IErrorHandler> errorHandlerMap);
}
