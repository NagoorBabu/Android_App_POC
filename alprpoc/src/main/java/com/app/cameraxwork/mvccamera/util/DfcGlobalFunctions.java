/* Decompiler 319ms, total 609ms, lines 2013 */
package com.app.cameraxwork.mvccamera.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.provider.Settings.Secure;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.KeySpec;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.UUID;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

public class DfcGlobalFunctions {
   public static final String INTERNAL_SD_CARD = "internalSdCard";
   public static final String EXTERNAL_SD_CARD = "externalSdCard";
   protected static final char[] hexArray = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
   private static final int MASK = 255;
   private static final String[][] g_arrSpecialChars = new String[][]{{"%", "%25"}, {"#", "%23"}, {"$", "%24"}, {"&", "%26"}, {"*", "%2A"}, {"+", "%2B"}, {"-", "%2D"}, {"<", "%3C"}, {">", "%3E"}, {"?", "%3F"}, {"{", "%7B"}, {"}", "%7D"}, {"�", "%A2"}, {"�", "%A3"}, {"�", "%A5"}, {"�", "%A6"}, {"�", "%A7"}, {"�", "%A9"}, {"@", "@"}, {"!", "!"}, {"^", "^"}, {"&", "&"}, {"(", "("}, {"/", "%2f"}, {")", ")"}};
   private static final String[][] g_arrProvinceThailand = new String[][]{{"อำนาจเจริญ;Amnat Charoen", "1"}, {"มุกดาหาร;Mukdahan", "2"}, {"พิษณุโลก;Phitsanulok", "3"}, {"สระแก้ว;Sa Kaeo", "4"}, {"่างทอง; Ang Thong", "5"}, {"พระนครศรีอยุธยา; Phra Nakhon Si Ayutthaya", "6"}, {"กรุงเทพมหานคร; Bangkok", "7"}, {"บึงกาฬ; Bueng Kan", "8"}, {"บุรีรัมย์; Buriram", "9"}, {"ฉะเชิงเทรา; Chachoengsao", "10"}, {"ชัยนาท; Chainat", "11"}, {"ชัยภูมิ; Chaiyaphum", "12"}, {"จันทบุรี; Chanthaburi", "13"}, {"อุบลราชธาน ; Chiang Mai", "14"}, {"เชียงราย ; Chiang Rai", "15"}, {"ชลบุรี; Chonburi", "16"}, {"ชุมพร; Chumphon", "17"}, {"กาฬสินธุ์; Kalasin", "18"}, {"กำแพงเพชร; Kamphaeng Phet", "19"}, {"กาญจนบุรี; Kanchanaburi", "20"}, {"ขอนแก่น; Khon Kaen", "21"}, {"กระบี่; Krabi", "22"}, {"ลำปาง; Lampang", "23"}, {"ลำพูน; Lamphun", "24"}, {"เลย; Loei", "25"}, {"ลพบุรี; Lopburi", "26"}, {"แม่ฮ่องสอน; Mae Hong Son", "27"}, {"มหาสารคาม; Maha Sarakham", "28"}, {"นครนายก; Nakhon Nayok", "29"}, {"นครปฐม; Nakhon Pathom", "30"}, {"นครพนม; Nakhon Phanom", "31"}, {"นครราชสีมา; Nakhon Ratchasima", "32"}, {"นครสวรรค์; Nakhon Sawan", "33"}, {"นครศรีธรรมราช; Nakhon Si Thammarat", "34"}, {"น่าน; Nan", "35"}, {"นราธิวาส; Narathiwat", "36"}, {"หนองบัวลำภู; Nong Bua Lamphu", "37"}, {"หนองคาย; Nong Khai", "38"}, {"นนทบุรี; Nonthaburi", "39"}, {"ปทุมธานี; Pathum Thani", "40"}, {"ปัตตานี; Pattani", "41"}, {"พังงา; Phang Nga", "42"}, {"พัทลุง; Phatthalung", "43"}, {"พะเยา; Phayao", "44"}, {"เพชรบูรณ์; Phetchabun", "45"}, {"เพชรบุรี; Phetchaburi", "46"}, {"พิจิตร; Phichit", "47"}, {"แพร่; Phrae", "48"}, {"ภูเก็ต; Phuket", "49"}, {"ปราจีนบุรี; Prachinburi", "50"}, {"ประจวบคีรีขันธ์; Prachuap Khiri Khan", "51"}, {"ระนอง; Ranong", "52"}, {"ราชบุรี; Ratchaburi", "53"}, {"ระยอง; Rayong", "54"}, {"ร้อยเอ็ด; Roi Et", "55"}, {"สกลนคร; Sakon Nakhon", "56"}, {"สมุทรปราการ; Samut Prakan", "57"}, {"สมุทรสาคร; Samut Sakhon", "58"}, {"สมุทรสงคราม; Samut Songkhram", "59"}, {"สระบุรี; Saraburi", "60"}, {"สตูล; Satun", "61"}, {"สิงห์บุรี; Sing Buri", "62"}, {"ศรีสะเกษ; Sisaket", "63"}, {"สงขลา; Songkhla", "64"}, {"สุโขทัย; Sukhothai", "65"}, {"สุพรรณบุรี; Suphan Buri", "66"}, {"สุราษฎร์ธานี; Surat Thani", "67"}, {"สุรินทร์ ; Surin", "68"}, {"ตาก; Tak", "69"}, {"ตรัง; Trang", "70"}, {"ตราด; Trat", "71"}, {"อุบลราชธาน ; Ubon Ratchathani", "72"}, {"อุดรธานี; Udon Thani", "73"}, {"อุตรดิตถ์; Uttaradit", "74"}, {"ยะลา; Yala", "75"}, {"อุทัยธานี;Uthai Thani", "76"}, {"ยโสธร;Yasothon", "77"}};
   private static final String[] ArrayDateTimeFormat = new String[]{"yyyy-MM-dd H:mm:ss", "yyyy-MM-dd H:mm", "yyyy-MM-dd", "yy-MM-dd H:mm:ss", "yy-MM-dd H:mm", "yy-MM-dd", "MM/dd/yyyy HH:mm:ss", "MM/dd/yyyy H:mm", "MM/dd/yyyy", "yyyy/MM/dd H:mm:ss", "yyyy/MM/dd H:mm", "yyyy/MM/dd", "yy/MM/dd H:mm:ss", "yy/MM/dd H:mm", "yy/MM/dd", "MM-dd-yyyy H:mm:ss", "MM-dd-yyyy H:mm", "MM-dd-yyyy", "MM-dd-yy H:mm:ss", "MM-dd-yy H:mm", "MM-dd-yy", "MM/dd/yy H:mm:ss", "MM/dd/yy H:mm", "MM/dd/yy", "MM/dd/yyyy"};
   static byte[] HEX_CHAR_TABLE = new byte[]{48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 65, 66, 67, 68, 69, 70};

   public static int getProVinceID(String provinceName) {
      String strProvince = "";

      for(int i = 0; i < g_arrProvinceThailand.length; ++i) {
         strProvince = g_arrProvinceThailand[i][0];
         if (strProvince.compareToIgnoreCase(provinceName) == 0) {
            return Integer.parseInt(g_arrProvinceThailand[i][1]);
         }
      }

      return 0;
   }

