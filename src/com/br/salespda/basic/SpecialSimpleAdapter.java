package com.br.salespda.basic;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;

/**
 * 隔行变色适配器
 * @author administrator
 *
 */
public class SpecialSimpleAdapter extends SimpleAdapter {
 

	private int[] colors = new int[] { 0x30FF0000, 0x300000FF }; 

	public SpecialSimpleAdapter(Context context, List<? extends Map<String, ?>> data,
			int resource, String[] from, int[] to) {
		super(context, data, resource, from, to);

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = super.getView(position, convertView, parent);
		int colorPos = position % colors.length;
		if (colorPos == 1)
			view.setBackgroundColor(Color.argb(250, 255, 255, 255)); // 颜色设置
		else
			view.setBackgroundColor(Color.argb(255, 224, 243, 250));// 颜色设置
		return view;
	}
}
