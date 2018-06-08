package com.fscuat.mobiletrading.design;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.widget.ArrayAdapter;

public class FilterArrayAdapter<T> extends ArrayAdapter<T> {
	List<Condition<T>> conditions;
	protected List<T> filteredData;

	public FilterArrayAdapter(Context context, int resource, List<T> objects) {
		super(context, resource, objects);
		conditions = new ArrayList<Condition<T>>();
		filteredData = new ArrayList<T>();
		conditions.add(new Condition<T>() {

			@Override
			public boolean checkCondition(T item) {
				return true;
			}
		});
		checkConditions();
	}

	/**
	 * lọc dữ liệu đầu vào
	 * 
	 * @param inputList
	 */
	public void checkConditions() {
		filteredData.clear();
		if (conditions == null || conditions.size() == 0) {
			for (int i = 0; i < super.getCount(); i++) {
				filteredData.add(super.getItem(i));
			}
			return;
		}
		for (int i = 0; i < super.getCount(); i++) {
			T item = super.getItem(i);
			if (checkItemWithConditions(item)) {
				filteredData.add(item);
			}
		}
	}

	private boolean checkItemWithConditions(T item) {
		for (int j = 0; j < conditions.size(); j++) {
			Condition<T> condition = conditions.get(j);
			if (!condition.checkCondition(item)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public int getCount() {
		return filteredData.size();
	}

	@Override
	public void notifyDataSetChanged() {
		checkConditions();
		super.notifyDataSetChanged();
	}

	/**
	 * add thêm điều kiện lọc vào adapter
	 * 
	 * @param condition
	 */
	public void addCondition(Condition<T> condition) {
		if (!conditions.contains(condition)) {
			conditions.add(condition);
		}
	}

	/**
	 * xóa điều kiện lọc khỏi adapter
	 * 
	 * @param condition
	 */
	public void removeCondition(Condition<T> condition) {
		if (conditions.contains(condition)) {
			conditions.remove(condition);
		}
	}

	public interface Condition<E> {
		/**
		 * định nghĩa điều kiện lọc
		 * 
		 * @param item
		 *            : đối tượng lọc
		 * @return
		 */
		public boolean checkCondition(E item);
	}

}