   public static float atof(String str) {
      try {
         str = str.trim();
         str = str.replace("\r", "");
         str = str.replace("\n", "");
         str = str.replace("!", "");
         str = str.replace("@", "");
         str = str.replace("#", "");
         str = str.replace("$", "");
         str = str.replace("%", "");
         str = str.replace("^", "");
         str = str.replace("&", "");
         str = str.replace("*", "");
         str = str.replace("(", "");
         str = str.replace(")", "");
         str = str.replace("_", "");
         str = str.replace("`", "");
         str = str.replace("~", "");
         str = str.replace("\\", "");
         str = str.replace("/", "");
         str = str.replace("?", "");
         str = str.replace(">", "");
         str = str.replace("<", "");
         return Float.parseFloat(str);
      } catch (Exception var2) {
         return 0.0F;
      }
   }

   public static void deleteFile(String string) {
      try {
         File f = new File(string);
         f.delete();
         Log.d("test", string);
      } catch (Exception var2) {
      }

   }

   public static String createSHA_512(String input) {
      MessageDigest md = null;

      try {
         md = MessageDigest.getInstance("SHA-512");
      } catch (NoSuchAlgorithmException var3) {
         return input;
      }

      byte[] digest = md.digest(input.getBytes());
      return byteArrayToHexString(digest);
   }

   public static String createSHA_512_ASCII(String input) {
      byte[] digest = null;

      try {
         digest = input.getBytes("US-ASCII");
      } catch (UnsupportedEncodingException var3) {
         var3.printStackTrace();
      }

      return byteArrayToHexString(digest);
   }

   public static boolean checkDirectory(String strPath) {
      File f = new File(strPath);
      if (!f.exists()) {
         f.mkdirs();
      }

      return false;
   }

   public static boolean checkExist(String strPath) {
      if (strPath == null) {
         return false;
      } else {
         File f = new File(strPath);
         return f.exists();
      }
   }

   /*public static byte[] compressImage(Bitmap bitmap, int quality, String path) {
      ByteArrayOutputStream output = new ByteArrayOutputStream();
      bitmap.compress(CompressFormat.JPEG, quality, output);
      byte[] p = output.toByteArray();
      if (path != null) {
         try {
            CFile f = new CFile();
            if (f.Open(path, CFile.ModeWrite)) {
               f.Write(p);
               f.Close();
            }
         } catch (Exception var6) {
         }
      }

      return p;
   }*/

   public static boolean copyResources(Context context, String destination) {
      AssetManager mng = context.getAssets();

      try {
         String[] files = mng.list("");
         String[] var4 = files;
         int var5 = files.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            String str = var4[var6];
            if (str.contains(".")) {
               String strDes = destination + "/" + str;
               File fDes = new File(strDes);
               InputStream in;
               if (!fDes.exists()) {
                  try {
                     in = mng.open(str);
                     OutputStream out = new FileOutputStream(fDes);
                     byte[] buf = new byte[1024];

                     int len;
                     while((len = in.read(buf)) > 0) {
                        out.write(buf, 0, len);
                     }

                     in.close();
                     out.close();
                  } catch (Exception var20) {
                     var20.printStackTrace();
                  }
               } else {
                  in = mng.open(str);
                  InputStream inFile = new FileInputStream(fDes);
                  if (in.available() != inFile.available()) {
                     try {
                        /*InputStream*/ in = mng.open(str);
                        OutputStream out = new FileOutputStream(fDes);
                        byte[] buf = new byte[1024];

                        int len;
                        while((len = in.read(buf)) > 0) {
                           out.write(buf, 0, len);
                        }

                        out.close();
                     } catch (Exception var21) {
                        var21.printStackTrace();
                     }
                  }

                  in.close();
                  inFile.close();
               }
            } else {
               String[] sSub = mng.list(str);
               if (sSub.length > 0) {
                  String strDes = destination + "/" + str;
                  checkDirectory(strDes);
                  String[] var10 = sSub;
                  int var11 = sSub.length;

                  for(int var12 = 0; var12 < var11; ++var12) {
                     String s = var10[var12];
                     if (s.contains(".")) {
                        String sDes = strDes + "/" + s;
                        File fDes = new File(sDes);

                        try {
                           InputStream in = mng.open(str + "/" + s);
                           OutputStream out = new FileOutputStream(fDes);
                           byte[] buf = new byte[1024];

                           int len;
                           while((len = in.read(buf)) > 0) {
                              out.write(buf, 0, len);
                           }

                           in.close();
                           out.close();
                        } catch (Exception var22) {
                           var22.printStackTrace();
                        }
                     }
                  }
               }
            }
         }
      } catch (Exception var23) {
         var23.printStackTrace();
      }

