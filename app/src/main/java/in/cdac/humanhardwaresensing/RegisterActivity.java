package in.cdac.humanhardwaresensing;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Admin on 10/25/2018.
 */

public class RegisterActivity extends AppCompatActivity {

    EditText editName,editEmail,editPassword,editContact,editCity,editState,editCountry;
    Button registerbtn;
    ApiInterface apiInterface;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);

        apiInterface=ApiClient.getApiClient().create(ApiInterface.class);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Home Automation");

        editName=(EditText)findViewById(R.id.editTextNm);
        editEmail=(EditText)findViewById(R.id.editTextId);
        editPassword=(EditText)findViewById(R.id.editTextP);
        editContact=(EditText)findViewById(R.id.editTextContact);
        editCity=(EditText)findViewById(R.id.editTextAddress1);
        editState=(EditText)findViewById(R.id.editTextAddress2);
        editCountry=(EditText)findViewById(R.id.editTextAddress3);
        registerbtn=(Button)findViewById(R.id.register);
        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String email=editEmail.getText().toString().trim();
                String pas=editPassword.getText().toString().trim();
                String nm=editName.getText().toString().trim();
                String con=editContact.getText().toString().trim();
                String city=editCity.getText().toString().trim();
                String state=editState.getText().toString().trim();
                String country=editCountry.getText().toString().trim();

                if(email.length()==0)
                {
                    editEmail.setHint("enter email");
                }
                if(pas.length()==0)
                {
                    editPassword.setHint("enter password");
                }
                if(nm.length()==0)
                {
                    editName.setHint("enter name");
                }
                if(con.length()==0)
                {
                    editContact.setHint("enter contact number");
                }
                if(city.length()==0)
                {
                    editCity.setHint("enter city");
                }

                if(state.length()==0)
                {
                    editState.setHint("enter state");
                }

                if(country.length()==0)
                {
                    editCountry.setHint("enter country");
                }

               else if((email.length()!=0)&&(pas.length()!=0)&&(nm.length()!=0)&&(con.length()!=0)&&(city.length()!=0)&&(state.length()!=0)&&(country.length()!=0))
                {
                    insertUser(email,pas,nm,con,city,state,country);
                    Toast.makeText(RegisterActivity.this,"registerd",Toast.LENGTH_LONG).show();
                }

            }
        });

    }
    public int  insertUser(String uemail,String upass,String uname,String ucontact,String ucity,String ustate,String ucountry)
    {
        final int[] res = {0};
        Call<User> call=apiInterface.insertUser(uemail,upass,uname,ucontact,ucity,ustate,ucountry);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                if(response.isSuccessful()) {
                    res[0] = response.code();
                    Log.e("res", "code" + res);
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                    Toast.makeText(RegisterActivity.this, "registerd", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {

                if(call.isCanceled()){
                    Log.e("ffff " + res[0], "request aborted" + t.getMessage());
                }
                else {
                    Log.e("ffff " + res[0], "unable to submit" );
                }
                t.printStackTrace();
            }
        });
        return res[0];
    }
}
