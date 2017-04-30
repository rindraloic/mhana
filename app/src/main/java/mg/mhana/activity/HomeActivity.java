package mg.mhana.activity;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Typeface;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.hanks.htextview.HTextView;
import com.hanks.htextview.HTextViewType;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import it.sephiroth.android.library.tooltip.Tooltip;
import mg.mhana.R;
import mg.mhana.manager.TagManager;

public class HomeActivity extends AppCompatActivity {
    @BindView(R.id.logo)
    HTextView txtLogo;
    @BindView(R.id.home_tip)
    HTextView tip;
    @BindView(R.id.lottery)
    HTextView lotteryMenu;
    @BindView(R.id.gifts)
    HTextView giftMenu;
    NfcAdapter nfcAdapter;
    PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (nfcAdapter==null) Log.d("NFC","adapter null");
        if (!nfcAdapter.isEnabled())Log.d("NFC","Not activated");
        pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this,getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        txtLogo.setAnimateType(HTextViewType.SCALE);
        txtLogo.animateText(getString(R.string.app_name));
        tip.animateText(getString(R.string.tip));
        giftMenu.animateText(getString(R.string.inventory));
        lotteryMenu.animateText(getString(R.string.lottery));

        Tooltip.make(this,
                new Tooltip.Builder(101)
                        .anchor(lotteryMenu, Tooltip.Gravity.TOP)
                        .activateDelay(800)
                        .showDelay(300)
                        .text(getString(R.string.soon))
                        .maxWidth(500)
                        .withArrow(true)
                        .withOverlay(false)
                        .typeface(Typeface.createFromAsset(getAssets(),"fonts/norwester.ttf"))
                        .floatingAnimation(Tooltip.AnimationBuilder.DEFAULT)
                        .build()
        ).show();
    }

    @Override public void onResume()  {
        super.onResume();
        nfcAdapter.enableForegroundDispatch(this, pendingIntent,null,null);
    }
    @Override
    public void onPause() {
        super.onResume();
        nfcAdapter.disableForegroundDispatch(this);
    }

    @Override
    public void onNewIntent(Intent intent){
        String action=intent.getAction();
        if ((NfcAdapter.ACTION_TAG_DISCOVERED.equals(action))||(NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action))||(NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)))processNfcIntent(intent) ;
    }

    private void processNfcIntent(Intent intent) {
        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        byte[] uid = tag.getId();

        Log.d("NFC", TagManager.getUID(uid));
        Intent i = new Intent(this, CheckProductActivity.class);
        i.putExtra("id",TagManager.getUID(uid));
        startActivity(i);
    }
    @OnClick(R.id.gifts)
    public void goToGifts() {
        Intent i = new Intent(this, InventoryActivity.class);
        startActivity(i);
    }
    @OnClick(R.id.lottery)
    public void goToLottery() {
        Intent i = new Intent(this, LotteryActivity.class);
        startActivity(i);
    }
    @OnClick(R.id.home_btn)
    public void goToHome() {
        Intent i = new Intent(this, HomeActivity.class);
        startActivity(i);
    }
}
