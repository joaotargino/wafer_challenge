package com.moolajoo.waferchallenge.network;

import org.json.JSONObject;

import java.util.List;

public interface OnTaskCompleted {
    void onTaskCompleted(List<JSONObject> countries);
}