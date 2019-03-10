package com.rk.mbtio;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class JsonRequestTool {

    private Context context;
    private JSONArray statusArr;

    // API URL
    private final String URL = "https://mbtio-234017.appspot.com";

    public JsonRequestTool(Context con) {
        context = con;
    }

    // JSON request
    public void JSONRequestObj(String in_url, final String requestType, JSONObject data, final VolleyObjCallback callback) {

        String url = URL + in_url;
        int type = -1;


        if (requestType.equals("GET"))
            type = Request.Method.GET;
        else if (requestType.equals("POST"))
            type = Request.Method.POST;


        JsonObjectRequest jRequest = new JsonObjectRequest
                (type, url, data, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
               //         Log.i("JSON", response.toString());
                        if (callback != null)
                          callback.onSuccess(response);

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.e("JSON", error+ "\nJSON response error");
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");
            //    params.put("secret-key", KEY);

                return params;
            }
        };

        // Access the RequestQueue through your singleton class.
        JSONSingleton.getInstance(context).addToRequestQueue(jRequest);

    }

    // JSON request
    public JSONArray JSONRequestArr(String in_url, final String requestType, JSONArray data, final VolleyArrCallback callBack) {
        String url = in_url;
        int type = -1;

        if (requestType.equals("GET"))
            type = Request.Method.GET;
        else if (requestType.equals("POST"))
            type = Request.Method.POST;


        JsonArrayRequest jRequest = new JsonArrayRequest
                (type, url, data, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i=0; i < response.length(); i++) {
                            try {
                                Log.i("JSON", response.get(i).toString());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        statusArr = response;
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.e("JSON", error+ "\nFuck ma'an we have an error");

                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");
                //    params.put("secret-key", KEY);

                return params;
            }
        };

        // Access the RequestQueue through your singleton class.
        JSONSingleton.getInstance(context).addToRequestQueue(jRequest);

        return statusArr;

    }

    public interface VolleyObjCallback {
        void onSuccess(JSONObject results);
    }

    public interface VolleyArrCallback {
        void onSuccess(JSONArray jArr);
    }

}
