package com.example.simya.src.fragment.mypage

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.simya.R
import com.example.simya.src.activity.myPage.MyPageLikeActivity
import com.example.simya.src.activity.myPage.MyPageReviewActivity
import com.example.simya.src.activity.myPage.ProfileEditActivity
import com.example.simya.databinding.FragmentHomeMyPageBinding
import com.example.simya.src.adpter.myPage.MultiProfileAdapter
import com.example.simya.src.data.UserTokenData
import com.example.simya.src.model.RetrofitBuilder
import com.example.simya.src.model.RetrofitService
import com.example.simya.src.model.profile.MyProfileResponse
import com.example.simya.src.model.profile.ProfileDTO
import com.example.simya.src.testData.TestDataMultiProfile
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyPageProfileFragment : Fragment() {
    private lateinit var binding: FragmentHomeMyPageBinding
    private lateinit var dataList: ArrayList<ProfileDTO>
    private lateinit var dataRVAdapter: MultiProfileAdapter

    private val retrofit by lazy {
        RetrofitBuilder.getInstnace()
    }
    private val simyaApi by lazy {
        retrofit.create(RetrofitService::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeMyPageBinding.inflate(layoutInflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()

        // Fragment에서 Activity로 넘어가깅!!
        // Fragment는 this 사용 불가이므로, this 대신 activity를 씀
        binding.btnMyPageProfile.setOnClickListener {
            val intent = Intent(activity, ProfileEditActivity::class.java)
            startActivity(intent)
        }

        // 찜한 이야기 집으로
        binding.ibMyPageMenu1.setOnClickListener {
            val intent = Intent(activity, MyPageLikeActivity::class.java)
            startActivity(intent)
        }

        // 내가 쓴 리뷰로
        binding.ibMyPageMenu2.setOnClickListener {
            val intent = Intent(activity, MyPageReviewActivity::class.java)
            startActivity(intent)
        }
    }

    private fun init() {

        dataList = arrayListOf()
        dataList.apply {
            add(dataList.size, ProfileDTO(0, "추가하기", "추가하기", "R.drawable.ic_baseline_add_24"))
        }
        dataRVAdapter = MultiProfileAdapter(this, dataList)
        binding.rvMyPageMultiProfile.adapter = dataRVAdapter
        binding.rvMyPageMultiProfile.layoutManager =
            LinearLayoutManager(
                this.context,
                RecyclerView.HORIZONTAL,
                false
            )
        getAllMyProfileService()
    }

    private fun getAllMyProfileService() {
        simyaApi.getMyAllProfile(UserTokenData.accessToken, UserTokenData.refreshToken)
            .enqueue(object : Callback<MyProfileResponse> {
                override fun onResponse(
                    call: Call<MyProfileResponse>,
                    response: Response<MyProfileResponse>
                ) {
                    val code = response.body()!!.code
                    if (code == 200) {
                        activity!!.runOnUiThread {
                            dataList.apply {
                                for (i: Int in 0 until response.body()!!.result.size) {
                                    add(response.body()!!.result[i])
                                    dataRVAdapter.notifyItemInserted(i+1)
                                }
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<MyProfileResponse>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            })
    }
}