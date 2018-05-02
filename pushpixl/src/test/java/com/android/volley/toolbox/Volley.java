package com.android.volley.toolbox;

import android.content.Context;

import com.android.volley.RequestQueue;

/**
 * Created by Florian ALONSO on 5/2/18.
 * For Neopixl
 */
public class Volley {
    private static final String DEFAULT_CACHE_DIR = "volley";

    public Volley() {
    }

    public static RequestQueue newRequestQueue(Context context, HttpStack stack) {
        return null;
    }

    public static RequestQueue newRequestQueue(Context context) {
        return newRequestQueue(context, (HttpStack)null);
    }
}
