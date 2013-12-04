package burst.web.response;

import burst.web.enums.HttpMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * TODO
 * @author zhongkai.zhao
 *         13-12-4 下午4:48
 */
public class ByteResponse extends BaseResponse {

    private byte[] data;

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    @Override
    public void response(HttpServletRequest request, HttpServletResponse response, HttpMethod httpMethod) throws Throwable {

    }
}
