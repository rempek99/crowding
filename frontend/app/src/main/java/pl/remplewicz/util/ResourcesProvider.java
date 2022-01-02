package pl.remplewicz.util;

import android.content.Context;

import androidx.annotation.StringRes;

public class ResourcesProvider {
    private Context mContext;

    public ResourcesProvider(Context context){
        mContext = context;
    }

    public String getString(@StringRes int id){
        return mContext.getString(id);
    }
}
