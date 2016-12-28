package com.javalong.rrdm.retrofit;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by javalong on 2016/6/7.
 */
public class TWGsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {

    private final Gson gson;
    private final TypeAdapter<T> adapter;
    private final TypeToken<?> typeToken;

    TWGsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter, TypeToken<?> typeToken) {
        this.gson = gson;
        this.adapter = adapter;
        this.typeToken = typeToken;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        Object obj = checkBasicType(value);
        if (obj != null) return (T) obj;
        JsonReader jsonReader = gson.newJsonReader(value.charStream());
        try {
            return adapter.read(jsonReader);
        } finally {
            value.close();
        }
    }

    /**
     * 如果是基本类型就转化后，返回
     */
    private Object checkBasicType(ResponseBody value) throws IOException {
        String typeName = typeToken.getRawType().getSimpleName();
        if ("String".equals(typeName)) {
            return value.string();
        } else if ("Boolean".equals(typeName)) {
            return Boolean.parseBoolean(value.string());
        } else if ("Integer".equals(typeName)) {
            return Integer.parseInt(value.string());
        }
        return null;
    }
}
