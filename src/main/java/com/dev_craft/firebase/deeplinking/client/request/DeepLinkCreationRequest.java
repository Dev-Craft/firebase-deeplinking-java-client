package com.dev_craft.firebase.deeplinking.client.request;

import lombok.Data;

@Data
public class DeepLinkCreationRequest {

    private DynamicLinkInfo dynamicLinkInfo;

    public DeepLinkCreationRequest(String domainUriPrefix, String link, String androidPackageName, String iosBundleId) {
        this.dynamicLinkInfo = new DynamicLinkInfo(domainUriPrefix, link, androidPackageName, iosBundleId);
    }

}
