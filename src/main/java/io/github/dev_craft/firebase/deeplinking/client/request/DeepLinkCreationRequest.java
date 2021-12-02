package io.github.dev_craft.firebase.deeplinking.client.request;

import lombok.Data;

@Data
public class DeepLinkCreationRequest {

    private DynamicLinkInfo dynamicLinkInfo;

    public DeepLinkCreationRequest(String domainUriPrefix,
                                   String link,
                                   String androidPackageName,
                                   String iosBundleId,
                                   String iosAppStoreId) {
        this.dynamicLinkInfo = new DynamicLinkInfo(domainUriPrefix, link, androidPackageName, iosBundleId, iosAppStoreId);
    }
}
