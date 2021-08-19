package com.codehospitality.deeplinking.request;

import lombok.Data;

@Data
public class AndroidInfo {
    private String androidPackageName;

    public AndroidInfo(String androidPackageName) {
        this.androidPackageName = androidPackageName;
    }

}
