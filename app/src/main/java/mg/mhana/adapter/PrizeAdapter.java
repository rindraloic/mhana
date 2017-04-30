package mg.mhana.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

import mg.mhana.R;
import mg.mhana.model.Prize;

/**
 * Created by Rindra Loic on 4/28/2017.
 */

public class PrizeAdapter extends RecyclerView.Adapter<PrizeHolder> {
    List<Prize> list = Collections.emptyList();
    Context context;

    public PrizeAdapter(List<Prize> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public PrizeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Inflate the layout, initialize the View Holder
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout, parent, false);
        PrizeHolder holder = new PrizeHolder(v);
        return holder;

    }

    @Override
    public void onBindViewHolder(PrizeHolder holder, int position) {

        //Use the provided View Holder on the onCreateViewHolder method to populate the current row on the RecyclerView
        holder.title.setText(list.get(position).title);
        holder.imageView.setImageResource(list.get(position).imageId);

        //animate(holder);

    }

    @Override
    public int getItemCount() {
        //returns the number of elements the RecyclerView will display
        return list.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
