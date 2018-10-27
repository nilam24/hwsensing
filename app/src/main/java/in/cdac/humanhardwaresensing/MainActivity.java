package in.cdac.humanhardwaresensing;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    TextView speechToText;
    String userid;
    final static int REQ_CODE = 100;
    ApiInterface apiInterface;
    DevicePojo devicePojo;
    List<DevicePojo> devicePojoList1, devicePojoList2;
    String emailuser;
    String deviceName, deviceStatus;
    static String TAG1 = "light";
    static String TAG2 = "fan";
    static String TAG3 = "motor";
    static String statusTAG1 = "on";
    static String statusTAG2 = "off";
    String result = "";
    String d1;
    String s1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        speechToText = (TextView) findViewById(R.id.textSpeech);

        devicePojo = new DevicePojo();
        devicePojoList1 = new ArrayList<>();
        devicePojoList2 = new ArrayList<>();

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        Intent in = getIntent();
        if (in != null) {
            userid = in.getStringExtra("user_id");
            Log.e("id==", "" + userid);
        }
        Call<List<DevicePojo>> call = apiInterface.getList();
        call.enqueue(new Callback<List<DevicePojo>>() {
            @Override
            public void onResponse(Call<List<DevicePojo>> call, Response<List<DevicePojo>> response) {

                response.code();
                devicePojoList1 = response.body();
                for (int i = 0; i < devicePojoList1.size(); i++) {
                    emailuser = devicePojoList1.get(i).getUserId();
                    // if (userid.equals(emailuser)) {
                    Toast.makeText(MainActivity.this, "call success " + emailuser, Toast.LENGTH_LONG).show();
                    //    result = emailuser;
                    // }

                }
            }

            @Override
            public void onFailure(Call<List<DevicePojo>> call, Throwable t) {

                Log.e("call", "fail" + t.getMessage());
            }
        });
//        Log.e("=====", "" );
//        if (userid.equals(emailuser)) {
//            int r = doUpdate(deviceName, deviceStatus);
//            Log.e("=====", "" + r);
//        }

        try {

            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "hi speak turn on or turn off");
            startActivityForResult(intent, REQ_CODE);

            Log.e("intent", "" + intent.toString());


        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }


    }

//    public String getList() {
//
//        Call<List<DevicePojo>> call = apiInterface.getList();
//        call.enqueue(new Callback<List<DevicePojo>>() {
//            @Override
//            public void onResponse(Call<List<DevicePojo>> call, Response<List<DevicePojo>> response) {
//
//                response.code();
//                devicePojoList1 = response.body();
//                for (int i = 0; i < devicePojoList1.size(); i++) {
//                    emailuser = devicePojoList1.get(i).getUserId();
//                    if (userid.equals(emailuser)) {
//                        Toast.makeText(MainActivity.this, "call success " + emailuser, Toast.LENGTH_LONG).show();
//                        result = emailuser;
//                    }
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<DevicePojo>> call, Throwable t) {
//
//                Log.e("call", "fail" + t.getMessage());
//            }
//        });
//        return result;
//
//    }

    public int doUpdate(String deviceName, String deviceStatus) {
        Call<List<DevicePojo>> listCall = apiInterface.doUpdate(deviceName, deviceStatus);
        listCall.enqueue(new Callback<List<DevicePojo>>() {
            @Override
            public void onResponse(Call<List<DevicePojo>> call, Response<List<DevicePojo>> response) {

                int code = response.code();
                devicePojoList2 = response.body();
                Log.e("update call ", "" + devicePojoList2);
            }

            @Override
            public void onFailure(Call<List<DevicePojo>> call, Throwable t) {
                Log.e("update call ", "" + t.getMessage());
            }
        });


        return 0;
    }

    @Override
    protected void onActivityResult(final int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQ_CODE:
                if (resultCode == RESULT_OK && data != null) {
                    //  String str = data.getStringExtra(RecognizerIntent.EXTRA_RESULTS);
                    ArrayList<String> listresult = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    // if(listresult.contains("turn on lights")) {
                    speechToText.setText(listresult.get(0));

                    //send to database
                    //send signal to bloothooth device
                    //fetch from database
                    //authenticate user first
                    //authenticate values for status as well as for the device type
                    //update status to databse according to the device name
                    //fetch data to change status on/off the device
                    //condition decide for on / off status
                    //what happen when n next to

                    // getstatus;


                    String res = speechToText.getText().toString().trim();

                    Log.e("res==", " " + res);
                    if ((res != null) && (res.contains("light on"))) {
                        deviceName = TAG1;
                        deviceStatus = statusTAG1;

                        devicePojo = new DevicePojo(deviceName, deviceStatus);

                        String dnm = devicePojo.getDeviceName();
                        String dst = devicePojo.getDeviceStatus();
                        //update status in database for light
                        Log.e(userid + " light = " + deviceName, "on = " + deviceStatus);

                        //
                        // int r = doUpdate(dnm, dst);
                       // Log.e("=====", "" + r);
//                        }
//                        String em = getList();
//                        Log.e("=====", "" + em);
//                        if (userid.equals(em)) {
//                            int r = doUpdate(deviceName, deviceStatus);
//                            Log.e("=====", "" + r);
//                        }


                    }
                    if ((res != null) && (res.contains("fan off"))) {
                        //update status in database for fan

                        deviceName = TAG2;
                        deviceStatus = statusTAG2;

                        devicePojo = new DevicePojo(deviceName, deviceStatus);
                        JsonArray jsonArray = new JsonArray();
                        jsonArray.add(deviceName);
                        jsonArray.add(deviceStatus);
                        JSONObject obj = new JSONObject();
                        if (obj.has("device_name")) {
                            try {
                                deviceName = obj.getString("device_name");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        if (obj.has("device_status")) {
                            try {
                                deviceStatus = obj.getString("device_status");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                        if ((deviceName != null) && (deviceStatus != null)) {
                            devicePojo = new DevicePojo(deviceName, deviceStatus);
                            int r2 = doUpdate(deviceName, deviceStatus);
                            devicePojo.getDeviceName();
                            devicePojo.getDeviceStatus();
                        }


                        //update status in database for light
                        Log.e("fan = " + deviceName, "on = " + deviceStatus);
                        // String e = getList();
                        //if (userid.equals(emailuser)) {
                        int r2 = doUpdate(deviceName, deviceStatus);
                        Log.e("=====", "" + r2);
                        //  }

                    }
                }
        }
    }
}

/*
 devicePojo=new DevicePojo(admin_email,token);
        JsonArray jsonArray=new JsonArray();
        jsonArray.add(admin_email);
        jsonArray.add(token);
        Log.e("",""+jsonArray.toString());
        JSONObject object=new JSONObject();
        if(object.has("admin_email")){
            try {
                admin_email=object.getString("admin_email");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if(object.has("token"))
        {
            try {
                token=object.getString("token");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if((admin_email!=null)&&(token!=null)) {

            devicePojo=new DevicePojo(admin_email,token);
//            devicePojo.getAdminEmail();
//            devicePojo.getToken();
            int code = 0;

            code = sendRegistration(admin_email,token);

            Log.e("code", "" + code + admin_email + token);


        }
 */
