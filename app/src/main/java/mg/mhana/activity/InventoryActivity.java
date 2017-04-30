package mg.mhana.activity;

import android.app.PendingIntent;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;

import com.hanks.htextview.HTextView;
import com.hanks.htextview.HTextViewType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mg.mhana.R;
import mg.mhana.adapter.PrizeAdapter;
import mg.mhana.manager.TagManager;
import mg.mhana.model.Prize;

public class InventoryActivity extends AppCompatActivity {
    @BindView(R.id.lottery)
    HTextView lotteryMenu;
    @BindView(R.id.gifts)
    HTextView giftMenu;
    @BindView(R.id.scan)
    HTextView scan;
    @BindView(R.id.prizeList)
    RecyclerView prizeList;
    public static List<Prize>data=fill_with_data();
    NfcAdapter nfcAdapter;
    PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);
        ButterKnife.bind(this);
        giftMenu.animateText(getString(R.string.inventory));
        lotteryMenu.animateText(getString(R.string.lottery));
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);

        if (nfcAdapter==null) Log.d("NFC","adapter null");
        if (!nfcAdapter.isEnabled())Log.d("NFC","Not activated");
        pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this,getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);


        PrizeAdapter adapter = new PrizeAdapter(data, getApplication());
        prizeList.setAdapter(adapter);
        prizeList.setLayoutManager(new GridLayoutManager(this,2));

        scan.animateText(getString(R.string.redeem));
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
    public static List<Prize> fill_with_data() {

        List<Prize> data = new ArrayList<>();
        data.add(new Prize("Pen",  R.drawable.pen));
        data.add(new Prize("Pen",  R.drawable.pen));
        data.add(new Prize("Headphones",  R.drawable.headphones));

        return data;
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

    @Override
    public void onNewIntent(Intent intent){
        String action=intent.getAction();
        if ((NfcAdapter.ACTION_TAG_DISCOVERED.equals(action))||(NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action))||(NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)))processNfcIntent(intent) ;
    }

    private void processNfcIntent(Intent intent) {
        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        byte[] uid = tag.getId();
        //Remove all prizes
        prizeList.setAdapter(new PrizeAdapter(Collections.<Prize>emptyList(),this));

        //notify server

        //update text
        scan.setAnimateType(HTextViewType.EVAPORATE);
        scan.animateText("You can now get your prizes from this claiming point!");
    }

}
