package com.gtoteck.app.haychongiadung;

import java.io.InputStream;

import com.gtoteck.app.dao.GiaDungDAO;
import com.gtoteck.app.dao.GiaDungEntity;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private ProgressBar mProgressBar;
	private TextView mTvSec;
	private TextView mTvCoin;
	private LinearLayout mLlLeft;
	private LinearLayout mLlRight;
	private ImageView mImgLeft;
	private ImageView mImgRight;
	private TextView mTvLeft;
	private TextView mTvRight;
	private Button mBtnSkip;

	private GiaDungDAO mGiaDungDAO;
	private Context mContext = this;

	private int mIndex = 0;
	private int mSize = 0;
	private int mRuby = 0;

	private GiaDungEntity mGiaDungEntity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initUI();
	}

	private void initUI() {
		mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
		mTvSec = (TextView) findViewById(R.id.tvSec);
		mTvCoin = (TextView) findViewById(R.id.tvCoin);
		mLlLeft = (LinearLayout) findViewById(R.id.llLeft);
		mLlRight = (LinearLayout) findViewById(R.id.llRight);
		mImgLeft = (ImageView) findViewById(R.id.imgLeft);
		mImgRight = (ImageView) findViewById(R.id.imgRight);
		mTvLeft = (TextView) findViewById(R.id.tvLeft);
		mTvRight = (TextView) findViewById(R.id.tvRight);
		mBtnSkip = (Button) findViewById(R.id.btnSkip);
		mBtnSkip.setOnClickListener(mBtnSkipOnClickListener);

		mGiaDungDAO = new GiaDungDAO(mContext);
		mSize = mGiaDungDAO.getSize();

		next();
	}

	private OnClickListener mBtnSkipOnClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			next();
		}
	};

	private void next() {

		if (mIndex >= mSize) {
			Toast.makeText(mContext, "Qua man choi cuoi...", Toast.LENGTH_LONG)
					.show();
			finish();
			return;
		}

		mGiaDungEntity = mGiaDungDAO.getGiaDungEntityByPosition(mIndex);
		
		Bitmap bmLeft = getBitmapFromAsset(mGiaDungEntity.getIconleft());
		mImgLeft.setImageBitmap(bmLeft);
		
		Bitmap bmRight = getBitmapFromAsset(mGiaDungEntity.getIconright());
		mImgRight.setImageBitmap(bmRight);
		
		mTvLeft.setText(mGiaDungEntity.getTextleft());
		mTvRight.setText(mGiaDungEntity.getTextright());

		// increment
		mIndex++;
	}

	private Bitmap getBitmapFromAsset(String name) {
		AssetManager assetManager = getAssets();
		InputStream istr = null;

		try {
			istr = assetManager.open("images/" + name);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		Bitmap bitmap = BitmapFactory.decodeStream(istr);
		return bitmap;
	}

}
