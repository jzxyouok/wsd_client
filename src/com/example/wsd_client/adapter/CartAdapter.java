package com.example.wsd_client.adapter;

import java.util.List;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.wsd_client.R;
import com.example.wsd_client.activity.MainActivity;
import com.example.wsd_client.application.Myapplication;
import com.example.wsd_client.entity.Cart;
import com.example.wsd_client.entity.CartItem;
import com.example.wsd_client.fragment.impl.UploadCartDataViewImpl;
/**
 * ���ﳵ��Ʒ�б�adapter��������ʾ���ﳵ�����еĹ�������Ϣ
 * @author wsd_leiguoqiang
 * ��Ҫ���ݷ��������ݽ�������----------------------------------------------------
 */
public class CartAdapter extends BaseAdapter{
	/**
	 * CartItem���ݼ���
	 */
	private List<CartItem> list;
	/**
	 * �����Ķ���
	 */
	private Context context;
	/**
	 * listview����
	 */
	private ListView listView;
	/**
	 * ���ﳵfragment����
	 */
	private UploadCartDataViewImpl uploadCartView;
	/**
	 * ��Ǳ����������ж��Ƿ���ʾɾ��ͼ���ѡ��ͼ��
	 * ֵΪfalse����ʾѡ��ͼ��
	 * ֵΪtrue����ʾɾ��ͼ��
	 */
	private boolean flag = false;

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	/**
	 * �ܼ��ı�
	 */
	private TextView tv_total_price;
	/**
	 * ��Ǳ�����������ѡ��ͼƬ
	 */
	private boolean flag_select_all = false;

	public boolean isFlag_select_all() {
		return flag_select_all;
	}

	public void setFlag_select_all(boolean flag_select_all) {
		this.flag_select_all = flag_select_all;
	}
	/**
	 * �������ݼ���
	 */
	private List<CartItem> list_total_cartItem;
	/**
	 * ���㹺������Ŀ
	 */
	private TextView tv_total_cartItem_count;
	/**
	 * ȫѡ�ı���ť
	 */
	private TextView tv_select_all_button;

	private Myapplication app = (Myapplication) Myapplication.getContext();

	private Cart cart = app.getCart();

	public CartAdapter(List<CartItem> list, List<CartItem> list_total_cartItem,UploadCartDataViewImpl fragment, ListView listView ,
			TextView tv_total_price ,TextView tv_total_cartItem_count ,TextView tv_select_all_button) {
		super();
		this.list = list;
		this.context = fragment.getActivity();
		this.listView = listView;
		this.tv_total_price = tv_total_price;
		this.list_total_cartItem = list_total_cartItem;
		this.tv_total_cartItem_count = tv_total_cartItem_count;
		this.tv_select_all_button = tv_select_all_button;
		this.uploadCartView = fragment;
	}

	@Override
	public int getCount() {
		return list==null?0:list.size();
	}

	//�Զ���viewholder
	class ViewHolder{
		/**
		 * ɾ��ͼ��
		 */
		ImageView iv_delete;
		/**
		 * ѡ��ͼ��
		 */
		ImageView iv_select;
		/**
		 * չʾСͼ
		 */
		ImageView iv_small_pictrue;
		/**
		 * ��Ʒ����
		 */
		TextView tv_product_name;
		/**
		 * ��Ʒ�۸�
		 */
		TextView tv_product_price;
		/**
		 * ��Ʒ����
		 */
		TextView tv_product_type;
		/**
		 * ��Ʒ����
		 */
		TextView tv_product_count;
		/**
		 * ����
		 */
		ImageView iv_reduce;
		/**
		 * ����
		 */
		ImageView iv_add;
		/**
		 * ��ʾ������
		 */
		TextView tv_display_count;
		/**
		 * ��Ʒ�༭��ť
		 */
		TextView tv_product_edite;
	}

