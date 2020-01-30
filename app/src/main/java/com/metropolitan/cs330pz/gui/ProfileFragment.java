package com.metropolitan.cs330pz.gui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.metropolitan.cs330pz.MainActivity;
import com.metropolitan.cs330pz.R;

public class ProfileFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        TextView username = (TextView)v.findViewById(R.id.profile_username);
        TextView email = (TextView)v.findViewById(R.id.profile_email);
        Button logOutBtn = v.findViewById(R.id.logOutBtn);

        logOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logOut();
            }
        });

        username.setText(MainActivity.sharedPreferences.getString("username", ""));
        email.setText(MainActivity.sharedPreferences.getString("email", ""));

        return v;

    }

    public void logOut() {

        MainActivity.prefsEditor.remove("user_logged-in");
        MainActivity.prefsEditor.remove("username");
        MainActivity.prefsEditor.remove("email");
        MainActivity.prefsEditor.remove("passwd");
        MainActivity.prefsEditor.commit();

        Intent goToLogin = new Intent(getActivity().getApplicationContext(), LoginActivity.class);
        goToLogin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        goToLogin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        goToLogin.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        goToLogin.putExtra("EXIT", true);
        startActivity(goToLogin);

        Log.e("Login", "User logged out");

        getActivity().finish();
    }
}
