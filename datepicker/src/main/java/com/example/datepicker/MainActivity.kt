package com.example.datepicker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    fun showDialog(v :View){
        DDialog().show(supportFragmentManager,"hh")
    }

//    private fun showList() {
//        val listview = findViewById<RecyclerView>(R.id.list)
//        listview.layoutManager = object : LinearLayoutManager(this) {
//            override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
//                return super.generateDefaultLayoutParams().apply {
//                    width = RecyclerView.LayoutParams.MATCH_PARENT
//                }
//            }
//        }
//
//        listview.addItemDecoration(object : RecyclerView.ItemDecoration() {
//            override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
//                c.drawColor(222)
//                super.onDraw(c, parent, state)
//            }
//
//            override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
//                super.onDrawOver(c, parent, state)
//            }
//        })
//
//        val snapHelper = LinearSnapHelper()
//        snapHelper.attachToRecyclerView(listview)
//        listview.adapter = MyAdapter()
//    }
}

class MyAdapter : RecyclerView.Adapter<MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_item, null))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindData(position)
    }

    override fun getItemCount(): Int {
        return 30
    }

}

class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    fun bindData(position: Int) {
    }

}