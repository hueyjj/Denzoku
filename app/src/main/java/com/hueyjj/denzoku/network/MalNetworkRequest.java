package com.hueyjj.denzoku.network;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hueyjj.denzoku.parser.MalEntry;
import com.hueyjj.denzoku.parser.MalParser;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.List;

public class MalNetworkRequest {

    final String TAG = "MalNetworkRequest";

    private String apiUrl;
    private RequestQueue queue;
    private StringRequest animeListRequest;

    public MalNetworkRequest(Context context, String username) {
        System.out.println("username: " + username);
        this.queue = Volley.newRequestQueue(context);
        this.apiUrl = buildAPILink(username);
        System.out.println("api url: " + this.apiUrl);
        this.animeListRequest = new StringRequest(Request.Method.GET, this.apiUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        //mTextView.setText("Response is: "+ response.substring(0,500));
                        //System.out.println(response.length());
                        //System.out.println(response);
                        MalParser parser = new MalParser();
                        try {
                            List<MalEntry> result = parser.parse(response);
                            for (MalEntry entry : result) {
                                Log.v(TAG, entry.toString());
                            }
                        } catch (XmlPullParserException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //mTextView.setText("That didn't work!");
                        System.out.println("Failed to get anime list");
                        System.out.println(error.getMessage());
                    }
                });
    }

    public String getAnimeList() {
        this.queue.add(this.animeListRequest);
        return null;
    }

    private String buildAPILink(String username) {
        String userParam = "u=" + username;
        String statusParam = "status=all";
        String typeParam = "type=anime";

        String param = TextUtils.join("&", new String[]{userParam, statusParam, typeParam});

        String baseUrl = "https://myanimelist.net/malappinfo.php?";

        return baseUrl + param;
    }
}
