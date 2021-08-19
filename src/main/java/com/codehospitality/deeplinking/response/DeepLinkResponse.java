package com.codehospitality.deeplinking.response;

import lombok.Data;

import java.util.List;

@Data
public class DeepLinkResponse {
    private String shortLink;
    private List<Warning> warning;
    private String previewLink;
}
