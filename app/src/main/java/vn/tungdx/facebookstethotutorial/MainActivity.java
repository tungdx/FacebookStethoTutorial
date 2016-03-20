package vn.tungdx.facebookstethotutorial;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "Stetho";
    private ImageView mImageView;
    private TextView mResponseView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImageView = (ImageView) findViewById(R.id.image);
        mResponseView = (TextView) findViewById(R.id.response);

        findViewById(R.id.load_image).setOnClickListener(this);
        findViewById(R.id.call_api).setOnClickListener(this);
        findViewById(R.id.save_prefer).setOnClickListener(this);
    }

    private void callApi() {
        OkHttpClient client = new OkHttpClient.Builder().addNetworkInterceptor(new StethoInterceptor()).build();
        Request request = new Request.Builder().url("https://api.myjson.com/bins/1gi9v").build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i(TAG, "call api error");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
                final String body = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mResponseView.setText(body);
                    }
                });
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.load_image:
                Picasso.with(this).load("http://www.wholesale7.net/images/201310/goods_img/115013_P_1381825983592.jpg").into(mImageView);
                break;
            case R.id.call_api:
                callApi();
                break;
            case R.id.save_prefer:
                SharedPreferences sp = getSharedPreferences("test_file", Context.MODE_PRIVATE);
                sp.edit().putString("key", "stetho").commit();
                Toast.makeText(MainActivity.this, "Data saved!", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
