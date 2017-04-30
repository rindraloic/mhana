package mg.mhana.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.hanks.htextview.HTextView;
import com.hanks.htextview.animatetext.HText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mg.mhana.R;
import mg.mhana.adapter.PrizeAdapter;
import mg.mhana.model.Prize;

public class LotteryActivity extends AppCompatActivity {
    @BindView(R.id.lottery)
    HTextView lotteryMenu;
    @BindView(R.id.gifts)
    HTextView giftMenu;
    @BindView(R.id.lottery_info)
    HTextView lotteryInfo;
    @BindView(R.id.prizeLabel)
    HTextView prizeLabel;
    @BindView(R.id.prizeList)
    RecyclerView prizeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lottery);
        ButterKnife.bind(this);
        giftMenu.animateText(getString(R.string.inventory));
        lotteryMenu.animateText(getString(R.string.lottery));

        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        Date d=Calendar.getInstance().getTime();

        lotteryInfo.animateText(getString(R.string.lotteryInfo) +" "+format.format(d));
        prizeLabel.animateText(getString(R.string.possiblePrizes));

        List<Prize> data = fill_with_data();

        PrizeAdapter adapter = new PrizeAdapter(data, getApplication());
        prizeList.setAdapter(adapter);
        prizeList.setLayoutManager(new GridLayoutManager(this,2));
    }

    public List<Prize> fill_with_data() {

        List<Prize> data = new ArrayList<>();

        data.add(new Prize("Pen",  R.drawable.pen));
        data.add(new Prize("Mac",  R.drawable.mac));
        data.add(new Prize("Headphones",  R.drawable.headphones));
        data.add(new Prize("Game",  R.drawable.game));

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

}
