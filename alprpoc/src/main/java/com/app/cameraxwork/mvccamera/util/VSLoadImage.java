/* Decompiler 22ms, total 283ms, lines 190 */
package com.app.cameraxwork.mvccamera.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.LruCache;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.URL;

public class VSLoadImage {
   private LruCache<String, Bitmap> mMemoryCache;
   private ImageTaskListener mImageTaskListener;
   private static VSLoadImage instance = null;
   private int wImage;
   private int hImage;

   public static VSLoadImage getInstance() {
      if (instance == null) {
         instance = new VSLoadImage();
      }

      return instance;
   }

   public VSLoadImage setImageTaskListener(ImageTaskListener mImageTaskListener) {
      this.mImageTaskListener = mImageTaskListener;
      return this;
   }

   public VSLoadImage setWHImage(int wImage, int hImage) {
      this.wImage = wImage;
      this.hImage = hImage;
      return this;
   }

   private VSLoadImage() {
      int maxMemory = (int)(Runtime.getRuntime().maxMemory() / 1024L);
      int cacheSize = maxMemory / 8;
      this.mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
         protected int sizeOf(String key, Bitmap bitmap) {
            return bitmap.getByteCount() / 1024;
         }

         /*// $FF: synthetic method
         // $FF: bridge method
         protected int sizeOf(Object var1, Object var2) {
            return this.sizeOf((String)var1, (Bitmap)var2);
         }*/
      };
   }

   private LoadImage loadBitmap(String url, ImageView imageView) {
      String imageKey = String.valueOf(url);
      Bitmap bitmap = this.getBitmapFromMemCache(imageKey);
      if (bitmap != null) {
         imageView.setImageBitmap(bitmap);
         if (this.mImageTaskListener != null) {
            this.mImageTaskListener.complete(bitmap);
         }

         return null;
      } else {
         imageView.setImageBitmap((Bitmap)null);
         return new LoadImage(imageView, url);
      }
   }

   public LoadImage execute(String url, ImageView imageView) {
      LoadImage loadImage = this.loadBitmap(url, imageView);
      if (loadImage != null) {
         loadImage.execute(new Void[0]);
      }

      return loadImage;
   }

   private void addBitmapToMemoryCache(String key, Bitmap bitmap) {
      if (this.getBitmapFromMemCache(key) == null) {
         this.mMemoryCache.put(key, bitmap);
      }

   }

   private Bitmap getBitmapFromMemCache(String key) {
      return (Bitmap)this.mMemoryCache.get(key);
   }

   private class DataModel {
      private String url;
      int wImage;
      int hImage;

      public String getUrl() {
         return this.url;
      }

      DataModel(String url, int wImage, int hImage) {
         this.url = url;
         this.wImage = wImage;
         this.hImage = hImage;
      }
   }

   public interface ImageTaskListener {
      void complete(Bitmap var1);

      void error(String var1);
   }

   public class LoadImage extends AsyncTask<Void, Void, Bitmap> {
      private ImageView bmImage;
      private String url;
      private boolean isReload;
      private Options options;

      LoadImage(ImageView bmImage, String url) {
         this.bmImage = bmImage;
         this.url = url;
         this.options = new Options();
         if (VSLoadImage.this.wImage != 0 || VSLoadImage.this.hImage != 0) {
            this.options.inSampleSize = DfcGlobalFunctions.calculateInSampleSize(this.options, DfcGlobalFunctions.dpToPx(VSLoadImage.this.wImage), DfcGlobalFunctions.dpToPx(VSLoadImage.this.hImage));
         }

         this.options.inJustDecodeBounds = false;
      }

      protected Bitmap doInBackground(Void... params) {
         Bitmap mIcon = null;

         try {
            if (DfcGlobalFunctions.isHttpOrHttpsUrl(this.url)) {
               InputStream in = (new URL(this.url)).openStream();
               if (in != null) {
                  mIcon = BitmapFactory.decodeStream(in, (Rect)null, this.options);
                  in.close();
               }

               if (!this.isCancelled()) {
                  VSLoadImage.this.addBitmapToMemoryCache(this.url, mIcon);
               }
            } else if (DfcGlobalFunctions.IsBase64Encoded(this.url)) {
               byte[] decoded = Base64.decode(this.url.getBytes(), 0);
               mIcon = BitmapFactory.decodeByteArray(decoded, 0, decoded.length, this.options);
            } else {
               mIcon = BitmapFactory.decodeFile(this.url, this.options);
            }
         } catch (Exception var4) {
            if (VSLoadImage.this.mImageTaskListener != null) {
               VSLoadImage.this.mImageTaskListener.error(var4.toString());
            }

            if (!this.isReload) {
               (VSLoadImage.this.new LoadImage(this.bmImage, this.url)).execute(new Void[0]);
               this.isReload = true;
            }

            var4.printStackTrace();
         }

         return mIcon;
      }

      protected void onPostExecute(Bitmap result) {
         if (!this.isCancelled() && result != null) {
            this.bmImage.setImageBitmap(result);
            if (VSLoadImage.this.mImageTaskListener != null) {
               VSLoadImage.this.mImageTaskListener.complete(result);
            }
         }

      }

      /*// $FF: synthetic method
      // $FF: bridge method
      protected void onPostExecute(Object var1) {
         this.onPostExecute((Bitmap)var1);
      }

      // $FF: synthetic method
      // $FF: bridge method
      protected Object doInBackground(Object[] var1) {
         return this.doInBackground((Void[])var1);
      }*/
   }
}
