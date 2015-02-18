package tempconverter2.valdez.com.tempconverter2;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity implements OnSeekBarChangeListener
{

    //define the SharedPrefrences object
    private SharedPreferences savedValues;

    private TextView celsiusText;
    private TextView kelvinText;
    private TextView fahrenheitText;
    private String fahrenheitTextString;
    private SeekBar seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Reference for the widget
        fahrenheitText = (TextView) findViewById(R.id.fahrenheitText);
        celsiusText = (TextView) findViewById(R.id.celsiusText);
        kelvinText = (TextView) findViewById(R.id.kelvinText);
        seekBar = (SeekBar) findViewById(R.id.seekBar);

        //set the listeners
        seekBar.setOnSeekBarChangeListener(this);

        savedValues = getSharedPreferences("SavedValues", MODE_PRIVATE);
    }

    @Override
    public void onPause()
    {
        //save the instance variables
        SharedPreferences.Editor editor = savedValues.edit();
        editor.putString("fahrenheitTextString", fahrenheitTextString);
        editor.commit();

        super.onPause();
    }

    @Override
    public void onResume()
    {
        super.onResume();

        //get instance variables
        fahrenheitTextString = savedValues.getString("fahrenheitTextString", "");

        //set the temp amount on the widget
        fahrenheitText.setText(fahrenheitTextString);
        //int progress = savedValues.getInt("seekBar", 32);
        //seekBar.setProgress(progress);
        convertDegrees();
    }

    public void convertDegrees()
    {
        float temperature;
        int progress = seekBar.getProgress();
        temperature = (float)progress;

        //Convert to Celsius
            //c = (f-32) * 5/9
        float converted = ((temperature - 32) * 5) / 9;
        String finalTemperature = String.valueOf(converted);
        celsiusText.setText(finalTemperature + "°");


        //Convert to Kelvin
            //Kelvin = (Fahrenheit + 459.67) * 5 / 9
        Double kConverted = ((temperature + 459.67) * 5) / 9;
        String finalKtemp = String.valueOf(kConverted);
        kelvinText.setText(finalKtemp + "°");


    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
    {
        fahrenheitText.setText(progress + "°");
        if(progress >= 150)
        {
            fahrenheitText.setTextColor(Color.rgb(255, 0, 0));
            celsiusText.setTextColor(Color.rgb(255, 0, 0));
            kelvinText.setTextColor(Color.rgb(255, 0, 0));
        }
        else if(progress <= 149 && progress >= 51)
        {
            fahrenheitText.setTextColor(Color.rgb(255, 255, 255));
            celsiusText.setTextColor(Color.rgb(255, 255, 255));
            kelvinText.setTextColor(Color.rgb(255, 255, 255));
        }
        else
        {
            fahrenheitText.setTextColor(Color.rgb(0, 251, 255));
            celsiusText.setTextColor(Color.rgb(0, 251, 255));
            kelvinText.setTextColor(Color.rgb(0, 251, 255));
        }

        convertDegrees();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar)
    {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar)
    {
        //convertDegrees();
    }
}
