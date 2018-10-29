package in.cdac.humanhardwaresensing;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

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

    EditText speechToText;
    ToggleButton toggleButtonlight,toggleButtonfan,toggleButtonmotor;

    ImageView imglighton,imglightoff, imgfanon, imgfanoff,imgmotoron,imgmotoroff;
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
    static String strLO="light on";
    static String strLOF="light off";
    static String strFO="fan on";
    static String strFOF="fan off";
    static String strMO="motor on";
    static String strMOF="motor off";
    String result = "";
    String d1;
    String s1;
    String statusFromDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        speechToText = (EditText) findViewById(R.id.editText);
        imglighton = (ImageView) findViewById(R.id.imageViewlighton);
        imglightoff = (ImageView) findViewById(R.id.imageViewlightoff);
        imgfanon = (ImageView) findViewById(R.id.imageViewfanon);
        imgfanoff = (ImageView) findViewById(R.id.imageViewfanoff);
        toggleButtonlight=(ToggleButton)findViewById(R.id.toggleButton1);
        toggleButtonfan=(ToggleButton)findViewById(R.id.toggleButton2);
        toggleButtonmotor=(ToggleButton)findViewById(R.id.toggleButton3);
        imgmotoron=(ImageView)findViewById(R.id.imageViewmotorOn);
        imgmotoroff=(ImageView)findViewById(R.id.imageViewmotorOff);



        toggleButtonlight.setChecked(false);
        toggleButtonfan.setChecked(false);
        devicePojo = new DevicePojo();
        devicePojoList1 = new ArrayList<>();
        devicePojoList2 = new ArrayList<>();
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Home Automation");

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
                    //deviceName=devicePojoList1.get(i).getDeviceName();
                    statusFromDb = devicePojoList1.get(i).getDeviceStatus();
                    String s="current status  ";


                    if (userid.equals(emailuser)) {
                        Toast.makeText(MainActivity.this, "email and status " + emailuser + statusFromDb, Toast.LENGTH_LONG).show();
                        //    result = emailuser;
                    }
                }
            }

            @Override
            public void onFailure(Call<List<DevicePojo>> call, Throwable t) {

                Log.e("call", "fail" + t.getMessage());
            }
        });
        try {

            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "hi speak light on or light off");
            startActivityForResult(intent, REQ_CODE);

            Log.e("intent", "" + intent.toString());

        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }

        toggleButtonlight.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked)
                {
                    deviceName=TAG1;
                    deviceStatus=statusTAG1;
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

                        for (int y=0;y<devicePojoList1.size();y++) {

                            emailuser=devicePojoList1.get(y).getUserId();
                            if(emailuser.equals(userid)) {
                                int r1 = doUpdate(deviceName, deviceStatus);

                                imglighton.setVisibility(View.VISIBLE);
                                imglightoff.setVisibility(View.GONE);
                                imgfanon.setVisibility(View.GONE);
                                imgfanoff.setVisibility(View.GONE);
                                imgmotoron.setVisibility(View.GONE);
                                imgmotoroff.setVisibility(View.GONE);
                                break;
                            }}

                    }
                }
                else {
                    deviceName=TAG1;
                    deviceStatus=statusTAG2;
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

                        for (int y=0;y<devicePojoList1.size();y++) {

                            emailuser=devicePojoList1.get(y).getUserId();
                            if(emailuser.equals(userid)) {
                                int r1 = doUpdate(deviceName, deviceStatus);


                                imgfanon.setVisibility(View.GONE);
                                imglightoff.setVisibility(View.VISIBLE);
                                imglighton.setVisibility(View.GONE);
                                imgfanoff.setVisibility(View.GONE);
                                imgmotoron.setVisibility(View.GONE);
                                imgmotoroff.setVisibility(View.GONE);
                                break;
                            }
                        }
                    }

                }
            }
        });

        toggleButtonfan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    deviceName=TAG2;
                    deviceStatus=statusTAG1;
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

                        for (int y=0;y<devicePojoList1.size();y++) {

                            emailuser = devicePojoList1.get(y).getUserId();
                            if (emailuser.equals(userid)) {
                                int r1 = doUpdate(deviceName, deviceStatus);
                                imglighton.setVisibility(View.GONE);
                                imglightoff.setVisibility(View.GONE);
                                imgfanoff.setVisibility(View.GONE);
                                imgfanon.setVisibility(View.VISIBLE);
                                imgmotoron.setVisibility(View.GONE);
                                imgmotoroff.setVisibility(View.GONE);
                                break;
                            }
                        }

                    }
                }
                else {
                    deviceName=TAG2;
                    deviceStatus=statusTAG2;
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
                        for (int y=0;y<devicePojoList1.size();y++) {

                            emailuser = devicePojoList1.get(y).getUserId();
                            if (emailuser.equals(userid)) {
                                int r1 = doUpdate(deviceName, deviceStatus);
                                imgfanon.setVisibility(View.GONE);
                                imglighton.setVisibility(View.GONE);
                                imglightoff.setVisibility(View.GONE);
                                imgfanoff.setVisibility(View.VISIBLE);
                                imgmotoron.setVisibility(View.GONE);
                                imgmotoroff.setVisibility(View.GONE);
                                break;
                            }
                        }
                    }

                }

            }
        });

        toggleButtonmotor.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    deviceName=TAG3;
                    deviceStatus=statusTAG1;
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

                        for (int y=0;y<devicePojoList1.size();y++) {

                            emailuser = devicePojoList1.get(y).getUserId();
                            if (emailuser.equals(userid)) {
                                int r1 = doUpdate(deviceName, deviceStatus);
                                imglighton.setVisibility(View.GONE);
                                imglightoff.setVisibility(View.GONE);
                                imgfanon.setVisibility(View.GONE);
                                imgfanoff.setVisibility(View.GONE);
                                imgmotoron.setVisibility(View.VISIBLE);
                                imgmotoroff.setVisibility(View.GONE);
                                break;
                            }
                        }

                    }
                }
                else {
                    deviceName=TAG3;
                    deviceStatus=statusTAG2;
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
                        for (int y=0;y<devicePojoList1.size();y++) {

                            emailuser = devicePojoList1.get(y).getUserId();
                            if (emailuser.equals(userid)) {
                                int r1 = doUpdate(deviceName, deviceStatus);
                                imglighton.setVisibility(View.GONE);
                                imglightoff.setVisibility(View.GONE);
                                imgfanon.setVisibility(View.GONE);
                                imgfanoff.setVisibility(View.GONE);
                                imgmotoroff.setVisibility(View.VISIBLE);
                                imgmotoron.setVisibility(View.GONE);
                                break;
                            }
                        }
                    }

                }

            }
        });

    }

    public int doUpdate(String deviceName, String deviceStatus) {
        Call<List<DevicePojo>> listCall = apiInterface.doUpdate(deviceName, deviceStatus);
        listCall.enqueue(new Callback<List<DevicePojo>>() {
            @Override
            public void onResponse(Call<List<DevicePojo>> call, Response<List<DevicePojo>> response) {

                int code = response.code();

                devicePojoList2 = response.body();
                // Toast.makeText(MainActivity.this," status",Toast.LENGTH_LONG).show();
                Log.e("update call ", "" + devicePojoList2);
            }

            @Override
            public void onFailure(Call<List<DevicePojo>> call, Throwable t) {
                // Toast.makeText(MainActivity.this," status",Toast.LENGTH_LONG).show();
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
                    ArrayList<String> listresult = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    // if(listresult.contains("turn on lights")) {
                    speechToText.setHint(listresult.get(0));

                    String res1=speechToText.getText().toString().trim();

                    Log.e("res==", " " + res1);

                    speechToText.setHint(listresult.get(0)+" "+res1);

                   // imglighton1.setVisibility(View.VISIBLE);
//                    if(statusFromDb.equals("off")){
                    if ((listresult != null) && (listresult.contains("light on"))) {


                        deviceName = TAG1;
                        deviceStatus = statusTAG1;

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
                            imglighton.setVisibility(View.VISIBLE);
                            imglightoff.setVisibility(View.GONE);
                            toggleButtonlight.setChecked(true);
                            toggleButtonlight.setTextOn(statusTAG1);
                            devicePojo.getDeviceName();
                            speechToText.setHint(devicePojo.getDeviceStatus());
                        }


                        //update status in database for light
                        Log.e("lightr = " + deviceName, "on = " + deviceStatus);
                        //if (userid.equals(emailuser)) {
                        int r2 = doUpdate(deviceName, deviceStatus);
                        Log.e("=====", "" + r2);
                        //  }

                    }
               //     toggleButtonlight.setChecked(false);

                    if ((listresult != null) && (listresult.contains("fan on"))) {
                        //update status in database for fan

                        deviceName = TAG2;
                        deviceStatus = statusTAG1;
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
                            imgfanon.setVisibility(View.VISIBLE);
                            imgfanoff.setVisibility(View.GONE);
                            toggleButtonfan.setChecked(true);
                            toggleButtonfan.setTextOn(statusTAG1);
                            devicePojo.getDeviceName();
                            speechToText.setHint(devicePojo.getDeviceStatus());
                        }


                        //update status in database for light
                        Log.e("fan = " + deviceName, "on = " + deviceStatus);
                        // String e = getList();
                        //if (userid.equals(emailuser)) {
                      // int r2 = doUpdate(deviceName, deviceStatus);
                       // Log.e("=====", "" + r2);
                        //  }

                    }
                //    toggleButtonfan.setChecked(false);
// }

                    // if(statusFromDb.equals("on")){
                    if ((listresult != null) && (listresult.contains("light off"))) {
                        deviceName = TAG1;
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
                            imglightoff.setVisibility(View.VISIBLE);
                            imglighton.setVisibility(View.GONE);
                            toggleButtonlight.setChecked(true);
                            toggleButtonlight.setTextOn(statusTAG2);
                            devicePojo.getDeviceName();
                            devicePojo.getDeviceStatus();
                        }


                        //update status in database for light
                        Log.e("lightr = " + deviceName, "off = " + deviceStatus);
                        //if (userid.equals(emailuser)) {
                      //  int r2 = doUpdate(deviceName, deviceStatus);
                       // Log.e("=====", "" + r2);
                        //  }
                    }

                //    toggleButtonlight.setChecked(false);

                    if ((listresult != null) && (listresult.contains("fan off"))) {
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
                            imgfanoff.setVisibility(View.VISIBLE);
                            imgfanon.setVisibility(View.GONE);
                            toggleButtonfan.setChecked(true);
                            toggleButtonfan.setTextOff(statusTAG2);
                            devicePojo.getDeviceName();
                            devicePojo.getDeviceStatus();
                            Log.e("", "== " +r2);
                        }
                        //update status in database for light
                        Log.e("fan = " + deviceName, "off = " + deviceStatus);
                        // String e = getList();
                        //if (userid.equals(emailuser)) {
                    //    int r2 = doUpdate(deviceName, deviceStatus);
                      //  Log.e("=====", "" + r2);
                        //  }

                    }
                   // toggleButtonfan.setChecked(false);

                    if ((listresult != null) && (listresult.contains("motor on"))) {
                        deviceName = TAG3;
                        deviceStatus = statusTAG1;

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
                            imgmotoroff.setVisibility(View.GONE);
                            toggleButtonmotor.setChecked(true);
                            toggleButtonmotor.setTextOn(statusTAG1);
                            imgmotoron.setVisibility(View.VISIBLE);
                            devicePojo.getDeviceName();
                            devicePojo.getDeviceStatus();
                        }


                        //update status in database for light
                        Log.e("lightr = " + deviceName, "on = " + deviceStatus);
                        //if (userid.equals(emailuser)) {
                      //  int r2 = doUpdate(deviceName, deviceStatus);
                       // Log.e("=====", "" + r2);
                        //  }

                    }
                   // toggleButtonmotor.setChecked(false);

                    if ((listresult != null) && (listresult.contains("motor off"))) {
                        deviceName = TAG3;
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
                            imgmotoroff.setVisibility(View.VISIBLE);
                            imgmotoron.setVisibility(View.GONE);
                            toggleButtonmotor.setChecked(true);
                            toggleButtonmotor.setTextOff(statusTAG2);
                            devicePojo.getDeviceName();
                            speechToText.setHint(devicePojo.getDeviceStatus());
                        }


                        //update status in database for light
                        Log.e("lightr = " + deviceName, "off = " + deviceStatus);
                        //if (userid.equals(emailuser)) {
                    //    int r2 = doUpdate(deviceName, deviceStatus);
                     //   Log.e("=====", "" + r2);
                        //  }
                    }
                   // toggleButtonmotor.setChecked(false);

                }
        }
    }
}

/*

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
 */