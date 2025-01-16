package com.example.fitness_runnting_tracker.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

import com.bumptech.glide.Glide
import com.example.fitness_runnting_tracker.R
import com.example.fitness_runnting_tracker.db.Run
import com.example.fitness_runnting_tracker.other.TrackingUtility

import java.text.SimpleDateFormat
import java.util.*

class RunAdapter : RecyclerView.Adapter<RunAdapter.RunViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<Run>() {
        override fun areItemsTheSame(oldItem: Run, newItem: Run): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Run, newItem: Run): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    // ListDiffer to efficiently deal with changes in the RecyclerView
    val differ = AsyncListDiffer(this, diffCallback)

    inner class RunViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    fun submitList(list: List<Run>) = differ.submitList(list)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RunViewHolder {
        return RunViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_run,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: RunViewHolder, position: Int) {
        val run = differ.currentList[position]
        // set item data
        holder.itemView.apply {
            Glide.with(this).load(run.img).into(holder.itemView.findViewById<ImageView>(R.id.ivRunImage))

            val calendar = Calendar.getInstance().apply {
               timeInMillis = run.timestamp
            }
            val dateFormat = SimpleDateFormat("dd.MM.yy", Locale.getDefault())
            holder.itemView.findViewById<TextView>(R.id.tvDate).text = dateFormat.format(calendar.time)

            "${run.avgSpeedInKMH}km/h".also {
                holder.itemView.findViewById<TextView>(R.id.tvAvgSpeed).text = it
            }
            "${run.distanceInMeters / 1000f}km".also {
                holder.itemView.findViewById<TextView>(R.id.tvDistance).text = it
            }
            holder.itemView.findViewById<TextView>(R.id.tvTime).text = TrackingUtility.getFormattedStopWatchTime(run.timeInMillis)
            "${run.caloriesBurned}kcal".also {
                holder.itemView.findViewById<TextView>(R.id.tvCalories).text = it
            }
        }
    }
}