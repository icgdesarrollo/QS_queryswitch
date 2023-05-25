package com.icg.QuerySwitch;


import java.io.InputStream;
import java.io.StringWriter;
import java.util.concurrent.ConcurrentHashMap;

import org.keycloak.adapters.KeycloakConfigResolver;
import org.keycloak.adapters.KeycloakDeployment;
import org.keycloak.adapters.KeycloakDeploymentBuilder;
import org.keycloak.adapters.OIDCHttpFacade;
import org.keycloak.representations.adapters.config.AdapterConfig;

/**
 * @author Edward P. Legaspi | czetsuya@gmail.com
 */
public class PathBasedConfigResolver implements KeycloakConfigResolver {

    private final ConcurrentHashMap<String, KeycloakDeployment> cache = new ConcurrentHashMap<>();

    @SuppressWarnings("unused")
    private static AdapterConfig adapterConfig;

    @Override
    public KeycloakDeployment resolve(OIDCHttpFacade.Request request) {

        String path = request.getURI();
        int multitenantIndex = path.indexOf("tenant/");

/*        if (multitenantIndex == -1) {
            throw new IllegalStateException("Not able to resolve realm from the request path!");
        }
*/
        String realm="";
        
        System.out.println("checking realm");
       
        if(path.indexOf("tenant/")==-1) {
        	realm="legacy";
        }else {
        	realm = path.substring(path.indexOf("tenant/")).split("/")[1];
            
        }
        
        
        if (realm.contains("?")) {
            realm = realm.split("\\?")[0];
        }
        System.out.println("checking realm"+realm);
        if (!cache.containsKey(realm)) {
         //   InputStream is = getClass().getResourceAsStream("/" + realm + "-keycloak.json");
        	//realm="indlgtgc";
        	System.out.println(realm);
        	
        	InputStream is = getClass().getResourceAsStream("/" + realm + "-realm.json");
            System.out.println("config file loaded");       
            StringWriter writer = new StringWriter();
        	cache.put(realm, KeycloakDeploymentBuilder.build(is));
        	System.out.println("added to cache");
        }else {
        	System.out.println("realm already in cache");
        }
        System.out.println("result"+cache.get(realm).toString());
        return cache.get(realm);
    }

    static void setAdapterConfig(AdapterConfig adapterConfig) {
        PathBasedConfigResolver.adapterConfig = adapterConfig;
    }

}