	@Override
	public View getView(int arg0, View convertView, ViewGroup arg2) {
		//��ȡ��ǰ����
		CartItem cartItem = list.get(arg0);
		Myapplication.log("cartadapter", "��������"+cartItem.getProduct_count());
		ViewHolder holder = null;
		if(convertView==null){
			//��ȡview����
			convertView = View.inflate(context, R.layout.modle_for_adapter_cart_items, null);
			//��ȡ�ӿؼ�����
			holder = new ViewHolder();
			holder.iv_delete = (ImageView) convertView.findViewById(R.id.iv_fragment_cart_pager_delete_button);
			holder.iv_select = (ImageView) convertView.findViewById(R.id.iv_fragment_cart_pager_selecte);
			holder.iv_small_pictrue = (ImageView) convertView.findViewById(R.id.iv_fragmnet_cart_pager_pic_show);

			holder.tv_product_name = (TextView) convertView.findViewById(R.id.tv_modle_for_adapter_product_name);
			holder.tv_product_price = (TextView) convertView.findViewById(R.id.tv_modle_for_adapter_product_price);
			holder.tv_product_type = (TextView) convertView.findViewById(R.id.tv_modle_for_adapter_product_attribute);
			holder.tv_product_count = (TextView) convertView.findViewById(R.id.tv_modle_for_adapter_product_count);
			holder.tv_product_edite = (TextView) convertView.findViewById(R.id.tv_modle_for_adapter_product_edite);

			holder.iv_reduce = (ImageView) convertView.findViewById(R.id.iv_modle_for_adapter_product_reduce);
			holder.iv_add = (ImageView) convertView.findViewById(R.id.iv_modle_for_adapter_product_add);
			holder.tv_display_count = (TextView) convertView.findViewById(R.id.tv_modle_for_adapter_count_display);
			//��holder��ӵ�convertView��
			convertView.setTag(holder);
		}
		holder = (ViewHolder) convertView.getTag();

		//�����ӿؼ�����
		holder.tv_product_name.setText(cartItem.getProduct().getYWM_Name());
		holder.tv_product_price.setText(cartItem.getProduct().getYWM_Price()+"");
		holder.tv_product_type.setText(cartItem.getProduct().getYWM_Code());
		holder.tv_product_count.setText("X"+cartItem.getBuyParam().get("ordernumber"));
		holder.tv_display_count.setText(cartItem.getBuyParam().get("ordernumber"));

		//���ӿؼ���ӱ��
		holder.tv_display_count.setTag("tv_display_count"+arg0);
		holder.tv_product_count.setTag("tv_product_count"+arg0);
		holder.iv_delete.setTag("iv_delete"+arg0);
		holder.iv_select.setTag("iv_select"+arg0);

		//�ж�ɾ��ͼ���Ƿ���ʾ
		if(!flag){
			holder.iv_delete.setScaleX(0);
			holder.iv_delete.setScaleY(0);
			holder.iv_delete.setVisibility(View.INVISIBLE);
			holder.iv_select.setScaleX(1);
			holder.iv_select.setScaleY(1);
			holder.iv_select.setVisibility(View.VISIBLE);
			//��������ʾѡ��ͼ��ʱ���ж��Ƿ���ȫѡ״̬
			if(flag_select_all){
				holder.iv_select.setSelected(true);
			}else if(list_total_cartItem.contains(list.get(arg0))){
				holder.iv_select.setSelected(true);
			}else{
				holder.iv_select.setSelected(false);
			}
		}else{
			holder.iv_delete.setScaleX(1);
			holder.iv_delete.setScaleY(1);
			holder.iv_delete.setVisibility(View.VISIBLE);
			holder.iv_select.setScaleX(0);
			holder.iv_select.setScaleY(0);
			//�����ڱ༭״̬��ʱ����holder.iv_selected���ڷ�ѡ��״̬
			holder.iv_select.setSelected(false);
			holder.iv_select.setVisibility(View.INVISIBLE);
		}

		//�Կؼ���Ӽ�����
		holder.iv_add.setOnClickListener(new AddAndReduceListener(arg0, AddAndReduceListener.FLAG_ADD, holder.iv_select));
		holder.iv_reduce.setOnClickListener(new AddAndReduceListener(arg0, AddAndReduceListener.FLAG_REDUCE, holder.iv_select));
		holder.iv_delete.setOnClickListener(new DeleteListener(arg0));
		holder.iv_select.setOnClickListener(new AmendSelectIconListener(holder.iv_select ,arg0));
		holder.tv_product_edite.setOnClickListener(new AddAndReduceListener(arg0, AddAndReduceListener.FLAG_EDITE, null));
		return convertView;
	}

