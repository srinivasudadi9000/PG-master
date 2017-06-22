package sales.pg.functions;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class Validations {
	private static final String TAG = null;
	private static int internetStatus;
	private static String internetType;
	public static  Context ctx;
	public static boolean setflag = true;


	public static final String[] navMenuTitles = new String[]{"Feed", "Direct", "Explore","Profile"};

    public static String[] feed_labels={" Antonzuenco "," @cher.mania ","nice eve studio photography"," #ua #model #new #face #girls #2015 #st "};

	public static final String profile_names[] = {
			"crew_music",
			"ex_white",

	};

	public static final String profile_address[] = {
			"@ann.fox friend",
			"following",

	};

	public static String grid_names[] = {"Image1","Image2","image3","image4","image5","image6"


	};






	public static boolean phnovalidation(String name) {
		String spl = "!@#$%^&*()=[]\\\';,./{}|\":<>?";
		if (name.length() > 2) {
			boolean flag = true;
			for (int i = 0; i < name.length(); i++) {
				if (spl.indexOf(name.charAt(i)) != -1) {
					flag = false;
					break;
				}
			}
			if (flag) {
				return true;
			}
		}
		return false;
	}


	public static boolean isConnectedToInternet(Context context) {
		Log.i(TAG, "Checking Internet Connection...");

		boolean found = false;

		try {
			ConnectivityManager cm = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo netInfo = cm.getActiveNetworkInfo();
			if (netInfo != null && netInfo.isConnectedOrConnecting()) {
				found = true;
				internetStatus = 0;
			}

			NetworkInfo wifi = cm
					.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
			NetworkInfo _3g = cm
					.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
			if (wifi.isConnected())
				internetType = "WiFi";

			if (_3g.isConnected())
				internetType = "3G";

		} catch (Exception e) {
			//Log.e("CheckConnectivity Exception", e.getMessage(), e);
		}

		if (found)
			Log.i(TAG, "Internet Connection found.");
		else
			Log.i(TAG, "Internet Connection not found.");

		return found;
	}



	public static boolean compareEndDatewithStartDate(String startDate,
			String endDate) {
		System.out.println("Step1+:" + endDate + "----" + startDate);
		try {
			SimpleDateFormat dateFormat1 = new SimpleDateFormat("YYYY-MM-DD");
			Date sDate = dateFormat1.parse(startDate);
			Date eDate = dateFormat1.parse(endDate);
			System.out.println("Step+:" + eDate + "----" + sDate);

			if (eDate.compareTo(sDate) < 0) {
				return true;
			}

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("e.getmessage");
		}

		return false;
	}

	public static boolean validateDate(String datestr) {
		Calendar cal = Calendar.getInstance();

		Date date = null;
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD");
			date = dateFormat.parse(datestr);

			cal.setTime(date);
			int year = cal.get(Calendar.YEAR);
			int month = cal.get(Calendar.MONTH);
			int day = cal.get(Calendar.DAY_OF_MONTH);
			if (year > 1900) {
				if (month < 12) {
					if (month == 0 || month == 2 || month == 4 || month == 6
							|| month == 7 || month == 9 || month == 11) {
						if (day < 32) {
							return true;
						}
					} else if (month == 3 || month == 5 || month == 8
							|| month == 10) {
						if (day < 31) {
							return true;
						}
					} else if (month == 1) {
						if (year % 4 == 0 && day < 30) {
							return true;
						} else if (day < 29) {
							return true;
						}
					}
				}
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("e.getmessage");
		}
		return false;
	}

	public static boolean email(String name) {
		String spl = "!#$%^&*()+=-[]\\\';,/{}|\":<>?";
		if (name.length() > 2) {
			boolean flag = true;
			for (int i = 0; i < name.length(); i++) {
				if (spl.indexOf(name.charAt(i)) != -1) {
					flag = false;
					break;
				}
			}
			if (flag) {
				if (name.contains("@") && name.contains(".")) {
					return true;
				}
			}
		}
		return false;
	}

	public static boolean name(String name) {
		String spl = "!@#$%^&*()+=-[]\\\';,./{}|\":<>?0987654321";
		if (name.length() > 2) {
			boolean flag = true;
			for (int i = 0; i < name.length(); i++) {
				if (spl.indexOf(name.charAt(i)) != -1) {
					flag = false;
					break;
				}
			}
			if (flag) {
				return true;
			}
		}
		return false;
	}

	public static boolean age(String number) {
		if (number.length() > 0 && number.length() <= 2) {
			double value = Double.parseDouble(number);
			if (value > 14 && value < 90) {
				return true;
			}
		}
		return false;
	}

	public static String getDateTimeStamp() {
		String timeStamp = "";
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		String year = "" + cal.get(Calendar.YEAR);
		String month = "" + (cal.get(Calendar.MONTH) + 1);
		if (month.length() == 1) {
			month = "0" + month;
		}
		String day = "" + cal.get(Calendar.DAY_OF_MONTH);
		if (day.length() == 1) {
			day = "0" + day;
		}
		String hour = "" + cal.get(Calendar.HOUR_OF_DAY);
		if (hour.length() == 1) {
			hour = "0" + hour;
		}
		String min = "" + cal.get(Calendar.MINUTE);
		if (min.length() == 1) {
			min = "0" + min;
		}
		String sec = "" + cal.get(Calendar.SECOND);
		if (sec.length() == 1) {
			sec = "0" + sec;
		}
		timeStamp = year + month + day + hour + min + sec;
		return timeStamp;
	}

	public static void turnGPSOn(Context ctx) {
		Intent intent = new Intent("android.location.GPS_ENABLED_CHANGE");
		intent.putExtra("enabled", true);
		ctx.sendBroadcast(intent);

		String provider = Settings.Secure.getString(ctx.getContentResolver(),
				Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
		if (!provider.contains("gps")) { // if gps is disabled
			final Intent poke = new Intent();
			poke.setClassName("com.android.settings",
					"com.android.settings.widget.SettingsAppWidgetProvider");
			poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
			poke.setData(Uri.parse("3"));
			ctx.sendBroadcast(poke);

		}
	}

	// automatic turn off the gps
	public void turnGPSOff(Context ctx) {
		String provider = Settings.Secure.getString(ctx.getContentResolver(),
				Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
		if (provider.contains("gps")) { // if gps is enabled
			final Intent poke = new Intent();
			poke.setClassName("com.android.settings",
					"com.android.settings.widget.SettingsAppWidgetProvider");
			poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
			poke.setData(Uri.parse("3"));
			ctx.sendBroadcast(poke);
		}
	}

	public static void getDBBackUp(String DBName, String outDBName, Context ctx) {
		// File sd = Environment.getExternalStorageDirectory();
		File data = Environment.getDataDirectory();
		File sd = new File(Environment.getExternalStorageDirectory().getPath()
				+ "/" + "eldercare.db");
		if (!sd.exists())
			sd.mkdirs();

		if (sd.canWrite()) {
			String currentDBPath = "/data/data/" + ctx.getPackageName()
					+ "/databases/" + DBName;
			String backupDBPath = outDBName;

			System.out.println("currentDBPath:" + currentDBPath);
			System.out.println("backupDBPath:" + backupDBPath);

			File currentDB = new File(currentDBPath);
			File backupDB = new File(sd + "/" + backupDBPath);

			if (currentDB.exists()) {
				System.out.println("S 1");
				FileChannel src;
				try {
					src = new FileInputStream(currentDB).getChannel();
					FileChannel dst = new FileOutputStream(backupDB)
							.getChannel();
					dst.transferFrom(src, 0, src.size());
					src.close();
					dst.close();
					System.out.println("S 1");
					/*
					 * Toast.makeText(ctx, "Back up Taken Successfull",
					 * Toast.LENGTH_LONG).show();
					 */
					/*
					 * Helper.AlertBox( VoHome.this,
					 * "Backup taken successfully in " +
					 * backupDB.getAbsolutePath());
					 */
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					System.out.println("S C 1");
					e.printStackTrace();
				} catch (Exception e) {
					// TODO: handle exception
					System.out.println("S C 2");
				}

			}
			System.out.println("S 1");
		}

	}

	private void copyDataBase(Context ctx) {
		ContextWrapper cw = new ContextWrapper(ctx);
		// String DB_PATH = cw.getFilesDir().getAbsolutePath() + "/database/";
		String DB_PATH = "/data/data/" + ctx.getPackageName() + "/databases/";
		System.out.println("DB_PATH: " + DB_PATH);
		Log.i("Database", "New database is being copied to device!");
		byte[] buffer = new byte[1024];
		OutputStream myOutput = null;
		int length;
		// Open your local db as the input stream
		InputStream myInput = null;
		try {
			myInput = ctx.getAssets().open("TBK.db");
			System.out.println("myInput: " + myInput.toString());
			// transfer bytes from the inputfile to the
			// outputfile
			myOutput = new FileOutputStream(DB_PATH + "TBK.db");
			while ((length = myInput.read(buffer)) > 0) {
				System.out.println("Step 1");
				myOutput.write(buffer, 0, length);
			}
			myOutput.close();
			myOutput.flush();
			myInput.close();
			Log.i("Database", "New database has been copied to device!");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void getBackUp(String DBName, Context ctx) {
		// File sd = Environment.getExternalStorageDirectory();
		String currentDBPath = "/data/data/" + ctx.getPackageName()
				+ "/databases/" + DBName;

		System.out.println("currentDBPath:" + currentDBPath);

		File currentDB = new File(currentDBPath);

		System.out.println("S 1");
		FileChannel src;

		System.out.println("S 1");

	}

	public static Bitmap getRoundedCroppedBitmap(Bitmap bitmap, int radius) {
		Bitmap finalBitmap;
		if (bitmap.getWidth() != radius || bitmap.getHeight() != radius)
			finalBitmap = Bitmap.createScaledBitmap(bitmap, radius, radius,
					false);
		else
			finalBitmap = bitmap;
		Bitmap output = Bitmap.createBitmap(finalBitmap.getWidth(),
				finalBitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, finalBitmap.getWidth(),
				finalBitmap.getHeight());

		paint.setAntiAlias(true);
		paint.setFilterBitmap(true);
		paint.setDither(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(Color.parseColor("#BAB399"));
		canvas.drawCircle(finalBitmap.getWidth() / 2 + 0.7f,
				finalBitmap.getHeight() / 2 + 0.7f,
				finalBitmap.getWidth() / 2 + 0.1f, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(finalBitmap, rect, rect, paint);

		return output;
	}

	public static boolean isFutureDate(Date firstDate, Date secondDate) {
		if (validateDate(firstDate) && validateDate(secondDate)) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(secondDate);
			int year = cal.get(Calendar.YEAR);
			int month = cal.get(Calendar.MONTH);
			int day = cal.get(Calendar.DAY_OF_MONTH);
			Calendar cal1 = Calendar.getInstance();
			cal1.setTime(firstDate);
			int year1 = cal1.get(Calendar.YEAR);
			int month1 = cal1.get(Calendar.MONTH);
			int day1 = cal1.get(Calendar.DAY_OF_MONTH);
			if (year > year1) {
				return true;
			} else if (year == year1) {
				if (month > month1) {
					return true;
				} else if (month == month1) {
					if (day > day1) {
						return true;
					} else {
						return false;
					}
				} else {
					return false;
				}
			} else {
				return false;
			}
		}
		return false;
	}

	public static boolean checkFuture(String dateofbirth) {
		boolean futureFlag = false;

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String today1 = getTodayDate1("yyyy-MM-dd");
		try {

			Date today = sdf.parse(today1);
			Date trainingDate = sdf.parse(dateofbirth);
			System.out.println("future: " + trainingDate + "---------" + today);
			if (trainingDate.compareTo(today) >= 0) {
				futureFlag = true;
			}
		} catch (Exception e) {

		}
		return futureFlag;
	}

	public static boolean checkPast(String dateofbirth) {
		boolean futureFlag = false;

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String today1 = getTodayDate1("yyyy-MM-dd");
		try {

			Date today = sdf.parse(today1);
			Date trainingDate = sdf.parse(dateofbirth);
			if (trainingDate.compareTo(today) < 0) {
				futureFlag = true;
			}
		} catch (Exception e) {

		}
		return futureFlag;
	}

	/*Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+1:00"));
	Date currentLocalTime = cal.getTime();
	DateFormat date = new SimpleDateFormat("HH:mm a");
// you can get seconds by adding  "...:ss" to it
date.setTimeZone(TimeZone.getTimeZone("GMT+1:00"));

	String localTime = date.format(currentLocalTime);
*/
	public static String getTodayDate1(String str) {
		SimpleDateFormat sdf = new SimpleDateFormat(str);
		return sdf.format(new Date());
	}

	public static String getCurrentTime(String str) {
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(str);
		String currentDateTimeString = sdf.format(d);
		System.out.println("currentDateTimeString: " + currentDateTimeString);
		return currentDateTimeString;
	}

	public static String cal10beforegiventime(String myTime) {
		String newTime = "";
		SimpleDateFormat df = new SimpleDateFormat("HH:mm");
		Date d;
		try {
			d = df.parse(myTime);
			Calendar cal = Calendar.getInstance();
			cal.setTime(d);
			cal.add(Calendar.MINUTE, -5);
			newTime = df.format(cal.getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return newTime;

	}

	public static boolean validateDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		if (year > 1900) {
			if (month < 12) {
				if (month == 0 || month == 2 || month == 4 || month == 6
						|| month == 7 || month == 9 || month == 11) {
					if (day < 32) {
						return true;
					}
				} else if (month == 3 || month == 5 || month == 8
						|| month == 10) {
					if (day < 31) {
						return true;
					}
				} else if (month == 1) {
					if (year % 4 == 0 && day < 30) {
						return true;
					} else if (day < 29) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public static String get_count_of_days(String Created_date_String,
			String Expire_date_String) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd",
				Locale.getDefault());

		Date Created_convertedDate = null, Expire_CovertedDate = null, todayWithZeroTime = null;
		try {
			Created_convertedDate = dateFormat.parse(Created_date_String);
			Expire_CovertedDate = dateFormat.parse(Expire_date_String);

			Date today = new Date();

			todayWithZeroTime = dateFormat.parse(dateFormat.format(today));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		int c_year = 0, c_month = 0, c_day = 0;

		if (Created_convertedDate.after(todayWithZeroTime)) {
			Calendar c_cal = Calendar.getInstance();
			c_cal.setTime(Created_convertedDate);

			c_year = c_cal.get(Calendar.YEAR);
			c_month = c_cal.get(Calendar.MONTH);
			c_day = c_cal.get(Calendar.DAY_OF_MONTH);

		} else {
			Calendar c_cal = Calendar.getInstance();
			c_cal.setTime(todayWithZeroTime);

			c_year = c_cal.get(Calendar.YEAR);
			c_month = c_cal.get(Calendar.MONTH);
			c_day = c_cal.get(Calendar.DAY_OF_MONTH);
		}
		Calendar e_cal = Calendar.getInstance();
		e_cal.setTime(Expire_CovertedDate);

		int e_year = e_cal.get(Calendar.YEAR);
		int e_month = e_cal.get(Calendar.MONTH);
		int e_day = e_cal.get(Calendar.DAY_OF_MONTH);

		Calendar date1 = Calendar.getInstance();
		Calendar date2 = Calendar.getInstance();

		date1.clear();
		date1.set(c_year, c_month, c_day);
		date2.clear();
		date2.set(e_year, e_month, e_day);

		long diff = date2.getTimeInMillis() - date1.getTimeInMillis();

		float dayCount = (float) diff / (24 * 60 * 60 * 1000);

		return ("" + (int) dayCount + " Days");
	}

}
