package pl.remplewicz.util;

import android.content.Context;
import android.widget.Toast;

public class InformationBar {

    public static Context currentView;

    private InformationBar(){}

    public static void showInfo(String info){
       Toast.makeText(currentView,info,Toast.LENGTH_SHORT).show();
    }


}
