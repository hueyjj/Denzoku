package com.hueyjj.denzoku.network;

import android.content.Context;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class NyaaNetworkRequest {

    private String apiUrl;
    private String query, sort;
    private int pageNum;

    private RequestQueue queue;
    private StringRequest nyaaRequest;

    public NyaaNetworkRequest(Context context) {
        this.queue = Volley.newRequestQueue(context);

        // Default settings for now
        this.pageNum = 0;
        this.sort = "seeders";

        System.out.println("api url: " + this.apiUrl);
    }

    public String getNyaaList() {
        this.queue.add(this.nyaaRequest);
        return null;
    }
    
    public void setPageNum(int num) {
        this.pageNum = num;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public void setSort(String sort) {
        // TODO sort = SOME_CONSTANT_LIKE_BY_SEED
        this.sort = sort;
    }

    public void build() {
        this.apiUrl = this.buildAPILink();

        this.nyaaRequest = new StringRequest(Request.Method.GET, this.apiUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        //mTextView.setText("Response is: "+ response.substring(0,500));
                        System.out.println(response.length());
                        System.out.println(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //mTextView.setText("That didn't work!");
                        System.out.println("Failed to get nyaa list");
                        System.out.println(error.getMessage());
                    }
                });
    }

    private String buildAPILink() {
        String[] params = new String[] {
                "q=" + this.query,
                "page=rss",
                "p=" + this.pageNum,
                "s=" + this.sort,
        };

        String param = TextUtils.join("&", params);

        String baseUrl = "https://nyaa.si/?";
        System.out.println(baseUrl + param);

        return baseUrl + param;
    }
}
