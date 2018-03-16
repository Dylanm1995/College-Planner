package ie.wit.collegeplanner.activities;
/*
References
https://inducesmile.com/android/how-to-create-android-spl
 */
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ie.wit.collegeplanner.R;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }
}
