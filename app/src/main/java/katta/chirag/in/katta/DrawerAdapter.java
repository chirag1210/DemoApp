package katta.chirag.in.katta;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Win on 15-07-2017.
 */

public class DrawerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<DrawerModel> arrayList = new ArrayList<>();
    private LayoutInflater inflater;
    private Context context;
    private CallBack callBack;

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;


    public DrawerAdapter(Context context, ArrayList<DrawerModel> arrayList, CallBack callBack) {
        this.context = context;
        this.callBack=callBack;
        inflater = LayoutInflater.from(context);
        this.arrayList = arrayList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      /*  View view = inflater.inflate(R.layout.lv_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;*/

        if(viewType == TYPE_HEADER) {
            View v = LayoutInflater.from (parent.getContext ()).inflate (R.layout.header_item, parent, false);
            return new HeaderViewHolder (v);
        } else if(viewType == TYPE_ITEM) {
            View v = LayoutInflater.from (parent.getContext ()).inflate (R.layout.lv_item, parent, false);
            return new GenericViewHolder (v);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

       /* holder.title.setText(arrayList.get(position).getName());
        holder.ivicon.setImageResource(arrayList.get(position).getImage());*/

        if(holder instanceof HeaderViewHolder) {
            HeaderViewHolder headerHolder = (HeaderViewHolder) holder;
            headerHolder.txtTitleHeader.setText ("CATEGORIES");

        } else if(holder instanceof GenericViewHolder) {
            DrawerModel currentItem = getItem (position - 1);
            GenericViewHolder genericViewHolder = (GenericViewHolder) holder;
            if (position==1){
                genericViewHolder.title.setTextColor(Color.parseColor("#358E3F"));
                genericViewHolder.title.setAllCaps(true);
            }
            if(position == 10){
                genericViewHolder.title.setTextColor(Color.parseColor("#358E3F"));
                genericViewHolder.title.setAllCaps(true);
            }else if(position == 11){
                genericViewHolder.title.setTextColor(Color.parseColor("#358E3F"));
                genericViewHolder.title.setAllCaps(true);
            }
            genericViewHolder.title.setText (currentItem.getName ());


            genericViewHolder.ListItemContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    callBack.onClickedItemPostion(position-1);
                }
            });
        }
    }
    private DrawerModel getItem (int position) {
        return arrayList.get (position);
    }
    @Override
    public int getItemCount() {
        return arrayList.size()+1;
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitleHeader;

        public HeaderViewHolder (View itemView) {
            super (itemView);
            this.txtTitleHeader = (TextView) itemView.findViewById (R.id.textView);

        }
    }

    class GenericViewHolder  extends RecyclerView.ViewHolder {

        TextView title;
       //         ,right_arrow;
        RelativeLayout ListItemContainer;

        public GenericViewHolder (View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.name);
        //    right_arrow = (TextView) itemView.findViewById(R.id.right_arrow);
        //    right_arrow.setTypeface(SapnaApplication.getIconTypeFace());

            ListItemContainer= (RelativeLayout) itemView.findViewById(R.id.list_item_container);

        }
    }

    @Override
    public int getItemViewType (int position) {
        if(isPositionHeader (position)) {
            return TYPE_HEADER;
        }
        return TYPE_ITEM;
    }
    private boolean isPositionHeader (int position) {
        return position == 0;
    }
}