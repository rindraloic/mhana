package mg.mhana.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.hanks.htextview.HTextView;
import com.hanks.htextview.HTextViewType;

import butterknife.BindView;
import butterknife.ButterKnife;
import mg.mhana.R;

public class SplashActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 3500;
    @BindView(R.id.txt_splash)
    HTextView txtSplash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        txtSplash.setAnimateType(HTextViewType.SCALE);
        txtSplash.animateText(getString(R.string.app_name));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                txtSplash.setAnimateType(HTextViewType.ANVIL);
                txtSplash.animateText("Mbola ho avy ny anjaranao");
            }
        }, 1000);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                txtSplash.setAnimateType(HTextViewType.FALL);
                txtSplash.animateText("MHANA");
            }
        }, 2500);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashActivity.this, HomeActivity.class);
                startActivity(i);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
