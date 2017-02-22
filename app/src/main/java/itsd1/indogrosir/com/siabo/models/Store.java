package itsd1.indogrosir.com.siabo.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Paulina on 1/18/2017.
 */
public class Store
{
    @SerializedName("id_store")
    private int id_store;
    @SerializedName("store_code")
    private String store_code;
    @SerializedName("store_name")
    private String store_name;
    @SerializedName("address")
    private String address;
    @SerializedName("latitude")
    private String latitude;
    @SerializedName("longitude")
    private String longitude;
    @SerializedName("branch")
    Branch branch;

    public Store(int id_store, String store_code, String store_name, String address, String latitude, String longitude, Branch branch) {
        this.id_store = id_store;
        this.store_code = store_code;
        this.store_name = store_name;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.branch = branch;
    }

    public int getId_store() {
        return id_store;
    }

    public String getStore_code() {
        return store_code;
    }

    public String getStore_name() {
        return store_name;
    }

    public String getAddress() {
        return address;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setId_store(int id_store) {
        this.id_store = id_store;
    }

    public void setStore_code(String store_code) {
        this.store_code = store_code;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setLatitude(String latitude)
    {
        this.latitude = latitude;
    }

    public void setLongitude(String longitude)
    {
        this.longitude = longitude;
    }

    public void setBranch(Branch branch)
    {
        this.branch = branch;
    }
}
