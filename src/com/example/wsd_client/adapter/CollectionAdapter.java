package com.example.wsd_client.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.ImageLoader.ImageContainer;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.example.wsd_client.R;
import com.example.wsd_client.activity.CollectionActivity;
import com.example.wsd_client.application.Myapplication;
import com.example.wsd_client.entity.Cart;
import com.example.wsd_client.entity.CartItem;
import com.example.wsd_client.entity.Product;
import com.example.wsd_client.urlUtils.UrlPath;
import com.example.wsd_client.util.BitmapLruCache;

/**
 * �ղ�ҳ��listviewadapter
 * @author wsd_leiguoqaing
 */
public class CollectionAdapter extends BaseAdapter{
	private Context context;
	/**
	 * ��Ʒ����
	 */
	private List<Product> lists;
	/**
	 * �ղ�ҳ��
	 */
	private CollectionActivity collectionActivity;
	private RequestQueue mQu;// �������
	private ImageCache cach;
	//ImagerLoader�Ĵ���
	private ImageLoader loader;

	public CollectionAdapter(List<Product> lists,
			CollectionActivity collectionActivity) {
		super();
		this.context = collectionActivity;
		this.lists = lists;
		this.collectionActivity = collectionActivity;
		mQu=Myapplication.getRequestQueue();
		cach = new BitmapLruCache();
		loader = new ImageLoader(mQu, cach);
	}

	@Override
	public int getCount() {
		return lists==null?0:lists.size();
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final Product info = lists.get(position);
		final ViewHolder holder;
		if(convertView==null){
			holder=new ViewHolder();
			convertView=LayoutInflater.from(context).inflate(R.layout.modle_for_adapter_collection_product_item, null);
			holder.tvCode=(TextView) convertView.findViewById(R.id.tv_liebiao_code);
			holder.tvName=(TextView) convertView.findViewById(R.id.tv_liebiao_name);
			holder.tvKind=(TextView) convertView.findViewById(R.id.tv_liebiao_kind);
			holder.tvPrice=(TextView) convertView.findViewById(R.id.tv_liebiao_price);
			holder.btn_add_to_cart=(Button) convertView.findViewById(R.id.btn_liebiao_buy);
			holder.btn_promptly_buy = (Button) convertView.findViewById(R.id.btn_liebiao_buynow);
			holder.ivPicture=(ImageView) convertView.findViewById(R.id.iv_liebiao);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder) convertView.getTag();
		}

		holder.tvCode.setText("Ʒ�ִ��룺"+info.getYWM_Code());
		holder.tvName.setText("Ʒ�����ƣ�"+info.getYWM_Name());
		holder.tvKind.setText("���ࣺ"+info.getYWM_Kind());
		holder.tvPrice.setText("���ۣ���"+info.getYWM_Price());
		holder.tvPrice.setTextColor(Color.RED);

		String requestUrl=UrlPath.PRODUCT_ITEM_URL+"?"+info.getYWM_PicName();
		loader.get(requestUrl, new ImageListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				holder.ivPicture.setImageResource(R.drawable.ic_launcher);

			}

			@Override
			public void onResponse(ImageContainer response, boolean isImmediate) {
				if(isImmediate){
					Bitmap bitmap = response.getBitmap();
					holder.ivPicture.setImageBitmap(bitmap);
				}
			}
		}, 0, 0);

		//���û����������Ĳ�Ʒͨ����Ʒ���봫��LiebiaoFragment�Ĺ��ﳵ��ť
		holder.btn_add_to_cart.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				collectionActivity.displayBuyParam(position);
				//�ı��Ǳ���
				collectionActivity.setClick_flag(1);
				collectionActivity.setPosition(position);
			}
		});
		//�����������
		holder.btn_promptly_buy.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				int productId = lists.get(position).getYWM_ID();
				Cart cart = new Cart();
				List<CartItem> list_cartItem = cart.getList_cartItem();
				for(CartItem cartItem:list_cartItem){
					int productID = cartItem.getProduct().getYWM_ID();
					//���жϹ��ﳵ����û�и���Ʒ���еĻ�ֱ����ת�����ﳵ����
					if(productId==productID){
						Myapplication.toast("��Ʒ���ڹ��ﳵ���빺��");
						//������ת�����ﳵҳ��
						Intent intent = new Intent("main_activity");
						intent.putExtra("TAB", 2);
						collectionActivity.startActivity(intent);
						return;
					}
				}
				//���������������
				collectionActivity.displayBuyParam(position);
				//�ı��Ǳ���
				collectionActivity.setClick_flag(2);
				collectionActivity.setPosition(position);
			}
		});
		return convertView;
	}

	class ViewHolder {
		TextView tvCode;
		TextView tvName;
		TextView tvKind;
		TextView tvPrice;
		Button btn_add_to_cart;
		Button btn_promptly_buy;
		ImageView ivPicture;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

}
