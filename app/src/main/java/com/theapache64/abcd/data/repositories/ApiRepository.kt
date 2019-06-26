package com.theapache64.abcd.data.repositories

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jaredrummler.android.device.DeviceName
import com.squareup.moshi.Moshi
import com.theapache64.abcd.data.remote.ApiInterface
import com.theapache64.abcd.data.remote.receiveimage.ReceiveImageRequest
import com.theapache64.abcd.data.remote.receiveimage.ReceiveImageResponse
import com.theapache64.abcd.data.remote.submitmap.SubmitMapRequest
import com.theapache64.abcd.data.remote.updaterandom.UpdateRandomRequest
import com.theapache64.twinkill.network.utils.Resource
import okhttp3.*
import java.io.IOException
import javax.inject.Inject

class ApiRepository @Inject constructor(
    private val moshi: Moshi,
    private val apiInterface: ApiInterface,
    private val okHttpClient: OkHttpClient
) {

    /**
     * Submits the map to gaugan server
     */
    fun submitMap(submitMapRequest: SubmitMapRequest) = apiInterface.submitMap(
        DeviceName.getDeviceName(),
        "data:image/png;base64,${submitMapRequest.imageBase64}",
        submitMapRequest.name
    )

    /**
     * To receive image from gaugan
     */
    fun receiveImage(request: ReceiveImageRequest): LiveData<Resource<Bitmap>> {

        val ld = MutableLiveData<Resource<Bitmap>>()

        // Building form body
        val formBody = FormBody.Builder()
            .add("name", request.mapFile.nameWithoutExtension)
            .add("style_name", request.style.apiCode)
            .add("artistic_style_name", request.artStyle.apiCode)
            .add("source", DeviceName.getDeviceName())
            .build()

        // Building server url
        val url = "http://theapache64.com/abcd/receive_image"

        // Building request
        val imageRequest = Request.Builder()
            .url(url)
            .method("POST", formBody)
            .build()

        ld.value = Resource.loading()

        // Processing request
        okHttpClient.newCall(imageRequest).enqueue(object : Callback {

            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                ld.postValue(Resource.error("Failed to generate image. \nERROR : ${e.message}"))
            }

            override fun onResponse(call: Call, response: Response) {
                try {

                    val byteStream = response.body()!!.byteStream()
                    val bitmap = BitmapFactory.decodeStream(byteStream)
                    if (bitmap != null) {
                        ld.postValue(Resource.success(bitmap))
                    } else {
                        val jsonString = response.body()!!.string()
                        val adapter = moshi.adapter(ReceiveImageResponse::class.java)
                        val resp = adapter.fromJson(jsonString)
                        ld.postValue(Resource.error("Failed to generate image, Please try again. \nERROR : ${resp?.message}"))
                    }

                } catch (e: IOException) {
                    e.printStackTrace()
                    ld.postValue(Resource.error("Failed to generate image. \nERROR : ${e.message}"))
                }
            }

        })

        return ld
    }

    /**
     * To update random
     */
    fun updateRandom(request: UpdateRandomRequest) = apiInterface.updateRandom(
        DeviceName.getDeviceName(),
        request.fileName
    )
}