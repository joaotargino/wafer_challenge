package com.moolajoo.waferchallenge.network;

import com.moolajoo.waferchallenge.model.Country;

import java.util.List;

public interface OnTaskCompleted {
    void onTaskCompleted(List<Country> countries);
}