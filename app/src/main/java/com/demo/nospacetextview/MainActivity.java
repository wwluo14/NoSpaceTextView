package com.demo.nospacetextview;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

	private Button   switchWords;
	private TextView noSpaceText;

	private String[] mContents = {
			"NoSpaceView  自定义 没有空白哈哈哈哈vvssisifsnifdnsifsnf"
			, "ap卡了ξτβㄎㄊěǔぬも┰┠"
			, "❤❥웃유♋☮✌☏☢☠✔☑♚▲"
			, "སེམས་གཅོང་ གས་དོན་མི་འ ག"
			, "Είμαι ένα προσαρμοσμένο κείμενο"
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		switchWords = findViewById(R.id.switchWords);
		noSpaceText = findViewById(R.id.noSpaceText);

		switchWords.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Random random = new Random();
				int nextInt = random.nextInt(5);

				noSpaceText.setText(mContents[nextInt]);
			}
		});
	}
}
