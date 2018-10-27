package in.cdac.humanhardwaresensing;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText editEmail, editPas;
    Button btnlogin, btnregister;
    ApiInterface apiInterface;
    User user;
    List<User> userList;
    String uemail, upass;
    String email1, pas1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        editEmail = (EditText) findViewById(R.id.editTextEmail);
        editPas = (EditText) findViewById(R.id.editTextPas);
        btnlogin = (Button) findViewById(R.id.buttonlogin);
        btnregister = (Button) findViewById(R.id.buttonregister);

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        user = new User();
        userList = new ArrayList<>();

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                uemail=editEmail.getText().toString().trim();
                upass=editPas.getText().toString().trim();
                Log.e("list ", "response ");

                Call<List<User>> call = apiInterface.doLogin();
                call.enqueue(new Callback<List<User>>() {
                    @Override
                    public void onResponse(Call<List<User>> call, Response<List<User>> response) {

                        //if (response.isSuccessful()) {
                            int c = response.code();
                            userList = response.body();
                            for (int i = 0; i < userList.size(); i++) {
                                email1 = userList.get(i).getUserId();
                                pas1 = userList.get(i).getUserPass();


                                if ((uemail.equals(email1)) && (upass.equals(pas1))) {
                                    Toast.makeText(LoginActivity.this, "login success :-) "+email1+pas1, Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    intent.putExtra("user_id", email1);
                                    startActivity(intent);

                                }
                                else {
                                    editEmail.setHint("invalid email id");
                                    editPas.setHint("invalid password");
                                   // Toast.makeText(LoginActivity.this, "login failed :-(", Toast.LENGTH_LONG).show();
                                }
                                Log.e("list "+uemail+upass + c, "response " + email1 + pas1);
                            }



                       //}
                    }

                    @Override
                    public void onFailure(Call<List<User>> call, Throwable t) {

                        Log.e("list " + call.toString(), "response " + t.getMessage());
                    }
                });

            }
        });

    }
}
