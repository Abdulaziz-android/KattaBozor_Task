package com.kattabozor.task.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kattabozor.task.R
import com.kattabozor.task.databinding.ItemProductBinding
import com.kattabozor.task.domain.model.Offer
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso

class OfferProductAdapter(
    private val listener: OnOfferClickListener
) : ListAdapter<Offer, OfferProductAdapter.PVH>(DiffUtilItemCallback) {

    inner class PVH(
        private val itemBinding: ItemProductBinding
    ) : RecyclerView.ViewHolder(itemBinding.root) {
        fun onBind(offer: Offer) = with(itemBinding) {
            Picasso.get().load(offer.image.url)
                .networkPolicy(NetworkPolicy.NO_CACHE, NetworkPolicy.NO_STORE)
                .placeholder(R.drawable.ic_placeholder_image).into(imageIv)

            titleTv.text = offer.name
            priceTv.text = offer.category

            root.setOnClickListener {
                listener.onOfferClicked(offer)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PVH {
        return PVH(ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: PVH, position: Int) {
        holder.onBind(getItem(position))
    }

    interface OnOfferClickListener {
        fun onOfferClicked(offer: Offer)
    }

    object DiffUtilItemCallback : androidx.recyclerview.widget.DiffUtil.ItemCallback<Offer>() {
        override fun areItemsTheSame(oldItem: Offer, newItem: Offer): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Offer, newItem: Offer): Boolean {
            return oldItem.id == newItem.id
        }

    }

}