      return false;
   }

   public static String ChangeDate(String strInput) {
      String strOutput = "";

      try {
         SimpleDateFormat formater = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
         Date date = formater.parse(strInput);
         if (date != null) {
            SimpleDateFormat output = new SimpleDateFormat("MM-dd-yy", Locale.US);
            strOutput = output.format(date);
         }
      } catch (Exception var6) {
         strOutput = strInput;
      }

      if (strOutput.length() == 0) {
         try {
            String[] arr = SplitString(strInput, "/");
            if (arr[2].length() == 2) {
               strOutput = arr[0] + "-" + arr[1] + "-" + arr[2];
            } else {
               String strYear = arr[2].substring(arr[2].length() - 2, arr[2].length());
               strOutput = arr[0] + "-" + arr[1] + "-" + strYear;
            }
         } catch (Exception var5) {
         }
      }

      return strOutput;
   }

   public static String ChangeTime(String strInput) {
      String strOutput = "";
      String strAP = "";
      if (strInput.indexOf("AM") == -1 && strInput.indexOf("PM") == -1) {
         try {
            SimpleDateFormat formater = new SimpleDateFormat("hh:mm:ss", Locale.US);
            Date date = formater.parse(strInput);
            if (date != null) {
               SimpleDateFormat output = new SimpleDateFormat("hh:mm:ss a", Locale.US);
               strOutput = output.format(date);
            }
         } catch (Exception var8) {
            strOutput = "";
         }

         if (strOutput.length() == 0) {
            String[] arrStringTime = strInput.split(":");

            try {
               int nHour = Integer.parseInt(arrStringTime[0]);
               String strMinutes = arrStringTime[1];
               String strSecond = arrStringTime[2].substring(0, 2);
               if (nHour >= 12) {
                  strAP = " PM";
               } else {
                  strAP = " AM";
               }

               if (nHour == 0) {
                  nHour = 12;
                  strAP = " AM";
               }

               if (nHour > 12) {
                  nHour -= 12;
               }

               if (nHour == 0) {
                  nHour = 12;
               }

               strOutput = String.format(Locale.US, "%d:%s:%s%s", nHour, strMinutes, strSecond, strAP);
            } catch (Exception var7) {
               strOutput = strInput;
               var7.printStackTrace();
            }
         }
      } else {
         strOutput = strInput;
      }

      int pos = strOutput.indexOf("0");
      if (pos == 0) {
         strOutput = strOutput.substring(1, strOutput.length());
      }

      return strOutput;
   }

   public static Bitmap decodeImage(byte[] data, int nOutWidth) {
      try {
//         int nImageHeight = false;
         Options opts = new Options();
         opts.inJustDecodeBounds = true;
         BitmapFactory.decodeByteArray(data, 0, data.length, opts);
         opts.inJustDecodeBounds = false;
         int nImageHeight = opts.outHeight * nOutWidth / opts.outWidth;
         int nRatio = getRatio(opts.outWidth, nOutWidth);
         boolean bResizeTwice = false;
         if (nRatio >= 1) {
            bResizeTwice = true;
            opts.inSampleSize = nRatio;
         } else {
            int iUocChungLonNhat = TimUocChungLonNhat(opts.outWidth, nOutWidth);
            opts.inDensity = opts.outWidth / iUocChungLonNhat;
            opts.inTargetDensity = nOutWidth / iUocChungLonNhat;
         }

         opts.inPreferredConfig = Config.ARGB_8888;
         opts.inTempStorage = new byte[16384];
         Bitmap bitmapTemp = BitmapFactory.decodeByteArray(data, 0, data.length, opts);
         if (bitmapTemp != null) {
            if (bResizeTwice) {
               bitmapTemp = Bitmap.createScaledBitmap(bitmapTemp, nOutWidth, nImageHeight, true);
            }

            return bitmapTemp;
         } else {
            return null;
         }
      } catch (Exception var8) {
         return null;
      }
   }

   public static Bitmap decodeImage(String strFileName, int nOutWidth, int orientation) {
      try {
         if (nOutWidth == -1) {
            Options opts = new Options();
            opts.inPreferredConfig = Config.ARGB_8888;
            opts.inTempStorage = new byte[16384];
            Bitmap bitmap = BitmapFactory.decodeFile(strFileName, opts);
            return bitmap;
         } else {
            int nImageWidth = nOutWidth;
//            int nImageHeight = false;
            Options opts = new Options();
            opts.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(strFileName, opts);
            opts.inJustDecodeBounds = false;
            int nImageHeight = opts.outHeight * nOutWidth / opts.outWidth;
            int nRatio = getRatio(opts.outWidth, nOutWidth);
            boolean bResizeTwice = false;
            if (nRatio >= 1) {
               bResizeTwice = true;
               opts.inSampleSize = nRatio;
            } else {
               int iUocChungLonNhat = TimUocChungLonNhat(opts.outWidth, nOutWidth);
               opts.inDensity = opts.outWidth / iUocChungLonNhat;
               opts.inTargetDensity = nOutWidth / iUocChungLonNhat;
            }

            opts.inPreferredConfig = Config.ARGB_8888;
            opts.inTempStorage = new byte[16384];
            Bitmap bitmapTemp = BitmapFactory.decodeFile(strFileName, opts);
            if (bitmapTemp != null) {
               if (bResizeTwice) {
                  bitmapTemp = Bitmap.createScaledBitmap(bitmapTemp, nOutWidth, nImageHeight, true);
               }

               if (orientation != -1) {
                  Matrix mtx = new Matrix();
                  mtx.postRotate((float)orientation);
                  bitmapTemp = Bitmap.createBitmap(bitmapTemp, 0, 0, nOutWidth, nImageHeight, mtx, true);
               } else {
                  try {
                     ExifInterface exif = new ExifInterface(strFileName);
                     int nOrientation = exif.getAttributeInt("Orientation", 1);
                     if (nOrientation != 1 || nOrientation != 0) {
                        int degress = 0;
                        if (nOrientation == 6) {
                           degress = 90;
                        } else if (nOrientation == 3) {
                           degress = 180;
                        } else if (nOrientation == 8) {
                           degress = 270;
                        }

                        if (degress != 0) {
                           Matrix mtx = new Matrix();
                           mtx.postRotate((float)degress);
                           bitmapTemp = Bitmap.createBitmap(bitmapTemp, 0, 0, nImageWidth, nImageHeight, mtx, true);
                        }
                     }
                  } catch (IOException var13) {
                  }
               }

               return bitmapTemp;
            } else {
               return null;
            }
         }
      } catch (Exception var14) {
         return null;
      }
   }

   public static int getRatio(int nInWidth, int nMinOutWitdh, int nMaxOutWitdh) {
      for(int i = nMaxOutWitdh; i >= nMinOutWitdh; --i) {
         int nFirst = nInWidth / i;
         int nLast = nInWidth % i;
         if (nLast == 0 && Is2PowerK(nFirst)) {
            return nFirst;
         }
      }

      return 1;
   }

   public static int getRatio(int nInWidth, int nOutWitdh) {
      double fRatio = (double)(nInWidth / nOutWitdh);
      int nRatio = (int)Math.floor(fRatio);
      if (nRatio >= 0 && nRatio <= 1) {
         return 1;
      } else if (nRatio >= 2 && nRatio <= 3) {
         return 2;
      } else if (nRatio >= 4 && nRatio <= 7) {
         return 4;
      } else if (nRatio >= 8 && nRatio <= 15) {
         return 8;
      } else {
         return nRatio >= 16 ? 16 : 1;
      }
   }

   static boolean Is2PowerK(int n) {
      switch(n) {
      case 2:
         return true;
      case 4:
         return true;
      case 8:
         return true;
      case 16:
         return true;
      case 32:
         return true;
      default:
         return false;
      }
   }
