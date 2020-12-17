package com.example.refadapter

import android.annotation.SuppressLint
import android.view.View


@SuppressLint("NonConstantResourceId")
@VHLayoutId(R.layout.layout_people)
class PeopleViewHolder(override val containerView: View) : TSLViewHolder<People>(containerView) {

    override fun bindData(data: People) {

    }
}


@SuppressLint("NonConstantResourceId")
@VHLayoutId(R.layout.layout_animal)
class AnimalViewHolder(override val containerView: View) : TSLViewHolder<Animal>(containerView) {
    override fun bindData(data: Animal) {
    }

}