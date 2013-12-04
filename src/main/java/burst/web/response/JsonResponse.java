package burst.web.response;

import burst.web.annotations.ResponseField;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import org.joda.time.DateTime;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.Map;

/**
 * @author zhongkai.zhao
 *         13-12-4 下午2:38
 */
public class JsonResponse extends PlainResponse {

    public JsonResponse() {
        contentType = "application/json; charset=UTF-8";
    }

    @Override
    protected String getResponseString() throws IllegalAccessException {
        return transformToJSON(getData()).toString();
    }

    private Object transformToJSON(Object data) throws IllegalAccessException, JSONException {
        Class clazz = data.getClass();
        if(
            JSONObject.class.isAssignableFrom(clazz) ||
            JSONArray.class.isAssignableFrom(clazz) ||
            String.class.equals(clazz) ||
            Integer.class.equals(clazz) ||
            Short.class.equals(clazz) ||
            Byte.class.equals(clazz) ||
            Long.class.equals(clazz) ||
            Double.class.equals(clazz) ||
            Float.class.equals(clazz) ||
            Boolean.class.equals(clazz)
        ) {
            return data;
        } else if(Date.class.equals(clazz)) {
            return new DateTime(data).toString("yyyy-MM-dd");
        } else if(DateTime.class.equals(clazz)) {
            return ((DateTime) data).toString("yyyy-MM-dd");
        } else if(clazz.isArray() || Iterable.class.isAssignableFrom(clazz)) {
            JSONArray result = new JSONArray();
            for(Object obj : (Iterable) data) {
                result.add(transformToJSON(obj));
            }
            return result;
        } else if(Map.class.isAssignableFrom(clazz)) {
            return new JSONObject((Map) data);
        } else {
            JSONObject result = new JSONObject();
            for(Field field : clazz.getDeclaredFields()) {
                ResponseField fieldAnnotation = field.getAnnotation(ResponseField.class);
                if(fieldAnnotation != null) {
                    field.setAccessible(true);
                    result.put(fieldAnnotation.key(), transformToJSON(field.get(data)));
                }
            }
            return result;
        }
    }
}
