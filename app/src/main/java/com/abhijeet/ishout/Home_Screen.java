package com.abhijeet.ishout;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Locale;

public class Home_Screen extends AppCompatActivity {

    private boolean isFabMenuOpen = false;
    private static final int VOICE_REQUEST_CODE = 1001;

    // FABs and labels
    FloatingActionButton fabMain, fabWrite, fabSearch;
    TextView fabWriteLabel, fabSearchLabel;

    // Search dialog references
    private AlertDialog searchDialog;
    private TextInputEditText searchInput;

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

        // Write Post FAB action
        fabWrite.setOnClickListener(v -> showCreatePostDialog());

        // Search FAB action
        fabSearch.setOnClickListener(v -> showSearchDialog());
    }

    /** --- Create Post Dialog --- **/
    private void showCreatePostDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_create_post, null);
        builder.setView(dialogView);

        AlertDialog dialog = builder.create();

        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setLayout(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            dialog.getWindow().setGravity(Gravity.BOTTOM);
            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogSlideAnimation;
        }

        dialog.show();
    }

    /** --- Search Dialog with Voice Input --- **/
    private void showSearchDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.activity_dialog_search, null);
        builder.setView(dialogView);

        searchDialog = builder.create();

        if (searchDialog.getWindow() != null) {
            searchDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            searchDialog.getWindow().setLayout(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            searchDialog.getWindow().setGravity(Gravity.BOTTOM);
            searchDialog.getWindow().getAttributes().windowAnimations = R.style.DialogSlideAnimation;
        }

        searchDialog.show();

        // Initialize search input
        TextInputLayout searchInputLayout = dialogView.findViewById(R.id.search_input_layout);
        searchInput = dialogView.findViewById(R.id.edit_search_text);

        if (searchInput != null) {
            searchInput.requestFocus();
            searchDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        }

        // Handle mic icon click
        if (searchInputLayout != null) {
            searchInputLayout.setEndIconOnClickListener(v -> startVoiceInput());
        }
    }

    /** --- Start Voice Recognition --- **/
    private void startVoiceInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak now...");
        try {
            startActivityForResult(intent, VOICE_REQUEST_CODE);
        } catch (Exception e) {
            Toast.makeText(this, "Voice input not supported on this device", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == VOICE_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            ArrayList<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (results != null && !results.isEmpty()) {
                if (searchInput != null) {
                    searchInput.setText(results.get(0)); // Set recognized text
                }
            }
        }
    }

    /** --- FAB Menu Logic --- **/
    private void toggleFabMenu() {
        if (isFabMenuOpen) {
            // Hide in reverse order
            hideFabWithZoom(fabSearch, fabSearchLabel);
            hideFabWithZoom(fabWrite, fabWriteLabel);
            fabMain.animate().rotation(0).setDuration(200).start();
        } else {
            // Show with staggered effect
            showFabWithZoom(fabWrite, fabWriteLabel, 0);
            showFabWithZoom(fabSearch, fabSearchLabel, 100);
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
