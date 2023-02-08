package com.example.simya.src.fragment.mystoryFragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.simya.src.Constants
import com.example.simya.src.Constants.BORDER_MAIN_MENU
import com.example.simya.src.Constants.BORDER_TITLE
import com.example.simya.src.Constants.HOUSE_ID
import com.example.simya.src.activity.story.OpenMyStoryActivity
import com.example.simya.databinding.FragmentHomeMainGridBinding
import com.example.simya.src.adpter.mystory.MyStoryGVAdapter
import com.example.simya.src.data.UserTokenData
import com.example.simya.src.model.RetrofitBuilder
import com.example.simya.src.model.RetrofitService
import com.example.simya.src.model.story.LoadMyStoryResponse
import com.example.simya.src.model.story.LoadMyStoryResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyStoryGridFragment : Fragment() {
    private lateinit var binding: FragmentHomeMainGridBinding
    private var dataList: ArrayList<LoadMyStoryResult> = arrayListOf()
    private lateinit var dataGVAdapter: MyStoryGVAdapter

    private val retrofit by lazy {
        RetrofitBuilder.getInstnace()
    }
    private val simyaApi by lazy {
        retrofit.create(RetrofitService::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeMainGridBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        loadMyStory()

    }

    private fun loadMyStory() {
        simyaApi.getMyStory(UserTokenData.accessToken, UserTokenData.refreshToken)
            .enqueue(object :
                Callback<LoadMyStoryResponse> {
                override fun onResponse(
                    call: Call<LoadMyStoryResponse>,
                    response: Response<LoadMyStoryResponse>
                ) {
                    if (response.code() == Constants.OK) {
                        Log.d("Response", response.body().toString())
                        if (response.body()!!.message == "이야기 집이 없습니다.") {
                            Log.d("이야기집", "이야기 집이 없습니다.")
                        }  else {
                            activity!!.runOnUiThread {
                                Log.d("이야기집","이야기 집이 있습니다.")
                                dataList = arrayListOf()
                                dataList.apply {
                                    for (i: Int in 0 until response.body()!!.result.size) {
                                        add(response.body()!!.result[i])
                                        Log.d("이야기집",i.toString())
                                    }
                                }
                                val gridLayoutManager = GridLayoutManager(activity, 2)
                                dataGVAdapter = MyStoryGVAdapter(dataList)
                                binding.gvHomeMainGrid.adapter = dataGVAdapter
                                binding.gvHomeMainGrid.layoutManager = gridLayoutManager
                                clickMyStory()
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<LoadMyStoryResponse>, t: Throwable) {
                    Log.d("ERROR", t.toString())
                }

            })
    }

    private fun clickMyStory() {
        dataGVAdapter.setOnItemClickListener(object : MyStoryGVAdapter.OnItemClickListener {
            override fun onItemClick(v: View, data: LoadMyStoryResult, position: Int) {
                val intent =
                    Intent(this@MyStoryGridFragment.context, OpenMyStoryActivity::class.java)
                intent.putExtra(BORDER_MAIN_MENU, data.category)
                intent.putExtra(BORDER_TITLE, data.houseName)
                intent.putExtra(HOUSE_ID,data.houseId)
                startActivity(intent)
            }

            override fun onLongClick(v: View, data: LoadMyStoryResult, position: Int) {
                Log.d("STATUS", "onLongClick NO ACTION")
            }

        })
    }
}