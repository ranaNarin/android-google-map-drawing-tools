package com.service.drawingtoolmap;

/**
 * Created by narinder.rana on 6/6/2016.
 */
public class GPSTrackingPacket {

    public String MessageType;
    public String UserID;
    public String IMEI;
    public String Longitude;
    public String Latitude;
    public String Altitude;
    public String Speed;
    public String Heading;
    public String GpsStatus;
    public String Satellites;
    public String TrackingDateTme;
    public String Temperature;
    public String Battery;
    public String CreatedOn;
    public String IsMobileData;
    public String Access_token;
    public String Status;
    public String Message;


    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getMessageType() {
        return MessageType;
    }

    public void setMessageType(String messageType) {
        MessageType = messageType;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getIMEI() {
        return IMEI;
    }

    public void setIMEI(String IMEI) {
        this.IMEI = IMEI;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getAltitude() {
        return Altitude;
    }

    public void setAltitude(String altitude) {
        Altitude = altitude;
    }

    public String getSpeed() {
        return Speed;
    }

    public void setSpeed(String speed) {
        Speed = speed;
    }

    public String getHeading() {
        return Heading;
    }

    public void setHeading(String heading) {
        Heading = heading;
    }

    public String getGpsStatus() {
        return GpsStatus;
    }

    public void setGpsStatus(String gpsStatus) {
        GpsStatus = gpsStatus;
    }

    public String getSatellites() {
        return Satellites;
    }

    public void setSatellites(String satellites) {
        Satellites = satellites;
    }

    public String getTrackingDateTme() {
        return TrackingDateTme;
    }

    public void setTrackingDateTme(String trackingDateTme) {
        TrackingDateTme = trackingDateTme;
    }

    public String getTemperature() {
        return Temperature;
    }

    public void setTemperature(String temperature) {
        Temperature = temperature;
    }

    public String getBattery() {
        return Battery;
    }

    public void setBattery(String battery) {
        Battery = battery;
    }

    public String getCreatedOn() {
        return CreatedOn;
    }

    public void setCreatedOn(String createdOn) {
        CreatedOn = createdOn;
    }

    public String getIsMobileData() {
        return IsMobileData;
    }

    public void setIsMobileData(String isMobileData) {
        IsMobileData = isMobileData;
    }

    public String getAccess_token() {
        return Access_token;
    }

    public void setAccess_token(String access_token) {
        Access_token = access_token;
    }
}
