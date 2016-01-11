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
	
	
	//�����豸������
	public void activate(View v){
		Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
		intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, mDeviceAdminSample);
		intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "���˳����豸�����������ǾͿ���Զ�̲������ݺ�������");
		startActivity(intent);
	}
	//һ������
	public void lockScreen(View v){
		//�ж��豸�������Ƿ񼤻�
		if (mDPM.isAdminActive(mDeviceAdminSample)) {
			mDPM.lockNow();
			mDPM.resetPassword("123", 0);
		}
		else{
			Toast.makeText(this, "�����豸��������û�м���,�����ȼ���", Toast.LENGTH_SHORT).show();
		}
	}
	
	//��������
	public void clearData(View v){
		if (mDPM.isAdminActive(mDeviceAdminSample)) {
			mDPM.wipeData(0);
		}
		else{
			Toast.makeText(this, "�����豸��������û�м���,�����ȼ���", Toast.LENGTH_SHORT).show();
		}
	}
	
	//ж�س���
	public void uninstall(View v){
		//������Ƴ��豸����Ա���޷�ж�ظ�Ӧ�ó����
		if (mDPM.isAdminActive(mDeviceAdminSample)) {
			mDPM.removeActiveAdmin(mDeviceAdminSample);//ȡ������
		}
			// ж�س���
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setAction(Intent.ACTION_DELETE);
		intent.addCategory(Intent.CATEGORY_DEFAULT);
		intent.setData(Uri.parse("package:" + getPackageName()));
		startActivity(intent);
	}
	
	

}
