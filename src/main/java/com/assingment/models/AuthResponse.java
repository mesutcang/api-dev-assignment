package com.assingment.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class AuthResponse {
    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("partner_name")
    private String partnerName;
    @JsonProperty("partner_id")
    private String partnerId;
    private String expires;
    @JsonProperty("token_type")
    private String tokenType;

}
