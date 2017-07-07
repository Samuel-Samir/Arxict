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
import butterknife.internal.Utils;
import samuel.example.com.arxict.R;
import samuel.example.com.arxict.model.UserData;
import samuel.example.com.arxict.model.dataBase.UserContract;
import samuel.example.com.arxict.model.dataBase.UserDbHelper;
import samuel.example.com.arxict.utilities;

import static samuel.example.com.arxict.utilities.saveUserToSharedPreferences;

public class SignUpActivity extends AppCompatActivity {

    @BindView(R.id.sign_up_username)
    EditText userNameEditText;

    @BindView(R.id.sign_up_email)
    EditText emailEditText;

    @BindView(R.id.sign_up_password)
    EditText passwordEditText;

    @BindView(R.id.sign_up_finish)
    Button finishButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(SignUpActivity.this);

        // set Content Description for accessibility
        userNameEditText.setContentDescription(getResources().getString(R.string.enter_username));
        emailEditText.setContentDescription(getResources().getString(R.string.enter_mail));
        passwordEditText.setContentDescription(getResources().getString(R.string.enter_password));

        final UserDbHelper userDbHelper = new UserDbHelper(getBaseContext());
        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if ( userNameEditText.getText().toString().equals(""))
                {
                    userNameEditText.setError( getResources().getString(R.string.field_required) );
                }

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
                    String name  = userNameEditText.getText().toString();
                    String email  = emailEditText.getText().toString();
                    String pass  = passwordEditText.getText().toString();
                    UserData userData = new UserData(name , email ,pass);
                    double index = utilities.addNewUserTODb(userDbHelper , userData);

                    // if email already exist
                    if (index ==-2)
                    {
                        Toast.makeText(getBaseContext() , getResources().getString(R.string.email_exist) ,Toast.LENGTH_LONG).show();
                    }
                    if (index > 0)
                    {
                        saveUserToSharedPreferences (userData , getBaseContext());
                        startActivity(new Intent(SignUpActivity.this , MainActivity.class));
                        SignUpActivity.this.finish();

                    }

                }
            }
        });
    }
}
