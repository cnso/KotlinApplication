package org.jash.common.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import java.util.function.Predicate

class CommonAdapter<D>(private val ids: Map<Class<out D>, Pair<Int, Int>>, private var data:MutableList<D> = mutableListOf()):RecyclerView.Adapter<CommonViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        return ids[this.data[position]!!::class.java]?.first ?: -1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommonViewHolder {
        return CommonViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), viewType, parent, false))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: CommonViewHolder, position: Int) {
        val d = data[position]
        holder.binding.setVariable(ids[d!!::class.java]?.second ?: 0, d)
    }
    operator fun plusAssign(d: D) {
        data += d
        notifyItemInserted(data.size - 1)
    }
    operator fun plusAssign(d: List<D>) {
        val size = data.size
        data += d
        notifyItemRangeInserted(size, d.size)
    }
    fun clear() {
        val size = data.size
        data.clear()
        notifyItemRangeRemoved(0, size)
    }
    fun removeIf(p:Predicate<D>){
        data.removeIf(p)
        notifyDataSetChanged()
    }

}
class CommonViewHolder(val binding:ViewDataBinding):ViewHolder(binding.root)