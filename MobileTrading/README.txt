1. Khi tạo một Class chức năng phải tuân theo những điều sau
	- extends từ abstract class AbstractFragment;
	- tạo 1 hàm khởi tạo tĩnh newInstance theo format
			public static YourFunctionClass newInstance(MainActivity mActivity) {
				YourFunctionClass self = new YourFunctionClass();
				self.mainActivity = mActivity;
				self.TITLE = mActivity.getStringResource(R.string.YourFunctionClass);
				return self;
	}
	-