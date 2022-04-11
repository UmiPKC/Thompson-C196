package com.example.thompsonc196.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thompsonc196.Entity.Assessment;
import com.example.thompsonc196.Entity.Term;
import com.example.thompsonc196.R;

import java.util.List;

public class TermAdapter extends RecyclerView.Adapter<TermAdapter.TermViewHolder> {
    class TermViewHolder extends RecyclerView.ViewHolder {
        private final TextView termItemView;

        private TermViewHolder(View termView) {
            super(termView);
            termItemView = termView.findViewById(R.id.textView2);
            termView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final Term current = mTerm.get(position);
                    Intent intent = new Intent(context, TermDetail.class);
                    intent.putExtra("id", current.getTermID());
                    intent.putExtra("title", current.getTermTitle());
                    intent.putExtra("start", current.getTermStart());
                    intent.putExtra("end", current.getTermEnd());
                    context.startActivity(intent);
                }
            });
        }
    }

    private List<Term> mTerm;
    private final Context context;
    private final LayoutInflater mInflater;

    public TermAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public TermAdapter.TermViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View termView = mInflater.inflate(R.layout.term_list_item, parent, false);
        return new TermViewHolder(termView);
    }

    @Override
    public void onBindViewHolder(@NonNull TermAdapter.TermViewHolder holder, int position) {
        if (mTerm != null) {
            Term current = mTerm.get(position);
            String title = current.getTermTitle();
            holder.termItemView.setText(title);
        } else {
            holder.termItemView.setText("No assessment title");
        }

    }

    public void setTerm(List<Term> terms) {
        mTerm = terms;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mTerm != null) {
            return mTerm.size();
        } else {
            return 0;
        }

    }
}