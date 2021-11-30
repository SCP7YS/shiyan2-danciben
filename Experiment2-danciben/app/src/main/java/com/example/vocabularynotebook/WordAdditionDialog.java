package com.example.vocabularynotebook;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

public class WordAdditionDialog extends DialogFragment {
    WordAdditionDialogCallback callback;
    DatabaseWrapper databaseWrapper;
    String title;
    String word;
    String meaning;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        databaseWrapper = new DatabaseWrapper(getContext());

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.word_addition_dialog, null);

        TextView txtTitle = dialogView.findViewById(R.id.word_addition_dialog_dialog_title);
        final EditText etxtNewWord = dialogView.findViewById(R.id.word_addition_dialog_input_new_word);
        final EditText etxtMeaning = dialogView.findViewById(R.id.word_addition_dialog_input_meaning);
        Button btnOk = dialogView.findViewById(R.id.word_addition_dialog_btn_ok);
        Button btnCancel = dialogView.findViewById(R.id.word_addition_dialog_btn_cancel);

        txtTitle.setText(title);

        if(title == getResources().getString(R.string.word_addition_dialog_dialog_title_edit)){
            etxtNewWord.setText(word);
            etxtMeaning.setText(meaning);
        }

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(title == getResources().getString(R.string.word_addition_dialog_dialog_title_add)) {
                    String eng = etxtNewWord.getText().toString();
                    String vn = etxtMeaning.getText().toString();
                    databaseWrapper.insertWord(eng, vn);
                } else { //edit word
                    String newEng = etxtNewWord.getText().toString();
                    String newVn = etxtMeaning.getText().toString();
                    databaseWrapper.updateRow(word, newEng, newVn);
                }
                callback.onOkClick();
                dismiss();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        builder.setView(dialogView);

        return builder.create();
    }

    @Override
    public void onStop() {
        super.onStop();
        databaseWrapper.closeDatabase();
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setWord(String word){
        this.word = word;
    }

    public void setMeaning(String meaning){
        this.meaning = meaning;
    }

    public void setCallback(WordAdditionDialogCallback callback){
        this.callback = callback;
    }

    public interface WordAdditionDialogCallback {
        public void onOkClick();
    }
}
