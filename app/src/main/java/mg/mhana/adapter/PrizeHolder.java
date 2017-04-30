package mg.mhana.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import mg.mhana.R;

/**
 * Created by Rindra Loic on 4/28/2017.
 */

public class PrizeHolder extends RecyclerView.ViewHolder {
    CardView cv;
    TextView title;
    ImageView imageView;
    PrizeHolder(View itemView) {
        super(itemView);
        cv = (CardView) itemView.findViewById(R.id.cardView);
        title = (TextView) itemView.findViewById(R.id.title);
        imageView = (ImageView) itemView.findViewById(R.id.imageView);
    }
}
