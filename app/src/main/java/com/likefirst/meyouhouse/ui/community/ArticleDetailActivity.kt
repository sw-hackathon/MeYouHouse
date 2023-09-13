package com.likefirst.meyouhouse.ui.community

import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.likefirst.meyouhouse.data.dto.community.*
import com.likefirst.meyouhouse.databinding.ActivityArticleDetailBinding
import com.likefirst.meyouhouse.ui.BaseActivity
import com.likefirst.meyouhouse.util.RetrofitInterface
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*

class ArticleDetailActivity :
    BaseActivity<ActivityArticleDetailBinding>(ActivityArticleDetailBinding::inflate) {

    val comments = mutableListOf<Comment>()
    val images = mutableListOf<String>()

    lateinit var retrofit: Retrofit
    lateinit var retrofitService : RetrofitInterface
    private lateinit var commentAdapter: CommentRVAdapter
    private lateinit var detailImageAdapter: DetailImageRVAdapter
    private var postId = 0
    private lateinit var date : String

    override fun initAfterBinding() {
        postId = intent.getIntExtra(CommunityFragment.POST_ID,0)
        date = intent.getStringExtra(CommunityFragment.DATE).toString()

        initCommentRV()
        initDetailImageRV()
        initRetrofit()
        getDetailArticle()
        initCommentSubmitButton()
    }

    private fun initDetailImageRV() {
        detailImageAdapter = DetailImageRVAdapter(images)
        binding.detailImageRecyclerView.layoutManager = LinearLayoutManager(this).apply {
            orientation = LinearLayoutManager.HORIZONTAL
        }
        binding.detailImageRecyclerView.adapter = detailImageAdapter
    }

    private fun initBodyAndDateTextView(content : String, date : String) {
        binding.detailBodyTextView.text = content
        binding.detailDate.text = date
    }

    private fun initCommentRV() {
        commentAdapter = CommentRVAdapter(comments)
        binding.commentRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.commentRecyclerView.adapter = commentAdapter
    }


    private fun initRetrofit() {
        retrofit = Retrofit.Builder().baseUrl(CommunityFragment.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

         retrofitService = retrofit.create(RetrofitInterface::class.java)
    }

    private fun getDetailArticle() {
        retrofitService.getArticlesDetail(postId.toString())
            .enqueue(object : Callback<DetailArticle>{
                override fun onResponse(
                    call: Call<DetailArticle>,
                    response: Response<DetailArticle>,
                ) {
                    if(response.isSuccessful.not()) return
                    response.body().let {
                        if (it == null ) return
                        initBodyAndDateTextView(it.content,date)
                        detailImageAdapter.setData(it.imgs)
                        commentAdapter.setData(it.comments)
                    }
                }
                override fun onFailure(call: Call<DetailArticle>, t: Throwable) {
                    showToast("데이터 불러오기 실패")
                }
            })
    }

    private fun initCommentSubmitButton() {
        binding.commentSubmitButton.setOnClickListener {
            val content = binding.commentEditText.text.toString()
            // TODO userID를 어떻게 처리해야하는지 질문
            val userId = 1
            val comment = PostCommentResult(content,userId,postId.toInt())

            retrofitService.postComment(comment)
                .enqueue(object : Callback<ResponseBody>{
                    override fun onResponse(
                        call: Call<ResponseBody>,
                        response: Response<ResponseBody>,
                    ) {
                        if(response.isSuccessful.not()) return
                        Log.d(TAG,response.body().toString())
                        binding.commentEditText.text.clear()
                        getDetailArticle()
                    }
                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        Log.d(TAG,t.message.toString())
                        showToast("네트워크 오류")
                    }
                })
        }
    }

    companion object {
        const val TAG = "ArticleDetailAt"
    }
}