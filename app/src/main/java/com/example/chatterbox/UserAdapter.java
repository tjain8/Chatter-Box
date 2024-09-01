package com.example.chatterbox;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.viewholder> {
    Context mainActivity;
    ArrayList<Users> userArrayList;

    public UserAdapter(MainActivity mainActivity, ArrayList<Users> userArrayList) {
        this.mainActivity=mainActivity;
        this.userArrayList=userArrayList;
    }

    @NonNull
    @Override
    public UserAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mainActivity).inflate(R.layout.user_item,parent,false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.viewholder holder, int position) {
        Users users=userArrayList.get(position);
        holder.username.setText(users.userName);
        holder.userStatus.setText(users.status);
        Picasso.get().load(users.profilepic).into(holder.userimage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mainActivity, chatWin.class);
                intent.putExtra("nameeee",users.getUserName());
                intent.putExtra("recieverImg",users.getProfilepic());
                intent.putExtra("uid",users.getUserId());
                mainActivity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userArrayList.size();
    }

    public static class viewholder extends RecyclerView.ViewHolder {
        CircleImageView userimage;
        TextView username;
        TextView userStatus;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            userimage=itemView.findViewById(R.id.userimage);
            username=itemView.findViewById(R.id.username);
            userStatus=itemView.findViewById(R.id.userStatus);
        }
    }
}
