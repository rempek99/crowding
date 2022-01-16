package pl.remplewicz.util;

import org.json.JSONObject;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;

public class AuthTokenStore {

    private AuthTokenStore() {
    }

    public static String ROLE_ADMIN = "ADMIN";
    public static String ROLE_USER = "USER";

    private static AuthTokenStore instance;

    private PropertyChangeSupport changes = new PropertyChangeSupport(this);

    private String token;

    public String getToken() {
        if(token!=null) {
            byte[] bytes = Base64.getDecoder().decode(token.split("\\.")[1]);
            String string = new String(bytes, StandardCharsets.UTF_8);
            try {
                JSONObject json = new JSONObject(string);
                if((Integer) json.get("exp") > System.currentTimeMillis()){
                    setToken(null);
                }
            } catch (Exception ex) {
                return null;
            }
        }
        return token;
    }

    public void setToken(String token) {
        String oldValue = this.token;
        this.token = token;
        changes.firePropertyChange("token",oldValue,token);
    }

    public static AuthTokenStore getInstance() {
        if(instance == null) {
            instance = new AuthTokenStore();
        }
        return instance;
    }

    public void invalidateToken(){
        String oldValue = this.token;
        token = null;
        changes.firePropertyChange("token",oldValue, null);
    }

    public String getUsername() {
        byte[] bytes = Base64.getDecoder().decode(token.split("\\.")[1]);
        String string = new String(bytes, StandardCharsets.UTF_8);
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(string);
            return (String) jsonObject.get("sub");
        } catch (Exception e) {
            return null;
        }
    }

    public String[] getRoles() {
        byte[] bytes = Base64.getDecoder().decode(token.split("\\.")[1]);
        String string = new String(bytes, StandardCharsets.UTF_8);
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(string);
            return ((String) jsonObject.get("roles")).split(",");
        } catch (Exception e) {
            return null;
        }
    }

    public boolean isAdmin() {
       return Arrays.stream(getRoles()).anyMatch(r -> r.equals(AuthTokenStore.ROLE_ADMIN));
    }

    public void addPropertyChangeListener(PropertyChangeListener l) {
        changes.addPropertyChangeListener(l);
    }
}
