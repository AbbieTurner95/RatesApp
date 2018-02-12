package android.example.com.ratescurrencyapp;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ConversionActivity extends AppCompatActivity {

    private EditText topBaseAmount;
    private EditText baseAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversion);

        TextView currencyTitleTop = findViewById(R.id.tvCurrencyTitleTop);
        topBaseAmount = findViewById(R.id.etBaseAmount);
        TextView dateInformation = findViewById(R.id.tvDateInformation);
        TextView baseDateUpdated = findViewById(R.id.baseDateUpdated);

        FloatingActionButton fab1 = findViewById(R.id.fab1);
        FloatingActionButton fab2 = findViewById(R.id.fab2);

        TextView currencyTitleBottom = findViewById(R.id.tvCurrencyTitleBottom);
        baseAmount = findViewById(R.id.etToAmount);
        TextView bottomCurrencyShort = findViewById(R.id.bottomCurrencyShort);

        Intent intent = getIntent();
        final String dateUpdated = intent.getStringExtra(MainActivity.DATE_KEY);
        final Rate rate = intent.getExtras().getParcelable(MainActivity.RATE_KEY);      //rate to convert with - second selected
        final String baseCurrency = intent.getStringExtra(MainActivity.CURRENCY_KEY);   //base currency - first selected


        /* FIRST RATE SELECTED SETTINGS */
        currencyTitleTop.setText(baseCurrency);
        baseDateUpdated.setText(String.format(getString(R.string.updated_rates_string) + " : %s", dateUpdated));
        dateInformation.setText(getString(R.string.updateInfoText));

        /*  SECOND RATE SELECTED SETTINGS  */
        String titleBottom = rate.getSymbol();
        currencyTitleBottom.setText(titleBottom);
        final double secondRateChosen = rate.getRate();
        bottomCurrencyShort.setText(String.format(getString(R.string.live_string) + " " + titleBottom + " " + getString(R.string.rates_string) +" : %s", secondRateChosen));

        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (topBaseAmount.getText().toString().trim().length() <= 0) {
                    Toast.makeText(getApplicationContext(), getString(R.string.emptyString_toastMessage), Toast.LENGTH_SHORT).show();
                } else {
                    double amount = Double.parseDouble(topBaseAmount.getText().toString());
                    double convertedAmount = convertFromBase(amount, rate);
                    baseAmount.setText(String.format("%.2f", convertedAmount));
                }
            }
        });

        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if (baseAmount.getText().toString().trim().length() <= 0) {
                   Toast.makeText(getApplicationContext(), getString(R.string.emptyString_toastMessage), Toast.LENGTH_SHORT).show();
               } else {
                    double amount = Double.parseDouble(baseAmount.getText().toString());
                    double convertedAmount = convertToBase(amount, rate);
                    topBaseAmount.setText(String.format("%.2f", convertedAmount));
                }
            }
        });
    }

    private double convertFromBase(double amount, Rate rate) {
        return amount * rate.getRate();
    }

    private double convertToBase(double amount, Rate rate) {
        return amount * 1 / rate.getRate();
    }
}
