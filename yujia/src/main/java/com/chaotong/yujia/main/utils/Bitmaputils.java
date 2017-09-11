package com.chaotong.yujia.main.utils;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Bitmaputils {

	public Bitmaputils() {

	}

	public Bitmap getCompressBitmap(Resources res, int resid,
			int requestHeight, int requestWidth) {

		BitmapFactory.Options options = new BitmapFactory.Options();

		options.inJustDecodeBounds = true;
		BitmapFactory.decodeResource(res, resid, options);
		options.inSampleSize = GetinSampleSize(options, requestWidth,
				requestHeight);

		options.inJustDecodeBounds = false;

		return BitmapFactory.decodeResource(res, resid, options);
	}

	public static int GetinSampleSize(BitmapFactory.Options options,
			int requestWidth, int requestHeight) {
		
		int imageHeight = options.outHeight;
		int imageWidth = options.outWidth;
		
		int inSampleSize = 1;

		if (imageHeight > requestHeight || imageWidth > requestWidth) {

			int HeightRation = (int) Math.round((double) imageHeight
					/ (double) requestHeight);
			int WidthRation = (int) Math.round((double) imageWidth
					/ (double) requestWidth);

			inSampleSize = HeightRation < WidthRation ? HeightRation
					: WidthRation;

		}

		return inSampleSize;

	}

}
