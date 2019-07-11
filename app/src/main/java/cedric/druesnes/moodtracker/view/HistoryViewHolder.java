package cedric.druesnes.moodtracker.view;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.ButterKnife;



public class HistoryViewHolder extends RecyclerView.ViewHolder {

    public HistoryViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }


}
