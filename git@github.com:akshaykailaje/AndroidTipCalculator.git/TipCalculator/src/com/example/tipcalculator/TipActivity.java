package com.example.tipcalculator;

import java.text.DecimalFormat;

import android.os.Bundle;
import android.app.Activity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class TipActivity extends Activity {

	private TextView tvTipPercent;
	private TextView tvTipAmount;
	private TextView tvCurrency;
	// private SeekBar sbTipPercent;
	private EditText etTotalAmount;
	private TextView tvTotalAmountLabel;
	private TextView tvTotalAmount;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip);
        
    	etTotalAmount = (EditText) findViewById(R.id.etTipBox);
        tvTipAmount = (TextView) findViewById(R.id.tvTip);
        tvCurrency = (TextView) findViewById(R.id.tvCurrency);
        tvTotalAmount = (TextView) findViewById(R.id.tvTotalAmount);
        // get the tip label and set the tip amount
    	tvTipPercent = (TextView) findViewById(R.id.tvTipPercent);
    	tvTotalAmountLabel = (TextView) findViewById(R.id.tvTotalAmountLabel);
    	
    	// TODO: This line gives cannot cast SeekBar to TextView error for some reason. Investigate
    	//sbTipPercent = (SeekBar) findViewById(R.id.sbCustomTip);
    	final SeekBar sbTipPercent = (SeekBar) findViewById(R.id.sbCustomTip);
        sbTipPercent.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				if (fromUser) {
					tvTipPercent.setText(String.valueOf(progress) + " %");
					displayTip(tvTipPercent);
				}
				
			}
		});
        
        etTotalAmount.setOnEditorActionListener(new OnEditorActionListener() {
			
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				final double tipPercent = getTipPercentFromLabel(); 
				if (actionId == EditorInfo.IME_ACTION_DONE && tipPercent > 0.0d) {
					displayTip(tvTipPercent);
				}
		
				return false;
			}
		});
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.tip, menu);
        return true;
    }
    
    /**
     * Event handler for tip button click. Handles all three tip buttons
     * @param v view / button that the click event is associated with
     */
    public void displayTip(View v) {
    	
    	double amount = 0.0d;
    	try {
    		amount = Double.parseDouble(etTotalAmount.getText().toString().trim());
    	} catch (NumberFormatException ex) {
    		return;
    	}
    	
    	if (amount <= 0.0d) {
    		return;
    	}
    	
    	double tipPercent = 0.0d;
    	switch(v.getId()) {
    		case R.id.btTenPercent:
    			tipPercent = 10.0d;
    			break;
    		case R.id.btFifteenPercent:
    			tipPercent = 15.0d;
    			break;
    		case R.id.btTwentyPercent:
    			tipPercent = 20.0d;
    			break;
    		default:
    			// default for custom tip
    			tipPercent = getTipPercentFromLabel();
    			break;
    	}
    	
    	// adjust the current progress on the seekbar
    	final SeekBar sbTipPercent = (SeekBar) findViewById(R.id.sbCustomTip);
    	sbTipPercent.setProgress((int)Math.round(tipPercent));
    	
    	DecimalFormat df = new DecimalFormat("#.##");
    	// round the total amount upto to decimal places, and set the text box
    	String amountRounded = df.format(amount);
    	amount = Double.parseDouble(amountRounded);
    	etTotalAmount.setText(amountRounded);
    	
    	final double tipAmount = TipCalculator.getTipAmount(amount, tipPercent);
    	
    	final double totalAmount = amount + tipAmount;
    	String totalAmountText = tvCurrency.getText().toString() + " " + df.format(totalAmount);
    	
    	String tip = tvCurrency.getText().toString() + " " + df.format(tipAmount);
    	String tipPercentage = df.format(tipPercent) + " %";
    	
    	// set the tip amount
    	tvTipPercent.setText(tipPercentage);
    	
    	tvTipAmount.setVisibility(View.VISIBLE);
    	tvTipAmount.setText("Tip:" + " " + tip);
    	
    	tvTotalAmountLabel.setVisibility(View.VISIBLE);
    	
    	// final TextView tvTotalAmount = (TextView) findViewById(R.id.tvTotalAmount);
    	tvTotalAmount.setVisibility(View.VISIBLE);
    	tvTotalAmount.setText(totalAmountText);
    	
    }
    
    private double getTipPercentFromLabel() {
    	double tip = 0.0d;
    	try {
    		tip = Double.parseDouble(this.tvTipPercent.getText().toString().split(" ")[0]);
    	} catch (NumberFormatException ex) {
    		// TODO: log error
    	}
    	return tip;
    }
    
}
