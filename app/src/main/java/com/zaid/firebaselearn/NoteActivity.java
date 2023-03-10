package com.zaid.firebaselearn;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.zaid.firebaselearn.models.Note;

public class NoteActivity extends AppCompatActivity {

    TextInputEditText noteTitle;
    EditText noteText;
    String currentNote;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        initializeUI();
        db = FirebaseFirestore.getInstance();

        currentNote = getIntent().getStringExtra("currentNote");

        if (currentNote != null && !currentNote.isEmpty()) {
            readNote(currentNote);
        }

    }


    private void saveNote() {
        Note note = new Note(noteTitle.getText().toString(), noteText.getText().toString());
        db.collection("notes").document(noteTitle.getText().toString()).set(note).addOnSuccessListener( documentReference -> {
            Toast.makeText(this, "Note Saved!", Toast.LENGTH_SHORT).show();
        }).addOnFailureListener(exception -> {
            Toast.makeText(this, exception.toString(), Toast.LENGTH_SHORT).show();
        });
        // save note
    }

    private void readNote(String title) {
        db.collection("notes").document(title).get().addOnCompleteListener(task -> {
            DocumentSnapshot documentSnapshot = task.getResult();
            Note note = documentSnapshot.toObject(Note.class);

            if (note != null) {
                noteTitle.setText(note.getTitle());
                noteTitle.setEnabled(false);
                noteText.setText(note.getText());
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_Save: {
                saveNote();
                break;
            }
        }
        return true;
    }

    private void initializeUI(){
        noteTitle = findViewById(R.id.note_title);
        noteText = findViewById(R.id.note_text);
    }
}