package id.ac.unsyiah.android.asynctask;

import model.SmartPhoneList;
import retrofit2.Call;
import retrofit2.http.GET;

public interface PhoneAPI {

    @GET("/api/toko-hape/rest/list/json")
    Call<SmartPhoneList> getFeed();

}
