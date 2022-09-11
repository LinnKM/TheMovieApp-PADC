package com.padc.themovieapp.adapters

import android.annotation.SuppressLint
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.padc.themovieapp.R
import com.padc.themovieapp.data.vos.ActorVO
import com.padc.themovieapp.viewholders.BestActorViewHolder
import kotlinx.android.synthetic.main.view_holder_best_actor.view.*

class ActorAdapter : RecyclerView.Adapter<BestActorViewHolder>() {

    private var mActorList: List<ActorVO> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BestActorViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_holder_best_actor, parent, false)
        return BestActorViewHolder(view)
    }

    override fun onBindViewHolder(holder: BestActorViewHolder, position: Int) {
        if(mActorList.isNotEmpty()){
            holder.bindData(mActorList[position])
        }
    }

    override fun getItemCount(): Int {
        return mActorList.count()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setNewData(actorList: List<ActorVO>){
        mActorList = actorList
        notifyDataSetChanged()
    }
}