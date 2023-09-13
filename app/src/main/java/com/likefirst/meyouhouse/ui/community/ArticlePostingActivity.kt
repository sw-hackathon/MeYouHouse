package com.likefirst.meyouhouse.ui.community

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.core.net.toUri
import androidx.recyclerview.widget.LinearLayoutManager
import com.likefirst.meyouhouse.data.dto.community.SelectedImage
import com.likefirst.meyouhouse.databinding.ActivityArticlePostingBinding
import com.likefirst.meyouhouse.ui.BaseActivity
import com.likefirst.meyouhouse.util.MultiPartResolver
import com.likefirst.meyouhouse.util.RetrofitInterface
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File


class ArticlePostingActivity :
    BaseActivity<ActivityArticlePostingBinding>(ActivityArticlePostingBinding::inflate) {

    lateinit var retrofit: Retrofit
    lateinit var retrofitService : RetrofitInterface

    override fun initAfterBinding() {
        initSelectedImageRV()
        initPhotoButton()
        initRetroFit()
        initSubmitButton()
        initCancelButton()
    }


    private val selectedImageList = mutableListOf<SelectedImage>()
    private lateinit var selectedImageAdapter : SelectedImageRVAdapter

    private fun initPhotoButton() {
        binding.selectPhotoButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
                type = "image/*"
            }
            startActivityForResult(intent, READ_REQUEST_CODE)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == READ_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                //선택된 이미지 리스트 초기화
                    selectedImageList.clear()
                if (data == null) return
                val clipData = data.getClipData()
                if (clipData == null) {
                    Log.d(TAG, "한장일때" + data.data.toString())
                    selectedImageList.add(SelectedImage("img1",data.data.toString().toUri()))
                    selectedImageAdapter.setData(selectedImageList)
                    binding.selectedImageCountTextView.text = "${1}/5"
                } else{
                    if (clipData.itemCount > 5) {
                        showToast("사진은 5장까지만 등록가능해요")
                    } else {
                        Log.d(TAG, clipData.itemCount.toString())
                        for (i in 0 until clipData.itemCount) {
                            val imageSingleUri = clipData.getItemAt(i).uri
                            selectedImageList.add(SelectedImage("img${i+1}",imageSingleUri))
                            Log.d(TAG, "${i}장일때" + imageSingleUri.toString())
                        }
                        selectedImageAdapter.setData(selectedImageList)
                        binding.selectedImageCountTextView.text = "${clipData.itemCount}/5"
                    }
                }
            }
        }
    }

    private fun initSelectedImageRV() {
        val initList = mutableListOf<SelectedImage>()
        selectedImageAdapter = SelectedImageRVAdapter(initList)
        binding.selectedPhotoRecyclerView.layoutManager = LinearLayoutManager(this).apply {
            orientation = LinearLayoutManager.HORIZONTAL
        }
        binding.selectedPhotoRecyclerView.adapter = selectedImageAdapter
    }

    private fun initCancelButton() {
        binding.cancelTextButton.setOnClickListener {
            selectedImageList.clear()
            finish()
        }
    }

    private fun initRetroFit() {
        retrofit = Retrofit.Builder()
            .baseUrl(CommunityFragment.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofitService = retrofit.create(RetrofitInterface::class.java)
    }

    private fun initSubmitButton() {
        binding.submitTextButton.setOnClickListener {
            showToast("등록")
            val userId = 1
            val homeId = 1
            val content = binding.bodyEditText.text.toString()
            val files = arrayListOf<MultipartBody.Part>()
            selectedImageList.forEachIndexed { index, selectedImage ->
                val file = File(selectedImage.uri.path)

            }
            val fUserId = FormDataUtil.getBody("userId",userId)
            val fHomeId = FormDataUtil.getBody("homeId",userId)
            val fContent = FormDataUtil.getBody("content",userId)
        }
    }

    //핸드폰 갤러리에 있는 사진의 uri 를 통해 경로를 얻는 것.
    @SuppressLint("Range")
    fun getPathFromUri(uri: Uri): String {
        val cursor = contentResolver.query(uri, null, null, null, null)
        cursor!!.moveToNext()
        val path = cursor.getString(cursor!!.getColumnIndex("_data"))
        cursor.close()

        return path
    }

    override fun onDestroy() {
        super.onDestroy()
        selectedImageList.clear()
        Log.d(TAG,"onDestoryed! list be cleard")
    }


    companion object {
        const val READ_REQUEST_CODE = 1000
        const val TAG = "ArticlePostAv"
    }
}

object FormDataUtil {

    fun getBody(key: String, value: Any): MultipartBody.Part {
        return MultipartBody.Part.createFormData(key, value.toString())
    }

    fun getImageBody(key: String, file: File): MultipartBody.Part {
        return MultipartBody.Part.createFormData(
            name = key,
            filename = file.name,
            body = file.asRequestBody("image/*".toMediaType())
        )
    }

    fun getVideoBody(key: String, file: File): MultipartBody.Part {
        return MultipartBody.Part.createFormData(
            name = key,
            filename = file.name,
            body = file.asRequestBody("video/*".toMediaType())
        )
    }
}