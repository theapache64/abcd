package com.theapache64.abcd.data.repositories

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.theapache64.abcd.data.remote.ApiInterface
import com.theapache64.abcd.data.remote.submitmap.SubmitMapRequest
import com.theapache64.abcd.models.Server
import com.theapache64.abcd.models.Style
import com.theapache64.twinkill.network.utils.Resource
import okhttp3.*
import java.io.File
import java.io.IOException
import javax.inject.Inject

class GauganRepository @Inject constructor(
    private val apiInterface: ApiInterface,
    private val okHttpClient: OkHttpClient,
    private val server: Server
) {

    /**
     * Submits the map to gaugan server
     */
    fun submitMap(submitMapRequest: SubmitMapRequest) = apiInterface.submitMap(
        "data:image/png;base64,${submitMapRequest.imageBase64}",
        submitMapRequest.name
    )

    fun receiveImage(mapFile: File, style: Style, artisticStyle: Style): LiveData<Resource<Bitmap>> {
        val ld = MutableLiveData<Resource<Bitmap>>()

        // Building form body
        val formBody = FormBody.Builder()
            .add("name", mapFile.nameWithoutExtension)
            .add("style_name", style.code)
            .add("artistic_style_name", artisticStyle.code)
            .build()

        // Building server url
        val url = "http://${server.ip}:443/gaugan_receive_image"

        // Building request
        val request = Request.Builder()
            .url(url)
            .method("POST", formBody)
            .build()

        ld.value = Resource.loading()

        // Processing request
        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                ld.value = Resource.error("Failed to generate image. \nERROR : ${e.message}")
            }

            override fun onResponse(call: Call, response: Response) {
                val byteStream = response.body()!!.byteStream()
                val bitmap = BitmapFactory.decodeStream(byteStream)

                ld.postValue(Resource.success(bitmap))
            }

        })

        return ld
    }
}