package zhx.cimage.simple;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.zhx.common.image.CImage;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CImage.init(this);
        ImageView imageView = findViewById(R.id.test_img);
        ImageView imageView1=findViewById(R.id.test_img2);
        CImage.with(imageView).from("https://alifei04.cfp.cn/creative/vcg/veer/800water/veer-373217773.jpg");
        CImage.with(imageView1).from("https://alifei04.cfp.cn/creative/vcg/veer/800water/veer-373217773.jpg");
        findViewById(R.id.list_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ListActivity.class));
            }
        });
    }
}
