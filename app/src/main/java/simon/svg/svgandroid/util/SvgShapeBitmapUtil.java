package simon.svg.svgandroid.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;

import simon.svg.svgandroid.SVG;
import simon.svg.svgandroid.SVGParser;

public class SvgShapeBitmapUtil {
	/**
	 * 通过svg文件生成Bitmap
	 */
	public static Bitmap getSvgBitmap(Context context,int width,int height,int svgRawResourceId){
		//绘制SVG里的picture对象
		Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);//抗锯齿
		paint.setColor(Color.BLACK);
		//绘制
		if(svgRawResourceId>0){
			SVG svg = SVGParser.getSVGFromInputStream(context.getResources().openRawResource(svgRawResourceId),width,height);
			canvas.drawPicture(svg.getPicture());
		}else{
			canvas.drawRect(new Rect(0,0,width,height), paint);
		}
		return bitmap;
	}


	/**
	 * 根据svg形状图和原始图合成目标图片
	 */
	public static Bitmap getSvgShapeBitmap(Context context,Bitmap fg,int svgRawResourceId){
		//获取原图妹子的最大尺寸
		int size = Math.min(fg.getWidth(), fg.getHeight());
		int x = (fg.getWidth()-size)/2;
		int y = (fg.getHeight()-size)/2;
		//先加载svg图片
		Bitmap bg = getSvgBitmap(context, size, size, svgRawResourceId);
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);//抗锯齿
		Bitmap bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		canvas.drawBitmap(bg, new Matrix(), paint);
		//设置交集模式
		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
		canvas.drawBitmap(Bitmap.createBitmap(fg, x, y, size, size), new Matrix(), paint);
		return bitmap;
	}


}
