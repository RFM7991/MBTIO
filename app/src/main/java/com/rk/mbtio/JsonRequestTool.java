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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class JsonRequestTool {

    private Context context;
    private JSONArray statusArr;

    GlobalSingleton gg;

    // API URL
    private final String URL = "https://mbtio-234017.appspot.com";

    public JsonRequestTool(Context con) {
        context = con;
    }

    // test POST for send message
    public void sendMessage(int sid, int rid, int pin, int num, String message) {

        JSONObject data = new JSONObject();

        try {
            data.put("sid", sid);
            data.put("rid", rid);
            data.put("num", num);
            data.put("pin", pin);
            data.put("message", message);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONRequestObj("/messages/send", "POST", data, new JsonRequestTool.VolleyObjCallback() {
            @Override
            public void onSuccess(JSONObject results) {

                Log.d("JSON", results.toString());
            }
        });
    }

    // test POST for get messages
    public void getMessages(int sid, int pin) {
        JSONObject data = new JSONObject();

        try {
            data.put("sid", sid);
            data.put("pin", pin);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONRequestObj("/messages/get", "POST", data, new JsonRequestTool.VolleyObjCallback() {
            @Override
            public void onSuccess(JSONObject results) {
                Log.d("JSON", results.toString());
            }
        });
    }

    // test POST for sending twilio text
    public void sendTwilio(String phone) {
        JSONObject data = new JSONObject();

        try {
            data.put("phone", phone);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONRequestObj("/twilio/send", "POST", data, new JsonRequestTool.VolleyObjCallback() {
            @Override
            public void onSuccess(JSONObject results) {
                Log.d("JSON", results.toString());
            }
        });
    }


    // test POST for sending twilio text
    public void verifyTwilio(String phone, int twilioPin) {
        JSONObject data = new JSONObject();

        try {
            data.put("phone", phone);
            data.put("twiliopin", twilioPin);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONRequestObj("/twilio/verify", "POST", data, new JsonRequestTool.VolleyObjCallback() {
            @Override
            public void onSuccess(JSONObject results) {
                Log.d("JSON", results.toString());
            }
        });
    }

    // test POST for sending twilio text
    public void getMatches(GlobalSingleton g, int uid, int pin, int max) {
        JSONObject data = new JSONObject();
        gg = g;

        try {
            data.put("uid", uid);
            data.put("pin", pin);
            data.put("max", max);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONRequestObj("/matches/get", "POST", data, new JsonRequestTool.VolleyObjCallback() {
            @Override
            public void onSuccess(JSONObject results) {
                try {
                   gg.addMatches( ParseMatches(results));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            }
        });
    }

    // test POST for gettng all profiles
    public void getAllProfiles(int uid, int pin) {
        JSONObject data = new JSONObject();

        JSONRequestObj("/profile/all", "POST", data, new JsonRequestTool.VolleyObjCallback() {
            @Override
            public void onSuccess(JSONObject results) {
                Log.d("JSON", results.toString());
            }
        });
    }

    // test POST for sending twilio text
    public void updateProfile(int uid, int pin, HashMap<String, String> strings, HashMap<String,Float> floats, HashMap<String, Integer> ints) {
        JSONObject data = new JSONObject();

        try {
            if (floats.containsKey("lat")) {
                data.put("lat", floats.get("lat"));
            }

            if (floats.containsKey("long")) {
                data.put("long", floats.get("long"));
            }


            if (ints.containsKey("age")) {
                data.put("age", floats.get("age"));
            }

            if (ints.containsKey("height")) {
                data.put("height", floats.get("height"));
            }


            if (strings.containsKey("bio")) {
                data.put("bio", floats.get("bio"));
            }

            if (strings.containsKey("mbti")) {
                data.put("mbti", floats.get("mbti"));
            }

            if (strings.containsKey("sex")) {
                data.put("sex", strings.get("sex"));
            }

            if (strings.containsKey("interest")) {
                data.put("interest", strings.get("interest"));
            }

            if (strings.containsKey("name")) {
                data.put("name", strings.get("name"));
            }

            data.put("uid", uid);
            data.put("pin", pin);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONRequestObj("/profile/update", "POST", data, new JsonRequestTool.VolleyObjCallback() {
            @Override
            public void onSuccess(JSONObject results) {
                try {
                    ParseMatches(results);
                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }
        });
    }

    public ArrayList<User> ParseMatches(JSONObject json) throws JSONException {
        JSONArray jsonMainArr = json.getJSONArray("M");

        ArrayList<User> us = new ArrayList<User>();
        for (int i = 0; i < jsonMainArr.length(); i++) {  // **line 2**
            JSONObject childJSONObject = jsonMainArr.getJSONObject(i);
            User u = new User();


            u.name = childJSONObject.getString("Name");
            u.age = childJSONObject.getInt("Age");
            u.mbti = childJSONObject.getString("MBTI");
            u.height = childJSONObject.getInt("Height");
            u.bio = childJSONObject.getString("Bio");
            u.score = childJSONObject.getInt("Score");
            u.distance = childJSONObject.getInt("Distance");

            us.add(u);

        }

        return us;
    }


    // test POST for sending twilio text
    public void checkProfileReady(int uid, int pin) {
        JSONObject data = new JSONObject();

        try {
            data.put("uid", uid);
            data.put("pin", pin);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONRequestObj("/profile/ready", "POST", data, new JsonRequestTool.VolleyObjCallback() {
            @Override
            public void onSuccess(JSONObject results) {
                Log.d("JSON", results.toString());
            }
        });
    }

    // test POST for getting self-user data
    public void getSelf(int uid, int pin) {
        JSONObject data = new JSONObject();

        try {
            data.put("uid", uid);
            data.put("pin", pin);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONRequestObj("/profile/self", "POST", data, new JsonRequestTool.VolleyObjCallback() {
            @Override
            public void onSuccess(JSONObject results) {
                Log.d("JSON", results.toString());
            }
        });
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
