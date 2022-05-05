package com.example.thompsonc196.UI;

import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thompsonc196.Entity.Assessment;
import com.example.thompsonc196.R;

import java.util.List;

public class AssessAdapter extends RecyclerView.Adapter<AssessAdapter.AssessViewHolder> {
    class AssessViewHolder extends RecyclerView.ViewHolder {
        private final TextView assessItemView;
        private AssessViewHolder(View assessView) {
            super(assessView);
            assessItemView = assessView.findViewById(R.id.textView2);
            assessView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final Assessment current = mAssess.get(position);
                    Intent intent = new Intent(context, AssessDetail.class);
                    intent.putExtra("id", current.getAssessID());
                    intent.putExtra("title", current.getAssessTitle());
                    intent.putExtra("start", current.getAssessStart().getTime());
                    intent.putExtra("end", current.getAssessEnd().getTime());
                    intent.putExtra("type", current.getType());
                    context.startActivity(intent);
                }
            });
        }
    }
    private List<Assessment> mAssess;
    private final Context context;
    private final LayoutInflater mInflater;

    public AssessAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public AssessAdapter.AssessViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View assessView = mInflater.inflate(R.layout.assess_list_item, parent, false);
        return new AssessViewHolder(assessView);
    }

    @Override
    public void onBindViewHolder(@NonNull AssessAdapter.AssessViewHolder holder, int position) {
        if (mAssess != null) {
            Assessment current = mAssess.get(position);
            String title = current.getAssessTitle();
            holder.assessItemView.setText(title);
        }
        else {
            holder.assessItemView.setText("No assessment title");
        }

    }

    public void setAssess(List<Assessment> assessments) {
        mAssess = assessments;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mAssess != null) {
            return mAssess.size();
        }
        else {
            return 0;
        }

    }
}
