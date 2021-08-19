package com.dev_craft.firebase.deeplinking.client.request;

import lombok.Data;

@Data
public class DynamicLinkInfo {

    private String domainUriPrefix;
    private String link;
    private AndroidInfo androidInfo;
    private IosInfo iosInfo;

    public DynamicLinkInfo(String domainUriPrefix, String link, String androidPackageName, String iosBundleId) {
        this.domainUriPrefix = domainUriPrefix;
        this.link = link;
        this.androidInfo = new AndroidInfo(androidPackageName);
        this.iosInfo = new IosInfo(iosBundleId);
    }
}
