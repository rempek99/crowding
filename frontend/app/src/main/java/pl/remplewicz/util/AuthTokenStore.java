package pl.remplewicz.util;

import org.json.JSONObject;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import lombok.Getter;

public class AuthTokenStore {

    private AuthTokenStore() {
    }


    private static AuthTokenStore instance;

    private PropertyChangeSupport changes = new PropertyChangeSupport(this);

    @Getter
    private String token;

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

    public void addPropertyChangeListener(PropertyChangeListener l) {
        changes.addPropertyChangeListener(l);
    }
}
