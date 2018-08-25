package com.promise.www.schoollibrary;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class Adapterpdfs extends RecyclerView.Adapter<Adapterpdfs.AllpdfHolder> {
private Context context;
private List<PdfModel> stringList;



    public Adapterpdfs(Context context, List<PdfModel> stringList) {
        this.context = context;
        this.stringList = stringList;
    }


    @NonNull
    @Override
    public AllpdfHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View  view = LayoutInflater.from(parent.getContext()).inflate(R.layout.simglepdf_lists, parent, false);
        return new AllpdfHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllpdfHolder holder, int position) {
        PdfModel namew= stringList.get(position);
        holder.name.setText(namew.getName());

    }



    @Override
    public int getItemCount() {
        return (stringList != null)?stringList.size():0;
    }

    public class AllpdfHolder extends RecyclerView.ViewHolder  {
       public TextView name ;


        AllpdfHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.name);

        }





        // parent activity will implement this method to respond to click events

    }


}
