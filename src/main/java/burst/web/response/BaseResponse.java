package burst.web.response;

import burst.web.IResponse;

/**
 * @author zhongkai.zhao
 *         13-12-4 下午4:49
 */
public abstract class BaseResponse implements IResponse {

    private String errorCode;

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public String getErrorCode() {
        return this.errorCode;
    }
}
