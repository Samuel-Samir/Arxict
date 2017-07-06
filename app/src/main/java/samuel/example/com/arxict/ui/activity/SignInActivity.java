package samuel.example.com.arxict.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import samuel.example.com.arxict.R;
import samuel.example.com.arxict.model.UserData;
import samuel.example.com.arxict.model.dataBase.UserDbHelper;

import static samuel.example.com.arxict.utilities.getUserFromDb;
import static samuel.example.com.arxict.utilities.getUserFromSharedPreferences;

public class SignInActivity extends AppCompatActivity {

    @BindView(R.id.input_email)
    EditText emailEditText;

    @BindView(R.id.input_password)
    EditText passwordEditText;

    @BindView(R.id.btn_login)
    Button loginButton;

    @BindView(R.id.link_signup)
    Button signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        ButterKnife.bind(SignInActivity.this);
        final UserDbHelper userDbHelper = new UserDbHelper(getBaseContext());
        UserData userData = getUserFromSharedPreferences (getBaseContext());
        if (userData==null || userData.getUserEmail().equals("-1")) {

            signUpButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    startActivity(new Intent(SignInActivity.this, SignUpActivity.class));

                }
            });

            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String email  = emailEditText.getText().toString();
                    String password = passwordEditText.getText().toString();


                    if ( emailEditText.getText().toString().equals(""))
                    {
                        emailEditText.setError( getResources().getString(R.string.field_required) );
                    }
                    if (passwordEditText.getText().toString().equals(""))
                    {
                        passwordEditText.setError( getResources().getString(R.string.field_required) );

                    }
                    else

                    {
                        UserData userData = getUserFromDb(userDbHelper , email);
                        if (userData==null || !userData.getUserEmail().equals(email) || !userData.getUserPassword().equals(password))
                        {
                            Toast.makeText(getBaseContext() ,getResources().getString(R.string.logIn_error) ,Toast.LENGTH_LONG).show();
                        }
                        else {
                            startActivity(new Intent(SignInActivity.this, MainActivity.class));
                        }
                    }

                }
            });
        }
        else  {
            startActivity(new Intent(SignInActivity.this, MainActivity.class));

        }
    }
}
