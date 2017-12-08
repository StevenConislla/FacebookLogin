package com.plug.mod3class04;

import android.app.Application;

import com.facebook.FacebookSdk;
import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by OSKAR on 09/08/2017.
 * Oskar Steven Conislla Contreras
 * oskarconislla20@gmail.com
 * 947446763
 */

public class Configuracion extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        /*ImagePipelineConfig config = ImagePipelineConfig.newBuilder(getApplicationContext())
                .setDownsampleEnabled(true)
                .build();
        Fresco.initialize(getApplicationContext(), config);*/
        Fresco.initialize(this);
        FacebookSdk.sdkInitialize(getApplicationContext());
    }
}