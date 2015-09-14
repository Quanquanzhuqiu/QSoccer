package com.qqzq.network;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.qqzq.activity.BaseApplication;
import com.qqzq.config.Constants;
import com.qqzq.util.json.DateDeserializer;
import com.qqzq.util.json.DateSerializer;
import com.qqzq.util.json.ObjectDeserializer;

import java.util.BitSet;
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
    private final Map<String, Object> mParameters;

    private static final String TAG = "GsonRequest";
    private static final String PROTOCOL_CHARSET = "utf-8";
    private static final String PROTOCOL_CONTENT_TYPE = String.format(
            "application/json; charset=%s", PROTOCOL_CHARSET);

    public GsonRequest(String url, Class<T> clazz, ResponseListener listener) {
        this(Method.GET, url, clazz, null, null, listener);
    }

    public GsonRequest(int method, String url, Class<T> clazz, Map<String, String> headers, Map<String, Object> parameters,
                       ResponseListener listener) {
        super(method, url, listener);
        Log.i(TAG, url);
        this.mClazz = clazz;
        this.mHeaders = headers;
        this.mParameters = parameters;
        this.mListener = listener;
        setShouldCache(false);

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

            System.out.println(networkResponse.headers);
            if (networkResponse.headers.containsKey(Constants.HTTP_HEADER_TOKER)) {
                String token = networkResponse.headers.get(Constants.HTTP_HEADER_TOKER);
                Log.v(TAG, "TOKEN = " + token);
                BaseApplication.QQZQ_TOKENT = token;
            }


            String json = new String(networkResponse.data, HttpHeaderParser.parseCharset(networkResponse.headers));
            Log.v(TAG, json);
            System.out.println(json);

            return Response.success(mGson.fromJson(json, mClazz),
                    HttpHeaderParser.parseCacheHeaders(networkResponse));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected VolleyError parseNetworkError(VolleyError volleyError) {
        if (volleyError.networkResponse != null && volleyError.networkResponse.data != null) {
            System.out.println(new String(volleyError.networkResponse.data));
            VolleyError error = new VolleyError(new String(volleyError.networkResponse.data));
            volleyError = error;
        }
        return volleyError;
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
        return PROTOCOL_CONTENT_TYPE;
    }

    @Override
    public byte[] getBody() throws AuthFailureError {

        try {
            if (mParameters != null) {
                Object object = mParameters.get(Constants.GSON_REQUST_POST_PARAM_KEY);
                String json = mGson.toJson(object);
                Log.i(TAG, json);
                System.out.println("Request json =>" + json);
                if (json != null) {
                    return json.getBytes(getParamsEncoding());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, e.getMessage());
        }

        return null;
    }
}
