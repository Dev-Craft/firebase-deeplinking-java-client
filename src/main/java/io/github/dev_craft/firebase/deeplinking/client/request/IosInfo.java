package io.github.dev_craft.firebase.deeplinking.client.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class IosInfo {

    private String iosBundleId;
    private String iosAppStoreId;
}
