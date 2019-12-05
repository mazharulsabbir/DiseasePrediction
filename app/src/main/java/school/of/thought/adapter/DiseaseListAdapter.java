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
    private static final int ITEM_POSITION_EVEN = 0;
    private static final int ITEM_POSITION_ODD = 1;
    private List<Disease> diseaseList;
    private Context context;

    public DiseaseListAdapter(List<Disease> diseaseList, Context context) {
        this.diseaseList = diseaseList;
        this.context = context;
    }

    @NonNull
    @Override
    public DiseaseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == ITEM_POSITION_ODD)
            return new DiseaseHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_disease, parent, false));
        else
            return new DiseaseHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_disease, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DiseaseHolder holder, int position) {
        Disease disease = diseaseList.get(position);

        holder.name.setText(disease.getName());
        holder.shortDesc.setText(disease.getShortDesc());

        Glide.with(context).load(disease.getImageUrl()).error(R.drawable.ic_launcher_background).into(holder.image);
    }

    @Override
    public int getItemViewType(int position) {
        if (position % 2 == 0) {
            return ITEM_POSITION_EVEN;
        } else {
            return ITEM_POSITION_ODD;
        }
    }

    @Override
    public int getItemCount() {
        return diseaseList.size();
    }


    public class DiseaseHolder extends RecyclerView.ViewHolder {
        private ImageView image;
        private TextView name;
        private TextView shortDesc;

        public DiseaseHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.disease_image);
            name = itemView.findViewById(R.id.disease_name);
            shortDesc = itemView.findViewById(R.id.disease_desc);
        }
    }
}
