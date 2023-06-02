package com.icg.QuerySwitch;

import java.util.Base64;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

@Component
public class SecurityTools {
    public String getIssuerFromToken(String token){
        token=token.replace("Bearer ","");
        String[] chunks = token.split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();
        String payload = new String(decoder.decode(chunks[1]));
        JSONObject jsonpayload=new JSONObject(payload);
        String issuer=jsonpayload.getString("iss");
        return issuer;
    }
    public String getPreferred_username(String token){
        token=token.replace("Bearer ","");
        String[] chunks = token.split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();
        String payload = new String(decoder.decode(chunks[1]));
        JSONObject jsonpayload=new JSONObject(payload);
        String preferedusername=jsonpayload.getString("preferred_username");
        return preferedusername;
    }
}