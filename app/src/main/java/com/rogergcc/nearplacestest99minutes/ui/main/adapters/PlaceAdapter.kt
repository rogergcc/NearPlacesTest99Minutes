package com.rogergcc.nearplacestest99minutes.ui.main.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.rogergcc.nearplacestest99minutes.core.BaseViewHolder
import com.rogergcc.nearplacestest99minutes.data.model.PlaceItem
import com.rogergcc.nearplacestest99minutes.databinding.PlaceItemBinding

class PlaceAdapter(
    val placeNearbyDetailsAction: (placeItem: PlaceItem) -> Unit,
) : RecyclerView.Adapter<BaseViewHolder<*>>() {

    var mItemsPlace = listOf<PlaceItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val itemBinding =
            PlaceItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val holder = UpcomingPlacesViewHolder(itemBinding, parent.context)

        itemBinding.root.setOnClickListener {
            val position =
                holder.bindingAdapterPosition.takeIf { it != DiffUtil.DiffResult.NO_POSITION }
                    ?: return@setOnClickListener
            placeNearbyDetailsAction(mItemsPlace[position])
        }

        return holder
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (holder) {
            is UpcomingPlacesViewHolder -> holder.bind(mItemsPlace[position])
        }
    }

    override fun getItemCount(): Int = mItemsPlace.size

    private inner class UpcomingPlacesViewHolder(
        val binding: PlaceItemBinding,
        val context: Context,
    ) : BaseViewHolder<PlaceItem>(binding.root) {
        override fun bind(item: PlaceItem) {

            binding.apply {
                tvTitleNamePlace.text = item.name
                tvAvAdressPlace.text = item.vicinity

                Glide.with(context).load(item.photo)
                    .centerCrop().into(imvPhotoPlace)
            }
        }
    }
}