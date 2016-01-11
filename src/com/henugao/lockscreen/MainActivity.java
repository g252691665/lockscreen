package com.henugao.lockscreen;

import com.henugao.lockscreen.receiver.MyDeviceAdminReceiver;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {

	private DevicePolicyManager mDPM;
	private ComponentName mDeviceAdminSample;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mDPM = (DevicePolicyManager) getSystemService(DEVICE_POLICY_SERVICE);
		mDeviceAdminSample = new ComponentName(this, MyDeviceAdminReceiver.class);
	}
	
	
	//激活设备管理器
	public void activate(View v){
		Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
		intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, mDeviceAdminSample);
		intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "有了超级设备管理器，我们就可以远程擦除数据和锁屏了");
		startActivity(intent);
	}
	//一键锁屏
	public void lockScreen(View v){
		//判断设备管理器是否激活
		if (mDPM.isAdminActive(mDeviceAdminSample)) {
			mDPM.lockNow();
			mDPM.resetPassword("123", 0);
		}
		else{
			Toast.makeText(this, "您的设备管理器还没有激活,必须先激活", Toast.LENGTH_SHORT).show();
		}
	}
	
	//擦除数据
	public void clearData(View v){
		if (mDPM.isAdminActive(mDeviceAdminSample)) {
			mDPM.wipeData(0);
		}
		else{
			Toast.makeText(this, "您的设备管理器还没有激活,必须先激活", Toast.LENGTH_SHORT).show();
		}
	}
	
	//卸载程序
	public void uninstall(View v){
		//如果不移除设备管理员是无法卸载该应用程序的
		if (mDPM.isAdminActive(mDeviceAdminSample)) {
			mDPM.removeActiveAdmin(mDeviceAdminSample);//取消激活
		}
			// 卸载程序
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setAction(Intent.ACTION_DELETE);
		intent.addCategory(Intent.CATEGORY_DEFAULT);
		intent.setData(Uri.parse("package:" + getPackageName()));
		startActivity(intent);
	}
	
	

}
