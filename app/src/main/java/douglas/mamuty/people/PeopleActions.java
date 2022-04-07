package douglas.mamuty.people;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class PeopleActions extends AppCompatActivity {

    EditText name, email, birthday;
    ImageView avatar;
    PeopleModel people = new PeopleModel();
    final int PICK_IMAGE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people_actions);
        name = (EditText) findViewById(R.id.name);
        email = (EditText) findViewById(R.id.email);
        birthday = (EditText) findViewById(R.id.birthday);
        avatar = (ImageView) findViewById(R.id.avatar);

        final DatabaseHelper helper = new DatabaseHelper(this);

        Bundle b = getIntent().getExtras();
        if(b != null){
            people = helper.get(b.getInt("id"));
            name.setText(people.getName());
            email.setText(people.getEmail());
            birthday.setText(people.getBirthday());
            avatar.setBackground(new BitmapDrawable(getApplicationContext().getResources(), BitmapFactory.decodeByteArray(people.getAvatar(), 0, people.getAvatar().length)));
        } else {
            avatar.setBackgroundResource(R.drawable.avatar);
        }

        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, PICK_IMAGE);
            }
        });

        findViewById(R.id.save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!name.getText().toString().isEmpty() && !email.getText().toString().isEmpty() && !birthday.getText().toString().isEmpty()) {
                    people.setName(name.getText().toString());
                    people.setEmail(email.getText().toString());
                    people.setBirthday(birthday.getText().toString());
                    avatar.buildDrawingCache();

                    Bitmap avatarBit = avatar.getDrawingCache();
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    avatarBit.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                    people.setAvatar(bos.toByteArray());

                    //CHECK IF FOR UPDATE
                    if (b != null) {
                        if (helper.update(people)){
                            Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }else {
                            Toast.makeText(getApplicationContext(), "NOT Updated", Toast.LENGTH_LONG).show();
                        };
                        return;
                    } else {
                        //INSERT SECTION
                        if (helper.insert(people)) {
                            Toast.makeText(getApplicationContext(), "Inserted", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        } else {
                            Toast.makeText(getApplicationContext(), "NOT Inserted", Toast.LENGTH_LONG).show();
                        }
                    }

                } else {
                    name.setError("Enter Name");
                    email.setError("Enter Email");
                    birthday.setError("Enter Birthday");
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);


        if (resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                avatar.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();
            }

        }else {
            Toast.makeText(getApplicationContext(), "You haven't picked Image",Toast.LENGTH_LONG).show();
        }
    }
}