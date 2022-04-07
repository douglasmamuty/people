package douglas.mamuty.people;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class PeopleRVAdapter  extends RecyclerView.Adapter<PeopleRVAdapter.ViewHolder>{

        // variable for our array list and context
        private ArrayList<PeopleModel> peopleModelArrayList;
        private Context context;
        private DatabaseHelper helper;


    // constructor
        PeopleRVAdapter(ArrayList<PeopleModel> courseModalArrayList, Context context) {
            this.peopleModelArrayList = courseModalArrayList;
            this.context = context;
            this.helper = new DatabaseHelper(context);
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            // on below line we are inflating our layout
            // file for our recycler view items.
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.people_rv_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            // on below line we are setting data
            // to our views of recycler view item.
            PeopleModel modal = peopleModelArrayList.get(position);
            holder.name.setText(modal.getName());
            holder.email.setText(modal.getEmail());
            holder.birthday.setText(modal.getBirthday());
            holder.avatar.setBackground(new BitmapDrawable(context.getResources(), BitmapFactory.decodeByteArray(modal.getAvatar(), 0, modal.getAvatar().length)));

            //bind the edit action on btn
            holder.btnEdit.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    Intent intent = new Intent(context.getApplicationContext(), PeopleActions.class);
                    Bundle b = new Bundle();
                    b.putInt("id", getId(holder.getAdapterPosition()));
                    intent.putExtras(b);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });

            holder.btnDelete.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    delete(holder.getAdapterPosition());
                }
            });
        }

        public void delete(int position) {
            helper.delete(getId(position));
            peopleModelArrayList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, getItemCount());
        }

        @Override
        public int getItemCount() {
            // returning the size of our array list
            return peopleModelArrayList.size();
        }

        public int getId(int position) {
            return peopleModelArrayList.get(position).getId();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            // creating variables for our text views.
            TextView name, email, birthday;
            Button btnDelete, btnEdit;
            ImageView avatar;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                // initializing our text views
                name = itemView.findViewById(R.id.idName);
                email = itemView.findViewById(R.id.idEmail);
                birthday = itemView.findViewById(R.id.idBirthday);
                btnDelete = itemView.findViewById(R.id.btnDelete);
                btnEdit = itemView.findViewById(R.id.btnEdit);
                avatar = itemView.findViewById(R.id.avatar);
            }
        }

}
