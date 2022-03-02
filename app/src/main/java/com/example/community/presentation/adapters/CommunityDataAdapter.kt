package com.example.community.presentation.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.community.R
import com.example.community.Utilities.Prefs
import com.example.community.data.models.Response
import com.example.community.databinding.CommunityLiBinding
import com.example.community.presentation.MainApplication
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class CommunityDataAdapter : PagingDataAdapter<Response, CommunityDataAdapter.MyViewHolder>(PassengersComparator){

    private val communityList = ArrayList<Response>()
    var likedIndexesList: MutableList<String> = ArrayList()
    val prefs: Prefs by lazy {
        Prefs(MainApplication.instance)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding : CommunityLiBinding = DataBindingUtil.inflate(layoutInflater,
            R.layout.community_li,
            parent,
            false
        )
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return communityList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val item = getItem(position)
        item?.let { holder.bind(it,position) }

    }

   inner class MyViewHolder(val binding: CommunityLiBinding): RecyclerView.ViewHolder(binding.root){


        fun bind(item: Response, position: Int){
            binding.learnerName.text = item.firstName
            binding.topic.text = item.topic

            val nativesString = item.natives.joinToString { it ->  it  }
            binding.natives.text = nativesString

            val learnsString = item.learns.joinToString { it ->  it  }
            binding.learns.text = learnsString

            binding.referenceCount.text = "${item.referenceCnt}"

            if(item.referenceCnt==0){
                binding.referenceCount.visibility = View.GONE
                binding.newTag.visibility = View.VISIBLE
            }else{
                binding.referenceCount.visibility = View.VISIBLE
                binding.newTag.visibility = View.GONE
            }
            val imgUri = item.pictureUrl.toUri().buildUpon().scheme("https").build()
            binding.image.load(imgUri) {
                placeholder(R.drawable.image_loading_anim)
            }

            if(!prefs.likedIndexes.equals("")){
                val myType = object : TypeToken<List<String>>() {}.type

                var obj:MutableList<String> = Gson().fromJson<MutableList<String>>(prefs.likedIndexes,  myType)
                likedIndexesList = obj
                Log.v("tag_clicked","Stored Data  $likedIndexesList")
            }

            if(likedIndexesList.contains(item.id.toString())){
                binding.likeImg.setImageDrawable(binding.likeImg.context.getDrawable(R.drawable.like))
            }else{
                binding.likeImg.setImageDrawable(binding.likeImg.context.getDrawable(R.drawable.dislike))
            }

            binding.mainLayout.setOnClickListener {
                if(likedIndexesList.contains(item.id.toString())){
                    likedIndexesList.remove(item.id.toString())
                }else{
                    likedIndexesList.add(item.id.toString())
                }

                prefs.likedIndexes = likedIndexesList.toString()
                notifyItemChanged(position)

                Log.v("tag_clicked","  ${item.id}")
            }

        }

    }

    object PassengersComparator : DiffUtil.ItemCallback<Response>() {
        override fun areItemsTheSame(oldItem: Response, newItem: Response): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Response, newItem: Response): Boolean {
            return oldItem == newItem
        }
    }

}


