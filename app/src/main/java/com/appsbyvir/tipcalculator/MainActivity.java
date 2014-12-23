package com.appsbyvir.tipcalculator;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class MainActivity extends Activity {

	private static final String TOTAL_BILL = "TOTAL_BILL", CURRENT_TIP = "CURRENT_TIP",
            BILL_WITHOUT_TIP = "BILL_WITHOUT_TIP", FINAL_TIP = "FINAL_TIP",
            SPLIT_BILL = "SPLIT_BILL", PER_PERSON = "PER_PERSON";

	private double billBeforeTip, tipAmount, finalBill, finalTip, perPerson;
	private int splitBill;

	EditText billBeforeTipET, tipAmountET, finalBillET, finalTipET, perPersonET, splitBillET;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		billBeforeTipET = (EditText) findViewById(R.id.billEditText);
		tipAmountET = (EditText) findViewById(R.id.tipEditText);
		finalBillET = (EditText) findViewById(R.id.finalBillEditText);
		finalTipET = (EditText) findViewById(R.id.finalTipEditText);
		perPersonET = (EditText) findViewById(R.id.perPersonEditText);
		splitBillET = (EditText) findViewById(R.id.splitBillEditText);
		
		tipSeekBar = (SeekBar) findViewById(R.id.changeTipSeekBar);
		tipSeekBar.setOnSeekBarChangeListener(tipSeekBarListener);
		
		billBeforeTipET.addTextChangedListener(billBeforeTipListener);
		tipAmountET.addTextChangedListener(tipAmountListener);
		splitBillET.addTextChangedListener(splitBillListener);

        if(savedInstanceState == null){
            billBeforeTip= 0.0;
            tipAmount = 15.0;
            finalBill = 0.0;
            finalTip = 0.0;
            perPerson = 0.0;
            splitBill = 1;
        } else {
            billBeforeTip = savedInstanceState.getDouble(BILL_WITHOUT_TIP);
            if(billBeforeTip > 0)billBeforeTipET.setText(String.valueOf(billBeforeTip));
            tipAmount = savedInstanceState.getDouble(CURRENT_TIP);
            if(tipAmount > 0)tipAmountET.setText(String.valueOf(tipAmount));
            finalBill = savedInstanceState.getDouble(TOTAL_BILL);
            if(finalBill > 0)finalBillET.setText(String.valueOf(finalBill));
            finalTip = savedInstanceState.getDouble(FINAL_TIP);
            if(finalTip > 0)finalTipET.setText(String.valueOf(finalTip));
            perPerson = savedInstanceState.getDouble(PER_PERSON);
            if(perPerson > 0)perPersonET.setText(String.valueOf(perPerson));
            splitBill = savedInstanceState.getInt(SPLIT_BILL);
            if(splitBill > 1)splitBillET.setText(String.valueOf(splitBill));
        }
	}

	private TextWatcher billBeforeTipListener = new TextWatcher(){
		@Override
		public void afterTextChanged(Editable arg0) {
			try{
				billBeforeTip = Double.parseDouble(arg0.toString());
			}
			catch(NumberFormatException e){
				billBeforeTip = 0.0;
			}
			updateTipAndFinalBill();			
		}
		@Override
		public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {}
		@Override
		public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {}
	};

	private TextWatcher tipAmountListener = new TextWatcher(){
		@Override
		public void afterTextChanged(Editable arg0) {
			try{
				tipAmount = Double.parseDouble(arg0.toString());
			}
			catch(NumberFormatException e){
				tipAmount = 0.0;
				finalTip = 0.0;
			}
			updateTipAndFinalBill();			
		}
		@Override
		public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {}
		@Override
		public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {}
	};
	
	private TextWatcher splitBillListener = new TextWatcher(){
		@Override
		public void afterTextChanged(Editable arg0) {
			try{
				splitBill = Integer.parseInt(arg0.toString());
			}
			catch(NumberFormatException e){
				splitBill = 1;
			}
			updateTipAndFinalBill();			
		}
		@Override
		public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {}
		@Override
		public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {}
	};	
	
	private void updateTipAndFinalBill(){
		try{		
			tipAmount = Double.parseDouble(tipAmountET.getText().toString());
		} catch(NumberFormatException e){
			tipAmount = 0;
		}

		double finalTip = billBeforeTip*(tipAmount*.01);
		double finalBill = billBeforeTip + finalTip;
		double perPerson = finalBill/splitBill;
		
		finalBillET.setText(String.format("%.02f", finalBill));
		finalTipET.setText(String.format("%.02f", finalTip));
		perPersonET.setText(String.format("%.02f", perPerson));
	}
	
	protected void onSaveInstanceState(@NonNull Bundle outState){
		super.onSaveInstanceState(outState);
		outState.putDouble(BILL_WITHOUT_TIP, billBeforeTip);
		outState.putDouble(TOTAL_BILL, finalBill);
		outState.putDouble(CURRENT_TIP, tipAmount);
		outState.putDouble(FINAL_TIP, finalTip);
		outState.putDouble(PER_PERSON, perPerson);
		outState.putInt(SPLIT_BILL, splitBill);
	}

    protected void  onRestoreInstanceState(@NonNull Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        billBeforeTip = savedInstanceState.getDouble(BILL_WITHOUT_TIP);
        if(billBeforeTip > 0)billBeforeTipET.setText(String.valueOf(billBeforeTip));
        tipAmount = savedInstanceState.getDouble(CURRENT_TIP);
        if(tipAmount > 0)tipAmountET.setText(String.valueOf(tipAmount));
        finalBill = savedInstanceState.getDouble(TOTAL_BILL);
        if(finalBill > 0)finalBillET.setText(String.valueOf(finalBill));
        finalTip = savedInstanceState.getDouble(FINAL_TIP);
        if(finalTip > 0)finalTipET.setText(String.valueOf(finalTip));
        perPerson = savedInstanceState.getDouble(PER_PERSON);
        if(perPerson > 0)perPersonET.setText(String.valueOf(perPerson));
        splitBill = savedInstanceState.getInt(SPLIT_BILL);
        if(splitBill > 1)splitBillET.setText(String.valueOf(splitBill));
    }

	private SeekBar tipSeekBar;
	
	private OnSeekBarChangeListener tipSeekBarListener = new OnSeekBarChangeListener(){
		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			tipAmount = (tipSeekBar.getProgress());
			tipAmountET.setText(String.format("%.02f", tipAmount));
			updateTipAndFinalBill();
		}
		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {}
		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return super.onCreateOptionsMenu(menu);
	}
}