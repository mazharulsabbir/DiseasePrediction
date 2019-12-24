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
import school.of.thought.utils.OnItemClickListener;

public class DiseaseListAdapter extends RecyclerView.Adapter<DiseaseListAdapter.DiseaseHolder> {
    private List<Disease> diseaseList;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public DiseaseListAdapter(List<Disease> diseaseList, Context context) {
        this.diseaseList = diseaseList;
        this.context = context;
    }

    @NonNull
    @Override
    public DiseaseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_disease, parent, false);
        return new DiseaseHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DiseaseHolder holder, int position) {
        Disease disease = diseaseList.get(position);

        holder.name.setText(disease.getName());
        holder.shortDesc.setText(disease.getDescription());

        if (disease.getImage().isEmpty())
            Glide.with(context).load(R.drawable.ic_launcher_background).into(holder.image);
        else Glide.with(context).load(disease.getImage()).into(holder.image);

    }


    @Override
    public int getItemCount() {
        return diseaseList.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public class DiseaseHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView image;
        private TextView name;
        private TextView shortDesc;

        public DiseaseHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.doctor_image);
            name = itemView.findViewById(R.id.doctor_name);
            shortDesc = itemView.findViewById(R.id.disease_desc);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int pos = getAdapterPosition();
            if (pos != RecyclerView.NO_POSITION) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(pos);
                }
            }
        }
    }
}
