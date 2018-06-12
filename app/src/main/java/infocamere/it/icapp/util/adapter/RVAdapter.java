/*
 * Copyright (c)
 * Created by Luca Visentin - yyi4216
 * 29/05/18 12.17
 */

package infocamere.it.icapp.util.adapter;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

import infocamere.it.icapp.R;
import infocamere.it.icapp.sipert.SipertTabActivity;
import infocamere.it.icapp.model.ItemUI;
import infocamere.it.icapp.search.SearchableActivity;
import infocamere.it.icapp.splash.SplashActivity;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.ItemUIViewHolder> implements ItemTouchHelperAdapter {

    List<ItemUI> items;
    private  OnStartDragListener mDragStartListener;

    public RVAdapter(List<ItemUI> items, OnStartDragListener mDragStartListener) {
        this.items = items;
        this.mDragStartListener = mDragStartListener;
    }

    @Override
    public void onItemMove(int fromIndex, int toIndex) {
        if (fromIndex < items.size() && toIndex < items.size()) {
            if (fromIndex < toIndex) {
                for (int i = fromIndex; i < toIndex; i++) {
                    Collections.swap(items, i, i + 1);
                }
            } else {
                for (int i = fromIndex; i > toIndex; i--) {
                    Collections.swap(items, i, i - 1);
                }
            }
            notifyItemMoved(fromIndex, toIndex);
        }
    }

    @Override
    public void onItemDismiss(int position) {
        if (items.size()< position) {
            items.remove(position);
            notifyItemRemoved(position);
        }
    }

    public static class ItemUIViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        CardView cv;
        LinearLayout linearCont;
        TextView header;
        TextView titleSection;
        TextView subtitleSection;
        ImageView imgSection;

        ItemUIViewHolder (View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.card_ui);
            linearCont = (LinearLayout) itemView.findViewById(R.id.linear_cont);
            header = (TextView) itemView.findViewById(R.id.txt_header);
            titleSection = (TextView) itemView.findViewById(R.id.txt_titleSection);
            subtitleSection = (TextView) itemView.findViewById(R.id.txt_subsection);
            imgSection = (ImageView) itemView.findViewById(R.id.img_section);
        }

        @Override
        public void onClick(View view) {
            Log.i("ITEMUIVIEWHOLDER", "click");
        }
    }

    @NonNull
    @Override
    public ItemUIViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = (View) LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_ui, parent, false);

        ItemUIViewHolder itemuivvh = new ItemUIViewHolder(v);

        return itemuivvh;
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemUIViewHolder holder, final int position) {
        holder.header.setText(items.get(position).getHeaderTitle());
        holder.titleSection.setText(items.get(position).getTitle());
        holder.subtitleSection.setText(items.get(position).getSubtitle());
        if (items.get(position).getBkg_image().contains("tempo")) {
            holder.imgSection.setBackgroundResource(R.drawable.tempo2);
        }
        else {
            holder.imgSection.setBackgroundResource(R.drawable.trasferte);
        }
        holder.linearCont.setBackgroundColor(Color.parseColor(items.get(position).getBkg_color()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (position) {

                    case 0:
                        Intent presenze = new Intent(v.getContext(), SipertTabActivity.class);
                        v.getContext().startActivity(presenze);

                        break;

                    case 1:
                        Intent trasferte = new Intent(v.getContext(), SearchableActivity.class);
                        v.getContext().startActivity(trasferte);

                        break;

                    case 2:
                        Intent drawerMain = new Intent(v.getContext(), SplashActivity.class);
                        v.getContext().startActivity(drawerMain);

                        break;

                    default:
                        Toast.makeText(
                                v.getContext(),
                                "Position: " + position,
                                Toast.LENGTH_LONG).show();
                }
            }
        });

        holder.itemView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            mDragStartListener.onStartDrag(holder);
                            return false;
                        }
                    });
                }
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
