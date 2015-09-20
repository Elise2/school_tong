package fragment;

import android.support.v4.app.Fragment;

public abstract class BaseFragment extends Fragment {
	protected boolean isVisible;// 用于表明当前的fragment是否可见

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		// TODO Auto-generated method stub
		super.setUserVisibleHint(isVisibleToUser);
		if (isVisibleToUser) {
			isVisible = true;
			// 加载数据
			lazyLoadData();
		} else {
			isVisible = false;
		}
	}

	public abstract void lazyLoadData();
}
