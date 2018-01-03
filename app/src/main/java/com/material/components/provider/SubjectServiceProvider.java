package com.material.components.provider;


import android.content.Context;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.material.components.AppController;
import com.material.components.activity.subject.Subjects;
import com.material.components.config.AppConfig;
import com.material.components.helper.JSONReaderHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class SubjectServiceProvider {

    private static final String TAG = SubjectServiceProvider.class.getSimpleName();


    public static List<Subjects> loadSubjects(){

        return null;
    }
}
