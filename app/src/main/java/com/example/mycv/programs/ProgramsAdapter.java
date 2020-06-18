package com.example.mycv.programs;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mycv.R;
import com.example.mycv.db.Program;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ProgramsAdapter extends RecyclerView.Adapter<ProgramsAdapter.ProgramsViewHolder> {
    private List<Program> programsList = new ArrayList<>();
    private ProgramCallBack programCallBack;


    public ProgramsAdapter(ProgramCallBack programCallBack){
        this.programCallBack = programCallBack;
    }



    @NonNull
    @Override
    public ProgramsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_program, parent, false);
        return new ProgramsViewHolder(view);

    }


    public void setItems(Collection<Program> programs) {
        programsList.addAll(programs);
        notifyDataSetChanged();
    }

    public void clearItems() {
        programsList.clear();
        notifyDataSetChanged();
    }


    @Override
    public void onBindViewHolder(@NonNull ProgramsViewHolder holder, int position) {
        holder.bind(programsList.get(position));

    }

    @Override
    public int getItemCount() {
        return programsList.size();
    }

    public class ProgramsViewHolder extends RecyclerView.ViewHolder {
        private TextView programName;
        private TextView programDescription;
        private ImageView gpButton;
        private ImageView siteButton;


        public ProgramsViewHolder(@NonNull View itemView) {
            super(itemView);
            programName = itemView.findViewById(R.id.program_name);
            programDescription = itemView.findViewById(R.id.program_description);
            gpButton = itemView.findViewById(R.id.gp_button);
            siteButton = itemView.findViewById(R.id.site_button);
        }

        public void bind(Program program) {
            programName.setText(program.getName());
            programDescription.setText(program.getDescription());
            if(program.getLink().contains("play.google.com")){
                gpButton.setVisibility(View.VISIBLE);
            }else{
               siteButton.setVisibility(View.VISIBLE);
            }

            gpButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    programCallBack.clickGp(program.getLink());
                }
            });
            siteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    programCallBack.clickGp(program.getLink());
                }
            });

        }
    }

    interface ProgramCallBack {
       void clickGp(String link);
    }

}
