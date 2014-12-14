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
import android.view.ViewGroup;
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
	private LinearLayout mLlBoard;
	private ImageView mImgLeft;
	private ImageView mImgRight;
	private TextView mTvLeft;
	private TextView mTvRight;
	private TextView mTvQuestionIdx;
	private Button mBtnSkip;
	private Button mBtnShare;

	private GiaDungDAO mGiaDungDAO;
	private Context mContext = this;

	private int mIndex = 0;
	private int mSize = 0;
	private int mRuby = Define.COINT_DEFAULT;

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
		mBtnShare = (Button) findViewById(R.id.btnShare);
		mBtnShare.setOnClickListener(mBtnShareOnClickListener);
		mLlBoard = (LinearLayout) findViewById(R.id.board);

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
			worng();
			next();
		}
	};

	private OnClickListener mBtnShareOnClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			// code share viết sau

			// tạm thời cộng 5 coint
			mRuby += Define.COINT_ADD;

			if (mLlBoard.getAlpha() == Define.ALPHA_BLUR) {
				next();
			}
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
	 * 
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

		next();
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
		mRuby += Define.COINT_ADD;
	}

	/**
	 * Tra loi sai
	 */
	private void worng() {
		Toast.makeText(
				mContext,
				":Worng: " + mGiaDungEntity.getPriceleft() + "$ - "
						+ mGiaDungEntity.getPriceright() + "$",
				Toast.LENGTH_SHORT).show();
		mRuby -= Define.COINT_MINUS;
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
	 * Hết coint
	 */
	private void endCoint() {
		Toast.makeText(
				mContext,
				"Bạn đã hết xu vui lòng bấm nút share facebook để share và nhận thêm 5 coint",
				Toast.LENGTH_LONG).show();
		mLlBoard.setAlpha(Define.ALPHA_BLUR);
		 enableDisableView(mLlBoard, false);
	}

	/**
	 * Dem nguoc
	 */
	private void countDown() {
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
			} else {
				worng();
				next();
			}
		}
	};

	/**
	 * Chuyen sang cau moi
	 */
	private void next() {

		mHandler.removeCallbacks(mRunnable);
		
		// open board
		 enableDisableView(mLlBoard, true);
		mLlBoard.setAlpha(Define.ALPHA_NORMAL);

		// update time
		mSec = Define.MAX_SEC;
		mTvSec.setText(mSec + " s");
		mProgressBar.setProgress(mSec);

		mRuby = (mRuby < 0) ? 0 : mRuby;
		mTvCoin.setText(mRuby + " Coint");
		mTvQuestionIdx.setText((mIndex + 1) + "");

		// Kiem tra xem da het cau hoi hay chua
		if (mIndex >= mSize) {
			pass();
			return;
		}

		mGiaDungEntity = mGiaDungDAO.getGiaDungEntityByPosition(mIndex);

		Bitmap bmLeft = getBitmapFromAsset(mGiaDungEntity.getIconleft());
		mImgLeft.setImageBitmap(bmLeft);

		Bitmap bmRight = getBitmapFromAsset(mGiaDungEntity.getIconright());
		mImgRight.setImageBitmap(bmRight);

		mTvLeft.setText(mGiaDungEntity.getTextleft());
		mTvRight.setText(mGiaDungEntity.getTextright());

		if (mRuby <= 0) {
			endCoint();
			return;
		}
		// dem nguoc
		countDown();

		// increment
		mIndex++;
	}
	
	public static void enableDisableView(View view, boolean enabled) {
        view.setEnabled(enabled);
        if ( view instanceof ViewGroup ) {
            ViewGroup group = (ViewGroup)view;
 
            for ( int idx = 0 ; idx < group.getChildCount() ; idx++ ) {
                enableDisableView(group.getChildAt(idx), enabled);
            } 
        } 
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
