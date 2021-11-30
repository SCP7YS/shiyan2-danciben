package com.example.vocabularynotebook;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class MyVocabularyActivity extends AppCompatActivity implements WordAdditionDialog.WordAdditionDialogCallback{
    private DatabaseWrapper databaseWrapper;
    private WordAdditionDialog wordAdditionDialog;
    private ListView vocabularyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_vocabulary);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        databaseWrapper = new DatabaseWrapper(this);
        wordAdditionDialog = new WordAdditionDialog();
        wordAdditionDialog.setCallback(this);

        Cursor cursor = databaseWrapper.selectAll(); //Query database
        VocabularyListAdapter adapter = new VocabularyListAdapter(getApplicationContext(), cursor);
        vocabularyList = findViewById(R.id.activity_my_vocabulary_vocabulary_list);
        vocabularyList.setAdapter(adapter);
    }

    @Override
    public void onOkClick() {
        CursorAdapter adapter = (CursorAdapter) vocabularyList.getAdapter();
        Cursor newCursor = databaseWrapper.selectAll(); //Query database
        adapter.changeCursor(newCursor);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        databaseWrapper.closeDatabase();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_my_vocabulary_action_bar_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                wordAdditionDialog.setTitle(getResources().getString(R.string.word_addition_dialog_dialog_title_add));
                wordAdditionDialog.show(getSupportFragmentManager(), "");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public class VocabularyListAdapter extends CursorAdapter{
        public VocabularyListAdapter(Context context, Cursor c) {
            super(context, c, 0);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View view = inflater.inflate(R.layout.vocabulary_list_item, parent, false);

            return view;
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            TextView txtWord = view.findViewById(R.id.vocabulary_list_item_word);
            TextView txtMeaning = view.findViewById(R.id.vocabulary_list_item_meaning);

            txtWord.setText(cursor.getString(1));
            txtMeaning.setText(cursor.getString(2));

        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            if(convertView == null) {
                LayoutInflater inflater = getLayoutInflater();
                convertView = inflater.inflate(R.layout.vocabulary_list_item, parent, false);

                final TextView txtWord = convertView.findViewById(R.id.vocabulary_list_item_word);
                final TextView txtMeaning  = convertView.findViewById(R.id.vocabulary_list_item_meaning);

                final ImageButton btnOption = convertView.findViewById(R.id.vocabulary_list_item_btn_option);
                btnOption.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PopupMenu popupMenu = new PopupMenu(getApplicationContext(), btnOption);
                        popupMenu.getMenuInflater().inflate(R.menu.activity_my_vocabulary_item_option_popup_menu, popupMenu.getMenu());

                        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            public boolean onMenuItemClick(MenuItem item) {
                                switch (item.getItemId()){
                                    case R.id.activity_my_vocabulary_item_option_popup_menu_option_delete:
                                        databaseWrapper.deleteRow(txtWord.getText().toString());
                                        break;
                                    case R.id.activity_my_vocabulary_item_option_popup_menu_option_edit:
                                        wordAdditionDialog.setTitle(getResources().getString(R.string.word_addition_dialog_dialog_title_edit));
                                        wordAdditionDialog.setWord(txtWord.getText().toString());
                                        wordAdditionDialog.setMeaning(txtMeaning.getText().toString());
                                        wordAdditionDialog.show(getSupportFragmentManager(), "");
                                        break;
                                    default:
                                        break;
                                }

                                CursorAdapter adapter = (CursorAdapter) vocabularyList.getAdapter();
                                Cursor newCursor = databaseWrapper.selectAll(); //Query database
                                adapter.changeCursor(newCursor);

                                return true;
                            }
                        });

                        popupMenu.show();
                    }
                });
            }

            return super.getView(position, convertView, parent);
        }
    }
}