package com.codehospitality.deeplinking.request;

import lombok.Data;

@Data
public class IosInfo {
    private String iosBundleId;

    public IosInfo(String iosBundleId) {
        this.iosBundleId = iosBundleId;
    }
}
