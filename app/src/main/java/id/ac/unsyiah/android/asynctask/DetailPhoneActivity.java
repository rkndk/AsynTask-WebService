package id.ac.unsyiah.android.asynctask;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.InputStream;
import java.net.URL;

public class DetailPhoneActivity extends Activity {
    private ImageView photo;
    private TextView productName, category, price, description;
    private ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_phone);

        Intent i = getIntent();
        int productId = i.getIntExtra("productId", 0);
        String productNameStr = i.getStringExtra("productName");
        String categoryStr = i.getStringExtra("category");
        String descriptionStr = i.getStringExtra("description");
        String photoStr =  i.getStringExtra("photo");
        String priceStr =  i.getStringExtra("price");

        photo = (ImageView)findViewById(R.id.photo);
        productName = (TextView) findViewById(R.id.productName);
        category = (TextView) findViewById(R.id.category);
        price = (TextView) findViewById(R.id.price);
        description = (TextView) findViewById(R.id.description);
        pb = (ProgressBar)findViewById(R.id.progressBar);

        productName.setText(productNameStr);
        category.setText(categoryStr);
        price.setText(priceStr);
        description.setText(descriptionStr);

        //load new image in single async
        LoadImage loader = new LoadImage();
        loader.execute(photoStr);
    }

    private class LoadImage extends AsyncTask<String, Void, Bitmap>{

        @Override
        protected Bitmap doInBackground(String... params) {
            String photoStr = params[0];
            Bitmap bitmap;

            try {
                String imageUrl = MainActivity.PHONE_BASE_URL + photoStr;
                InputStream in = (InputStream) new URL(imageUrl).getContent();
                bitmap = BitmapFactory.decodeStream(in);
                in.close();
                return bitmap;
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            pb.setVisibility(View.INVISIBLE);
            photo.setImageBitmap(result);
        }
    }
}
