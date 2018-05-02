package com.akramlebcir.mac.drivinglicense.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.akramlebcir.mac.drivinglicense.R;
import com.akramlebcir.mac.drivinglicense.helper.FlipAnimator;
import com.akramlebcir.mac.drivinglicense.model.Infraction;

import java.util.ArrayList;
import java.util.List;


public class InfractionAdapter extends RecyclerView.Adapter<InfractionAdapter.MyViewHolder> {
    private Context mContext;
    private List<Infraction> infractions;
    private InfractionAdapterListener listener;
    public SparseBooleanArray selectedItems;

    // array used to perform multiple animation at once
    private SparseBooleanArray animationItemsIndex;
    private boolean reverseAllAnimations = false;

    // index is used to animate only the selected row
    // dirty fix, find a better solution
    private static int currentSelectedIndex = -1;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        public TextView infractionname, point, prix, iconText, timestamp;
        public ImageView iconImp, imgProfile;
        public LinearLayout messageContainer;
        public RelativeLayout iconContainer, iconBack, iconFront;

        public MyViewHolder(View view) {
            super(view);
            infractionname = view.findViewById(R.id.from);
            point = view.findViewById(R.id.txt_primary);
            prix = view.findViewById(R.id.txt_secondary);
            iconText = view.findViewById(R.id.icon_text);
            timestamp = view.findViewById(R.id.timestamp);
            iconBack =  view.findViewById(R.id.icon_back);
            iconFront =  view.findViewById(R.id.icon_front);
            iconImp =  view.findViewById(R.id.icon_star);
            imgProfile =  view.findViewById(R.id.icon_profile);
            messageContainer = view.findViewById(R.id.message_container);
            iconContainer =  view.findViewById(R.id.icon_container);
            view.setOnLongClickListener(this);
        }

