package edu.mytest;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Map;

import edu.mytest.models.RegisterModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends Activity implements View.OnClickListener{
    private EditText et_name;
    private EditText et_password;
    private Button btn_enter;
    private Button btn_reg;
    private Button btn_ok;
    private boolean isRegistration;
    private TextView tv_enter;
    private Gson gson;
    private Retrofit retrofit;
    private final String URL = "http://smktesting.herokuapp.com";
    public static APIService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_name = (EditText)findViewById(R.id.et_name);
        et_password = (EditText)findViewById(R.id.et_password);
        btn_enter = (Button) findViewById(R.id.btn_enter);
        btn_reg = (Button) findViewById(R.id.btn_reg);
        btn_ok = (Button) findViewById(R.id.btn_ok);
        btn_enter.setOnClickListener(this);
        btn_reg.setOnClickListener(this);
        btn_ok.setOnClickListener(this);
        tv_enter = (TextView)findViewById(R.id.tv_enter);

        gson = new GsonBuilder().create();
        retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(URL)
                .build();

        service = retrofit.create(APIService.class);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_enter:
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("name", getText(R.string.guest));
                intent.putExtra("isGuest", true);
                startActivity(intent);
            break;

            case R.id.btn_reg:
                if (!isRegistration){
                    tv_enter.setText(R.string.text_for_reg);
                    btn_ok.setText(R.string.ok_btn);
                    btn_reg.setVisibility(View.INVISIBLE);
                    isRegistration = true;
                }

            break;

            case R.id.btn_ok:
                if (isRegistration){
                    registration();
                } else {
                    Map <String, String> mapJson = new HashMap<>();
                    mapJson.put("username", et_name.getText().toString());
                    mapJson.put("password", et_password.getText().toString());

                    Call<RegisterModel> call = service.checkUser(mapJson);
                    call.enqueue(new Callback<RegisterModel>() {
                        @Override
                        public void onResponse(Call<RegisterModel> call, Response<RegisterModel> response) {
                            if (response.code() == 200){
                                RegisterModel rm = response.body();
                                if (rm.getSuccess()) {
                                    Intent intent = new Intent(getBaseContext(), MainActivity.class);
                                    intent.putExtra("name", et_name.getText().toString());
                                    intent.putExtra("isGuest", false);
                                    startActivity(intent);
                                } else {
                                    showMessage(getText(R.string.enter_error).toString());
                                }
                            }

                        }

                        @Override
                        public void onFailure(Call<RegisterModel> call, Throwable t) {
                            Toast.makeText(getBaseContext(), "SOME ERROR", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            break;
        }
    }

    public void showMessage (String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setMessage(message)
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void registration (){
        Map<String, String> mapJson = new HashMap<>();
        if (et_name.getText().toString().equals("") || et_password.getText().toString().equals("")) {
            showMessage(getText(R.string.text_error).toString());
        }else {
            mapJson.put("username", et_name.getText().toString());
            mapJson.put("password", et_password.getText().toString());

            Call<RegisterModel> call = service.registerUser(mapJson);
            call.enqueue(new Callback<RegisterModel>() {
                @Override
                public void onResponse(Call<RegisterModel> call, Response<RegisterModel> response) {
                        RegisterModel rm = response.body();
                        if (rm.getSuccess()) {
                            Intent intent = new Intent(getBaseContext(), MainActivity.class);
                            intent.putExtra("name", et_name.getText().toString());
                            intent.putExtra("isGuest", false);
                            startActivity(intent);
                        }else {
                            showMessage(getText(R.string.exists).toString());
                        }

                }

                @Override
                public void onFailure(Call<RegisterModel> call, Throwable t) {
                    Toast.makeText(getBaseContext(), "SOME ERROR", Toast.LENGTH_LONG).show();
                }
            });
        }

    }
}
