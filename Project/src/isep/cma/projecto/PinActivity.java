package isep.cma.projecto;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class PinActivity extends Activity implements OnClickListener {

	private TextView txt_pin;
	private Button btn_1;
	private Button btn_2;
	private Button btn_3;
	private Button btn_4;
	private Button btn_5;
	private Button btn_6;
	private Button btn_7;
	private Button btn_8;
	private Button btn_9;
	private Button btn_clear;
	private Button btn_0;
	private Button btn_ok;

	private String str_pin = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pin);

		txt_pin = (TextView) findViewById(R.id.txt_pin);
		btn_1 = (Button) findViewById(R.id.btn_1);
		btn_2 = (Button) findViewById(R.id.btn_2);
		btn_3 = (Button) findViewById(R.id.btn_3);
		btn_4 = (Button) findViewById(R.id.btn_4);
		btn_5 = (Button) findViewById(R.id.btn_5);
		btn_6 = (Button) findViewById(R.id.btn_6);
		btn_7 = (Button) findViewById(R.id.btn_7);
		btn_8 = (Button) findViewById(R.id.btn_8);
		btn_9 = (Button) findViewById(R.id.btn_9);
		btn_clear = (Button) findViewById(R.id.btn_clear);
		btn_0 = (Button) findViewById(R.id.btn_0);
		btn_ok = (Button) findViewById(R.id.btn_ok);

		btn_1.setOnClickListener(this);
		btn_2.setOnClickListener(this);
		btn_3.setOnClickListener(this);
		btn_4.setOnClickListener(this);
		btn_5.setOnClickListener(this);
		btn_6.setOnClickListener(this);
		btn_7.setOnClickListener(this);
		btn_8.setOnClickListener(this);
		btn_9.setOnClickListener(this);
		btn_clear.setOnClickListener(this);
		btn_0.setOnClickListener(this);
		btn_ok.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.btn_1:
			str_pin = str_pin + "1";
			txt_pin.setText(str_pin);
			break;
		case R.id.btn_2:
			str_pin = str_pin + "2";
			txt_pin.setText(str_pin);
			break;
		case R.id.btn_3:
			str_pin = str_pin + "3";
			txt_pin.setText(str_pin);
			break;
		case R.id.btn_4:
			str_pin = str_pin + "4";
			txt_pin.setText(str_pin);
			break;
		case R.id.btn_5:
			str_pin = str_pin + "5";
			txt_pin.setText(str_pin);
			break;
		case R.id.btn_6:
			str_pin = str_pin + "6";
			txt_pin.setText(str_pin);
			break;
		case R.id.btn_7:
			str_pin = str_pin + "7";
			txt_pin.setText(str_pin);
			break;
		case R.id.btn_8:
			str_pin = str_pin + "8";
			txt_pin.setText(str_pin);
			break;
		case R.id.btn_9:
			str_pin = str_pin + "9";
			txt_pin.setText(str_pin);
			break;
		case R.id.btn_clear:
			if (!str_pin.equals("")) {
				str_pin = str_pin.substring(0, str_pin.length()-1);
				txt_pin.setText(str_pin);
			}
			break;
		case R.id.btn_0:
			str_pin = str_pin + "0";
			txt_pin.setText(str_pin);
			break;
		case R.id.btn_ok:
			Intent i = new Intent(PinActivity.this, MainActivity.class);
			PinActivity.this.startActivity(i);
			PinActivity.this.finish();
			break;
		}
	}
}
