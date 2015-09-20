package utils;

import java.util.Collection;
import java.util.Stack;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

public class AppManager {
	private static Stack<Activity> activityStack;
	private static AppManager instance;

	// 定义线程安全的单例模式（私有的构造，私有的变量，静态的返回自个的单例）
	private AppManager() {
	}

	/**
	 * 单一实例
	 */
	public static AppManager getAppManager() {
		if (instance == null) {
			instance = new AppManager();
		}
		return instance;
	}

	/**
	 * 添加Activity到堆栈中
	 */

	public void addActivity(Activity activity) {
		if (activityStack == null) {
			activityStack = new Stack<Activity>();
		}
		activityStack.add(activity);
	}

	/**
	 * 获取当前Acitivty
	 */
	public Activity currentActivity() {
		Activity activity = activityStack.lastElement();
		return activity;
	}

	/**
	 * 结束当前Activity
	 */
	public void finishActivity(Activity activity) {
		if (activity != null) {
			activityStack.remove(activity);
			activity.finish();
			activity = null;
		}
	}

	/**
	 * 结束指定类名的Activity
	 */
	public void finishiActivity(Class<?> cls) {
		for (Activity activity : activityStack) {
			if (activity.getClass().equals(cls)) {
				finishActivity(activity);
			}
		}
	}

	/**
	 * 结束Acitivty
	 */
	public void finishiAllActivity() {
		for (int i = 0, size = activityStack.size(); i < size; i++) {
			if (activityStack.get(i) != null) {
				activityStack.get(i).finish();
			}
		}
	}

	/**
	 * 退出应用程序
	 */
	public void AppExit(Context context) {
		try {
			finishiAllActivity();
			ActivityManager activityManager = (ActivityManager) context
					.getSystemService(Context.ACTIVITY_SERVICE);
			activityManager.restartPackage(context.getPackageName());
			System.exit(0);
		} catch (Exception e) {
			// TODO: handle exception
		}

	}
}
