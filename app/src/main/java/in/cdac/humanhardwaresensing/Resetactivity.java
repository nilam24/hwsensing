package in.cdac.humanhardwaresensing;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
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

public class Resetactivity extends AppCompatActivity {

    EditText editEmail1,editOldps,editNewps,editName1,editContact;
    Button updatbtn,refreshbtn;
    ApiInterface apiInterface;
    User user;
    List<User>userList;
    String user_id;
    String unam,upas,ucon;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reset_pass_layout);

        editEmail1=(EditText)findViewById(R.id.editText2Email);
        editOldps=(EditText)findViewById(R.id.editTextOldPass);
        editNewps=(EditText)findViewById(R.id.editTextNewPass);
        editName1=(EditText)findViewById(R.id.editTextName);
        editContact=(EditText)findViewById(R.id.editTextContact);
        updatbtn=(Button)findViewById(R.id.buttonupdatepas);
        refreshbtn=(Button)findViewById(R.id.buttonrefresh);

        apiInterface=ApiClient.getApiClient().create(ApiInterface.class);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Home Automation");
        user=new User();
        userList=new ArrayList<>();


        Call<List<User>>listCall=apiInterface.doLogin();
        listCall.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {

                userList=response.body();
//                for(int i=0;i<userList.size();i++)
//                {
//                    user_id=userList.get(i).getUserId();
//
//                }

            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {

            }
        });

        updatbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String em=editEmail1.getText().toString().trim();
                String olps=editOldps.getText().toString().trim();
                String nps=editNewps.getText().toString().trim();
                String nm=editName1.getText().toString().trim();
                String con=editContact.getText().toString().trim();

                if(em.length()==0){
                    editEmail1.setHint("enter email Id");
                }
                if(olps.length()==0)
                {
                    editOldps.setHint("enter old password");
                }

                if(nps.length()==0)
                {
                    editNewps.setHint("enter new password");
                }


                if(nm.length()==0)
                {
                    editName1.setHint("enter name");
                }
                if(con.length()==0)
                {
                    editContact.setHint("enter contact number");
                }

                else if((em.length()!=0)&& (olps.length()!=0)&&(nps.length()!=0)&&(con.length()!=0))
                {

                    for(int i=0;i<userList.size();i++) {

                        user_id=userList.get(i).getUserId();
                        upas=userList.get(i).getUserPass();
                        unam=userList.get(i).getUserName();
                        ucon=userList.get(i).getUserContact();

                        if((user_id.equals(em))&&(upas.equals(olps))&&(unam.equals(nm))&&(ucon.equals(con))) {

                            if(olps.equals(nps)) {
                                editOldps.setHint("previous and new password is not matched");
                                editNewps.setHint("please enter matched password ");
                                doReset(em, nps);
                                Toast.makeText(Resetactivity.this, "password has been changed", Toast.LENGTH_LONG).show();
                            }
                            else {
                                Toast.makeText(Resetactivity.this,"either incorrect entry or password not matched ",Toast.LENGTH_LONG).show();
                            }
                        }
                        else {
                            editEmail1.setHint("check email id");
                            editOldps.setHint("enter previous password");
                            editName1.setHint("enter name");
                            editContact.setHint("enter valid contact");
                        }
                    }
                }

            }
        });

        refreshbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editEmail1.setHint("");
                editOldps.setHint("");
                editNewps.setHint("");
                editContact.setHint("");

            }
        });

    }

    public int doReset(String em,String nps)
    {
        final int[] res = new int[1];

        Call<List<User>> call=apiInterface.doReset(em,nps);
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {


                res[0] =response.code();
                Toast.makeText(Resetactivity.this,"password is has been reset",Toast.LENGTH_LONG).show();


            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {

                Log.e("failure","-- "+t.getMessage());
            }
        });

        return res[0];
    }

}
