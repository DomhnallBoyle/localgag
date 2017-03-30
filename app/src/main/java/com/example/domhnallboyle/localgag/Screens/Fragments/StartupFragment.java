package com.example.domhnallboyle.localgag.Screens.Fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;

import com.example.domhnallboyle.localgag.Helpers.Device;
import com.example.domhnallboyle.localgag.Helpers.DeviceStorage;
import com.example.domhnallboyle.localgag.R;
import com.example.domhnallboyle.localgag.Screens.Activities.MainScreenActivity;
import com.example.domhnallboyle.localgag.Screens.Fragments.LoginFragment;
import com.example.domhnallboyle.localgag.Screens.Fragments.SignupFragment;

/**
 * Created by DomhnallBoyle on 08/05/2016.
 */
public class StartupFragment extends Fragment {

    private View v;
    private ImageView logo;
    private Button login, signup;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_startup, container, false);

        logo = (ImageView)v.findViewById(R.id.logo);
        login = (Button)v.findViewById(R.id.login);
        signup = (Button)v.findViewById(R.id.signup);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fadeOutAnimation();

                FragmentManager childFragMan = getChildFragmentManager();
                FragmentTransaction childFragTrans = childFragMan.beginTransaction();
                LoginFragment loginFragment = new LoginFragment();
                childFragTrans.add(R.id.FRAGMENT_PLACEHOLDER, loginFragment);
                childFragTrans.addToBackStack("B");
                childFragTrans.commit();
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fadeOutAnimation();

                FragmentManager childFragMan = getChildFragmentManager();
                FragmentTransaction childFragTrans = childFragMan.beginTransaction();
                SignupFragment signupFragment = new SignupFragment();
                childFragTrans.add(R.id.FRAGMENT_PLACEHOLDER, signupFragment);
                childFragTrans.addToBackStack("B");
                childFragTrans.commit();
            }
        });

        Runnable startupRunnable = new Runnable(){
            @Override
            public void run()
            {

                if (loggedInAlready(getActivity().getApplicationContext()))
                {
                    Intent intent = new Intent(getActivity().getApplicationContext(), MainScreenActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    getActivity().getApplicationContext().startActivity(intent);
                }
                else
                {
                    //new TranslateAnimation(xFrom, xTo, yFrom, yTo)
                    TranslateAnimation logoAnimation = new TranslateAnimation(0, 0, 0, logo.getY() - Device.getScreenHeight(getActivity().getApplicationContext()) / 1.75f);
                    logoAnimation.setDuration(2000);  // animation duration
                    logoAnimation.setFillAfter(true);
                    logo.startAnimation(logoAnimation);  // start animation

                    Animation fadeIn = new AlphaAnimation(0, 1);  // the 1, 0 here notifies that we want the opacity to go from opaque (1) to transparent (0)
                    fadeIn.setInterpolator(new AccelerateInterpolator());
                    fadeIn.setStartOffset(1000); // Start fading out after 500 milli seconds
                    fadeIn.setDuration(1000); // Fadeout duration should be 1000 milli seconds
                    login.setAnimation(fadeIn);
                    signup.setAnimation(fadeIn);
                    login.setVisibility(View.VISIBLE);
                    signup.setVisibility(View.VISIBLE);
                }
            }
        };
        Handler h = new Handler();
        h.postDelayed(startupRunnable, 2000);

        return v;
    }

    private void fadeOutAnimation()
    {
        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setInterpolator(new AccelerateInterpolator());
        fadeOut.setStartOffset(200);
        fadeOut.setDuration(500);
        login.setAnimation(fadeOut);
        signup.setAnimation(fadeOut);
        login.setVisibility(View.INVISIBLE);
        signup.setVisibility(View.INVISIBLE);
    }

    private boolean loggedInAlready(Context context)
    {
        //0 - Not logged In
        //1 - Logged In
        int loggedInAlready = DeviceStorage.getIntegerSharedPreferences(context, "Session", 0);
        if (loggedInAlready == 0)
            return false;
        else
            return true;
    }
}
