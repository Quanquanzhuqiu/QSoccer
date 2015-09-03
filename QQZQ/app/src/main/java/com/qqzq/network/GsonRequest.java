package com.qqzq.network;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.qqzq.entity.RequestJsonParameter;
import com.qqzq.util.json.DateDeserializer;
import com.qqzq.util.json.DateSerializer;
import com.qqzq.util.json.ObjectDeserializer;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;

/**
 * Created by jie.xiao on 15/8/29.
 */
public class GsonRequest<T> extends Request<T> {

    private final Gson mGson;
    private final Class<T> mClazz;
    private final Response.Listener<T> mListener;
    private final Map<String, String> mHeaders;
    private final RequestJsonParameter mParameters;

    public GsonRequest(String url, Class<T> clazz, Response.Listener<T> listener, Response.ErrorListener errorListener) {
        this(Method.GET, url, clazz, null, null, listener, errorListener);
    }

    public GsonRequest(int method, String url, Class<T> clazz, Map<String, String> headers, RequestJsonParameter parameters,
                       Response.Listener<T> listener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);

        this.mClazz = clazz;
        this.mHeaders = headers;
        this.mParameters = parameters;
        this.mListener = listener;

        // Gson init
        this.mGson = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateSerializer())
                .registerTypeAdapter(Date.class, new DateDeserializer())
                .registerTypeAdapter(Object.class,
                        new ObjectDeserializer(mClazz.getName()))
                .create();
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse networkResponse) {
        try {
            System.out.println("response header ==>" + networkResponse.headers);
            String json = new String(networkResponse.data, HttpHeaderParser.parseCharset(networkResponse.headers));
            System.out.println("response json ==>" + json);
            return Response.success(mGson.fromJson(json, mClazz),
                    HttpHeaderParser.parseCacheHeaders(networkResponse));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected VolleyError parseNetworkError(VolleyError volleyError) {
        NetworkResponse networkResponse= volleyError.networkResponse;
        int statusCode = networkResponse.statusCode;
        System.out.println(statusCode);

        if(statusCode==400){
            return new VolleyError(new String(networkResponse.data));
        }

        return super.parseNetworkError(volleyError);
    }

    @Override
    protected void deliverResponse(T response) {
        mListener.onResponse(response);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return mHeaders != null ? mHeaders : super.getHeaders();
    }

    @Override
    public String getBodyContentType() {
        return "application/json";
    }

    @Override
    public byte[] getBody() throws AuthFailureError {


        try {
            Object object = mParameters.getParameter();
            String json = mGson.toJson(object);

            if (json != null) {
                System.out.println("request json ==>" + json);
                return json.getBytes(getParamsEncoding());
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }
}
