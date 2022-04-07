package douglas.mamuty.people;

import android.util.Log;

public class PeopleModel {
    private int id;
    private String name, email,birthday;
    private static final String TAG = "peopleModel";

    public PeopleModel(int id, String name, String email, String birthday){
        this.id= id;
        this.name = name;
        this.email = email;
        this.birthday = birthday;
    }

    public PeopleModel(){}


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
}