	/**
	 * �޸�ѡ���ı���ť������
	 * @author wsd_leiguoqiang
	 */
	class AmendSelectIconListener implements View.OnClickListener{
		/**
		 * ѡ��ͼ��
		 */
		private ImageView iv_select_icon;
		/**
		 * �����±�
		 */
		private int position;

		public AmendSelectIconListener(ImageView iv_select_icon , int position) {
			super();
			this.iv_select_icon = iv_select_icon;
			this.position = position;
		}

		@Override
		public void onClick(View v) {
			if(iv_select_icon.isSelected()){
				iv_select_icon.setSelected(false);
				//�޸Ľ��㼯��������
				list_total_cartItem.remove(list.get(position));
				//�ı�ȫѡ�ı���ť״̬
				tv_select_all_button.setTextColor(((Activity)context).getResources().getColor(R.color.black_light));
				Drawable drawable = ((Activity)context).getResources().getDrawable(R.drawable.icon_unseclete);
				tv_select_all_button.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
				//�ı�ȫѡ��Ǳ���
				flag_select_all = false;
			}else{
				iv_select_icon.setSelected(true);
				//�޸Ľ��㼯��������
				list_total_cartItem.add(list.get(position));
				//�޸�ȫѡ��ť��ѡ��״̬
				if(list_total_cartItem.size()==list.size()){
					//�ı�ȫѡ�ı���ť״̬
					tv_select_all_button.setTextColor(((Activity)context).getResources().getColor(R.color.wathet_blue));
					Drawable drawable = ((Activity)context).getResources().getDrawable(R.drawable.icon_seclete);
					tv_select_all_button.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
					//�ı�ȫѡ��Ǳ���
					flag_select_all = true;
				}
			}
			//���ºϼ��ܼۺͽ�������
			tv_total_price.setText("�ϼƣ���"+cart.totalPrice(list_total_cartItem));
			tv_total_cartItem_count.setText("����("+list_total_cartItem.size()+")");
			//�жϽ��㼯�����Ƿ�������
			if(list_total_cartItem.size()!=0){
				tv_total_cartItem_count.setBackgroundColor(((Activity)context).getResources().getColor(R.color.red));
				tv_total_cartItem_count.setClickable(true);
			}else{
				tv_total_cartItem_count.setBackgroundColor(((Activity)context).getResources().getColor(R.color.gray_light));
				tv_total_cartItem_count.setClickable(false);
			}

		}
	}

