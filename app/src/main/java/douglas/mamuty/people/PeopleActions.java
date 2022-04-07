package douglas.mamuty.people;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class PeopleActions extends AppCompatActivity {

    EditText name, email, birthday;
    PeopleModel people;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people_actions);
        name = (EditText) findViewById(R.id.name);
        email = (EditText) findViewById(R.id.email);
        birthday = (EditText) findViewById(R.id.birthday);

        final DatabaseHelper helper = new DatabaseHelper(this);

        Bundle b = getIntent().getExtras();
        if(b != null){
            people = helper.get(b.getInt("id"));
            name.setText(people.getName());
            email.setText(people.getEmail());
            birthday.setText(people.getBirthday());
        }


            findViewById(R.id.save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (b != null) {
                    people.setName(name.getText().toString());
                    people.setEmail(email.getText().toString());
                    people.setBirthday(birthday.getText().toString());
                    if (helper.update(people)){
                        Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    }else {
                        Toast.makeText(getApplicationContext(), "NOT Updated", Toast.LENGTH_LONG).show();
                    };
                    return;
                }

                if (!name.getText().toString().isEmpty() && !email.getText().toString().isEmpty() && !birthday.getText().toString().isEmpty()) {
                    if (helper.insert(name.getText().toString(), email.getText().toString(), birthday.getText().toString())) {
                        Toast.makeText(getApplicationContext(), "Inserted", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    } else {
                        Toast.makeText(getApplicationContext(), "NOT Inserted", Toast.LENGTH_LONG).show();
                    }
                } else {
                    name.setError("Enter Name");
                    email.setError("Enter Email");
                    birthday.setError("Enter Birthday");
                }
            }
        });

    }
}