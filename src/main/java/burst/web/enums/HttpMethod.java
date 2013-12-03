package burst.web.enums;

/**
 * @author Burst
 *         13-12-3 下午11:51
 */
public enum HttpMethod {

    UNKNOWN(""),
    GET("GET"),
    POST("POST"),
    PUT("PUT"),
    DELETE("DELETE");

    private String value;

    HttpMethod(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public HttpMethod getInstance(String value) {
        for(HttpMethod result : values()) {
            if(result.value.equals(value)) {
                return result;
            }
        }
        return HttpMethod.UNKNOWN;
    }
}
