package com.example.domhnallboyle.localgag.Screens.Fragments;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.domhnallboyle.localgag.Engine.Database.Signup;
import com.example.domhnallboyle.localgag.Engine.Database.Signup_Functions;
import com.example.domhnallboyle.localgag.R;
import com.github.rahatarmanahmed.cpv.CircularProgressView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SignupFragment extends Fragment{

    private View v;
    private EditText[] textBoxes = new EditText[4];
    private ImageView[] imageViews = new ImageView[4];
    private Button signup, back;
    private CircularProgressView progressView;

    private boolean[] validation = new boolean[4];

    public SignupFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_signup, container, false);

        progressView = (CircularProgressView) v.findViewById(R.id.progress_view_signup);
        progressView.setVisibility(View.INVISIBLE);

        textBoxes[0] = (EditText)v.findViewById(R.id.username_signup);
        textBoxes[1] = (EditText)v.findViewById(R.id.email_signup);
        textBoxes[2] = (EditText)v.findViewById(R.id.password_signup);
        textBoxes[3] = (EditText)v.findViewById(R.id.password_confirm_signup);

        imageViews[0] = (ImageView)v.findViewById(R.id.usernameTick);
        imageViews[1] = (ImageView)v.findViewById(R.id.emailTick);
        imageViews[2] = (ImageView)v.findViewById(R.id.passwordTick);
        imageViews[3] = (ImageView)v.findViewById(R.id.password2Tick);

        signup = (Button)v.findViewById(R.id.signup_signup);
        back = (Button)v.findViewById(R.id.back_signup);

        validation[0] = false;
        validation[1] = false;
        validation[2] = false;
        validation[3] = false;

        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new AccelerateInterpolator());
        fadeIn.setStartOffset(200);
        fadeIn.setDuration(500);
        for (EditText textBox: textBoxes)
        {
            textBox.setAnimation(fadeIn);
            textBox.setVisibility(View.VISIBLE);
        }
        for (ImageView imageView: imageViews)
        {
            imageView.setAnimation(fadeIn);
            imageView.setVisibility(View.INVISIBLE);
        }
        signup.setAnimation(fadeIn);
        signup.setVisibility(View.VISIBLE);
        back.setAnimation(fadeIn);
        back.setVisibility(View.VISIBLE);

        for (int i=0; i<textBoxes.length-1; i++)
        {
            final int index = i;
            imageViews[index].setVisibility(View.VISIBLE);
            textBoxes[index].setOnKeyListener(new View.OnKeyListener() {
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    // If the event is a key-down event on the "enter" button
                    if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                        // Perform action on key press
                        if (validated(textBoxes[index], textBoxes[index].getHint().toString())) {
                            imageViews[index].setImageResource(R.drawable.correct_validation);
                            validation[index] = true;
                        } else {
                            imageViews[index].setImageResource(R.drawable.incorrect_validation);
                            validation[index] = false;
                        }
                        return true;
                    }
                    return false;
                }
            });
        }

        textBoxes[3].setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    imageViews[3].setVisibility(View.VISIBLE);
                    String password = textBoxes[2].getText().toString();
                    String confirmPassword = textBoxes[3].getText().toString();
                    if (confirmPassword.equalsIgnoreCase(password)) {
                        imageViews[3].setImageResource(R.drawable.correct_validation);
                        validation[3] = true;
                    }
                    else
                    {
                        imageViews[3].setImageResource(R.drawable.incorrect_validation);
                        validation[3] = false;
                        toastText("The passwords entered don't match!");
                    }
                    return true;
                }
                return false;
            }
        });

        signup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                //signup done here
                boolean finished = false;
                for (boolean isValid: validation)
                {
                    if (isValid)
                        finished = true;
                    else {
                        finished = false;
                        break;
                    }
                }
                if (finished) {
                    String username = textBoxes[0].getText().toString();
                    String email = textBoxes[1].getText().toString();
                    String password = textBoxes[2].getText().toString();
                    SimpleDateFormat current_date_format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    Date date = new Date();
                    String date_created = current_date_format.format(date).toString();
                    new Signup(getActivity().getApplicationContext(), progressView).execute(username, email, password, "", "", date_created);
                }
                else
                    toastText("Some fields have not been filled in correctly!");
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //remove fragment
                getFragmentManager().beginTransaction().remove(getFragmentManager().findFragmentById(R.id.FRAGMENT_PLACEHOLDER)).commit();

                //refresh current fragment
                Fragment currentFragment = getActivity().getFragmentManager().findFragmentById(R.id.fragment_container);
                FragmentTransaction fragTransaction =  (getActivity()).getFragmentManager().beginTransaction();
                fragTransaction.detach(currentFragment);
                fragTransaction.attach(currentFragment);
                fragTransaction.commit();
            }
        });

        return v;
    }

    private boolean validated(EditText textBox, String hint)
    {
        String text = textBox.getText().toString();
        boolean validated = false;
        int characterLength = text.length();
        int numOfDigits = getNumberOfDigits(text);
        switch(hint){
            case "Username":
                if (characterLength >= 5 && characterLength <=40)
                    if (numOfDigits >= 2) {
                        if (checkAvailability(text, "username"))
                        {
                            validated = true;
                        }
                        else
                            toastText("Username has been taken!");
                    }
                    else
                        toastText("Username must have at least 2 digits!");
                else
                    toastText("Username must be between 5-40 characters!");
                break;
            case "Email-Address":
                if (checkEmailFormat(text))
                {
                    if (checkAvailability(text, "email"))
                    {
                        validated = true;
                    }
                    else
                        toastText("Email has been used already!");
                }
                else
                    toastText("Email is in the wrong format!");
                break;
            case "Password":
                if (characterLength >= 5 && characterLength <= 40)
                    if (numOfDigits >= 2)
                        validated = true;
                    else
                        toastText("Password must have at least 2 digits!");
                else
                    toastText("Password must be between 5-40 characters!");
                break;
        }
        return validated;
    }

    private int getNumberOfDigits(String text) {
        int numOfDigits = 0;
        for (int i=0; i < text.length(); i++)
        {
            char character = text.charAt(i);
            if (Character.isDigit(character))
                numOfDigits++;
        }
        return numOfDigits;
    }

    private boolean checkEmailFormat(String email)
    {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void toastText(String text)
    {
        Toast.makeText(getContext(), text, Toast.LENGTH_LONG).show();
    }

    private boolean checkAvailability(String text, String type)
    {
        String result = "";
        boolean available = false;

        try
        {
            result = new Signup_Functions().execute(text, type).get();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        try
        {
            JSONObject jsonObject = new JSONObject(result);

            String query_result = jsonObject.getString("query_result");
            if (query_result.equals("NOT_TAKEN"))
            {
                available = true;
            }
            else
            {
                available = false;
            }
        }
        catch(JSONException e) {
            e.printStackTrace();
            Toast.makeText(getActivity().getApplicationContext(), "Error parsing JSON data", Toast.LENGTH_LONG).show();
        }
        return available;
    }
}
