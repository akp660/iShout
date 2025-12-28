package com.abhijeet.ishout;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Login_screen extends AppCompatActivity {

    private android.widget.ImageView profilePic;
    private android.net.Uri imageUri;

    // Combined Image Picker Launcher
    private final androidx.activity.result.ActivityResultLauncher<android.content.Intent> imagePickerLauncher = registerForActivityResult(
            new androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    // Check for Camera Bitmap (Thumbnail from intent)
                    android.os.Bundle extras = result.getData().getExtras();
                    if (extras != null && extras.containsKey("data")) {
                        android.graphics.Bitmap imageBitmap = (android.graphics.Bitmap) extras.get("data");
                        if (profilePic != null) {
                            profilePic.setImageBitmap(imageBitmap);
                        }
                    } else if (result.getData().getData() != null) {
                        // Check for Gallery Uri
                        imageUri = result.getData().getData();
                        if (profilePic != null) {
                            profilePic.setImageURI(imageUri);
                        }
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_screen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize Views
        android.view.View loginContainer = findViewById(R.id.login_container);
        android.view.View signupContainer = findViewById(R.id.signup_container);
        android.view.View btnToggleToLogin = findViewById(R.id.login_Btn); // "Do you have an account? Login" text
        android.view.View btnLoginAction = findViewById(R.id.btnLogin); // "Log In" Button
        android.view.View btnToggleToSignup = findViewById(R.id.tvSignup); // "Don't have an account? Sign Up"

        // Profile Pic Views
        androidx.cardview.widget.CardView profilePicContainer = findViewById(R.id.profilePicContainer);
        profilePic = findViewById(R.id.profilePic);

        // Profile Pic Click Listener
        if (profilePicContainer != null) {
            profilePicContainer.setOnClickListener(v -> showImageSelectionDialog());
        }

        // Default State (as per XML): Login GONE, Signup VISIBLE.
        // User request: Default Signup visible. Logic aligns.

        // Toggle to Login Form
        btnToggleToLogin.setOnClickListener(v -> {
            signupContainer.setVisibility(android.view.View.GONE);
            loginContainer.setVisibility(android.view.View.VISIBLE);
        });

        // Optional: Toggle back to Signup Form (for better UX, though not explicitly
        // requested, implied 'toggle')
        if (btnToggleToSignup != null) {
            btnToggleToSignup.setOnClickListener(v -> {
                loginContainer.setVisibility(android.view.View.GONE);
                signupContainer.setVisibility(android.view.View.VISIBLE);
            });
        }

        // Login Action -> Go to Home
        btnLoginAction.setOnClickListener(v -> {
            android.content.Intent intent = new android.content.Intent(Login_screen.this, Home_Screen.class);
            startActivity(intent);
            finish(); // Optional: Close Login screen so back button exits app or goes to splash?
                      // Usually finish.
        });
    }

    private void showImageSelectionDialog() {
        // Intent to pick from Gallery
        android.content.Intent pickIntent = new android.content.Intent(android.content.Intent.ACTION_GET_CONTENT);
        pickIntent.setType("image/*");

        // Intent to take Photo
        android.content.Intent takePhotoIntent = new android.content.Intent(
                android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

        // System Chooser Intent
        android.content.Intent chooserIntent = android.content.Intent.createChooser(pickIntent,
                "Select Profile Picture");

        // Add Camera intent as an extra option
        chooserIntent.putExtra(android.content.Intent.EXTRA_INITIAL_INTENTS,
                new android.content.Intent[] { takePhotoIntent });

        // Launch
        try {
            imagePickerLauncher.launch(chooserIntent);
        } catch (Exception e) {
            android.widget.Toast.makeText(this, "No image picker available", android.widget.Toast.LENGTH_SHORT).show();
        }
    }
}