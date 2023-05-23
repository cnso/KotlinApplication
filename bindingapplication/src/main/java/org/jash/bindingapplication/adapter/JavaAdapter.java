package org.jash.bindingapplication.adapter;

import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class JavaAdapter<D> extends RecyclerView.Adapter<JavaAdapter.JavaViewHolder> {
   private Map<Class<D>, Pair<Integer, Integer>> ids;
   private List<D> data;

   public JavaAdapter(Map<Class<D>, Pair<Integer, Integer>> ids) {
      this(ids, new ArrayList<>());
   }

   public JavaAdapter(Map<Class<D>, Pair<Integer, Integer>> ids, List<D> data) {
      this.ids = ids;
      this.data = data;
   }

   @Override
   public int getItemViewType(int position) {
      return ids.get(data.get(position).getClass()).first;
   }

   @NonNull
   @Override
   public JavaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      return new JavaViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), viewType, parent, false));

   }

   @Override
   public void onBindViewHolder(@NonNull JavaViewHolder holder, int position) {
      D d = data.get(position);
      holder.binding.setVariable(ids.get(d).second, d);
   }

   @Override
   public int getItemCount() {
      return data.size();
   }

   public void add(D d) {
      data.add(d);
      notifyItemInserted(data.size() - 1);
   }
   public void add(Collection<D> d) {
      int size = data.size();
      data.addAll(d);
      notifyItemRangeInserted(size, d.size());
   }

   public void clear() {
      int size = data.size();
      data.clear();
      notifyItemRangeRemoved(0, size);
   }

   protected static class JavaViewHolder extends RecyclerView.ViewHolder {

      private final ViewDataBinding binding;

      public JavaViewHolder(@NonNull ViewDataBinding binding) {
         super(binding.getRoot());
         this.binding = binding;
      }
   }
}
