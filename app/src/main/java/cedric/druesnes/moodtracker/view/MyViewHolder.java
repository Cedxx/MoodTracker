package cedric.druesnes.moodtracker.view;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.ButterKnife;



public class MyViewHolder extends RecyclerView.ViewHolder {

    public MyViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }


}
