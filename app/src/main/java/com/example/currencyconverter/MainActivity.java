package com.example.currencyconverter;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.JsonObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText etAmount = findViewById(R.id.amount);
        final Spinner spFrom = findViewById(R.id.sp_from);
        final Spinner spTo = findViewById(R.id.sp_to);
        Button btnConvert = findViewById(R.id.convert);
        final TextView tvConversionMessage = findViewById(R.id.conversion_message);

        btnConvert.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                final String from = spFrom.getSelectedItem().toString();
                final String to = spTo.getSelectedItem().toString();
                final String amount = etAmount.getText().toString();
                if(amount.isEmpty()){
                    tvConversionMessage.setTextColor(getColor(R.color.colorRed));
                    tvConversionMessage.setText(R.string.empty_amount_field);
                } else if (from.equals(to)){
                    tvConversionMessage.setTextColor(getColor(R.color.colorRed));
                    tvConversionMessage.setText(R.string.change_one);
                } else {

                    APIInterface apiService = APIClient.gerClient().create(APIInterface.class);
                    Call<JsonObject> call = apiService.getExchangeRates(from);

                    call.enqueue(new Callback<JsonObject>() {
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                            JsonObject jsonObject = response.body();

                            if(jsonObject.get("result").getAsString().equals("success")){

                                JsonObject reatesJsonObject =jsonObject.get("conversion_rates").getAsJsonObject();
                                Double requstedRate = reatesJsonObject.get(to).getAsDouble();
                                Double finalValue = requstedRate*Double.parseDouble(amount);
                                tvConversionMessage.setTextColor(getColor(R.color.colorGreen));

                                tvConversionMessage.setText(from + " " + amount + " equal " + to + " " + finalValue);

                            } else {
                                tvConversionMessage.setTextColor(getColor(R.color.colorRed));
                                tvConversionMessage.setText(R.string.failed_to_convert);
                            }
                        }

                        @Override
                        public void onFailure(Call<JsonObject> call, Throwable t) {
                            Log.e("ExchangeRateAPI","Error: "+ t.toString());
                        }
                    });

                }
            }
        });
    }
}
