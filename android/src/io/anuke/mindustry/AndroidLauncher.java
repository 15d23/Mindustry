package io.anuke.mindustry;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import io.anuke.mindustry.io.PlatformFunction;
import io.anuke.ucore.function.Callable;
import io.anuke.ucore.scene.ui.layout.Unit;

public class AndroidLauncher extends AndroidApplication{
	boolean doubleScaleTablets = false;

	@SuppressLint("SimpleDateFormat")
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useImmersiveMode = true;
		
		Mindustry.hasDiscord = isPackageInstalled("com.discord");
		Mindustry.platforms = new PlatformFunction(){
			SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm");

			@Override
			public String format(Date date){
				return format.format(date);
			}

			@Override
			public String format(int number){
				return NumberFormat.getIntegerInstance().format(number);
			}

			@Override
			public void openLink(String link){
				Uri marketUri = Uri.parse(link);
			    Intent intent = new Intent( Intent.ACTION_VIEW, marketUri );
			    startActivity(intent); 
			}
		};
		
		Mindustry.donationsCallable = new Callable(){ @Override public void run(){ showDonations(); } };

		if(doubleScaleTablets){
			DisplayMetrics metrics = new DisplayMetrics();
			getWindowManager().getDefaultDisplay().getMetrics(metrics);

			float yInches = metrics.heightPixels / metrics.ydpi;
			float xInches = metrics.widthPixels / metrics.xdpi;
			double diagonalInches = Math.sqrt(xInches * xInches + yInches * yInches);
			if(diagonalInches >= 6.5){
				// 6.5inch device or bigger
				Unit.dp.multiplier = 2f;
			}else{
				// smaller device
				Unit.dp.multiplier = 1f;
			}
		}

		//Mindustry.args.add("-debug");
		
		config.hideStatusBar = true;
		
		initialize(new Mindustry(), config);
	}
	
	private boolean isPackageInstalled(String packagename) {
	    try {
	    	getPackageManager().getPackageInfo(packagename, 0);
	        return true;
	    } catch (Exception e) {
	        return false;
	    }
	}
	
	void showDonations(){
		Intent intent = new Intent(this, DonationsActivity.class);
		startActivity(intent);
	}
}
