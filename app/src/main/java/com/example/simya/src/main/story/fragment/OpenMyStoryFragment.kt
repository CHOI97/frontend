package com.example.simya.src.main.story.fragment

import android.content.Intent
import android.os.Bundle
import androidx.core.view.GravityCompat
import com.example.simya.R
import com.example.simya.config.BaseFragment
import com.example.simya.databinding.FragmentDrawerMyStroyOpenBinding
import com.example.simya.util.Constants.BORDER_MAIN_MENU
import com.example.simya.util.Constants.BORDER_TITLE
import com.example.simya.util.Constants.HOUSE_ID
import com.example.simya.src.main.prepare.PrepareActivity
import com.example.simya.src.main.story.OpenMyStoryActivity
import com.example.simya.src.main.story.StoryLikeActivity
import com.example.simya.src.main.story.StoryReviewActivity

class OpenMyStoryFragment :
    BaseFragment<FragmentDrawerMyStroyOpenBinding>(
        FragmentDrawerMyStroyOpenBinding::bind,
        R.layout.fragment_drawer_my_stroy_open) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init() {
        val drawerLayout = binding.dlMyStoryOpen
        binding.includedMyStoryOpen.tvRvMainMenu.text = (activity as OpenMyStoryActivity).category
        binding.includedMyStoryOpen.tvRvTitle.text = (activity as OpenMyStoryActivity).houseName
        // 드로어 오픈
        binding.includedMyStoryOpen.ibMyStoryOpenDrawer.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
            drawerInit()
        }
        // 찜 리스트
        binding.includedMyStoryOpen.btnMyStoryOpenLike.setOnClickListener {
            movePrepare()
        }
        // 리뷰 리스트
        binding.includedMyStoryOpen.btnMyStoryOpenReview.setOnClickListener {
            movePrepare()
        }
        // 오픈 준비하기기
        binding.includedMyStoryOpen.btnMyStoryOpenReady.setOnClickListener {
            var houseId = (activity as OpenMyStoryActivity).houseId
            moveToOpen(houseId)
        }
    }
    private fun movePrepare(){
        val intent = Intent(requireContext(),PrepareActivity::class.java)
        startActivity(intent)
    }
    private fun moveToReviewList() {
        val intent = Intent(requireContext(), StoryReviewActivity::class.java)
        startActivity(intent)
    }

    private fun moveToLikeList() {
        val intent = Intent(requireContext(), StoryLikeActivity::class.java)
        startActivity(intent)
    }

    private fun moveToOpen(houseId: Long) {
        val intent = Intent(requireContext(), OpenMyStoryInputFragment::class.java)
        intent.putExtra(BORDER_MAIN_MENU, binding.includedMyStoryOpen.tvRvMainMenu.text)
        intent.putExtra(BORDER_TITLE, binding.includedMyStoryOpen.tvRvTitle.text)
        intent.putExtra(HOUSE_ID, houseId)
        startActivity(intent)
    }

    private fun drawerInit() {
        binding.btnMyStoryOpenBorder.setOnClickListener {
            // 간판수정
        }
        binding.btnMyStoryOpenMainMenu.setOnClickListener {
            // 메인 메뉴 수정
        }
        binding.btnMyStoryClose.setOnClickListener {
            var dialog = CloseDialog(requireContext())
            dialog.showDia()
        }
    }
}
