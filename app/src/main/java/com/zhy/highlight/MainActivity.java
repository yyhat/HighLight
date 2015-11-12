package com.zhy.highlight;

import android.graphics.Point;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import zhy.com.highlight.HighLight;
import zhy.com.highlight.view.HightLightView;

public class MainActivity extends AppCompatActivity implements HighLight.OnPosCallback, HighLight.OnFinishCallback
{

    private HighLight mHighLight;
    private int mInfoHeight = 0;
    private View infoView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.id_btn_amazing).post(new Runnable() {
            @Override
            public void run() {
                Log.i("test", "default  startGuide ");
                startGuide(null);
            }
        });

    }


    //引导结束调用
    public void guideFinish(View view)
    {
        mHighLight.remove();

        int viewId = view.getId();

        if(viewId == R.id.id_btn_important)
        {
            mHighLight = new HighLight(MainActivity.this)//
                    .anchor(findViewById(R.id.id_container)) //
                    .intercept(false)
                    .addHighLight(R.id.testBtn, R.layout.info_down, MainActivity.this, MainActivity.this);
            mHighLight.build();
        }
        else if(viewId == R.id.testBtn)
        {
            Toast.makeText(MainActivity.this, "引导结束", Toast.LENGTH_SHORT).show();
        }
    }

    public void startGuide(View view)
    {
        Log.i("test", "click add button");
        mHighLight = new HighLight(MainActivity.this)//
                .anchor(findViewById(R.id.id_container)) //
                .intercept(false)
                .addHighLight(R.id.id_btn_important, R.layout.info_up, MainActivity.this, MainActivity.this);

        mHighLight.build();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public Point getPos(RectF rectF, View view) {

        if(view.getId() == R.id.id_btn_important)
        {

            Log.i("test", "id_btn_important x=" + rectF.left + ", " + rectF.top + ", " + rectF.right + ", " + rectF.bottom);
            return new Point((int) (rectF.left + rectF.width()) / 2, (int) rectF.bottom);
        }
        else if(view.getId() == R.id.testBtn)
        {
            Log.i("test", "testBtn x=" + rectF.left + ", " + rectF.top + ", " + rectF.right + ", " + rectF.bottom);
            return new Point((int) (rectF.left + rectF.width()) / 2, (int) rectF.top -259);
        }
        return null;
    }

    @Override
    public void OnClick(View view) {
        guideFinish(view);

    }
}
