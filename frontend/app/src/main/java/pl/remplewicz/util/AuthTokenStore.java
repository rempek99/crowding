package pl.remplewicz.util;

import lombok.Getter;
import lombok.Setter;

public class AuthTokenStore {

    private AuthTokenStore() {
    }

    private static AuthTokenStore instance;

    @Getter
    @Setter
    private String token;


    public static AuthTokenStore getInstance() {
        if(instance == null) {
            instance = new AuthTokenStore();
        }
        return instance;
    }

    public void invalidateToken(){
        token = null;
    }
}
