package com.example.listycity;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    // Declare the variables so that you will be able to reference it later.
    ListView cityList;
    ArrayAdapter<String> cityAdapter;
    ArrayList<String> dataList;
    Button addCityButton;
    Button deleteCityButton;
    EditText addCityInput;
    Button confirmCityButton;

    int indexItemHighlighted;
    boolean itemHighlighted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        cityList = findViewById(R.id.city_list);

        String[] cities = {(new City("Edmonton")).GetName(),
                (new City("Vancouver")).GetName(),
                (new City("Moscow")).GetName(),
                (new City("Sydney")).GetName(),
                (new City("Berlin")).GetName(),
                (new City("Vienna")).GetName(),
                (new City("Tokyo")).GetName(),
                (new City("Beijing")).GetName(),
                (new City("Osaka")).GetName(),
                (new City("New Dehli")).GetName()
        };

        dataList = new ArrayList<>();
        dataList.addAll(Arrays.asList(cities));

        cityAdapter = new ArrayAdapter<>(this, R.layout.content, dataList);
        cityList.setAdapter(cityAdapter);

        // Setting OnClickListener to CityList
        cityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                itemHighlighted = true;
                indexItemHighlighted = position;
            }
        });



        // Add City Button
        addCityButton = (Button) findViewById(R.id.add_city_button);
        addCityButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            addCityInput.setVisibility(View.VISIBLE);
            confirmCityButton.setVisibility((View.VISIBLE));
            }
        });

        // Delete City Button
        deleteCityButton = (Button) findViewById(R.id.delete_city_button);
        deleteCityButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (itemHighlighted) {
                    dataList.remove(indexItemHighlighted);
                    itemHighlighted = false;
                    cityList.setAdapter(cityAdapter);
                }
            }
        });

        // Add City Input Box
        addCityInput = (EditText) findViewById(R.id.add_city_input);
        addCityInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    handled = true;
                }
                return handled;
            }
        });
        addCityInput.setVisibility(View.GONE);

        // Confirm Add City Button
        confirmCityButton = (Button) findViewById(R.id.confirm_button);
        confirmCityButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String newCityText = addCityInput.getText().toString();
                if (!newCityText.isEmpty()) {
                    dataList.add((new City(newCityText)).GetName());
                    cityList.setAdapter(cityAdapter);
                }

                // Hide the input buttons
                addCityInput.setText("");
                addCityInput.setVisibility(View.GONE);
                confirmCityButton.setVisibility(View.GONE);
            }
        });
        confirmCityButton.setVisibility(View.GONE);
    }
}