        @Override
        public boolean onLongClick(View view) {
            listener.onRowLongClicked(getAdapterPosition());
            view.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
            return true;
        }
    }


    public InfractionAdapter(Context mContext, List<Infraction> infractions, InfractionAdapterListener listener) {
        this.mContext = mContext;
        this.infractions = infractions;
        this.listener = listener;
        selectedItems = new SparseBooleanArray();
        animationItemsIndex = new SparseBooleanArray();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.infraction_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        Infraction infraction = infractions.get(position);

        // displaying text view data
        holder.infractionname.setText(infraction.getInfractionname());
        holder.point.setText(infraction.getPoint()+" point");
        holder.prix.setText(infraction.getPrice()+" DA");
        holder.timestamp.setText(infraction.getInfractiondate());

        // displaying the first letter of From in icon text
        // infraction.getInfractionname().substring(0, 1)
        holder.iconText.setText(infraction.getLevel()+"");

        // change the row state to activated
        holder.itemView.setActivated(selectedItems.get(position, false));

        // change the font style depending on message read status
        applyReadStatus(holder, infraction);

        // handle message star
        applyImportant(holder, infraction);

        // handle icon animation
        applyIconAnimation(holder, position);

        // display profile image
        applyProfilePicture(holder, infraction);

        // apply click events
        applyClickEvents(holder, position);
    }

    private void applyClickEvents(MyViewHolder holder, final int position) {
        holder.iconContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onIconClicked(position);
            }
        });

        holder.iconImp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onIconImportantClicked(position);
            }
        });

        holder.messageContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onMessageRowClicked(position);
            }
        });

        holder.messageContainer.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                listener.onRowLongClicked(position);
                view.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
                return true;
            }
        });
    }

    private void applyProfilePicture(MyViewHolder holder, Infraction infraction) {

            holder.imgProfile.setImageResource(R.drawable.bg_circle);
            holder.imgProfile.setColorFilter(infraction.getLevel());
            holder.iconText.setVisibility(View.VISIBLE);
    }

    private void applyIconAnimation(MyViewHolder holder, int position) {
        if (selectedItems.get(position, false)) {
            holder.iconFront.setVisibility(View.GONE);
            resetIconYAxis(holder.iconBack);
            holder.iconBack.setVisibility(View.VISIBLE);
            holder.iconBack.setAlpha(1);
            if (currentSelectedIndex == position) {
                FlipAnimator.flipView(mContext, holder.iconBack, holder.iconFront, true);
                resetCurrentIndex();
            }
        } else {
            holder.iconBack.setVisibility(View.GONE);
            resetIconYAxis(holder.iconFront);
            holder.iconFront.setVisibility(View.VISIBLE);
            holder.iconFront.setAlpha(1);
            if ((reverseAllAnimations && animationItemsIndex.get(position, false)) || currentSelectedIndex == position) {
                FlipAnimator.flipView(mContext, holder.iconBack, holder.iconFront, false);
                resetCurrentIndex();
            }
        }
    }


    // As the views will be reused, sometimes the icon appears as
    // flipped because older view is reused. Reset the Y-axis to 0
    private void resetIconYAxis(View view) {
        if (view.getRotationY() != 0) {
            view.setRotationY(0);
        }
    }

    public void resetAnimationIndex() {
        reverseAllAnimations = false;
        animationItemsIndex.clear();
    }

    @Override
    public long getItemId(int position) {
        return infractions.get(position).getId();
    }

    private void applyImportant(MyViewHolder holder, Infraction infraction) {
        if (infraction.isPay()) {
            holder.iconImp.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_point_black_24dp));
            holder.iconImp.setColorFilter(ContextCompat.getColor(mContext, R.color.icon_tint_selected));
        } else {
            holder.iconImp.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_point_black_24dp));
            holder.iconImp.setColorFilter(ContextCompat.getColor(mContext, R.color.icon_tint_normal));
        }
    }

    private void applyReadStatus(MyViewHolder holder, Infraction infraction) {
//        if (infraction.isRead()) {
            holder.infractionname.setTypeface(null, Typeface.NORMAL);
            holder.point.setTypeface(null, Typeface.NORMAL);
            holder.infractionname.setTextColor(ContextCompat.getColor(mContext, R.color.subject));
            holder.point.setTextColor(ContextCompat.getColor(mContext, R.color.message));
//        } else {
//            holder.infractionname.setTypeface(null, Typeface.BOLD);
//            holder.point.setTypeface(null, Typeface.BOLD);
//            holder.infractionname.setTextColor(ContextCompat.getColor(mContext, R.color.from));
//            holder.point.setTextColor(ContextCompat.getColor(mContext, R.color.subject));
//        }
    }

    @Override
    public int getItemCount() {
        return infractions.size();
    }

    public void toggleSelection(int pos) {
        currentSelectedIndex = pos;
        if (selectedItems.get(pos, false)) {
            selectedItems.delete(pos);
            animationItemsIndex.delete(pos);
        } else {
            selectedItems.put(pos, true);
            animationItemsIndex.put(pos, true);
        }
        notifyItemChanged(pos);
    }

    public void clearSelections() {
        reverseAllAnimations = true;
        selectedItems.clear();
        notifyDataSetChanged();
    }

    public int getSelectedItemCount() {
        return selectedItems.size();
    }

    public List<Integer> getSelectedItems() {
        List<Integer> items =
                new ArrayList<>(selectedItems.size());
        for (int i = 0; i < selectedItems.size(); i++) {
            items.add(selectedItems.keyAt(i));
        }
        return items;
    }

    public void removeData(int position) {
        infractions.remove(position);
        resetCurrentIndex();
    }

    private void resetCurrentIndex() {
        currentSelectedIndex = -1;
    }

    public interface InfractionAdapterListener {
        void onIconClicked(int position);

        void onIconImportantClicked(int position);

        void onMessageRowClicked(int position);

        void onRowLongClicked(int position);
    }
}