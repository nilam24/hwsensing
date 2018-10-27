package in.cdac.humanhardwaresensing;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DevicePojo {

    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("device_name")
    @Expose
    private String deviceName;
    @SerializedName("device_status")
    @Expose
    private String deviceStatus;
    @SerializedName("sensor_reading")
    @Expose
    private String sensorReading;

    public DevicePojo() {
    }

    public DevicePojo(String deviceName, String deviceStatus) {
        this.deviceName = deviceName;
        this.deviceStatus = deviceStatus;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceStatus() {
        return deviceStatus;
    }

    public void setDeviceStatus(String deviceStatus) {
        this.deviceStatus = deviceStatus;
    }

    public String getSensorReading() {
        return sensorReading;
    }

    public void setSensorReading(String sensorReading) {
        this.sensorReading = sensorReading;
    }

}