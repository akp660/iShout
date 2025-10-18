package com.abhijeet.ishout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Home_Screen extends AppCompatActivity {

    private boolean isFabMenuOpen = false;

    // FABs and labels
    FloatingActionButton fabMain, fabWrite, fabSearch;
    TextView fabWriteLabel, fabSearchLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_screen);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Profile and Notification
        CardView Profile = findViewById(R.id.profile_Btn);
        Profile.setOnClickListener(v -> startActivity(new Intent(Home_Screen.this, Profile_screen.class)));

        CardView Notification = findViewById(R.id.notification_Btn);
        Notification.setOnClickListener(v -> startActivity(new Intent(Home_Screen.this, Notification_screen.class)));

        // FABs and labels
        fabMain = findViewById(R.id.fab_main);
        fabWrite = findViewById(R.id.fab_write);
        fabSearch = findViewById(R.id.fab_search);
        fabWriteLabel = findViewById(R.id.fab_write_label);
        fabSearchLabel = findViewById(R.id.fab_search_label);

        // Initially hide FABs and labels
        hideFabInstantly(fabWrite, fabWriteLabel);
        hideFabInstantly(fabSearch, fabSearchLabel);

        // FAB main click
        fabMain.setOnClickListener(v -> toggleFabMenu());

        // FAB actions (Add your real actions here)
        fabWrite.setOnClickListener(v -> {
            startActivity(new Intent(this, create_post.class));
        });

        fabSearch.setOnClickListener(v -> {
            // startActivity(new Intent(this, SearchActivity.class));
        });
    }

    private void toggleFabMenu() {
        if (isFabMenuOpen) {
            // Hide in reverse order
            hideFabWithZoom(fabSearch, fabSearchLabel);
            hideFabWithZoom(fabWrite, fabWriteLabel);
            fabMain.animate().rotation(0).setDuration(200).start();
        } else {
            // Show with slight delay for second item
            showFabWithZoom(fabWrite, fabWriteLabel, 0);
            showFabWithZoom(fabSearch, fabSearchLabel, 100);
            // fabMain.animate().rotation(45).setDuration(200).start();
        }
        isFabMenuOpen = !isFabMenuOpen;
    }

    private void showFabWithZoom(View fab, View label, long delay) {
        fab.setVisibility(View.VISIBLE);
        label.setVisibility(View.VISIBLE);

        Animation zoomInFab = AnimationUtils.loadAnimation(this, R.anim.zoom_in);
        zoomInFab.setStartOffset(delay);

        Animation zoomInLabel = AnimationUtils.loadAnimation(this, R.anim.zoom_in);
        zoomInLabel.setStartOffset(delay);

        fab.startAnimation(zoomInFab);
        label.startAnimation(zoomInLabel);
    }

    private void hideFabWithZoom(View fab, View label) {
        Animation zoomOutFab = AnimationUtils.loadAnimation(this, R.anim.zoom_out);
        Animation zoomOutLabel = AnimationUtils.loadAnimation(this, R.anim.zoom_out);

        zoomOutFab.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                fab.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });

        zoomOutLabel.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                label.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });

        fab.startAnimation(zoomOutFab);
        label.startAnimation(zoomOutLabel);
    }

    private void hideFabInstantly(View fab, View label) {
        fab.setVisibility(View.GONE);
        label.setVisibility(View.GONE);
    }
}
