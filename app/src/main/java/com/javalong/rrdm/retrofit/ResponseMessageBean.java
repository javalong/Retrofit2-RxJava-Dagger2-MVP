package com.javalong.rrdm.retrofit;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ygh
 *         2015/4/27
 *         <p/>
 *         请求数据内容
 */
public class ResponseMessageBean implements Serializable {
    public Object moreInfo = null;
    public Object data = null;
    public String error = null;
    public Integer errorCode = 200;//默认为200  是成功


    public static final String ERROR_CODE="errorCode";
    public static final String MORE_INFO="moreInfo";
    public static final String DATA="data";
    public static final String ERROR="error";

    /**
     * @return
     * @author ygh
     * <p/>
     * 2015/4/27
     * <p/>
     * 解析参数，主要的解析步骤还是需要在具体的requestSuccess方法里做解析。
     */
    public static ResponseMessageBean analyseReponse(String response) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(response);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(jsonObject==null)return null;
        ResponseMessageBean responseMessage = new ResponseMessageBean();
        try {
            if (jsonObject.has(ERROR)) {
                responseMessage.error = jsonObject.getString(ERROR);
            }
            if (jsonObject.has(ERROR_CODE)) {
                responseMessage.errorCode = jsonObject.getInt(ERROR_CODE);
            }
            if (jsonObject.has(DATA)) {
                responseMessage.data = jsonObject.get(DATA);
            }
            if (jsonObject.has(MORE_INFO)) {
                responseMessage.moreInfo = jsonObject.get(MORE_INFO);
            }
            jsonObject = null;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return responseMessage;
    }
}
