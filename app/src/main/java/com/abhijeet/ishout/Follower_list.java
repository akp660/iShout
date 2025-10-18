package com.abhijeet.ishout;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.abhijeet.ishout.Followers_Adaptor.FollowersAdapter;
import com.abhijeet.ishout.Followers_Adaptor.Followers_ui_model;

import java.util.ArrayList;
import java.util.List;

public class Follower_list extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FollowersAdapter adapter;
    private List<Followers_ui_model> followersList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_follower_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.recyclerViewFollowers);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2)); // two cards per row
        followersList = new ArrayList<>();
        loadDummyData();

        adapter = new FollowersAdapter(followersList);
        recyclerView.setAdapter(adapter);
    }
    private void loadDummyData() {
        followersList.add(new Followers_ui_model("Abhijeet Kumar Pandey", R.drawable.profile_image));
        followersList.add(new Followers_ui_model("Tushar Sharma", R.drawable.profile_pic));
        followersList.add(new Followers_ui_model("Riya Verma", R.drawable.profile_image));
        followersList.add(new Followers_ui_model("Ankit Singh", R.drawable.profile_pic));
        followersList.add(new Followers_ui_model("Priya Raj", R.drawable.profile_image));
        followersList.add(new Followers_ui_model("Devansh Mehta", R.drawable.profile_pic));
        followersList.add(new Followers_ui_model("Abhijeet Kumar Pandey", R.drawable.profile_image));
        followersList.add(new Followers_ui_model("Tushar Sharma", R.drawable.profile_pic));
        followersList.add(new Followers_ui_model("Riya Verma", R.drawable.profile_image));
        followersList.add(new Followers_ui_model("Ankit Singh", R.drawable.profile_pic));
        followersList.add(new Followers_ui_model("Priya Raj", R.drawable.profile_image));
        followersList.add(new Followers_ui_model("Devansh Mehta", R.drawable.profile_pic));
        followersList.add(new Followers_ui_model("Abhijeet Kumar Pandey", R.drawable.profile_image));
        followersList.add(new Followers_ui_model("Tushar Sharma", R.drawable.profile_pic));
        followersList.add(new Followers_ui_model("Riya Verma", R.drawable.profile_image));
        followersList.add(new Followers_ui_model("Ankit Singh", R.drawable.profile_pic));
        followersList.add(new Followers_ui_model("Priya Raj", R.drawable.profile_image));
        followersList.add(new Followers_ui_model("Devansh Mehta", R.drawable.profile_pic));
        followersList.add(new Followers_ui_model("Abhijeet Kumar Pandey", R.drawable.profile_image));
        followersList.add(new Followers_ui_model("Tushar Sharma", R.drawable.profile_pic));
        followersList.add(new Followers_ui_model("Riya Verma", R.drawable.profile_image));
        followersList.add(new Followers_ui_model("Ankit Singh", R.drawable.profile_pic));
        followersList.add(new Followers_ui_model("Priya Raj", R.drawable.profile_image));
        followersList.add(new Followers_ui_model("Devansh Mehta", R.drawable.profile_pic));
        followersList.add(new Followers_ui_model("Abhijeet Kumar Pandey", R.drawable.profile_image));
        followersList.add(new Followers_ui_model("Tushar Sharma", R.drawable.profile_pic));
        followersList.add(new Followers_ui_model("Riya Verma", R.drawable.profile_image));
        followersList.add(new Followers_ui_model("Ankit Singh", R.drawable.profile_pic));
        followersList.add(new Followers_ui_model("Priya Raj", R.drawable.profile_image));
        followersList.add(new Followers_ui_model("Devansh Mehta", R.drawable.profile_pic));
    }
}