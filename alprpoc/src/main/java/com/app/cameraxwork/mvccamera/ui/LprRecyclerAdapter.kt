package com.app.cameraxwork.mvccamera.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.cameraxwork.databinding.LayoutLprListItemBinding
import com.app.cameraxwork.mvccamera.model.Lpr
import com.app.cameraxwork.mvccamera.util.VSLoadImage

class LprRecyclerAdapter(private val interaction: Interaction? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Lpr>() {

        override fun areItemsTheSame(oldItem: Lpr, newItem: Lpr): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Lpr, newItem: Lpr): Boolean {
            return oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val viewBinding = LayoutLprListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return LprItemViewHolder(
            viewBinding,
            interaction
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is LprItemViewHolder -> {
                holder.bind(differ.currentList.get(position))
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<Lpr>) {
        differ.submitList(list)
    }

    class LprItemViewHolder
    constructor(
        private val viewBinding: LayoutLprListItemBinding,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(viewBinding.root) {

        fun bind(item: Lpr) = with(viewBinding) {
            viewBinding.root.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item)
            }
            viewBinding.itemScanPreviewTvPlateNumber.text = item.outputValue
            VSLoadImage.getInstance().setWHImage(110, 60)
                .execute(
                    item.vehicleImage,
                    viewBinding.itemScanPreviewImgPlate
                )

        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: Lpr)
    }
}