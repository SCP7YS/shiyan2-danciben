package com.example.vocabularynotebook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<MainMenuItem> mainMenuItems = new ArrayList<MainMenuItem>();
    private static final int MY_VOCABULARY = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainMenuItem item1 = new MainMenuItem(R.drawable.baseline_list_black_48, getString(R.string.main_menu_item_lb_my_vocabulary));
        MainMenuItem item2 = new MainMenuItem(R.drawable.baseline_games_black_48, getString(R.string.main_menu_item_lb_game));
        MainMenuItem item3 = new MainMenuItem(R.drawable.baseline_settings_black_48, getString(R.string.main_menu_item_lb_setting));

        mainMenuItems.add(item1);
        mainMenuItems.add(item2);
        mainMenuItems.add(item3);

        MainMenuAdapter adapter = new MainMenuAdapter(mainMenuItems);
        ListView mainMenu = findViewById(R.id.activity_main_main_menu);
        mainMenu.setAdapter(adapter);

        mainMenu.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int whichItem, long id) {
                if(whichItem == MY_VOCABULARY){
                    Intent intent = new Intent(getApplicationContext(), MyVocabularyActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    public class MainMenuAdapter extends ArrayAdapter<MainMenuItem> {
        ArrayList<MainMenuItem> mainMenuItems;

        public MainMenuAdapter(ArrayList<MainMenuItem> items) {
            super(getApplicationContext(), R.layout.main_menu_item, items);
            mainMenuItems = items;
        }

        @Override
        public View getView(int whichItem, View view, ViewGroup viewGroup) {
            if (view == null) {
                LayoutInflater inflater = (LayoutInflater) getLayoutInflater();
                view = inflater.inflate(R.layout.main_menu_item, null);

                ImageView itemIcon = view.findViewById(R.id.main_menu_item_item_icon);
                itemIcon.setImageDrawable(getDrawable(mainMenuItems.get(whichItem).getImageID()));

                TextView itemText = view.findViewById(R.id.main_menu_item_item_text);
                itemText.setText(mainMenuItems.get(whichItem).getContent());
            }

            return view;
        }
    }
}
