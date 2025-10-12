package com.abhijeet.ishout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class FollowersAdapter extends RecyclerView.Adapter<FollowersAdapter.ViewHolder> {

    private List<Followers_ui_model> followersList;

    public FollowersAdapter(List<Followers_ui_model> followersList) {
        this.followersList = followersList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate your renamed layout file here
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.followers_card_ui, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Followers_ui_model follower = followersList.get(position);
        holder.name.setText(follower.getName());
        holder.profileImage.setImageResource(follower.getImageResId());
    }

    @Override
    public int getItemCount() {
        return followersList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView profileImage;
        MaterialCardView followButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.follower_user_name);
            profileImage = itemView.findViewById(R.id.profile_image);
            followButton = itemView.findViewById(R.id.follow_button);
        }
    }
}
