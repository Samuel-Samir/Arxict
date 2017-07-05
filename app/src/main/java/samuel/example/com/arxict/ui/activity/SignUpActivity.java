package samuel.example.com.arxict.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import samuel.example.com.arxict.R;

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
        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this , MainActivity.class));
            }
        });
    }
}
