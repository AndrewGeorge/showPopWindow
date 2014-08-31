package com.example.showpopwindow;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.showpopwindow.adapter.CategoryListAdapter;

public class MainActivity extends Activity {

	private LinearLayout total_category,ly_search;//����
	private ArrayList<HashMap<String, Object>> itemList;
	private ArrayList<HashMap<String, Object>> childitemList;
	private LinearLayout layout;
	private ListView rootList;
	private ListView childList;
	private FrameLayout flChild;
	private PopupWindow mPopWin;
	private LinearLayout category, ofprice;
	private boolean muUrlFlag;
	private String mUrl;
	private String selectplace="ȫ������";
	private TextView text_category,iv_price_status;
	private int flag = -1;
	private String firstItem[] = { "ȫ��", "��Ӱ", "����", "�Ƶ�", "��ʳ", "����" };
	String secondItem[][] = new String[][] {
			new String[] { "ȫ��", "��·Ʊ��", "����Ʊ��", "�⳵����", "��·Ʊ��", "�ӳ�����",
					"��������", "�δ�" },
			new String[] { "ȫ��", "����Ԥ��", "��ҵ԰", "�������", "��Ȫ����", "ǩ֤����", "������Ʊ",
					"������", "��������" },
			new String[] { "ȫ��", "�Ƶ�Ԥ��", "�ȼٱ���", "ũ����Ԥ��", "��������¶�޵�", "���ѷ���" },
			new String[] { "ȫ��", "��������", "�����Ų�", "������", "��ɫ��ʳ", "��ɫ��ʳ��ѯ" },
			new String[] { "ȫ��", "����������Ŀ", "�����ݳ�", "ˮ����Ŀ", "��������԰", "��ѩ��",
					"���⹫԰", "����" },
			new String[] { "ȫ��", " ��ɫ��Ʒ����", "���μ���Ʒ����", "������Ʒ����", "����Ʒ����",
					" �ղ�Ʒ����", "�ݳ�Ʒ����" } };
	private Handler hander = new Handler() {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what == 0) {
				text_category.setText(selectplace);
			}else if(msg.what == 1){
				if (mPopWin!=null) {
					mPopWin.dismiss();
				}
			}
		}
	};
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		
		category=(LinearLayout) findViewById(R.id.total_category);
		ly_search=(LinearLayout) findViewById(R.id.ly_search);
		category.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showPopupWindow(ly_search.getWidth(), ly_search.getHeight());
				
			}
		});
	}

	
	private void showPopupWindow(int width, int height) {
		itemList = new ArrayList<HashMap<String, Object>>();

		for (int i = 0; i < firstItem.length; i++) {
			HashMap<String, Object> items = new HashMap<String, Object>();
			items.put("name", firstItem[i]);
			itemList.add(items);
		}
		layout = (LinearLayout) LayoutInflater.from(MainActivity.this)
				.inflate(R.layout.popup_category, null);
		rootList = (ListView) layout.findViewById(R.id.rootcategory);

		CategoryListAdapter cla = new CategoryListAdapter(
				MainActivity.this, itemList);
		rootList.setAdapter(cla);
		cla.notifyDataSetChanged();
		flChild = (FrameLayout) layout.findViewById(R.id.child_lay);
		childList = (ListView) layout.findViewById(R.id.childcategory);
		flChild.setVisibility(View.INVISIBLE);

		mPopWin = new PopupWindow(layout, width, height * 2 / 3, true);
		mPopWin.setBackgroundDrawable(new BitmapDrawable());
		mPopWin.showAsDropDown(category, 5, 1);
		mPopWin.update();
//���˵�ҳ��
		rootList.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				if (position != 0) {
					childitemList = new ArrayList<HashMap<String, Object>>();
					flChild.setVisibility(View.VISIBLE);
					int childlength = secondItem[position - 1].length;
					for (int i = 0; i < childlength; i++) {
						HashMap<String, Object> items = new HashMap<String, Object>();
						items.put("name", secondItem[position - 1][i]);
						childitemList.add(items);
					}
					CategoryListAdapter childcla = new CategoryListAdapter(
							MainActivity.this, childitemList);
					childList.setAdapter(childcla);
					childcla.notifyDataSetChanged();
					flag = position;
					Log.e("flag", "flag==" + position);
				} else {
					if (mPopWin != null) {
						mPopWin.dismiss();
					}
					text_category.setText("��Ӱ");
					hander.sendEmptyMessage(1);
				}
			}
		});
		//�����˵�ҳ��
		childList
				.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						 Log.e("childList", "flag=="+flag);
						switch (flag) {
						case 1:
							if (position == 0) {
								text_category.setText("�⳵");
							} else if (position == 1) {
								text_category.setText("��·Ʊ��");
							} else if (position == 2) {
								text_category.setText("����Ʊ��");
							} else if (position == 3) {
								text_category.setText("�⳵����");
							} else if (position == 4) {
								text_category.setText("��·Ʊ��");
							} else if (position == 5) {
								text_category.setText("�ӳ�����");
							} else if (position == 6) {
								text_category.setText("��������");
							} else if (position == 7) {
								text_category.setText("�δ�");
							}
							break;
						case 2:
							if (position == 0) {
								text_category.setText("����");
							} else if (position == 1) {
								text_category.setText("����Ԥ��");
							} else if (position == 2) {
								text_category.setText("��ҵ԰");
							} else if (position == 3) {
								text_category.setText("�������");
							} else if (position == 4) {
								text_category.setText("��Ȫ����");
							} else if (position == 5) {
								text_category.setText("ǩ֤����");
							} else if (position == 6) {
								text_category.setText("������Ʊ");
							} else if (position == 7) {
								text_category.setText("������");
							} else if (position == 8) {
								text_category.setText("��������");
							}
							break;
						case 3:
							if (position == 0) {
								text_category.setText("�Ƶ�");
							} else if (position == 1) {
								text_category.setText("�Ƶ�Ԥ��");
							} else if (position == 2) {
								text_category.setText("�ȼٱ���");
							} else if (position == 3) {
								text_category.setText("ũ����Ԥ��");
							} else if (position == 4) {
								text_category.setText("��������¶�޵�");
							} else if (position == 5) {
								text_category.setText("���ѷ���");
							}
							break;
						case 4:
							if (position == 0) {
								text_category.setText("��ʳ");
							} else if (position == 1) {
								text_category.setText("��������");
							} else if (position == 2) {
								text_category.setText("�����Ų�");
							} else if (position == 3) {
								text_category.setText("������");
							} else if (position == 4) {
								text_category.setText("��ɫ��ʳ");
							} else if (position == 5) {
								text_category.setText("��ɫ��ʳ��ѯ");
							}
							break;
						case 5:
							if (position == 0) {
								text_category.setText("����");
							} else if (position == 1) {
								text_category.setText("����������Ŀ");
							} else if (position == 2) {
								text_category.setText("�����ݳ�");
							} else if (position == 3) {
								text_category.setText("ˮ����Ŀ");
							} else if (position == 4) {
								text_category.setText("��������԰");
							} else if (position == 5) {
								text_category.setText("��ѩ��");
							} else if (position == 6) {
								text_category.setText("���⹫԰");
							} else if (position == 7) {
								text_category.setText("����");
							}
							break;
						case 6:
							if (position == 0) {
								text_category.setText("����");
							} else if (position == 1) {
								text_category.setText("������ɫ��Ʒ����");
							} else if (position == 2) {
								text_category.setText("���μ���Ʒ����");
							} else if (position == 3) {
								text_category.setText("������Ʒ����");
							} else if (position == 4) {
								text_category.setText("����Ʒ����");
							} else if (position == 5) {
								text_category.setText("�ղ�Ʒ����");
							} else if (position == 6) {
								text_category.setText("�ݳ�Ʒ����");
							}
							break;

						default:
							break;
						}
						hander.sendEmptyMessage(1);
						iv_price_status.setVisibility(View.GONE);
						muUrlFlag=false;
					}
				});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
