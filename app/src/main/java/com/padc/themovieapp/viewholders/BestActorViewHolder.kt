package com.padc.themovieapp.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.padc.themovieapp.R
import com.padc.themovieapp.data.vos.ActorVO
import com.padc.themovieapp.utils.IMAGE_BASE_URL
import kotlinx.android.synthetic.main.view_holder_best_actor.view.*

class BestActorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bindData(actor: ActorVO){
        Glide.with(itemView.context)
            .load("$IMAGE_BASE_URL${actor.profilePath}")
//            .fallback(R.drawable.img)
            .placeholder(R.drawable.placeholder_person)
            .into(itemView.ivBestActor)

        itemView.tvActorName.text = actor.name
    }
}