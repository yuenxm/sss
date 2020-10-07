package com.blueeyescloud.baselib.net;
/*
 * Copyright (C) 2015 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import android.util.Log;


import com.blueeyescloud.baselib.utils.LogUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.TypeAdapter;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;


final class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private final TypeAdapter<T> adapter;

    GsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }
    @Override
    public T convert(ResponseBody value) throws IOException {
        String body = value.string();
        LogUtils.d("GSON",body);

        BaseHttpResponse response = gson.fromJson(body, BaseHttpResponse.class);
        try {
            if (response.getReturnCode() == 1){
                JsonObject jsonObject = new JsonParser().parse(body).getAsJsonObject();
                return adapter.fromJson(jsonObject.get("content").toString());
            }else {
                throw new ServerException(response.getReturnCode(), response.getReturnMsg());
            }
        } finally {
            value.close();
        }
    }
}