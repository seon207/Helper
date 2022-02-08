package com.example.login;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class WritingAdapter extends RecyclerView.Adapter<WritingAdapter.contentViewHolder>{
        private ArrayList<WriteInfo> arrayList;
        private Context context;
        int position;

    public WritingAdapter(ArrayList<WriteInfo> arrayList, Context context) {
            this.arrayList = arrayList;
            this.context = context;
        }

        @NonNull
        @Override
        public WritingAdapter.contentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.writingitem, parent, false);
            WritingAdapter.contentViewHolder holder = new WritingAdapter.contentViewHolder(view);
            return holder;
        }

    @Override
    public void onBindViewHolder(@NonNull WritingAdapter.contentViewHolder holder, int position) {
        holder.title.setText("제목 : " + (String.valueOf(arrayList.get(position).getTitle())));
        holder.content.setText("내용 : " + (String.valueOf(arrayList.get(position).getContent())));
        holder.writer.setText("작성자 : " + (String.valueOf(arrayList.get(position).getUsername())));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Information.class);
                intent.putExtra("number",getItemCount());
                intent.putExtra("title",arrayList.get(position).getTitle());
                intent.putExtra("content",arrayList.get(position).getContent());
                intent.putExtra("writer",arrayList.get(position).getUsername());
                intent.putExtra("board",arrayList.get(position).getBoard());
                v.getContext().startActivity(intent);
            }
        });



    }



    @Override
        public int getItemCount() {
            return (arrayList!=null? arrayList.size() : 0);
        }

        public class contentViewHolder extends RecyclerView.ViewHolder{

            TextView title;
            TextView content;
            TextView writer;

            public contentViewHolder(@NonNull View itemView) {
                super(itemView);
                this.title = itemView.findViewById(R.id.utitle);
                this.content = itemView.findViewById(R.id.ucontent);
                this.writer = itemView.findViewById(R.id.writer);

            }

        }
public void update(ArrayList<WriteInfo> arrayList){
            this.arrayList=arrayList;
            notifyDataSetChanged();
}
}
