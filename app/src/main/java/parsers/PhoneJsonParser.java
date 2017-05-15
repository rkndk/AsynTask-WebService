package parsers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import model.SmartPhone;

public class PhoneJsonParser {
    public static List<SmartPhone> parseFeed(String content){
        try {
            List<SmartPhone> phoneList = new ArrayList<>();

            JSONObject jsonObj = new JSONObject(content);
            JSONArray ar = jsonObj.getJSONArray("products");

            for (int i=0; i<ar.length(); i++){
                JSONObject obj = ar.getJSONObject(i);
                SmartPhone phone = new SmartPhone();

                phone.setProductId(obj.getInt("productId"));
                phone.setProductName(obj.getString("productName"));
                phone.setCategory(obj.getString("category"));
                phone.setDescription(obj.getString("description"));
                phone.setPrice(obj.getString("price"));
                phone.setPhoto(obj.getString("photo"));

                phoneList.add(phone);
            }
            return phoneList;
        }catch (JSONException e){
            e.printStackTrace();
            return null;
        }
    }
}