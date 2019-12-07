package school.of.thought.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import school.of.thought.R;
import school.of.thought.model.Disease;

public class DiseaseListAdapter extends RecyclerView.Adapter<DiseaseListAdapter.DiseaseHolder> {
    private List<Disease> diseaseList;
    private Context context;
    onDiseasesListener mOnDiseasesListener;

    public DiseaseListAdapter(List<Disease> diseaseList, onDiseasesListener onDiseasesListener, Context context) {
        this.diseaseList = diseaseList;
        this.mOnDiseasesListener = onDiseasesListener;
        this.context=context;
    }

    @NonNull
    @Override
    public DiseaseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_disease, parent, false);
        /*if (viewType == ITEM_POSITION_ODD)
            return new DiseaseHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_disease, parent, false));
        else
            return new DiseaseHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_disease, parent, false));*/
        return new DiseaseHolder(itemView,mOnDiseasesListener);
    }

    @Override
    public void onBindViewHolder(@NonNull DiseaseHolder holder, int position) {
        Disease disease = diseaseList.get(position);

        holder.name.setText(disease.getName());
        holder.shortDesc.setText(disease.getShortDesc());

        if (disease.getImageUrl().isEmpty())
            Glide.with(context).load(R.drawable.ic_launcher_background).into(holder.image);
        else Glide.with(context).load(disease.getImageUrl()).into(holder.image);

    }


    @Override
    public int getItemCount() {
        return diseaseList.size();
    }

    public interface onDiseasesListener{
        void onDiseasesClick(int position);
    }



    public class DiseaseHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView image;
        private TextView name;
        private TextView shortDesc;
        onDiseasesListener onDiseasesListener;
        public DiseaseHolder(@NonNull View itemView ,onDiseasesListener onDiseasesListener) {
            super(itemView);

            image = itemView.findViewById(R.id.disease_image);
            name = itemView.findViewById(R.id.disease_name);
            shortDesc = itemView.findViewById(R.id.disease_desc);
            this.onDiseasesListener=onDiseasesListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onDiseasesListener.onDiseasesClick(getAdapterPosition());
        }
    }
}
