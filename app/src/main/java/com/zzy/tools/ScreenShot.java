package com.zzy.tools;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.ListView;
import android.widget.ScrollView;

import com.zzy.framework.Tools.Logger;
import com.zzy.framework.Tools.Tag;
import com.zzy.pianyu.ui.Impl.JPCallBack;

public class ScreenShot {
	// 获取指定Activity的截屏，保存到png文件
	public static Bitmap takeScreenShot(Activity activity) {
		// View是你需要截图的View
		View view = activity.getWindow().getDecorView();
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
		Bitmap b1 = view.getDrawingCache();

		// 获取状态栏高度
		Rect frame = new Rect();
		activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
		int statusBarHeight = frame.top;
		System.out.println(statusBarHeight);

		// 获取屏幕长和高
		int width = activity.getWindowManager().getDefaultDisplay().getWidth();
		int height = activity.getWindowManager().getDefaultDisplay()
				.getHeight();
		// 去掉标题栏
		// Bitmap b = Bitmap.createBitmap(b1, 0, 25, 320, 455);
		Bitmap b = Bitmap.createBitmap(b1, 0, statusBarHeight, width, height
				- statusBarHeight);
		view.destroyDrawingCache();
		try{
			savePic(b, "activity.png");
		}catch(Exception e)
		{

		}
		return b;
	}

	// 保存到sdcard
//	public static void savePic(Bitmap b, String strFileName) {
//		FileOutputStream fos = null;
//		try {
//			fos = new FileOutputStream(strFileName);
//			if (null != fos) {
//				b.compress(Bitmap.CompressFormat.PNG, 90, fos);
//				fos.flush();
//				fos.close();
//			}
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}

	/**
	 * 把View对象转换成bitmap
	 * */
	public static Bitmap convertViewToBitmap(View view) {
		view.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
				MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
		view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
		view.buildDrawingCache();
		Bitmap bitmap = view.getDrawingCache();
		if (bitmap != null) {
			
			//Logger.error("bitmap is not null");
		} else {
			//Logger.error("bitmap is null");
		}
		return bitmap;
	}

//	// 程序入口1
//	public static void shoot(Activity a) {
//		ScreenShot.savePic(ScreenShot.takeScreenShot(a), "/sdcard/screen_test.png");
//	}
//
//	// 程序入口2
//	public static void shootView(View view) {
//		ScreenShot.savePic(getViewBitmap(view),"sdcard/"+System.currentTimeMillis()+".png");
//	}

	public static Bitmap getViewBitmap(View v) {
		v.clearFocus();
		v.setPressed(false);

		boolean willNotCache = v.willNotCacheDrawing();
		v.setWillNotCacheDrawing(false);

		// Reset the drawing cache background color to fully transparent
		// for the duration of this operation
		int color = v.getDrawingCacheBackgroundColor();
		v.setDrawingCacheBackgroundColor(0);

		if (color != 0) {
			v.destroyDrawingCache();
		}
		v.buildDrawingCache();
		Bitmap cacheBitmap = v.getDrawingCache();
		if (cacheBitmap == null) {
			//Logger.error("bitmap is null");
			return null;
		}

		Bitmap bitmap = Bitmap.createBitmap(cacheBitmap);

		// Restore the view
		v.destroyDrawingCache();
		v.setWillNotCacheDrawing(willNotCache);
		v.setDrawingCacheBackgroundColor(color);
		
		// 测试输出
		FileOutputStream out = null;
		try {
			out = new FileOutputStream("/sdcard/screen_test.png");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			if (null != out) {
				bitmap.compress(Bitmap.CompressFormat.PNG, 60, out);
				out.flush();
				out.close();
			}
		} catch (IOException e) {
			// TODO: handle exception
		} finally {

		}

		return bitmap;
	}

//	/**
//	 * 截取scrollview的屏幕
//	 * **/
//	public static Bitmap getBitmapByView(ScrollView scrollView) {
//		int h = 0;
//		Bitmap bitmap = null;
//		// 获取listView实际高度
//		for (int i = 0; i < scrollView.getChildCount(); i++) {
//			h += scrollView.getChildAt(i).getHeight();
//			scrollView.getChildAt(i).setBackgroundColor(Color.parseColor("#ffffff"));
//		}
//		Logger.info("实际高度:" + h);
//		Logger.info(" 高度:" + scrollView.getHeight());
//		// 创建对应大小的bitmap
//		bitmap = Bitmap.createBitmap(scrollView.getWidth(), h,Bitmap.Config.ARGB_8888);
//		final Canvas canvas = new Canvas(bitmap);
//		scrollView.draw(canvas);
//		// 测试输出
//		FileOutputStream out = null;
//		try {
//			out = new FileOutputStream("/sdcard/screen_test.png");
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}
//		try {
//			if (null != out) {
//				bitmap.compress(Bitmap.CompressFormat.PNG, 70, out);
//				out.flush();
//				out.close();
//			}
//		} catch (IOException e) {
//			// TODO: handle exception
//		}
//		return bitmap;
//	}

	/**
	 * 截取scrollview的屏幕
	 * **/
	public static Bitmap getBitmapByView(ScrollView scrollView,String savename,JPCallBack cb) {
		cb.onBefore();
		int h = 0;
		Bitmap bitmap = null;
		// 获取listView实际高度
		for (int i = 0; i < scrollView.getChildCount(); i++) {
			h += scrollView.getChildAt(i).getHeight();
			//scrollView.getChildAt(i).setBackgroundColor(Color.parseColor("#ffffff"));
		}
//		Logger.info("实际高度:" + h);
//		Logger.info(" 高度:" + scrollView.getHeight());
		// 创建对应大小的bitmap
		bitmap = Bitmap.createBitmap(scrollView.getWidth(), h,Bitmap.Config.ARGB_8888);
		final Canvas canvas = new Canvas(bitmap);
		scrollView.draw(canvas);
		// 测试输出
		try {
			savePic(bitmap,savename);
		}catch (Exception e)
		{
			Logger.error(e.getMessage());
			cb.error();
			return null;
		}

		cb.suc(bitmap);
		cb.onAfter();
		return bitmap;
	}