/*

   public static int isImage(String strPath) {
      try {
         byte[] p = readBinaryFile(strPath);
         if (p.length == 1) {
            return -1;
         } else if (p.length == 7) {
            return 0;
         } else {
            Bitmap bitmap = decodeImage(strPath, 240, -1);
            return bitmap != null ? 1 : -1;
         }
      } catch (Exception var3) {
         return 0;
      }
   }
*/

   public static int convertHorizontal(int input, float density, int width, int STD_WIDTH, float STD_DENSITY) {
      float wRate = (float)width * STD_DENSITY / (float)STD_WIDTH / density;
      int output = (int)((float)input * wRate);
      return output;
   }

   public static int convertVertical(int input, float density, int height, int STD_HEIGHT, float STD_DENSITY) {
      float hRate = (float)height * STD_DENSITY / (float)STD_HEIGHT / density;
      int output = (int)((float)input * hRate);
      return output;
   }

   public static void SwapArrayForEndian(byte[] arr) {
      byte[] n = new byte[arr.length];

      for(int idx = 0; idx < n.length; ++idx) {
         n[idx] = arr[arr.length - idx - 1];
      }

      System.arraycopy(n, 0, arr, 0, arr.length);
   }

   public static int atoi(String strInput, int nRadix) {
      int i = 0;

      try {
         strInput = strInput.replace("!", "");
         strInput = strInput.replace("@", "");
         strInput = strInput.replace("#", "");
         strInput = strInput.replace("$", "");
         strInput = strInput.replace("%", "");
         strInput = strInput.replace("^", "");
         strInput = strInput.replace("&", "");
         strInput = strInput.replace("*", "");
         strInput = strInput.replace("(", "");
         strInput = strInput.replace(")", "");
         strInput = strInput.replace("_", "");
         strInput = strInput.replace("`", "");
         strInput = strInput.replace("~", "");
         strInput = strInput.replace("\\", "");
         strInput = strInput.replace("/", "");
         strInput = strInput.replace("?", "");
         strInput = strInput.replace(">", "");
         strInput = strInput.replace("<", "");
         i = Integer.parseInt(strInput, nRadix);
      } catch (Exception var4) {
      }

      return i;
   }

   public static int strlen(byte[] arr) {
      int iLen;
      for(iLen = 0; iLen < arr.length && arr[iLen] != 0; ++iLen) {
      }

      return iLen;
   }

   public static String ConvertByteArrayToString(byte[] arr) {
      String s = "";
      int iLen = strlen(arr);

      try {
         s = new String(arr, 0, iLen, "US-ASCII");
      } catch (UnsupportedEncodingException var4) {
         var4.printStackTrace();
         s = "";
      }

      return s;
   }

   public static int atoi(byte[] p, int idx) {
      byte[] data = new byte[16];
      System.arraycopy(p, idx, data, 0, 16);
      return atoi(data);
   }

   public static int atoi(byte[] p) {
      return atoi((String)ConvertByteArrayToString(p), 10);
   }

   public static int atoi(String strInput) {
      return atoi((String)strInput, 10);
   }

   public static double atod(String strInput) {
      double i = 0.0D;

      try {
         strInput = strInput.replace("!", "");
         strInput = strInput.replace("@", "");
         strInput = strInput.replace("#", "");
         strInput = strInput.replace("$", "");
         strInput = strInput.replace("%", "");
         strInput = strInput.replace("^", "");
         strInput = strInput.replace("&", "");
         strInput = strInput.replace("*", "");
         strInput = strInput.replace("(", "");
         strInput = strInput.replace(")", "");
         strInput = strInput.replace("_", "");
         strInput = strInput.replace("`", "");
         strInput = strInput.replace("~", "");
         strInput = strInput.replace("\\", "");
         strInput = strInput.replace("/", "");
         strInput = strInput.replace("?", "");
         strInput = strInput.replace(">", "");
         strInput = strInput.replace("<", "");
         i = Double.parseDouble(strInput);
      } catch (Exception var4) {
      }

      return i;
   }

   public static double atod(byte[] p, int idx) {
      byte[] data = new byte[16];
      System.arraycopy(p, idx, data, 0, 16);
      return atod(data);
   }

   public static double atod(byte[] p) {
      return atod(ConvertByteArrayToString(p));
   }

   public static int TimUocChungLonNhat(int a, int b) {
      while(a != b) {
         if (a > b) {
            a -= b;
         } else {
            b -= a;
         }
      }

      return a;
   }

   public static byte[] hexa2Byte(String strData) {
      if (strData.length() < 3) {
         return null;
      } else {
         byte[] b = new byte[(strData.length() - 2) / 2];
         int iIndex = 0;

         for(int i = 0; i < b.length; ++i) {
            iIndex += 2;
            int v = atoi((String)strData.substring(iIndex, iIndex + 2), 16);
            b[i] = (byte)v;
         }

         return b;
      }
   }

   public static byte[] hexStringToByteArray(String s, boolean have_header) {
      int len = s.length();
//      byte[] data = null;
      int start = 0;
      byte[] data;
      if (have_header) {
         start = 2;
         data = new byte[len / 2 - 1];
      } else {
         data = new byte[len / 2];
      }

      int des = 0;

      for(int i = start; i < len; i += 2) {
         data[des] = (byte)((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
         ++des;
      }

      return data;
   }

   public static String byteArrayToHexString(byte[] bytes) {
      char[] hexChars = new char[bytes.length * 2];

      for(int j = 0; j < bytes.length; ++j) {
         int v = bytes[j] & 255;
         hexChars[j * 2] = hexArray[v >>> 4];
         hexChars[j * 2 + 1] = hexArray[v & 15];
      }

      return new String(hexChars);
   }

   public static String GetTextContent(Node subNode) {
      try {
         if (subNode == null) {
            return "";
         } else {
            NodeList listChildNode = subNode.getChildNodes();

            for(int i = 0; i < listChildNode.getLength(); ++i) {
               Node node = listChildNode.item(i);
               if (node != null) {
                  String strText = node.getNodeValue();
                  if (strText != null) {
                     return strText.trim();
                  }

                  return "";
               }
            }

            return "";
         }
      } catch (Exception var5) {
         return "";
      }
   }

   public static String byte2Hexa(byte[] bytes) {
      return null;
   }

   public static String readTextFile(String strPath) {
      StringBuilder text = new StringBuilder();

      try {
         BufferedReader br = new BufferedReader(new FileReader(strPath));

         String line;
         while((line = br.readLine()) != null) {
            text.append(line);
            text.append('\n');
         }

         br.close();
         return text.toString();
      } catch (IOException var4) {
         return "";
      }
   }

  /* public static void writeTextFile(String strPath, String strData) {
      CFile f = new CFile();
      if (f.Open(strPath, CFile.ModeAppend)) {
         try {
            f.Write(strData);
         } catch (IOException var4) {
            var4.printStackTrace();
         }

         f.Close();
      }

   }

   public static void writeTextFileModeCreate(String strPath, String strData) {
      CFile f = new CFile();
      if (f.Open(strPath, CFile.ModeWrite)) {
         try {
            f.Write(strData);
         } catch (IOException var4) {
            var4.printStackTrace();
         }

         f.Close();
      }

   }

   public static void writeDataFile(byte[] pData, String path) {
      if (path != null) {
         try {
            CFile f = new CFile();
            if (f.Open(path, CFile.ModeWrite)) {
               f.Write(pData);
               f.Close();
            }
         } catch (Exception var3) {
         }
      }

   }*/

   public static String[] SplitString(String strInput, String strDe) {
      String[] aaa = null;

      try {
         List<String> arrReturnStr = new ArrayList();
         boolean var4 = true;

         while(true) {
            int nPos = strInput.indexOf(strDe);
            if (nPos == -1) {
               arrReturnStr.add(strInput);
            } else {
               arrReturnStr.add(strInput.substring(0, nPos));
            }

            if (nPos == -1) {
               aaa = new String[arrReturnStr.size()];
               arrReturnStr.toArray(aaa);
               break;
            }

            strInput = strInput.substring(nPos + strDe.length(), strInput.length());
         }
      } catch (Exception var5) {
         var5.printStackTrace();
      }

      return aaa;
   }

  /* public static boolean showImage(ImageView imgView, ProgressBar loading, TextView labelLoading, TextView noimage, String imagePath, boolean fullsize, int orientation) {
      if (!checkExist(imagePath)) {
         if (imgView != null) {
            imgView.setVisibility(8);
         }

         if (noimage != null) {
            noimage.setVisibility(0);
         }

         if (loading != null) {
            loading.setVisibility(8);
         }

         if (labelLoading != null) {
            labelLoading.setVisibility(0);
         }

         return false;
      } else {
         int status = isImage(imagePath);
         Bitmap bm = null;

         try {
            if (fullsize) {
               bm = decodeImage(imagePath, -1, orientation);
            } else {
               bm = decodeImage(imagePath, 240, orientation);
            }
         } catch (Exception var10) {
         }

         if (bm == null) {
            if (imgView != null) {
               imgView.setVisibility(8);
            }

            if (status == -1) {
               if (noimage != null) {
                  noimage.setVisibility(0);
               }

               if (loading != null) {
                  loading.setVisibility(8);
               }

               if (labelLoading != null) {
                  labelLoading.setVisibility(8);
               }

               return true;
            } else {
               if (status == 0) {
                  boolean bHaveLoading = false;
                  if (loading != null) {
                     loading.setVisibility(0);
                     bHaveLoading = true;
                  }

                  if (labelLoading != null) {
                     labelLoading.setVisibility(0);
                     bHaveLoading = true;
                  }

                  if (!bHaveLoading && noimage != null) {
                     noimage.setVisibility(0);
                  }
               }

               return false;
            }
         } else {
            if (noimage != null) {
               noimage.setVisibility(8);
            }

            if (loading != null) {
               loading.setVisibility(8);
            }

            if (labelLoading != null) {
               labelLoading.setVisibility(8);
            }

            if (imgView != null) {
               imgView.setVisibility(0);
               imgView.setImageBitmap(bm);
            }

            return true;
         }
      }
   }
*/
   public static String createDownloadStaticMapURL(double latitude, double longitude) {
      String strURL = "http://maps.google.com/maps/api/staticmap?center=" + latitude + "," + longitude + "&zoom=15&size=600x272&markers=color:red%7Clabel:A0%7C" + latitude + "," + longitude + "&sensor=false";
      return strURL;
   }

   public static String ReplaceSpecialCharaters1(String strURL) {
      String str = strURL;
      int size = g_arrSpecialChars.length;

      for(int i = 0; i < size; ++i) {
         try {
            int pos = -2;

            do {
               pos = str.indexOf(g_arrSpecialChars[i][0], pos + 2);
               if (pos != -1) {
                  String str1 = str.substring(0, pos);
                  String str2 = str.substring(pos + 1, str.length());
                  str = str1 + g_arrSpecialChars[i][1] + str2;
               }
            } while(pos >= 0);
         } catch (Exception var7) {
         }
      }

      return str;
   }

   public static String ReplaceSpecialCharaters(String strURL) {
      String strReturn = strURL;
      List<String> arrSpecial = new ArrayList();
      arrSpecial.add("&lt;");
      arrSpecial.add("&gt;");
      arrSpecial.add("&apos;");
      arrSpecial.add("&quot;");
      arrSpecial.add("&amp;");
      Iterator var3 = arrSpecial.iterator();

      while(var3.hasNext()) {
         String special = (String)var3.next();

         try {
            while(strReturn.contains(special)) {
               String[] ids = strReturn.split(special);
               int size = ids.length;
               if (special.compareToIgnoreCase("&lt;") == 0) {
                  if (size > 1) {
                     strReturn = ids[0] + "<" + ids[1];
                  } else if (size == 1) {
                     strReturn = ids[0] + "<";
                  }
               } else if (special.compareToIgnoreCase("&gt;") == 0) {
                  if (size > 1) {
                     strReturn = ids[0] + ">" + ids[1];
                  } else if (size == 1) {
                     strReturn = ids[0] + ">";
                  }
               } else if (special.compareToIgnoreCase("&apos;") == 0) {
                  if (size > 1) {
                     strReturn = ids[0] + "'" + ids[1];
                  } else if (size == 1) {
                     strReturn = ids[0] + "'";
                  }
               } else if (special.compareToIgnoreCase("&quot;") == 0) {
                  if (size > 1) {
                     strReturn = ids[0] + "\"" + ids[1];
                  } else if (size == 1) {
                     strReturn = ids[0] + "\"";
                  }
               } else if (special.compareToIgnoreCase("&amp;") == 0) {
                  if (size > 1) {
                     strReturn = ids[0] + "&" + ids[1];
                  } else if (size == 1) {
                     strReturn = ids[0] + "&";
                  }
               }
            }
         } catch (Exception var7) {
         }
      }

      return strReturn;
   }

   public static String getTimeZoneString() {
      TimeZone tz = TimeZone.getDefault();
      return tz.getDisplayName();
   }

   public static int getTimeZone() {
      TimeZone tz = TimeZone.getDefault();
      long rawOff = (long)tz.getRawOffset();
      return (int)(rawOff / 60000L);
   }

   public static String createGUID() {
      UUID uuid = UUID.randomUUID();
      return uuid.toString();
   }

   public static String getGMTTime(long now) {
      Date date = new Date(now);
      SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
      formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
      return formatter.format(date);
   }

   public static String getGMTTime(long now, String format) {
      Date date = new Date(now);
      SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.US);
      formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
      return formatter.format(date);
   }

   public static String getLocalTime(long now) {
      Date date = new Date(now);
      SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
      return formatter.format(date);
   }

   public static String getLocalTime(long now, String format) {
      Date date = new Date(now);
      SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.US);
      return formatter.format(date);
   }

   public static int ByteArrayToInt(byte[] pData, boolean bBigEndian) {
      int bits = 0;
      int i = 0;
      if (bBigEndian) {
         SwapArrayForEndian(pData);
      }

      for(int shifter = 3; shifter >= 0; --shifter) {
         bits |= (pData[i] & 255) << shifter * 8;
         ++i;
      }

      return bits;
   }

   public static byte[] IntToByteArray(int nInput, boolean bBigEndian) {
      byte[] result = new byte[4];

      for(int i = 0; i < 4; ++i) {
         int offset = (result.length - 1 - i) * 8;
         result[i] = (byte)(nInput >>> offset & 255);
      }

      if (bBigEndian) {
         SwapArrayForEndian(result);
      }

      return result;
   }

   /*public static byte[] readBinaryFile(String path) {
      CFile f = new CFile();
      if (f.Open(path, CFile.ModeRead)) {
         byte[] pData = new byte[f.GetLength()];
         f.Read(pData, f.GetLength());
         f.Close();
         return pData;
      } else {
         return null;
      }
   }*/

   public static void CopyAssets(Context c, String toPath) {
      AssetManager mng = c.getAssets();
      String[] files = null;
      InputStream in = null;
      FileOutputStream out = null;

      try {
         files = mng.list("");
      } catch (IOException var12) {
         var12.printStackTrace();
      }

      String[] var6 = files;
      int var7 = files.length;

      for(int var8 = 0; var8 < var7; ++var8) {
         String filename = var6[var8];
         System.out.println("Data" + filename);
         if (filename.contains(".")) {
            try {
               in = mng.open(filename);
               File f = new File(toPath + "/" + filename);
               if (!f.exists()) {
                  out = new FileOutputStream(f);
                  copyFile(in, out);
                  in.close();
                  in = null;
                  out.flush();
                  out.close();
                  out = null;
               }
            } catch (IOException var11) {
               var11.printStackTrace();
            }
         }
      }

   }

   private static void copyFile(InputStream in, OutputStream out) throws IOException {
      byte[] buffer = new byte[1024];

      int read;
      while((read = in.read(buffer)) != -1) {
         out.write(buffer, 0, read);
      }

   }

   public static boolean copyFile2File(String sIn, String sOut) {
      InputStream in = null;
      OutputStream out = null;
      File f = new File(sOut);

      try {
         in = new FileInputStream(sIn);
         if (!f.exists()) {
            out = new FileOutputStream(f);
            copyFile(in, out);
            in.close();
            out.flush();
            out.close();
         }

         boolean var5 = true;
         return var5;
      } catch (IOException var10) {
         var10.printStackTrace();
         boolean var6 = false;
         return var6;
      } finally {
         ;
      }
   }

   public static boolean Copy(InputStream input, String path) {
      File f = new File(path);
      if (f.exists()) {
         return true;
      } else {
         FileOutputStream out = null;

         try {
            boolean var5;
            try {
               out = new FileOutputStream(path);
               byte[] buff = new byte[1024];
               var5 = false;

               int read;
               while((read = input.read(buff)) > 0) {
                  out.write(buff, 0, read);
               }

               input.close();
               out.close();
               boolean var6 = true;
               return var6;
            } catch (Exception var10) {
               var5 = false;
               return var5;
            }
         } finally {
            ;
         }
      }
   }

   public static byte[] Int2ByteArray(int value) {
      String strValue = Integer.toString(value);
      return strValue.getBytes();
   }

   public static byte[] Bool2ByteArray(boolean value) {
      String strValue = Boolean.toString(value);
      return strValue.getBytes();
   }

   public static byte[] Double2ByteArray(double value) {
      String strValue = Double.toString(value);
      return strValue.getBytes();
   }

   public static String getTimeZoneStandard() {
      TimeZone tz = TimeZone.getDefault();
      long rawOff = (long)tz.getRawOffset();
      String strTimeZone = "";
      float fDiff = 0.0F;
      float f = (float)(rawOff / 3600000L);
      if (f == 0.0F) {
         strTimeZone = "GMT";
      } else if (f == -5.0F) {
         strTimeZone = "EST";
      } else if (f == -6.0F) {
         strTimeZone = "CST";
      } else if (f == -7.0F) {
         strTimeZone = "MST";
      } else if (f == -8.0F) {
         strTimeZone = "PST";
      } else {
         DecimalFormat df = null;
         if (f > 0.0F) {
            df = new DecimalFormat("+####");
         } else {
            df = new DecimalFormat("####");
         }

         df.setMinimumIntegerDigits(4);
         String strTmp = df.format((double)(f * 100.0F));
         strTimeZone = strTmp;
      }

      return strTimeZone;
   }

   public static String CreateMD5(byte[] input) {
      try {
         MessageDigest digest = MessageDigest.getInstance("MD5");
         digest.update(input);
         byte[] messageDigest = digest.digest();
         return ByteArrayToHexa(messageDigest, false);
      } catch (Exception var3) {
         return ByteArrayToHexa(input, false);
      }
   }

   public static String createSHA(String input) {
      String str = "";

      try {
         MessageDigest digest = MessageDigest.getInstance("SHA-1");
         byte[] p = input.getBytes();
         byte[] out = digest.digest(p);
         str = ByteArrayToHexa(out, false);
      } catch (NoSuchAlgorithmException var5) {
         var5.printStackTrace();
         str = CreateMD5(input.getBytes());
      }

      return str;
   }

   public static String ByteArrayToHexa(byte[] pData, boolean Ox) {
      try {
         if (pData == null) {
            return null;
         } else {
//            byte[] hex = null;
            int iIndex = 0;
            byte[] hex;
            if (Ox) {
               hex = new byte[pData.length * 2 + 2];
               hex[0] = 48;
               hex[1] = 120;
               iIndex = 2;
            } else {
               hex = new byte[pData.length * 2];
            }

            byte[] var4 = pData;
            int var5 = pData.length;

            for(int var6 = 0; var6 < var5; ++var6) {
               byte b = var4[var6];
               int v = b & 255;
               hex[iIndex++] = HEX_CHAR_TABLE[v >>> 4];
               hex[iIndex++] = HEX_CHAR_TABLE[v & 15];
            }

            return new String(hex, "ASCII");
         }
      } catch (Exception var9) {
         return null;
      }
   }

   public static String getHomeDateTime() {
      String strDateTime = "";
      Date curDate = new Date();
      SimpleDateFormat curFormater = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.US);
      strDateTime = curFormater.format(curDate);
      return strDateTime;
   }

   public static String getHomeGMTDateTime() {
      String strGMTDateTime = "";
      Date curDate = new Date();
      SimpleDateFormat curFormater = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.US);
      curFormater.setTimeZone(TimeZone.getTimeZone("GMT"));
      strGMTDateTime = curFormater.format(curDate);
      return strGMTDateTime;
   }

   public static String getDeviceID(Context context) {
      return Secure.getString(context.getContentResolver(), "android_id");
   }

   public static String getHomeTimeZoneOffset() {
      String strTimeZone = "";
      TimeZone tz = TimeZone.getDefault();
      int off = tz.getRawOffset() / 3600;
//      int hour = false;
//      int minute = false;
      int min = tz.getRawOffset() / 1000 / 60;
      int hour;
      int minute;
      if (min % 60 == 0) {
         minute = 0;
         hour = min / 60;
      } else {
         minute = min % 60;
         if (minute < 0) {
            minute = -minute;
         }

         hour = (min - min % 60) / 60;
      }

      if (off > 0) {
         strTimeZone = String.format(Locale.US, "+%1d:%2d", hour, minute);
      } else {
         strTimeZone = String.format(Locale.US, "%1d:%2d", hour, minute);
      }

      return strTimeZone;
   }

   public static String getHomeTimeZone() {
      String strTimeZone = "";
      TimeZone tz = TimeZone.getDefault();
      strTimeZone = tz.getDisplayName(false, 0, Locale.US);
      return strTimeZone;
   }

   public static Date stringToDate(String input, TimeZone tz) {
      Date output = null;
      String[] var3 = ArrayDateTimeFormat;
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         String format = var3[var5];

         try {
            SimpleDateFormat curFormater = new SimpleDateFormat(format, Locale.US);
            curFormater.setTimeZone(tz);
            output = curFormater.parse(input);
         } catch (Exception var8) {
         }

         if (output != null) {
            break;
         }
      }

      return output;
   }

   public static Date stringToDate(String input) {
      Date output = null;
      String[] var2 = ArrayDateTimeFormat;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         String format = var2[var4];

         try {
            SimpleDateFormat curFormater = new SimpleDateFormat(format, Locale.US);
            output = curFormater.parse(input);
         } catch (Exception var7) {
         }

         if (output != null) {
            break;
         }
      }

      return output;
   }

   public static String changeDateTimeFormat(String input, String format) {
      String output;
      try {
         Date date = stringToDate(input);
         SimpleDateFormat curFormater = new SimpleDateFormat(format, Locale.US);
         output = curFormater.format(date);
      } catch (Exception var5) {
         output = input;
      }

      return output;
   }

   public static String changeGMTDateTime2LocalFormat(String input, String format) {
      String output;
      try {
         Date date = stringToDate(input, TimeZone.getTimeZone("GMT"));
         SimpleDateFormat curFormater = new SimpleDateFormat(format, Locale.US);
         curFormater.setTimeZone(TimeZone.getDefault());
         output = curFormater.format(date);
      } catch (Exception var5) {
         output = input;
      }

      return output;
   }

   public static String location2Address(double longitude, double latitude, Context context, String path) {
      String str = "";
      boolean haveAddress = false;
      if (Geocoder.isPresent()) {
         Geocoder geoCoder = new Geocoder(context);

         try {
            List<Address> address = geoCoder.getFromLocation(latitude, longitude, 1);
            if (address != null && address.size() > 0) {
               haveAddress = true;
               str = ((Address)address.get(0)).toString();
            }
         } catch (IOException var27) {
            var27.printStackTrace();
         }
      }

      if (!haveAddress) {
         String strAPIKey = "";
         String strURL = "https://maps.googleapis.com/maps/api/geocode/json?latlng=" + latitude + "," + longitude + "&sensor=true&key=" + strAPIKey;

         try {
            URL url;
            try {
               url = new URL(strURL);
            } catch (MalformedURLException var26) {
               var26.printStackTrace();
               return "";
            }

            HttpURLConnection urlConnection;
            try {
               urlConnection = (HttpURLConnection)url.openConnection();
               urlConnection.setConnectTimeout(10000);
            } catch (SocketTimeoutException var24) {
               var24.printStackTrace();
               return "";
            } catch (IOException var25) {
               var25.printStackTrace();
               return "";
            }

            try {
               urlConnection.setChunkedStreamingMode(0);
               InputStream input = url.openStream();
               OutputStream output = new FileOutputStream(path);
               byte[] data = new byte[1024];
               boolean var15 = false;

               int count;
               while((count = input.read(data)) != -1) {
                  output.write(data, 0, count);
               }

               output.flush();
               output.close();
               input.close();
               str = readTextFile(path);
            } catch (Exception var28) {
               var28.printStackTrace();
            } finally {
               urlConnection.disconnect();
            }
         } catch (Exception var30) {
            var30.printStackTrace();
         }
      }

      return str;
   }

   public static float distance(double latitude, double longitude, double latitude1, double longitude1) {
      float[] results = new float[3];
      Location.distanceBetween(latitude1, longitude1, latitude, longitude, results);
      return results[0] / 1609.0F;
   }

   public static void deleteDirectory(String string) {
      File file = new File(string);
      if (file.exists()) {
         File[] files = file.listFiles();
         File[] var3 = files;
         int var4 = files.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            File f = var3[var5];
            if (f.isDirectory()) {
               deleteDirectory(f.getAbsolutePath());
            } else {
               f.delete();
            }
         }
      }

   }

   public static final Object fromJSON(String json, Class<?> cls) {
      Object obj = null;
      return obj;
   }

   public static final String toJSON(Object obj) {
      String json = "";
      return json;
   }

   public static String createDownloadAddress(String gps, String api_key) {
      String url = "https://maps.googleapis.com/maps/api/geocode/json?latlng=" + gps + "&sensor=false&result_type=street_address" + api_key;
      return url;
   }

   public static String createDownloadAddress(String gps) {
      String url = "https://maps.googleapis.com/maps/api/geocode/json?latlng=" + gps + "&sensor=false";
      return url;
   }

   public static String getDirectionsUrl(int origin, int dest) {
      String url = "";
      return url;
   }

   public static boolean atob(String input) {
      try {
         String s = input.toLowerCase();
         return Boolean.parseBoolean(s);
      } catch (Exception var2) {
         var2.printStackTrace();
         return false;
      }
   }

   public static String dateToString(Date date, String format) {
      String output = "";

      try {
         SimpleDateFormat curFormater = new SimpleDateFormat(format, Locale.US);
         output = curFormater.format(date);
      } catch (Exception var4) {
         var4.printStackTrace();
      }

      return output;
   }

   public static Date convertStringToDate(String strDate, String format) {
      SimpleDateFormat df = new SimpleDateFormat(format, Locale.US);

      try {
         return df.parse(strDate);
      } catch (ParseException var4) {
         var4.printStackTrace();
         return null;
      }
   }

   public static String changeDateFormat(String date, String srcFormat, String desFormat) {
      Date output = null;

      try {
         SimpleDateFormat curFormater = new SimpleDateFormat(srcFormat, Locale.US);
         output = curFormater.parse(date);
      } catch (Exception var5) {
      }

      return dateToString(output, desFormat);
   }

   public static String cursorToString(Cursor crs) {
      try {
         JSONObject row = new JSONObject();
         if (!crs.isAfterLast()) {
            int nColumns = crs.getColumnCount();

            for(int i = 0; i < nColumns; ++i) {
               String colName = crs.getColumnName(i);
               if (colName != null) {
                  try {
                     if (VERSION.SDK_INT >= 11) {
                        switch(crs.getType(i)) {
                        case 1:
                           row.put(colName, crs.getLong(i));
                           break;
                        case 2:
                           row.put(colName, crs.getDouble(i));
                           break;
                        case 3:
                           row.put(colName, crs.getString(i));
                        }
                     } else {
                        try {
                           String val = crs.getString(i);
                           row.put(colName, val);
                        } catch (Exception var6) {
                        }
                     }
                  } catch (JSONException var7) {
                  }
               }
            }
         }

         return row.toString();
      } catch (Exception var8) {
         var8.printStackTrace();
         return "";
      }
   }

  /* public static String convertImageToBase64(String imagePath) {
      byte[] byteArrayImage = readBinaryFile(imagePath);
      String result = Base64.encodeToString(byteArrayImage, 0);
      return result;
   }*/

   public static String encodeBase64(String strValue) {
      String strResult = "";
      byte[] encodeByte = Base64.encode(strValue.getBytes(), 0);
      strResult = new String(encodeByte);
      return strResult;
   }

   public static String getProVince(int idProvince) {
      if (idProvince > 0 && idProvince <= 77) {
         int id = idProvince - 1;
         String strRet = g_arrProvinceThailand[id][0];
         return strRet;
      } else {
         return "";
      }
   }

   public static Bitmap rotateImage(Bitmap source, float angle) {
      Matrix matrix = new Matrix();
      matrix.postRotate(angle);
      return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, false);
   }

   public static String generateFolderName() {
      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      Date date = new Date();
      String timeStamp = dateFormat.format(date);
      return timeStamp;
   }

   public static List<String> getPathFileSave(String path) {
      List<String> mPath = new ArrayList();
      File directory = new File(path);
      File[] files = directory.listFiles();

      for(int i = 0; i < files.length; ++i) {
         mPath.add(files[i].toString());
      }

      return mPath;
   }

   public static void deleteFolder(File folder) throws IOException {
      if (folder.isDirectory()) {
         File[] var1 = folder.listFiles();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            File ct = var1[var3];
            deleteFolder(ct);
         }
      }

      if (!folder.delete()) {
         throw new FileNotFoundException("Unable to delete: " + folder);
      }
   }

   public static int getScreenWidth() {
      return Resources.getSystem().getDisplayMetrics().widthPixels;
   }

   public static int getScreenHeight() {
      return Resources.getSystem().getDisplayMetrics().heightPixels;
   }

  /* public static boolean isGPSEnabled(Context mContext) {
      LocationManager lm = (LocationManager)mContext.getSystemService("location");
      return lm.isProviderEnabled("gps");
   }
*/
  /* public static boolean isNetworkAvailable(Context context) {
      ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService("connectivity");
      NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
      return activeNetworkInfo != null && activeNetworkInfo.isConnected();
   }

   public static void hideKeyboardPopup(Context context, View view) {
      InputMethodManager imm = (InputMethodManager)context.getSystemService("input_method");
      imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
   }

   public static void vibrateTouch(Context context) {
      Vibrator v = (Vibrator)context.getSystemService("vibrator");
      if (VERSION.SDK_INT >= 26) {
         v.vibrate(VibrationEffect.createOneShot(500L, -1));
      } else {
         v.vibrate(500L);
      }

   }*/

   public static Bitmap resizeBitmap(Bitmap image, int maxWidth, int maxHeight) {
      if (maxHeight > 0 && maxWidth > 0) {
         int width = image.getWidth();
         int height = image.getHeight();
         float ratioBitmap = (float)width / (float)height;
         float ratioMax = (float)maxWidth / (float)maxHeight;
         int finalWidth = maxWidth;
         int finalHeight = maxHeight;
         if (ratioMax > 1.0F) {
            finalWidth = (int)((float)maxHeight * ratioBitmap);
         } else {
            finalHeight = (int)((float)maxWidth / ratioBitmap);
         }

         image = Bitmap.createScaledBitmap(image, finalWidth, finalHeight, true);
         return image;
      } else {
         return image;
      }
   }

   public static Bitmap rotateBitmap(YuvImage yuvImage, int orientation, Rect rectangle) {
      ByteArrayOutputStream os = new ByteArrayOutputStream();
      yuvImage.compressToJpeg(rectangle, 100, os);
      Matrix matrix = new Matrix();
      matrix.postRotate((float)orientation);
      byte[] bytes = os.toByteArray();
      Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
      return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
   }

   public static Bitmap rotateBitmap(Bitmap bitmap, int orientation) {
      Matrix matrix = new Matrix();
      switch(orientation) {
      case 1:
         return bitmap;
      case 2:
         matrix.setScale(-1.0F, 1.0F);
         break;
      case 3:
         matrix.setRotate(180.0F);
         break;
      case 4:
         matrix.setRotate(180.0F);
         matrix.postScale(-1.0F, 1.0F);
         break;
      case 5:
         matrix.setRotate(90.0F);
         matrix.postScale(-1.0F, 1.0F);
         break;
      case 6:
         matrix.setRotate(90.0F);
         break;
      case 7:
         matrix.setRotate(-90.0F);
         matrix.postScale(-1.0F, 1.0F);
         break;
      case 8:
         matrix.setRotate(-90.0F);
         break;
      default:
         return bitmap;
      }

      try {
         Bitmap bmRotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
         bitmap.recycle();
         return bmRotated;
      } catch (OutOfMemoryError var4) {
         var4.printStackTrace();
         return null;
      }
   }

   public static String MD5Decode(String s) {
      String var1 = "MD5";

      try {
         MessageDigest digest = MessageDigest.getInstance("MD5");
         digest.update(s.getBytes());
         byte[] messageDigest = digest.digest();
         StringBuilder hexString = new StringBuilder();
         byte[] var5 = messageDigest;
         int var6 = messageDigest.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            byte aMessageDigest = var5[var7];

            String h;
            for(h = Integer.toHexString(255 & aMessageDigest); h.length() < 2; h = "0" + h) {
            }

            hexString.append(h);
         }

         return hexString.toString();
      } catch (NoSuchAlgorithmException var10) {
         var10.printStackTrace();
         return "";
      }
   }

  /* public static void openScreenLogin(Context context) {
      Intent i = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
      i.addFlags(67108864);
      context.startActivity(i);
      System.exit(0);
   }
*/
   public static Bitmap decodeBase64ToBitmap(String sBase64) {
      byte[] data = Base64.decode(sBase64, 0);
      return BitmapFactory.decodeByteArray(data, 0, data.length);
   }

   public static String decodeBitmapToBase64(Bitmap bitmap) {
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      bitmap.compress(CompressFormat.JPEG, 100, outputStream);
      return Base64.encodeToString(outputStream.toByteArray(), 0);
   }

   public static Drawable resizeDrawable(Context context, Drawable image, int w, int h) {
      Bitmap b = ((BitmapDrawable)image).getBitmap();
      Bitmap bitmapResized = Bitmap.createScaledBitmap(b, w, h, true);
      return new BitmapDrawable(context.getResources(), bitmapResized);
   }

   public static int calculateInSampleSize(Options options, int reqWidth, int reqHeight) {
      int height = options.outHeight;
      int width = options.outWidth;
      int inSampleSize = 1;
      if (height > reqHeight || width > reqWidth) {
         int halfHeight = height / 2;

         for(int halfWidth = width / 2; halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth; inSampleSize *= 2) {
         }
      }

      return inSampleSize;
   }

   public static int dpToPx(int dp) {
      return (int)((float)dp * Resources.getSystem().getDisplayMetrics().density);
   }

   public static int pxToDp(int dp) {
      return (int)((float)dp * Resources.getSystem().getDisplayMetrics().density);
   }

   public static boolean isHttpOrHttpsUrl(String url) {
      String patter = "^(http|https|ftp)://.*$";
      return url.matches(patter);
   }

   public static boolean IsBase64Encoded(String value) {
      String patter = "^(?:[A-Za-z0-9+/]{4})*(?:[A-Za-z0-9+/]{2}==|[A-Za-z0-9+/]{3}=)?$";
      return value.matches(patter);
   }

   public static boolean isBase64(String str) {
      if (TextUtils.isEmpty(str)) {
         return false;
      } else {
         try {
            Base64.decode(str, 0);
            return true;
         } catch (Exception var2) {
            return false;
         }
      }
   }

   public static String decrypt(String data) throws Exception {
      String keyValue = "vigilant";
      if (keyValue.length() > 8) {
         keyValue = keyValue.substring(0, 8);
      }

      ByteArrayOutputStream bout = new ByteArrayOutputStream();

      try {
         KeySpec keySpec = new DESKeySpec(keyValue.getBytes(Charset.forName("UTF-8")));
         SecretKey key = SecretKeyFactory.getInstance("DES").generateSecret(keySpec);
         IvParameterSpec iv = new IvParameterSpec(keyValue.getBytes(Charset.forName("UTF-8")));
         Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
         cipher.init(2, key, iv);
         byte[] inputByteArray = new byte[data.length() / 2];

         for(int i = 0; i < data.length() / 2; ++i) {
            int x = Integer.parseInt(data.substring(i * 2, i * 2 + 2), 16);
            inputByteArray[i] = (byte)x;
         }

         bout.write(cipher.doFinal(inputByteArray));
      } catch (Exception var10) {
         System.out.println("Exception ... " + var10);
      }

      return new String(bout.toByteArray(), "UTF-8");
   }

   public static String encrypt(String inputText) {
      String keyValue = "vigilant";
      if (keyValue.length() > 8) {
         keyValue = keyValue.substring(0, 8);
      }

      try {
         KeySpec keySpec = new DESKeySpec(keyValue.getBytes("US-ASCII"));
         SecretKey key = SecretKeyFactory.getInstance("DES").generateSecret(keySpec);
         IvParameterSpec iv = new IvParameterSpec(keyValue.getBytes("US-ASCII"));
         Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
         cipher.init(1, key, iv);
         byte[] inputByteArray = inputText.getBytes("UTF-8");
         byte[] cipherText = cipher.doFinal(inputByteArray);
         StringBuilder builder = new StringBuilder();

         for(int i = 0; i < cipherText.length; ++i) {
            builder.append(String.format("%02x", cipherText[i]));
         }

         return builder.toString();
      } catch (Exception var10) {
         System.out.println("Exception .. " + var10.getMessage());
         return null;
      }
   }

   @SuppressLint({"MissingPermission", "HardwareIds"})
   public static String getDeviceId() {
      String uniquePseudoID = "35" + Build.BOARD.length() % 10 + Build.BRAND.length() % 10 + Build.DEVICE.length() % 10 + Build.DISPLAY.length() % 10 + Build.HOST.length() % 10 + Build.ID.length() % 10 + Build.MANUFACTURER.length() % 10 + Build.MODEL.length() % 10 + Build.PRODUCT.length() % 10 + Build.TAGS.length() % 10 + Build.TYPE.length() % 10 + Build.USER.length() % 10;
      String serial = Build.getRadioVersion();
      String uuid = (new UUID((long)uniquePseudoID.hashCode(), (long)serial.hashCode())).toString();
      return uuid;
   }
}
