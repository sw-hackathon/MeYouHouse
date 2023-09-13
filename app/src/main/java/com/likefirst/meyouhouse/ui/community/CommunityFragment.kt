package com.likefirst.meyouhouse.ui.community

import android.content.Intent
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.likefirst.meyouhouse.data.dto.community.Article
import com.likefirst.meyouhouse.data.dto.community.Articles
import com.likefirst.meyouhouse.databinding.FragmentCommunityBinding
import com.likefirst.meyouhouse.ui.BaseFragment
import com.likefirst.meyouhouse.util.RetrofitInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CommunityFragment : BaseFragment<FragmentCommunityBinding>(FragmentCommunityBinding::inflate) {

    //Dummy Article Data
    lateinit var retrofit: Retrofit
    lateinit var retrofitService : RetrofitInterface
    private lateinit var articleAdapter : ArticleRVAdapter
    val Articles = mutableListOf<Article>()

    override fun initAfterBinding() {

        initFloatButton()
        initRetroFit()
        // TODO 리사이클러뷰 만들기
        // TODO 게시글 눌렀을때 상세정보 뿌려주기
        initArticleRecyclerView()
        getArticles()
    }

    private fun getArticles(){
        retrofitService.getArticles()
            .enqueue(object :Callback<Articles>{
                override fun onResponse(call: Call<Articles>, response: Response<Articles>) {
                    Log.d(TAG,"netwokring good")
                    if(response.isSuccessful.not()) return
                    response.body()?.let {
                        articleAdapter.setData(it.items)
                    }
                }
                override fun onFailure(call: Call<Articles>, t: Throwable) {
                    Log.d(TAG,t.message.toString())
                    showToast("데이터 가져오기 실패!")
                }
            })
    }


    private fun initFloatButton() {
        binding.addArticleFloatingButton.setOnClickListener {
            context?.let{
                val intent = Intent(it,ArticlePostingActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun initArticleRecyclerView() {
        articleAdapter = ArticleRVAdapter(Articles, viewOnClickListener = { postid,date ->
            val postId = postid
            val date = date
            context?.let{
                val intent = Intent(it,ArticleDetailActivity::class.java)
                intent.putExtra(POST_ID,postId)
                intent.putExtra(DATE,date)
                startActivity(intent)
            }
        })
        binding.communityArticleRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.communityArticleRecyclerView.adapter = articleAdapter
    }

    private fun initRetroFit() {
        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofitService = retrofit.create(RetrofitInterface::class.java)
    }



    companion object {
        const val TAG = "CommunityFg"
        const val BASE_URL = "http://52.78.12.56:8080"
        const val POST_ID = "POST_ID"
        const val DATE = "DATE"
    }
}