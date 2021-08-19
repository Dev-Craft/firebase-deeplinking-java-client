package com.codehospitality.deeplinking.request;

import lombok.Data;

@Data
public class DeepLinkRequest {
    private DynamicLinkInfo dynamicLinkInfo;

    public DeepLinkRequest(String domainUriPrefix, String link, String androidPackageName, String iosBundleId) {
        this.dynamicLinkInfo = new DynamicLinkInfo(domainUriPrefix, link, androidPackageName, iosBundleId);
    }

}
