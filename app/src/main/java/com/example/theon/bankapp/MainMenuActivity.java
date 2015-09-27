package com.example.theon.bankapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;

public class MainMenuActivity extends AppCompatActivity {

    String passableQuery = "";

    public void onRadioButtonClicked (View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch(view.getId()) {
            case R.id.groceryStores: {
                if (checked) {
                    passableQuery="grocery+stores";
                }
            }break;

            case R.id.gasStations: {
                if (checked) {
                    passableQuery="gas+stations";
                }
            }break;

            case R.id.restaurants: {
                if (checked) {
                    passableQuery="restaurants";
                }
            }break;

            case R.id.movie_theaters: {
                if (checked) {
                    passableQuery="movie+theaters";
                }
            }break;

            case R.id.pharmacies: {
                if (checked) {
                    passableQuery="pharmacies";
                }
            }break;

            case R.id.Laundromats: {
                if (checked) {
                    passableQuery="laundromats";
                }
            }break;

            case R.id.sportingGoods: {
                if (checked) {
                    passableQuery="sportings+goods+stores";
                }
            }break;

            case R.id.convenienceStores: {
                if (checked) {
                    passableQuery="convenience+stores";
                }
            }break;

            case R.id.clothingStores: {
                if (checked) {
                    passableQuery="clothing+stores";
                }
            }break;

            case R.id.hardwareStores: {
                if (checked) {
                    passableQuery="hardware+stores";
                }
            }break;

            case R.id.bank: {
                if (checked) {
                    passableQuery="capital+one+branch";
                }
            }break;
        }
    }

    public void GoToATMMap (View view) {
        Intent myIntent = new Intent(getApplicationContext(), ATMActivity.class);
        //myIntent.putExtra("key", value); //Optional parameters
        myIntent.putExtra("query",passableQuery);
        startActivity(myIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("baeTM");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