	/**
	 * �Զ���ɾ��ͼ���Ƿ���ʾ����,��Ʒ���ﳵ�б�Activity����
	 */
	public void deleteIconShow(){

		if(!flag){
			//��Ҫ�������ݼ����е�ÿ�����ݣ�������ɾ��ͼƬ���Ƿ���ʾ����
			for(int i = 0;i<list.size();i++){
				final ImageView iv_delete_icon = (ImageView) listView.findViewWithTag("iv_delete"+i);
				final ImageView iv_select_icon = (ImageView) listView.findViewWithTag("iv_select"+i);
				//ɾ��ͼ��
				ObjectAnimator anim = ObjectAnimator.ofFloat(iv_delete_icon, "abc", 1f, 0f);
				//ѡ��ͼ��
				ObjectAnimator anim_select = ObjectAnimator.ofFloat(iv_select_icon, "abc", 0f , 1f);
				anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
					@Override
					public void onAnimationUpdate(ValueAnimator valueAnimator) {
						float val = (Float) valueAnimator.getAnimatedValue();
						iv_delete_icon.setScaleX(val);
						iv_delete_icon.setScaleY(val);
					}
				});
				anim.setDuration(500);
				anim.start();

				anim_select.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

					@Override
					public void onAnimationUpdate(ValueAnimator animation) {
						float val = (Float) animation.getAnimatedValue();
						iv_select_icon.setScaleX(val);
						iv_select_icon.setScaleY(val);
					}
				});
				anim_select.setDuration(500);
				anim_select.start();
			}
		}else{
			for(int i = 0;i<list.size();i++){
				final ImageView iv_delete_icon = (ImageView) listView.findViewWithTag("iv_delete"+i);
				final ImageView iv_select_icon = (ImageView) listView.findViewWithTag("iv_select"+i);
				//ɾ��ͼ��
				ObjectAnimator anim = ObjectAnimator.ofFloat(iv_delete_icon, "abc", 0f, 1f);
				//ѡ��ͼ��
				ObjectAnimator anim_select = ObjectAnimator.ofFloat(iv_select_icon, "abc", 1f , 0f);
				anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

					@Override
					public void onAnimationUpdate(ValueAnimator animation) {
						float val = (Float) animation.getAnimatedValue();
						iv_delete_icon.setScaleX(val);
						iv_delete_icon.setScaleY(val);
					}
				});
				anim.setDuration(500);
				anim.start();

				anim_select.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

					@Override
					public void onAnimationUpdate(ValueAnimator animation) {
						float val = (Float) animation.getAnimatedValue();
						iv_select_icon.setScaleX(val);
						iv_select_icon.setScaleY(val);
					}
				});
				anim_select.setDuration(500);
				anim_select.start();
			}
		}

	}

	/**
	 * �Զ���ɾ����ť�¼�������
	 * @author wsd_leiguoqiang
	 */
	class DeleteListener implements View.OnClickListener{
		/**
		 * �����±�ֵ
		 */
		private int position;

		public DeleteListener(int position) {
			super();
			this.position = position;
		}

		@Override
		public void onClick(View v) {
			int id = list.get(position).getProduct().getYWM_ID();
			//ɾ������
			cart.deleteCartItem(id, CartAdapter.this);
			//��������
			cart.saveCartData();
			if(cart.getList_cartItem().size()==0){
				((MainActivity)context).getTv_cart_acount().setVisibility(View.INVISIBLE);
			}else{
				((MainActivity)context).getTv_cart_acount().setText(cart.getList_cartItem().size()+"");
			}
		}
	}

	/**
	 *  �Զ������Ӻͼ��ٰ�ť�¼������࣬������Ʒ�༭������
	 * @author wsd_leiguoqiang
	 */
	class AddAndReduceListener implements View.OnClickListener{
		//�������ӳ���
		public	static final int FLAG_ADD = 1;
		//������ٳ���
		public	static final int FLAG_REDUCE = 2;
		//������ٳ���
		public	static final int FLAG_EDITE = 3;
		/**
		 * �����±���������ڲ��ұ�ǿؼ�
		 */
		private int position;
		/**
		 * �������ͱ��������ڱ�������ӻ��Ǽ���
		 */
		private int flag;
		/**
		 * ����������ѡ��ͼ��
		 */
		private ImageView iv_select_icon;

		public AddAndReduceListener(int position, int flag , ImageView iv_select_icon) {
			super();
			this.position = position;
			this.flag = flag;
			this.iv_select_icon =  iv_select_icon;
		}

		@Override
		public void onClick(View v) {
			Cart cart = app.getCart();
			TextView tv_display_count = (TextView) listView.findViewWithTag("tv_display_count"+position);
			TextView tv_producte_count = (TextView) listView.findViewWithTag("tv_product_count"+position);
			int count = Integer.parseInt(tv_display_count.getText().toString());
			switch (flag) {
			case 1:
				count++;
				//��������
				tv_display_count.setText(count+"");
				tv_producte_count.setText("x"+count);
				//���¹��ﳵ���ݼ���
				list.get(position).getBuyParam().put("ordernumber", count+"");
				//�����ܼ�����
				if(iv_select_icon.isSelected()){
					list_total_cartItem.clear();
					list_total_cartItem.addAll(list);
					tv_total_price.setText("�ϼ�:��"+cart.totalPrice(list_total_cartItem));
				}
				break;
			case 2:
				count--;
				if(count<=1){
					count = 1;
				}
				//��������
				tv_display_count.setText(count+"");
				tv_producte_count.setText("x"+count);
				//���¹��ﳵ���ݼ���
				list.get(position).getBuyParam().put("ordernumber", count+"");
				if(iv_select_icon.isSelected()){
					//���½������ݼ���
					tv_total_price.setText("�ϼ�:��"+cart.totalPrice(list_total_cartItem));
				}
				break;
			//������Ʒ�༭��ť
			case 3:
				//���������������
				uploadCartView.displayBuyParam(position);
				//���õ�һ���ӿؼ�����
				uploadCartView.ll_first.setChildClickable(false);
				//���������±굽���ﳵҳ��
				uploadCartView.setPosition(position);
				break;
			}
			if(flag!=3){
				//�������ݼ���
				cart.amendCount(list.get(position).getProduct().getYWM_ID(), count);
				notifyDataSetChanged();
			}
		}

	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}


}
