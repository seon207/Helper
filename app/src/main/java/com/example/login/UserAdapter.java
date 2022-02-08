package com.example.login;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.contentViewHolder> {
    private ArrayList<User> arrayList;
    private Context context;

    public UserAdapter(ArrayList<User> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public contentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user, parent, false);
        contentViewHolder holder = new contentViewHolder(view);
        return holder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull contentViewHolder holder, int position) { //매칭
        holder.uemail.setText("이메일 : " + arrayList.get(position).getId());
        holder.upw.setText("비밀번호 : " + (String.valueOf(arrayList.get(position).getPassword())));
        holder.uname.setText("사용자 이름 : " + arrayList.get(position).getUsername());
    }

    @Override
    public int getItemCount() {
        return (arrayList!=null? arrayList.size() : 0);
    }

    public class contentViewHolder extends RecyclerView.ViewHolder {

        TextView uemail;
        TextView upw;
        TextView uname;

        public contentViewHolder(@NonNull View itemView) {
            super(itemView);
            this.uemail = itemView.findViewById(R.id.uemail);
            this.upw = itemView.findViewById(R.id.upw);
            this.uname = itemView.findViewById(R.id.uname);

        }
    }
}