//	public static Bitmap getBitmapByView(ScrollView scrollView) {
//		int h = 0;
//		Bitmap bitmap = null;
//		// 获取listView实际高度
//		for (int i = 0; i < scrollView.getChildCount(); i++) {
//			h += scrollView.getChildAt(i).getHeight();
//			scrollView.getChildAt(i).setBackgroundResource(R.drawable.bg3);
//		}
//		Log.d("wzm", "实际高度:" + h);
//		Log.d("wzm", " 高度:" + scrollView.getHeight());
//		// 创建对应大小的bitmap
//		bitmap = Bitmap.createBitmap(scrollView.getWidth(), h,
//				Bitmap.Config.ARGB_8888);
//		final Canvas canvas = new Canvas(bitmap);
//		scrollView.draw(canvas);
//		// 测试输出
//		FileOutputStream out = null;
//		try {
//			out = new FileOutputStream("/sdcard/screen_test.png");
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}
//		try {
//			if (null != out) {
//				bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
//				out.flush();
//				out.close();
//			}
//		} catch (IOException e) {
//			// TODO: handle exception
//		}
//		return bitmap;
//	}




	private static String TAG = "Listview and ScrollView item 截图:";

	/**
	 * 截图listview
	 * **/
	public static Bitmap getbBitmap(ListView listView) {
		int h = 0;
		Bitmap bitmap = null;
		// 获取listView实际高度
		for (int i = 0; i < listView.getChildCount(); i++) {
			h += listView.getChildAt(i).getHeight();
		}
//		Log.d(TAG, "实际高度:" + h);
//		Log.d(TAG, "list 高度:" + listView.getHeight());
		// 创建对应大小的bitmap
		bitmap = Bitmap.createBitmap(listView.getWidth(), h,
				Bitmap.Config.ARGB_8888);
		final Canvas canvas = new Canvas(bitmap);
		listView.draw(canvas);
		// 测试输出
		FileOutputStream out = null;
		try {
			out = new FileOutputStream("/sdcard/screen_test.png");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			if (null != out) {
				bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
				out.flush();
				out.close();
			}
		} catch (IOException e) {
			// TODO: handle exception
		}
		return bitmap;
	}


	/**
	 * 截图listview
	 * **/
	public static Bitmap getbBitmap(RecyclerView listView) {

		Logger.info("子VIEW个数:" + listView.getChildCount());
		int h = 0;
		Bitmap bitmap = null;


		// 获取listView实际高度
		for (int i = 0; i < listView.getChildCount(); i++) {
			h += listView.getChildAt(i).getHeight();
			Logger.info(i+"--实际高度:" + h);
			listView.getChildAt(i).setBackgroundColor(Color.parseColor("#ffffff"));
		}
		Logger.info("实际高度:" + h);
		Logger.info("list 高度:" + listView.getHeight());
		// 创建对应大小的bitmap
		bitmap = Bitmap.createBitmap(listView.getWidth(), h,
				Bitmap.Config.ARGB_8888);
		final Canvas canvas = new Canvas(bitmap);
		listView.draw(canvas);
		// 测试输出
		FileOutputStream out = null;
		try {
			out = new FileOutputStream("/sdcard/screen_test.png");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			if (null != out) {
				bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
				out.flush();
				out.close();
			}
		} catch (IOException e) {
			// TODO: handle exception
		}
		return bitmap;
	}



	/**
	 * 压缩图片
	 * @param image
	 * @return
	 */
	public static Bitmap compressImage(Bitmap image) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		int options = 100;
		// 循环判断如果压缩后图片是否大于100kb,大于继续压缩
		while (baos.toByteArray().length / 1024 > 100) {
			// 重置baos
			baos.reset();
			// 这里压缩options%，把压缩后的数据存放到baos中
			image.compress(Bitmap.CompressFormat.JPEG, options, baos);
			// 每次都减少10
			options -= 10;
		}
		// 把压缩后的数据baos存放到ByteArrayInputStream中
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
		// 把ByteArrayInputStream数据生成图片
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);
		return bitmap;
	}
	public static final String PathDir = Environment.getExternalStorageDirectory().getPath()+"/PianYu/";

	/**
	 * 保存到sdcard
	 * @param b
	 * @return
	 */
	public static String savePic(Bitmap b,String name) throws Exception{
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss",
//				Locale.US);
		File outfile = new File(PathDir);
		// 如果文件不存在，则创建一个新文件
		if(!outfile.exists())
		{
			//if (outfile.isDirectory()) {
				Logger.info("---------------");
				outfile.mkdirs();
			//}
		}else{
			Logger.info("===============");
		}

		String fname = outfile.getAbsolutePath()+ "/" + name + ".png";
		Logger.info(fname);
		FileOutputStream fos = null;

		fos = new FileOutputStream(fname);
		if (null != fos) {
			b.compress(Bitmap.CompressFormat.PNG, 70, fos);
			fos.flush();
			fos.close();
		}

		return fname;
	}
}
