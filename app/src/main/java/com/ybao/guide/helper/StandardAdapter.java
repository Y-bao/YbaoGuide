/**
 * @Title: StandardAdapter.java
 * @Package appframe.widget.adapter.recyclerview
 * @Description: TODO
 * @author Ybao
 * @date 2015年6月8日 下午4:23:15
 * @version V1.0
 */
package com.ybao.guide.helper;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.support.annotation.CallSuper;
import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * @author Ybao
 * @ClassName: StandardAdapter
 * @Description: TODO Recyclerview 通用的适配器
 * @date 2015年6月8日 下午4:23:15
 */
public class StandardAdapter<T extends StandardAdapter.ItemViewHolder, K> extends Adapter<T> {

    View eView;

    private List<? extends K> mList;

    public StandardAdapter() {
    }

    public StandardAdapter(List<? extends K> mList) {
        this.mList = mList;
    }

    public void setData(List<? extends K> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    public void setEmptyView(View eView) {
        this.eView = eView;
        if (eView != null) {
            eView.setVisibility(getItemCount() == 0 ? View.VISIBLE : View.GONE);
            registerAdapterDataObserver(adapterDataObserver);
        } else {
            unregisterAdapterDataObserver(adapterDataObserver);
        }
    }

    public int getItemIndex(Object object) {
        if (mList != null && !mList.isEmpty()) {
            return mList.indexOf(object);
        }
        return -1;
    }

    @Override
    public int getItemCount() {
        if (mList != null) {
            return mList.size();
        }
        return 0;
    }

    public K getItemData(int position) {
        if (mList != null) {
            return mList.get(position);
        }
        return null;
    }

    @CallSuper
    @Override
    public void onBindViewHolder(T holder, int position) {
        holder.setItemClickListener(thisOnItemClickListener);
    }


    @Override
    public T onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    public Object getItem(int arg0) {
        if (mList != null && mList.size() > arg0 && arg0 >= 0) {
            return mList.get(arg0);
        }
        return null;
    }

    public List<? extends K> getData() {
        return mList;
    }


    StandardAdapter.OnItemClickListener thisOnItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(View v, int position) {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(v, position);
            }
        }
    };

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        Context context;
        ViewDataBinding viewDataBinding;

        public ItemViewHolder(ViewDataBinding viewDataBinding) {
            this(viewDataBinding, true);
        }

        public ItemViewHolder(ViewDataBinding viewDataBinding, boolean clickAble) {
            super(viewDataBinding.getRoot());
            context = itemView.getContext();
//            viewDataBinding.getRoot().setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            this.viewDataBinding = viewDataBinding;
            if (clickAble) {
                viewDataBinding.getRoot().setOnClickListener(onClickListener);
            }
        }

        public Context getContext() {
            return context;
        }

        public <T extends ViewDataBinding> T getBinding() {
            return (T) viewDataBinding;
        }

        public ItemViewHolder(View view) {
            this(view, true);
        }

        protected ItemViewHolder(View view, boolean clickAble) {
            super(view);
            context = itemView.getContext();
            if (clickAble) {
                view.setOnClickListener(onClickListener);
            }
        }

        protected View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = getAdapterPosition();
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(v, pos);
                }
            }
        };

        private OnItemClickListener onItemClickListener;

        public void setItemClickListener(OnItemClickListener onItemClickListener) {
            this.onItemClickListener = onItemClickListener;
        }

        public <V extends View> V findViewById(@IdRes int id) {
            if (itemView == null) {
                return null;
            }
            return (V) itemView.findViewById(id);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

    private OnItemClickListener onItemClickListener;

    public void setItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    public void remove(int i) {
        if (mList != null && mList.size() > i) {
            mList.remove(i);
            notifyItemRemoved(i);
        }
    }

    public void checkDataEmpty() {
        if (eView != null) {
            eView.setVisibility(getItemCount() == 0 ? View.VISIBLE : View.GONE);
        }
    }

    RecyclerView.AdapterDataObserver adapterDataObserver = new RecyclerView.AdapterDataObserver() {
        @Override
        public void onChanged() {
            super.onChanged();
            checkDataEmpty();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            super.onItemRangeRemoved(positionStart, itemCount);
            checkDataEmpty();
        }
    };
}
