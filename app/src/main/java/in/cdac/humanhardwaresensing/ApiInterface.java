package in.cdac.humanhardwaresensing;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {


    @GET("userdetail3.php")  //okok login
    Call<List<User>> doLogin();


    @GET("devicedetail2.php")  //ok detail
    Call<List<DevicePojo>> getList();

    @GET("devicestatusupdate.php") //
    Call<List<DevicePojo>> doUpdate(@Query("device_name")String device_name,
                                    @Query("device_status")String device_status);            ;
//
//    @GET("testsensor1.php")
//    Call<List<SensorDataPojo>> getSensorData();
//
//
//    @GET("allocdoc.php")  //ok
//    Call<List<DocPojo>> getDocData();
//
//
//    @GET("testdetailpat.php")
//    Call<JsonArray> getArray();
//
//    @GET("testdetailpat.php") //ok
//    Call<List<PatResponse>> getPatResponse();//   @Query("pat_firstName") String pat_firstName,
//    //@Query("pat_pass") String pat_pass);
//
//    @GET("allocpat.php")  //ok
//    Call<List<PatResponse>> getPatientData();
//
//    @GET("admindetail.php")  //ok
//    Call<List<AdminPojo>> getAdminData();
//
//    @GET("docdetail.php")   //ok
//    Call<List<DocPojo>> getDocDetail();
//
//    @GET("testdetailpat.php")
//    Call<List<Pat>>  getPatResponseList();
//
    @GET("userresetpass.php")
    Call<List<User>> doReset(@Query("user_id")String user_id,
                             @Query("user_pass")String user_pass);


   // @FormUrlEncoded
    @GET("user.php")
    Call<User> insertUser(@Query("user_id") String user_id,
                             @Query("user_pass") String user_pass,
                             @Query("user_name") String user_name,
                             @Query("user_contact") String user_contact,
                             @Query("user_city") String user_city,
                             @Query("user_state")String user_state,
                             @Query("user_country")String user_country);



//    @FormUrlEncoded
//    @POST("patregister.php")
//    Call<PatResponse> insertPatDetail(@Field("pat_firstName") String pat_firstName,
//                                      @Field("pat_lastName") String pat_lastName,
//                                      @Field("pat_contact") String pat_contact,
//                                      @Field("pat_email") String pat_email,
//                                      @Field("pat_pass") String pat_pass,
//                                      @Field("pat_conf_pass") String pat_conf_pass,
//                                      @Field("pat_city") String pat_city,
//                                      @Field("pat_state") String pat_state,
//                                      @Field("pat_pin") String pat_pin,
//                                      @Field("pat_country") String pat_country);


//    @FormUrlEncoded
//    @POST("adminregister.php")
//    Call<AdminPojo> insertAdminnDetail(@Field("admin_firstName") String admin_firstName,
//                                       @Field("admin_lastName") String admin_lastName,
//                                       @Field("admin_contact") String admin_contact,
//                                       @Field("admin_email") String admin_email,
//                                       @Field("admin_pass") String admin_pass,
//                                       @Field("admin_conf_pass") String admin_conf_pass,
//                                       @Field("admin_city") String admin_city,
//                                       @Field("admin_state") String admin_state,
//                                       @Field("admin_pin") String admin_pin,
//                                       @Field("admin_country") String admin_country);


    //pat_firstName_fileName`,'doc_firstName','doc_contact','pat_contact',`w_pressure`, `heart_beat`, `temp`
//    @FormUrlEncoded
//    @POST("sensordatainsert.php")
//    Call<SensorDataPojo> insertSensorData(@Field("pat_firstName_fileName") String file_name,
//                                          @Field("doc_firstName")String doc_firstName,
//                                          @Field("doc_contact")String doc_contact,
//                                          @Field("pat_contact")String pat_contact,
//                                          @Field("w_pressure") String w_pressure,
//                                          @Field("heart_beat") String heart_beat,
//                                          @Field("temp") String temp);

//    @FormUrlEncoded
//    @POST("allocation.php")
//
//    Call<AllocationPojo> saveAllocation(@Field("doc_firstName")String doc_firstName,
//                                        @Field("pat_firstName")String pat_firstName);
//
//    @FormUrlEncoded
//    @POST("testdevice1.php")
//    Call<DevicePojo> sendRegistration(@Field("admin_email")String admin_email,
//                                      @Field("token")String token);




}
