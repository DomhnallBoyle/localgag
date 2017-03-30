package com.example.domhnallboyle.localgag.Screens.Fragments;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.domhnallboyle.localgag.Engine.Database.Login;
import com.example.domhnallboyle.localgag.R;
import com.github.rahatarmanahmed.cpv.CircularProgressView;


public class LoginFragment extends Fragment {

    private View v;
    private EditText username, password;
    private Button login, back;
    private CircularProgressView progressView;

    public LoginFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_login, container, false);

        progressView = (CircularProgressView) v.findViewById(R.id.progress_view_login);
        progressView.setVisibility(View.INVISIBLE);

        username = (EditText)v.findViewById(R.id.username_email_login);
        password = (EditText)v.findViewById(R.id.password_login);
        login = (Button)v.findViewById(R.id.login_login);
        back = (Button)v.findViewById(R.id.back_login);

        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new AccelerateInterpolator());
        fadeIn.setStartOffset(200);
        fadeIn.setDuration(500);
        username.setAnimation(fadeIn);
        password.setAnimation(fadeIn);
        login.setAnimation(fadeIn);
        back.setAnimation(fadeIn);
        username.setVisibility(View.VISIBLE);
        password.setVisibility(View.VISIBLE);
        login.setVisibility(View.VISIBLE);
        back.setVisibility(View.VISIBLE);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                String username_email = username.getText().toString();
                String pass = password.getText().toString();
                new Login(getActivity().getApplicationContext(), progressView).execute(username_email, pass);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //remove fragment
                getFragmentManager().beginTransaction().remove(getFragmentManager().findFragmentById(R.id.FRAGMENT_PLACEHOLDER)).commit();

                //refresh current fragment
                Fragment currentFragment = getActivity().getFragmentManager().findFragmentById(R.id.fragment_container);
                FragmentTransaction fragTransaction = (getActivity()).getFragmentManager().beginTransaction();
                fragTransaction.detach(currentFragment);
                fragTransaction.attach(currentFragment);
                fragTransaction.commit();
            }
        });

        return v;
    }

}
