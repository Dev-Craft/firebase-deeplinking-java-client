package com.dev_craft.firebase.deeplinking.client.response;

import lombok.Data;

import java.util.List;

@Data
public class DeepLinkCreationResponse {

    private String shortLink;
    private List<Warning> warning;
    private String previewLink;
}
