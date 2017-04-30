package mg.mhana.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hanks.htextview.HTextView;
import com.hanks.htextview.HTextViewType;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import it.sephiroth.android.library.tooltip.Tooltip;
import mg.mhana.R;
import mg.mhana.model.Prize;

public class CheckProductActivity extends AppCompatActivity {
    @BindView(R.id.loading_img)
    ImageView loadingImg;
    @BindView(R.id.waiting_text)
    HTextView waitingText;
    @BindView(R.id.sub_text)
    HTextView subText;
    private long showDuration=100;
    private static boolean resultsIn=false;
    private List<Integer> possibleImages=new ArrayList<Integer>();
    private List<String> possiblePrizes=new ArrayList<String>();
    private int currentImage=0;
    @BindView(R.id.home_btn)
    ImageView homeBtn;
    @BindView(R.id.bottomBar)
    RelativeLayout bottomBar;
    @BindView(R.id.lottery)
    HTextView lotteryMenu;
    @BindView(R.id.gifts)
    HTextView giftMenu;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_product);
        ButterKnife.bind(this);
        possibleImages.add(R.drawable.ic_launcher);
        possibleImages.add(R.drawable.headphones);
        possibleImages.add(R.drawable.mac);
        possibleImages.add(R.drawable.pen);
        possibleImages.add(R.drawable.game);

        possiblePrizes.add("mac");
        possiblePrizes.add("pen");
        possiblePrizes.add("game");
        possiblePrizes.add("headphones");
        uid=getIntent().getStringExtra("id");
        homeBtn.setVisibility(View.INVISIBLE);
        bottomBar.setVisibility(View.INVISIBLE);
        giftMenu.animateText(getString(R.string.inventory));
        lotteryMenu.animateText(getString(R.string.lottery));

        resultsIn=false;

        waitingText.setAnimateType(HTextViewType.TYPER);
        waitingText.animateText(getString(R.string.waiting)+"...");

        new Handler().postDelayed(new CustomRunnable() , showDuration);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (uid.compareTo("04:85:0c:32:bd:39:84")==0) {
                    int prize = new Random().nextInt(4);
                    onResultsReceived(possiblePrizes.get(prize));
                }
                else onResultsReceived("nothing");
            }
        }, 7000);
    }
    private class CustomRunnable implements Runnable {
        @Override
        public void run() {
            if(!CheckProductActivity.resultsIn){
                Picasso.with(CheckProductActivity.this)
                    .load(possibleImages.get(currentImage))
                    .into(loadingImg);
                 currentImage=(currentImage+1)%possibleImages.size();
                new Handler().postDelayed(this , showDuration);
            }
        }
    }

    private void onResultsReceived(String prize){
        String text="Yay! You won";
        String endText=" Please go to the nearest claiming point to redeem it.";
        resultsIn=true;
        boolean won=true;
        int image=R.drawable.lose;
        switch (prize){
            case "mac":
                text+=" a mac!";
                image=R.drawable.mac;
                break;
            case "pen":
                image=R.drawable.pen;
                text+=" a pen!";
                break;
            case "game":
                image=R.drawable.game;
                text+=" a game!";
                break;
            case "headphones":
                text+=" headphones!";
                image=R.drawable.headphones;
                break;
            default:
                won=false;
                text+=" nothing";
                endText=" May the odds be with you next time!";

        }
        if (won){
            InventoryActivity.data.add(new Prize(prize,image));
            Tooltip.make(this,
                    new Tooltip.Builder(101)
                            .anchor(giftMenu, Tooltip.Gravity.TOP)
                            .activateDelay(800)
                            .showDelay(300)
                            .text(getString(R.string.click))
                            .maxWidth(500)
                            .withArrow(true)
                            .withOverlay(false)
                            .typeface(Typeface.createFromAsset(getAssets(),"fonts/norwester.ttf"))
                            .floatingAnimation(Tooltip.AnimationBuilder.DEFAULT)
                            .build()
            ).show();
        }
        Picasso.with(CheckProductActivity.this)
                .load(image)
                .into(loadingImg);
        waitingText.animateText(text);

        subText.setAnimateType(HTextViewType.ANVIL);
        subText.animateText(endText);
        homeBtn.setVisibility(View.VISIBLE);
        bottomBar.setVisibility(View.VISIBLE);
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
