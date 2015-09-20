package utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorManager {
	private static ExecutorManager instance;

	public static ExecutorManager getInstance() {
		if (instance == null) {
			synchronized (ExecutorManager.class) {
				if (instance == null) {
					instance = new ExecutorManager();
				}
			}
		}
		return instance;
	}

	private ExecutorService executorService;

	private ExecutorManager() {
		init();
	}

	// 初始化线程池
	private void init() {
		int max = 8;
		int num = Runtime.getRuntime().availableProcessors() * 2 + 1;
		num = num > max ? max : num;
		executorService = Executors.newFixedThreadPool(num);
	}

	/**
	 * ִ执行内容
	 * 
	 * @param runnable
	 */
	public void execute(Runnable runnable) {
		this.executorService.execute(runnable);
	}

	public ExecutorService getExecutors() {
		return this.executorService;
	}
}
