package com.gtoteck.app.haychongiadung;

import java.io.InputStream;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.gtoteck.app.dao.GiaDungDAO;
import com.gtoteck.app.dao.GiaDungEntity;
import com.gtoteck.app.util.Define;

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
	private TextView mTvQuestionIdx;
	private Button mBtnSkip;

	private GiaDungDAO mGiaDungDAO;
	private Context mContext = this;

	private int mIndex = 0;
	private int mSize = 0;
	private int mRuby = 70;

	private int mSec = Define.MAX_SEC;

	private GiaDungEntity mGiaDungEntity;

	private Handler mHandler = new Handler();

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
		mTvQuestionIdx = (TextView) findViewById(R.id.tvQuestionIdx);
		mBtnSkip = (Button) findViewById(R.id.btnSkip);
		mBtnSkip.setOnClickListener(mBtnSkipOnClickListener);

		mGiaDungDAO = new GiaDungDAO(mContext);
		mSize = mGiaDungDAO.getSize();

		mLlRight.setOnClickListener(mLlOnClickListener);
		mLlLeft.setOnClickListener(mLlOnClickListener);

		next();
	}

	private OnClickListener mBtnSkipOnClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			next();
		}
	};

	private OnClickListener mLlOnClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			int id = v.getId();

			if (id == R.id.llLeft) {
				check(mGiaDungEntity.getPriceleft(),
						mGiaDungEntity.getPriceright());
			} else {
				check(mGiaDungEntity.getPriceright(),
						mGiaDungEntity.getPriceleft());
			}

		}
	};

	/**
	 * Kiem tra ket qua
	 * @param a
	 * @param b
	 */
	private void check(int a, int b) {
		int result = a - b;

		if (result > 0) {
			right();
		} else {
			worng();
		}
	}

	/**
	 * Tra loi dung
	 */
	private void right() {
		Toast.makeText(
				mContext,
				":Right: " + mGiaDungEntity.getPriceleft() + "$ - "
						+ mGiaDungEntity.getPriceright() + "$",
				Toast.LENGTH_LONG).show();
	}

	/**
	 * Tra loi sai
	 */
	private void worng() {
		Toast.makeText(
				mContext,
				":Worng: " + mGiaDungEntity.getPriceleft() + "$ - "
						+ mGiaDungEntity.getPriceright() + "$", 2000).show();
	}

	/**
	 * Hoan thanh man choi
	 */
	private void pass() {
		Toast.makeText(mContext, "Qua man choi cuoi...", Toast.LENGTH_LONG)
				.show();
		finish();
	}

	/**
	 * Dem nguoc
	 */
	private void countDown() {
		mSec = Define.MAX_SEC;
		mTvSec.setText(mSec + " s");
		mProgressBar.setProgress(mSec);
		mHandler.removeCallbacks(mRunnable);
		mHandler.postDelayed(mRunnable, Define.DELAY);
	}

	private Runnable mRunnable = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub

			if (--mSec > -1) {
				mTvSec.setText(mSec + " s");
				mProgressBar.setProgress(mSec);
				mHandler.postDelayed(mRunnable, Define.DELAY);
			}else{
				worng();
			}
		}
	};

	/**
	 * Chuyen sang cau moi
	 */
	private void next() {

		mTvCoin.setText(mRuby + " Coint");
		mTvQuestionIdx.setText((mIndex + 1) + "");

		//Kiem tra xem da het cau hoi hay chua
		if (mIndex >= mSize) {
			pass();
			return;
		}

		// dem nguoc
		countDown();

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

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		mHandler.removeCallbacks(mRunnable);
		super.onDestroy();
	}

}
