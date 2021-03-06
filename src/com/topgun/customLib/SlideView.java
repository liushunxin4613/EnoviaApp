package com.topgun.customLib;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;

import com.topgun.enoviaapp.R;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.view.ViewPager.PageTransformer;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Scroller;
import android.widget.ImageView.ScaleType;

public class SlideView extends FrameLayout implements OnPageChangeListener,View.OnClickListener{

	private Context context;

	private ViewPager viewPager;

	private PageTransformer transformer;
	
	/**
	 * 点击页面的事件监听器
	 */
	private OnItemClickListener onItemClickListener;

	private ImageView[] dotTips;

	private ImageView[] mImageViews;

	private int imagesCount;
	/**
	 * 是否开启线程
	 */
	private boolean openThread;

	private int millis;

	private boolean isTouching;

	public ImageView[] getDotTips() {
		return dotTips;
	}

	public void setDotTips(ImageView[] dotTips) {
		this.dotTips = dotTips;
	}

	public ImageView[] getmImageViews() {
		return mImageViews;
	}

	public void setmImageViews(ImageView[] mImageViews) {
		this.mImageViews = mImageViews;
	}

	public SlideView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context);
	}

	public SlideView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public SlideView(Context context) {
		super(context);
		init(context);
	}

	private void init(Context context) {
		this.context = context;
	}

	public ImageView getImageView(int index) {
		return mImageViews[index];
	}

	/**
	 * 界面初始化
	 * 
	 * @param imgUrl
	 *            图片Url数组
	 * @param defaultImg
	 *            默认图片
	 * @param errorImg
	 *            加载失败时的图片
	 * @param millis
	 *            自动播放的间隔时长 若小于0表示不自动播放
	 */
	public void setUp(int[] imgIdArray, final int millis) {
		imagesCount = imgIdArray.length;
		this.millis = millis;
		initPager(imagesCount);
		// 将图片装载到数组中
		for (int i = 0; i < imagesCount; i++) {
			CardImageView imageView = new CardImageView(context);
			mImageViews[i] = imageView;
			imageView.setScaleType(ScaleType.CENTER_CROP);
			imageView.setImageResource(imgIdArray[i]);
			imageView.setOnClickListener(this);
			// 如果图片少于3张
			if (imagesCount > 1 && imagesCount < 4) {
				imageView = new CardImageView(context);
				mImageViews[i + imagesCount] = imageView;
				imageView.setImageResource(imgIdArray[i]);
			}
		}

		setUpPager();

	}

	/**
	 * 初始化ViewPager
	 */
	private void initPager(int imagesCount) {
		viewPager = (ViewPager) findViewById(R.id.image_slide_pager);
		if (transformer != null)
			viewPager.setPageTransformer(true, transformer);
		dotTips = new ImageView[imagesCount];
		if (imagesCount > 3 || imagesCount == 1) {
			mImageViews = new CardImageView[imagesCount];
		} else {
			mImageViews = new CardImageView[imagesCount * 2];
		}

		LinearLayout dotGroup = (LinearLayout) findViewById(R.id.view_dot);
		for (int i = 0; i < imagesCount; i++) {
			ImageView imageView = new ImageView(context);
			ViewGroup.LayoutParams params = new LayoutParams(
					ViewGroup.LayoutParams.WRAP_CONTENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
			imageView.setLayoutParams(params);
			dotTips[i] = imageView;
			if (i == 0)
				imageView.setBackgroundResource(R.drawable.dot_focused);
			else
				imageView.setBackgroundResource(R.drawable.dot_unfocused);

			dotGroup.addView(imageView);
		}
	}
	/**
	 * 完成ViewPager的后续初始化工作
	 */
	private void setUpPager() {
		/**
		 * 监控用户是否在触摸，若是isTouching标记为true 以停止自动播放
		 */
		viewPager.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (openThread) {
					switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN:
						isTouching = true;
						break;
					case MotionEvent.ACTION_MOVE:
						if (!isTouching) {
							isTouching = true;
						}
						break;
					case MotionEvent.ACTION_UP:
						isTouching = false;
						break;
					default:
						break;
					}
					v.performClick();
					getParent().requestDisallowInterceptTouchEvent(true);
				}
				return false;
			}
		});
		// 设置Adapter
		viewPager.setAdapter(new SlidePagerAdapter(mImageViews));
		// 设置监听，主要是设置点点的背景
		viewPager.setOnPageChangeListener(this);
		// 设置ViewPager的默认项, 设置为长度的100倍，这样子开始就能往左滑动
		viewPager.setCurrentItem((mImageViews.length) * 1000);

		if (this.millis > 0)
			startPlay();
	}

	/**
	 * 启动线程播放幻灯片
	 */
	public void startPlay() {
		// 若图片个数大于1 开启线程循环播放
		if (imagesCount > 1 && millis > 0 && !openThread) {
			openThread = true;
			new Thread(new Runnable() {
				// 开启线程。循环播放图片
				@Override
				public void run() {
					while (openThread) {
						try {
							Thread.sleep(millis);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						while (isTouching) {
							try {
								Thread.sleep(millis);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
						handler.sendEmptyMessage(1);
					}
				}
			}).start();
		}
	}
	
	Handler handler = new MyHandler(this);

	static class MyHandler extends Handler {

		private WeakReference<SlideView> slideView;

		public MyHandler(SlideView view) {
			this.slideView = new WeakReference<SlideView>(view);
		}

		public void handleMessage(Message msg) {
			SlideView view = this.slideView.get();
			// 接收消息，播放下一个图片
			if (msg.what == 1) {
				view.viewPager.setCurrentItem(
						view.viewPager.getCurrentItem() + 1, true);
			}
		}
	}
	
	/**
	 * 停止播放幻灯片
	 */
	public void stopPlay() {
		openThread = false;
	}
	
	class SlidePagerAdapter extends PagerAdapter {

		private ImageView[] mImageViews;

		public SlidePagerAdapter(ImageView[] mImageViews) {
			this.mImageViews = mImageViews;
		}

		@Override
		public int getCount() {
			// 如果图片数量大于1 则循环 否则不动，item个数返回1
			if (dotTips.length > 1)
				return Integer.MAX_VALUE;
			else
				return 1;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void destroyItem(View container, int position, Object object) {
			((ViewPager) container).removeView(mImageViews[position
					% mImageViews.length]);
		}

		/**
		 * 载入图片进去，用当前的position 除以 图片数组长度取余数是关键
		 */
		@Override
		public Object instantiateItem(View container, int position) {
			((ViewPager) container).addView(mImageViews[position
					% mImageViews.length], 0);
			return mImageViews[position % mImageViews.length];
		}
	}
	
	
	
	
	
	@Override
	public void onClick(View v) {
		if (onItemClickListener == null)
			return;
		for (int i = 0; i < mImageViews.length; i++) {
			if (v.equals(mImageViews[i])) {
				onItemClickListener.onItemClick(this, v, i % imagesCount);
			}
		}
	}
	
	public interface OnItemClickListener {
		public void onItemClick(SlideView sView, View view, int position);
	}
	

	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	@Override
	public void onPageSelected(int arg0) {
		setImageBackground(arg0 % imagesCount);
	}
	
	private void setImageBackground(int selectItems) {
		for (int i = 0; i < dotTips.length; i++) {
			if (i == selectItems) {
				dotTips[i].setBackgroundResource(R.drawable.dot_focused);
			} else {
				dotTips[i].setBackgroundResource(R.drawable.dot_unfocused);
			}
		}
	}
	protected void onDetachedFromWindow() {
		// view被杀死时 停止线程
		openThread = false;
		super.onDetachedFromWindow();
	};
	
	public class FixedSpeedScroller extends Scroller {

		private int mDuration = 1000;

		public FixedSpeedScroller(Context context) {
			super(context);
		}

		public FixedSpeedScroller(Context context, Interpolator interpolator) {
			super(context, interpolator);
		}

		public FixedSpeedScroller(Context context, Interpolator interpolator,
				boolean flywheel) {
			super(context, interpolator, flywheel);
		}

		public void setDuration(int millis) {
			this.mDuration = millis;
		}

		@Override
		public void startScroll(int startX, int startY, int dx, int dy,
				int duration) {
			super.startScroll(startX, startY, dx, dy, mDuration);
		}

		@Override
		public void startScroll(int startX, int startY, int dx, int dy) {
			super.startScroll(startX, startY, dx, dy, mDuration);
		}
	}
	
	public void setDuration(int millis) {
		try {
			Field mScroller = ViewPager.class.getDeclaredField("mScroller");
			mScroller.setAccessible(true);
			FixedSpeedScroller scroller = new FixedSpeedScroller(
					viewPager.getContext(), new LinearInterpolator());
			scroller.setDuration(millis);
			mScroller.set(viewPager, scroller);
		} catch (Exception e) {
		}
	}
	
	public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
		this.onItemClickListener = onItemClickListener;
	}
	
	/**
	 * 设置切换动画
	 * 
	 * @return
	 */
	public PageTransformer getTransformer() {
		return transformer;
	}
	public void setTransformer(PageTransformer transformer) {
		this.transformer = transformer;
		if (viewPager != null)
			viewPager.setPageTransformer(true, transformer);
	}
	
}
