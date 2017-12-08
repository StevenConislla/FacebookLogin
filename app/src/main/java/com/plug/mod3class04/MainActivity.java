package com.plug.mod3class04;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.util.DiffUtil;
import android.view.Menu;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.nio.BufferUnderflowException;

public class MainActivity extends AppCompatActivity {
    CallbackManager callbackManager;
    private SimpleDraweeView sdvImagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        SharedPreferences sp=
                getSharedPreferences("facebook",MODE_PRIVATE);
        if (sp.contains("nombre")){
            Intent intent=new Intent(MainActivity.this,MenuActivity.class);
            startActivity(intent);
        }




        sdvImagen=(SimpleDraweeView)findViewById(R.id.sdvImagen);

        LoginButton loginButton = (LoginButton)findViewById(R.id.login_button);
        loginButton.setReadPermissions("email");
        //Callback obtiene la respuesta por parte de facebook
        callbackManager = CallbackManager.Factory.create();
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                //con granrequest cosigo los datos de fb
                GraphRequest request=GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback(){
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                //Application <code
                                //en el object estamos obteniendo id, name y link
                                Toast.makeText(MainActivity.this, "object", Toast.LENGTH_SHORT).show();

                                //opt-optional->Aqui obtengo la informacion del JSON
                                String rutaImagen=
                                        "https://graph.facebook.com/"+object.optString("id")+"/picture?type=large";
                                sdvImagen.setImageURI(Uri.parse(rutaImagen));

                                SharedPreferences.Editor editor=
                                        getSharedPreferences("facebook",MODE_PRIVATE).edit();
                                editor.putString("nombre",object.optString("first_name"));
                                editor.putString("apellido",object.optString("last_name"));
                                editor.putString("fotoruta",rutaImagen);
                                editor.apply();
                                /*
                                String nombre=object.optString("first_name");
                                String apellido=object.optString("last_name");
                                //Vamos a otra pantalla

                                //Pasamos la imagen*/

                                Intent intent=new Intent(MainActivity.this,MenuActivity.class);
                                intent.putExtra("imagen",rutaImagen);
                                //intent.putExtra("nombre",nombre);
                                //intent.putExtra("apellido",apellido);
                                startActivity(intent);



                                //Aqui pongo el intent para pasar a otra activity



                            }
                        });

                Bundle parameters=new Bundle();
                //Aqui coloco los datos que deseo obtener
                parameters.putString("fields","id,name,link,birthday,first_name,gender,last_name");
                request.setParameters(parameters);
                request.executeAsync();
                Toast.makeText(MainActivity.this, "OnSucces", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancel() {
                Toast.makeText(MainActivity.this, "OnCancel", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,resultCode,data);
    }
}
