package db.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//在该类中定义一个线程池
public class ExecutorManager {
	// 单例模式，只能new一次
	private ExecutorManager() {
		init();
	}

	private ExecutorService executorService;

	// 保证返回的对象始终就是一个
	private static ExecutorManager instance;

	public static ExecutorManager getInstance() {

		if (instance == null) {
			// synchronized关键字是实现线程同步，防止多个线程同时访问时创建多个对象
			synchronized (ExecutorManager.class) {
				if (instance == null) {
					instance = new ExecutorManager();
				}
			}
		}
		return instance;
	}

	private void init() {
		int max = 8;
		int num = Runtime.getRuntime().availableProcessors() * 2 + 1;
		num = num > max ? max : num;
		executorService = Executors.newFixedThreadPool(num);
	}

	/**
	 * 执行任务
	 */
	public void execute(Runnable runnable) {
		this.executorService.execute(runnable);
	}

}
