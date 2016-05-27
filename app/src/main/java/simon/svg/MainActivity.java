package simon.svg;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.util.Random;

import simon.svg.svgandroid.util.SvgShapeBitmapUtil;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    private int [] resIds = new int[]{ // 切换形状
            R.raw.shape_5,
            R.raw.shape_circle_2,
            R.raw.shape_flower,
            R.raw.shape_heart,
            R.raw.shape_star
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.imageview);
        SharedPreferences sp = this.getSharedPreferences("text", Context.MODE_PRIVATE);
        String string = sp.getString("hello", "danny");
        Log.i("INFO", string);
    }

    public void shape(View v){
        //随机形状id
        int resId = resIds[new Random().nextInt(resIds.length)];
        //原始图
        Bitmap meizi = BitmapFactory.decodeResource(getResources(), R.drawable.girl1);
        //合成的形状图
        Bitmap bitmap = SvgShapeBitmapUtil.getSvgShapeBitmap(this, meizi, resId);
        //显示图片在imageview上
        imageView.setImageBitmap(bitmap);
        meizi.recycle();
        // putDataToSp(String.valueOf(resId));
    }

    private void putDataToSp(String data){
        SharedPreferences sp = this.getSharedPreferences("text", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        for(int i = 0;i<100;i++){
            edit.putString("hello"+i, data);
        }
        edit.commit();
    }
}
