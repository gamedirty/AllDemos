package com.example.refadapter

import android.util.ArrayMap
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlin.reflect.KClass
import kotlin.reflect.full.isSubclassOf

/**
 * itemData指的是每个adapter中的数据项
 *
 * 这里分析一下 普通的[Adapter] 主要做的工作
 *
 * getItemViewType
 * 返回itemViewType 通常是和itemData对应的一个int值通常用来关联对应的ViewHolder 也在RecyclerViewPool中映射ViewHolder
 *
 * onCreateViewHolder
 * 返回对应的ViewHolder 即上边[getItemViewType]映射的ViewHolder
 *
 * bindViewHolder
 * 绑定ViewHolder 通常是给viewHolder设置数据 itemData
 *
 * 所以 这里边存在的映射关系为  itemDataType -->itemViewType-> ViewHolder  这里不考虑不符合这种映射关系的特殊adapter的处理方式
 * 根据adapter这样的设计思路，我们在开发的过程中，主要做的事情有两个：一个是编写dataModel，二是在ViewHolder中填充dataModel。这个框架的核心目的是尽可能的规避其他冗余工作
 *
 * 使用方法如下：
 * 1、编写dataModel 给dataClass添加注解[VHRef] 并指定对应的[TSLViewHolder]
 * 2、编写ViewHolder继承[TSLViewHolder]范型参数填对应的DataModel，同时添加注解[VHLayoutId]指定对应的布局文件,重写[TSLViewHolder.bindData]方法渲染数据
 * 3、使用[AnnotationAdapter]作为recyclerView的adapter 并设置包含dataModel的数据集
 *
 * 调用viewHolder 渲染 itemData
 */
object AnnotationAdapterDelegate : AdapterDelegate<TSLViewHolder<*>> {


    /**
     * itemData类型和itemViewType的映射
     */
    private val typeMap: ArrayMap<Class<*>, Int> by lazy { ArrayMap(10) }

    /**
     * itemData 和 ViewHolder type的映射
     */
    private val vhMap: SparseArray<Class<out TSLViewHolder<*>>> by lazy { SparseArray(10) }


    /**
     * viewHolder 和他对应的layoutId 映射
     */
    private val vhLayoutMap by lazy { ArrayMap<Class<out TSLViewHolder<*>>, Int>() }

    override fun getItemViewType(any: Any): Int {
        val cacheType = typeMap[any.javaClass]
        if (cacheType != null) {
            return cacheType
        }
        val vhType = typeOf(any)
        typeMap[any.javaClass] = vhType
        return vhType
    }

    private fun typeOf(any: Any): Int {
        any.javaClass.apply {
            val vhAno: VHRef = getAnnotation(VHRef::class.java)
                ?: throw RuntimeException("${any.javaClass.name}不包含${VHRef::class.java.name}注解")
            if (!vhAno.ref.isSubclassOf(TSLViewHolder::class)) {
                throw RuntimeException("${vhAno.ref.qualifiedName} 需要继承 ${TSLViewHolder::class.qualifiedName}")
            }
            val type = this.hashCode()
            val instance = vhAno.ref.java
            vhMap.put(type, instance)
            return type
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TSLViewHolder<*> {
        val layoutId = vhLayoutMap[vhMap[viewType]] ?: {
            val vhLayoutId =
                vhMap.get(viewType).getAnnotation(VHLayoutId::class.java)
                    ?: throw RuntimeException("${vhMap[viewType].name}没有添加${VHLayoutId::class.qualifiedName}注解")
            vhLayoutId.layoutId
        }.invoke()
        return vhMap.get(viewType).getConstructor(View::class.java)
            .newInstance(
                LayoutInflater.from(parent.context)
                    .inflate(layoutId, null)
            ) as TSLViewHolder
    }

    override fun bindViewHolder(holder: TSLViewHolder<*>, any: Any) {
        holder.bindDataInternal(any)
    }
}


@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class VHRef(val ref: KClass<out TSLViewHolder<*>>)

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class VHLayoutId(val layoutId: Int)


abstract class TSLViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView),
    LayoutContainer {
    fun bindDataInternal(any: Any) {
        bindData(any as T)
    }

    abstract fun bindData(data: T)
}