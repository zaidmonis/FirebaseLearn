package com.zaid.firebaselearn;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.zaid.firebaselearn.models.Note;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<Note> notes;
    ListView listView;

    FirebaseAuth firebaseAuth;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        listView = findViewById(R.id.notes_list_view);
        fetchNoteTitles();


    }

    private void fetchNoteTitles(){
        notes = new ArrayList<>();
        db.collection("notes").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                    Note note = documentSnapshot.toObject(Note.class);
                    notes.add(note);
                }
            } else {

            }
            List<String> titles = new ArrayList<>();
            for (Note note : notes) {
                titles.add(note.getTitle());
            }
            loadNoteTitles(titles);
        });
    }

    private void loadNoteTitles(List<String> noteTitles) {
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.list_element,
                R.id.note_element,
                noteTitles);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent intent = new Intent(MainActivity.this, NoteActivity.class);
            intent.putExtra("currentNote", noteTitles.get(i));
            startActivity(intent);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout: {
                firebaseAuth.signOut();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
                break;
            }
            case R.id.action_add_note: {

            }
        }
        return true;
    }
}