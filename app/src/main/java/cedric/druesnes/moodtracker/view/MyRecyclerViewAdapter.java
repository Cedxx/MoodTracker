package cedric.druesnes.moodtracker.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import cedric.druesnes.moodtracker.Controller.MainActivity;
import cedric.druesnes.moodtracker.Model.MoodModel;
import cedric.druesnes.moodtracker.R;

//RecyclerView and Adapter for the Mood
public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    // Member variable
    private ArrayList<MoodModel> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private ImageButton mImageButtonRow;


    //Set the height and width of the row
    private int width = Resources.getSystem().getDisplayMetrics().widthPixels;
    private LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
            Resources.getSystem().getDisplayMetrics().heightPixels / 7);


    // data is passed into the constructor
    public MyRecyclerViewAdapter(Context context, ArrayList<MoodModel> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_row, parent, false);
        return new ViewHolder(view);

    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

         //set width of RecyclerView and the color of mood for each row
        holder.itemView.setLayoutParams(params);

        //if comment is present retrieve the comment icon and display it on the mood row
        if (!(getComment(position).equals(""))) {
            mImageButtonRow.setImageResource(R.drawable.ic_comment_black_48px);
            mImageButtonRow.setVisibility(View.VISIBLE);
            mImageButtonRow.setOnClickListener(new View.OnClickListener() {

                //OnClick will display a Toast message with the current saved comment when present
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), getComment(position), Toast.LENGTH_SHORT).show();
                }
            });

            //if no comment are present for the mood hide the comment icon
        } else {
            mImageButtonRow.setImageResource(R.drawable.ic_comment_black_48px);
            mImageButtonRow.setVisibility(View.GONE);
        }

        // Switch to assign the correct color of each mood and the width associated with it
        setMoodScreenWidth(position, holder);

        //Start the setDateText method that will display the text schema for the 7 days to display
        setDateText(position, holder);

    }

    // Switch to assign the correct color of each mood and the width associated with it
    private void setMoodScreenWidth (int position, ViewHolder holder){
        switch (getMoodIndex(position)) {
            case 0:
                params.width = width * 20 / 100;
                holder.itemView.setBackgroundColor(holder.itemView.getContext().getResources().getColor(R.color.faded_red));
                break;
            case 1:
                params.width = width * 40 / 100;
                holder.itemView.setBackgroundColor(holder.itemView.getContext().getResources().getColor(R.color.warm_grey));
                break;
            case 2:
                params.width = width * 60 / 100;
                holder.itemView.setBackgroundColor(holder.itemView.getContext().getResources().getColor(R.color.cornflower_blue_65));
                break;
            case 3:
                params.width = width * 80 / 100;
                holder.itemView.setBackgroundColor(holder.itemView.getContext().getResources().getColor(R.color.light_sage));
                break;
            case 4:
                params.width = width;
                holder.itemView.setBackgroundColor(holder.itemView.getContext().getResources().getColor(R.color.banana_yellow));
                break;
            case 10:
                params.width = 0 / 100;
                holder.itemView.setBackgroundColor(holder.itemView.getContext().getResources().getColor(R.color.white));
                break;
        }
    }

    //Switch to display the proper date schema for the week
    private void setDateText(int position, ViewHolder holder){
        switch (position) {
            case 0:
                holder.myTextView.setText(R.string.one_week);
                break;
            case 1:
                holder.myTextView.setText(R.string.six_day);
                break;
            case 2:
                holder.myTextView.setText(R.string.five_day);
                break;
            case 3:
                holder.myTextView.setText(R.string.four_day);
                break;
            case 4:
                holder.myTextView.setText(R.string.three_day);
                break;
            case 5:
                holder.myTextView.setText(R.string.last_day);
                break;
            case 6:
                holder.myTextView.setText(R.string.yesterday);
                break;
        }
    }

    // Limit the total number of rows displayed to 7
    @Override
    public int getItemCount() {
        int limit = 7;
        if (mData.size() > limit) {
            return limit;
        } else {
            return mData.size();
        }
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextView;

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.recyclerComment);
            mImageButtonRow = itemView.findViewById(R.id.imageButtonRow);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
            mImageButtonRow = itemView.findViewById(R.id.imageButtonRow);
        }
    }

    private int getMoodIndex(int position) {
        return mData.get(position).getMoodIndex();
    }

    // convenience method for getting data at click position
    private String getComment(int position) {
        return mData.get(position).getComment();
    }

    // convenience method for getting data at click position
    public String getItem(int id) {
        return mData.get(id).getComment();
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }


}
