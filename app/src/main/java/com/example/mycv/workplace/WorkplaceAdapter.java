package com.example.mycv.workplace;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mycv.R;
import com.example.mycv.db.Job;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class WorkplaceAdapter extends RecyclerView.Adapter<WorkplaceAdapter.WorkplaceViewHolder> {

    private List<Job> jobList = new ArrayList<>();

    @NonNull
    @Override
    public WorkplaceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_workplace, parent, false);
        return new WorkplaceViewHolder(view);

    }


    public void setItems(Collection<Job> workplaces) {
        jobList.addAll(workplaces);
        notifyDataSetChanged();
    }

    public void clearItems() {
        jobList.clear();
        notifyDataSetChanged();
    }


    @Override
    public void onBindViewHolder(@NonNull WorkplaceViewHolder holder, int position) {
        holder.bind(jobList.get(position));

    }

    @Override
    public int getItemCount() {
       return jobList.size();
    }

    public class WorkplaceViewHolder extends RecyclerView.ViewHolder {
        private TextView workplaceCompany;
        private TextView workplacePosition;
        private TextView workplaceDates;
        private TextView workplaceResponsibilitiesLabel;
        private TextView workplaceResponsibilitiesValue;
        private TextView workplaceStackLabel;
        private TextView workplaceStackValue;

        public WorkplaceViewHolder(@NonNull View itemView) {
            super(itemView);
            workplaceCompany = itemView.findViewById(R.id.workplace_company);
            workplacePosition = itemView.findViewById(R.id.workplace_position);
            workplaceDates = itemView.findViewById(R.id.workplace_dates);
            workplaceResponsibilitiesLabel = itemView.findViewById(R.id.workplace_responsibilities_label);
            workplaceResponsibilitiesValue = itemView.findViewById(R.id.workplace_responsibilities_value);
            workplaceStackLabel = itemView.findViewById(R.id.workplace_stack_label);
            workplaceStackValue = itemView.findViewById(R.id.workplace_stack_value);
        }

        public void bind(Job job) {
            workplaceCompany.setText(job.getCompany());
            workplacePosition.setText(job.getPosition());
            workplaceDates.setText(job.getDates());
            workplaceResponsibilitiesValue.setText(job.getResponsibilities());
            if(job.getStack()!=null){
                workplaceStackValue.setText(job.getStack());
            }else{
                workplaceStackValue.setVisibility(View.GONE);
                workplaceStackLabel.setVisibility(View.GONE);
            }
        }
    }
}
