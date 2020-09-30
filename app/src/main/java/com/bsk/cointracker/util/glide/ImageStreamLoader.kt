package com.bsk.cointracker.util.glide

import com.bumptech.glide.load.Options
import com.bumptech.glide.load.model.ModelLoader
import com.bumptech.glide.load.model.ModelLoaderFactory
import com.bumptech.glide.load.model.MultiModelLoaderFactory
import com.bumptech.glide.signature.ObjectKey
import okhttp3.OkHttpClient
import java.io.InputStream

class ImageStreamLoader(private val client: OkHttpClient) : ModelLoader<String, InputStream> {

    class Factory(private val client: OkHttpClient) :
        ModelLoaderFactory<String, InputStream> {
        override fun build(multiFactory: MultiModelLoaderFactory): ModelLoader<String, InputStream> {
            return ImageStreamLoader(client)
        }

        override fun teardown() {
        }
    }

    override fun buildLoadData(displayImage: String, width: Int, height: Int, options: Options)
            : ModelLoader.LoadData<InputStream>? {
        return ModelLoader.LoadData(
            ObjectKey(displayImage),
            ImageStreamFetcher(
                client,
                displayImage
            )
        )
    }

    override fun handles(displayImage: String): Boolean {
        return true
    }
}