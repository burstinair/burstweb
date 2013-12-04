package burst.web.requestbuilder;

import burst.web.IRequest;
import burst.web.annotations.RequestField;
import burst.web.enums.HttpMethod;
import org.joda.time.DateTime;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.text.ParseException;

/**
 * @author Burst
 *         13-12-4 上午12:10
 */
public class DefaultRequestBuilder implements IRequestBuilder {

    public static final IRequestBuilder INSTANCE = new DefaultRequestBuilder();

    private DefaultRequestBuilder() { }

    @Override
    public <REQUEST extends IRequest> REQUEST build(Class<REQUEST> requestClass, HttpServletRequest rawRequest, HttpServletResponse rawResponse, HttpMethod method) throws Throwable {
        REQUEST result = requestClass.newInstance();
        for(Field field : requestClass.getDeclaredFields()) {
            RequestField fieldAnnotation = field.getAnnotation(RequestField.class);
            if(fieldAnnotation != null) {
                field.setAccessible(true);
                field.set(result, convert(rawRequest.getParameterValues(fieldAnnotation.key()), field.getType()));
            }
        }
        return result;
    }

    private static final String INT_TRUE = "1";
    private static final String INT_FALSE = "0";
    private static final String STRING_TRUE = "true";
    private static final String STRING_FALSE = "false";

    private <T> T convert(String ori, Class<T> destType) throws IllegalAccessException, InstantiationException, ParseException {
        if(ori == null) {
            return null;
        } else if(String.class.equals(destType)) {
            return (T) ori;
        } else if(Integer.class.equals(destType)) {
            return (T) Integer.valueOf(ori);
        } else if(Long.class.equals(destType)) {
            return (T) Long.valueOf(ori);
        } else if(Double.class.equals(destType)) {
            return (T) Double.valueOf(ori);
        } else if(Float.class.equals(destType)) {
            return (T) Float.valueOf(ori);
        } else if(Short.class.equals(destType)) {
            return (T) Short.valueOf(ori);
        } else if(Byte.class.equals(destType)) {
            return (T) Byte.valueOf(ori);
        } else if(Boolean.class.equals(destType)) {
            if (INT_TRUE.equals(ori)) {
                return (T) Boolean.TRUE;
            } else if (INT_FALSE.equals(ori)) {
                return (T) Boolean.FALSE;
            } else if (STRING_TRUE.equalsIgnoreCase(ori)) {
                return (T) Boolean.TRUE;
            } else if (STRING_FALSE.equalsIgnoreCase(ori)) {
                return (T) Boolean.FALSE;
            }
        } else if(DateTime.class.equals(destType)) {
            return (T) new DateTime(ori);
        }
        throw new RuntimeException("cannot convert " + ori + " to " + destType);
    }

    private <T> T convert(String[] ori, Class<T> destType) throws IllegalAccessException, InstantiationException, ParseException {
        T result = null;
        if(destType.isArray()) {
            if(ori == null) {
                return (T) Array.newInstance(destType.getComponentType(), 0);
            }
            result = (T) Array.newInstance(destType.getComponentType(), ori.length);
            for(int i = 0; i < ori.length; ++i) {
                Array.set(result, i, convert(ori[i], destType.getComponentType()));
            }
        } else {
            if(ori == null) {
                return null;
            }
            if(ori.length > 0) {
                result = convert(ori[0], destType);
            }
        }
        return result;
    }
}
