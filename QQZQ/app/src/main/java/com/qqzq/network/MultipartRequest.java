package com.qqzq.network;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.qqzq.entity.EntClientResponse;

import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by jie.xiao on 15/9/4.
 */
public class MultipartRequest<T> extends Request<T> {

    private static final String KEY_PICTURE = "file";
    private static final String TAG = "MutlipartRequest";

    private HttpEntity mHttpEntity;
    private final Class mClass;
    private Response.Listener mListener;
    private Map<String, String> mHeaders;
    private final Gson gson;

    public MultipartRequest(String url, File file,
                            Class clazz,
                            Map<String, String> headers,
                            ResponseListener listener) {
        super(Request.Method.POST, url, listener);
        Log.i("qqzq", url);
        mHeaders = headers;
        mClass = clazz;
        mListener = listener;
        gson = new Gson();
        mHttpEntity = buildMultipartEntity(file);
    }

    public MultipartRequest(String url, String path,
                            Class clazz,
                            Map<String, String> headers,
                            ResponseListener listener) {
        super(Request.Method.POST, url, listener);
        mHeaders = headers;
        mClass = clazz;
        mListener = listener;
        gson = new Gson();
        mHttpEntity = buildMultipartEntity(path);
    }

    private HttpEntity buildMultipartEntity(String path) {
        File file = new File(path);
        return buildMultipartEntity(file);
    }

    private HttpEntity buildMultipartEntity(File file) {
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        String fileName = file.getName();
        builder.addBinaryBody(KEY_PICTURE, file, ContentType.create("image/jpeg"), fileName);
        builder.addTextBody("filename", "logo.jpeg");
        return builder.build();
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return mHeaders != null ? mHeaders : super.getHeaders();
    }

    @Override
    public String getBodyContentType() {
        return mHttpEntity.getContentType().getValue();
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            mHttpEntity.writeTo(bos);
        } catch (IOException e) {
            VolleyLog.e("IOException writing to ByteArrayOutputStream");
        }
        return bos.toByteArray();
    }

    @Override
    protected Response parseNetworkResponse(NetworkResponse response) {
        String json;
        try {

            json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
        } catch (UnsupportedEncodingException e) {
            Log.e(TAG, String.format("Encoding problem parsing API response. NetworkResponse:%s", response.toString()), e);
            return Response.error(new ParseError(e));
        }
        try {

            Object mResponse = gson.fromJson(json, mClass);
            if (mResponse instanceof EntClientResponse && response.headers.containsKey("Location")) {
                ((EntClientResponse) mResponse).setLocation(response.headers.get("Location"));
            }

            return Response.success(mResponse, HttpHeaderParser.parseCacheHeaders(response));
        } catch (JsonSyntaxException e) {
            Log.e(TAG, String.format("Couldn't API parse JSON response. NetworkResponse:%s", response.toString()), e);
            Log.e(TAG, String.format("Couldn't API parse JSON response. Json dump: %s", json));
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(T response) {
        mListener.onResponse(response);
    }
}
