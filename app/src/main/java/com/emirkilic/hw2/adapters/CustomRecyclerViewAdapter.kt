package com.emirkilic.hw2.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.emirkilic.hw2.databinding.ItemJobHighBinding
import com.emirkilic.hw2.databinding.ItemJobNormalBinding
import com.emirkilic.hw2.db.Job_Application

class CustomRecyclerViewAdapter( private val context: Context, private val recyclerItemValues: ArrayList<Job_Application>)
    : RecyclerView.Adapter< RecyclerView.ViewHolder>() {

    interface RecyclerAdapterInterface{
        fun maintainJob(sc:Job_Application)
    }
    lateinit var recyclerAdapterInterface:RecyclerAdapterInterface

    init {
        recyclerAdapterInterface = context as RecyclerAdapterInterface
    }
    companion object {
        const val TYPE_NORMAL_PRIORITY = 1
        const val TYPE_HIGH_PRIORITY = 2
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_HIGH_PRIORITY) {
            val binding = ItemJobHighBinding.inflate(LayoutInflater.from(parent.context), parent, false)

            HighCustomRecyclerViewItemHolder(binding)
        } else {
            val binding = ItemJobNormalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            NormalCustomRecyclerViewItemHolder(binding)
        }
    }

    /*override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView: View
        val inflator = LayoutInflater.from(viewGroup.context)
        //STEP4
        return if (viewType == TYPE_EVEN_ITEM) {
            //STEP5
            itemView = inflator.inflate(R.layout.recycler_even_item, viewGroup, false)
            EvenCustomRecyclerViewItemHolder(itemView)
        } else { //if(viewType == TYPE_ODD_ITEM) odd item
            //STEP5
            itemView = inflator.inflate(R.layout.recycler_odd_item, viewGroup, false)
            OddCustomRecyclerViewItemHolder(itemView)
        }
    }*/

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentItem = recyclerItemValues[position]
        //STEP6
        if (getItemViewType(position) == TYPE_HIGH_PRIORITY) {
            //STEP7
            val itemHolder = holder as HighCustomRecyclerViewItemHolder
            itemHolder.bindingHigh.tvCompanyHigh.text = currentItem.company
            itemHolder.bindingHigh.tvTitleHigh.text = currentItem.title
            itemHolder.bindingHigh.tvStatusHigh.text = currentItem.status

            //STEP8
            itemHolder.bindingHigh.highItemLayout.setOnClickListener {
                recyclerAdapterInterface.maintainJob(currentItem)
            }
        } else if (getItemViewType(position) == TYPE_NORMAL_PRIORITY) {
            //STEP7
            val itemHolder = holder as NormalCustomRecyclerViewItemHolder
            itemHolder.bindingNormal.tvCompany.text = currentItem.company
            itemHolder.bindingNormal.tvTitle.text = currentItem.title
            itemHolder.bindingNormal.tvStatus.text = currentItem.status

            itemHolder.bindingNormal.normalItemLayout.setOnClickListener {
                recyclerAdapterInterface.maintainJob(currentItem)
            }



        }
    }

    override fun getItemCount(): Int {
        return recyclerItemValues.size
    }

    //STEP1:
    override fun getItemViewType(position: Int): Int {
        val sc = recyclerItemValues[position]
        return if (sc.priority.uppercase() == "HIGH") TYPE_HIGH_PRIORITY else TYPE_NORMAL_PRIORITY
    }

    //Using viewBinding
    inner class HighCustomRecyclerViewItemHolder(val bindingHigh: ItemJobHighBinding)
        : RecyclerView.ViewHolder(bindingHigh.root) {

    }

    inner class NormalCustomRecyclerViewItemHolder(val bindingNormal: ItemJobNormalBinding)
        : RecyclerView.ViewHolder(bindingNormal.root) {

    }

    //STEP2
    /*internal inner class EvenCustomRecyclerViewItemHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var tvEvenItemSocialName: TextView
        var imgEvenItemSocial: ImageView
        var itemEvenConstraintLayout: ConstraintLayout

        init {
            tvEvenItemSocialName = itemView.findViewById<TextView>(R.id.tvEvenItemSocialName)
            imgEvenItemSocial = itemView.findViewById<ImageView>(R.id.imgEvenItemSocial)
            itemEvenConstraintLayout = itemView.findViewById<ConstraintLayout>(R.id.evenItemLayout)
        }
    }*/

    //STEP2
    /*internal inner class OddCustomRecyclerViewItemHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var tvOddItemSocialName: TextView
        var cbOddItem: CheckBox
        var itemOddConstraintLayout: ConstraintLayout

        init {
            tvOddItemSocialName = itemView.findViewById<TextView>(R.id.tvOddItemSocialName)
            cbOddItem = itemView.findViewById<CheckBox>(R.id.cbOddItem)
            itemOddConstraintLayout = itemView.findViewById<ConstraintLayout>(R.id.oddItemLayout)
        }
    }*/

}
