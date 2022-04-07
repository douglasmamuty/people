package douglas.mamuty.people;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<PeopleModel> peopleModelsArrayList;
    private PeopleRVAdapter peopleRVAdapter;
    private RecyclerView peoplesRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn = (Button)findViewById(R.id.btnNew);
        final DatabaseHelper helper = new DatabaseHelper(this);
        // initializing our all variables.
        peopleModelsArrayList = new ArrayList<>();


        // getting our people array
        peopleModelsArrayList = helper.list();

        // on below line passing our array lost to our adapter class.
        peopleRVAdapter = new PeopleRVAdapter(peopleModelsArrayList, getApplicationContext());
        peoplesRV = findViewById(R.id.idRVPeoples);

        // setting layout manager for our recycler view.
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        peoplesRV.setLayoutManager(linearLayoutManager);

        // setting our adapter to recycler view.
        peoplesRV.setAdapter(peopleRVAdapter);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), PeopleActions.class));
            }
        });
    }
}