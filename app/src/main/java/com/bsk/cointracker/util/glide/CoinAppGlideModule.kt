package com.bsk.cointracker.util.glide

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.MemoryCategory
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestOptions
import dagger.hilt.EntryPoint
import dagger.hilt.EntryPoints
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import java.io.InputStream

@GlideModule
class CoinAppGlideModule : AppGlideModule() {

    @EntryPoint
    @InstallIn(ApplicationComponent::class)
    interface GlideModuleInterface {
        fun getOkHttpClient(): OkHttpClient

        fun getPerformanceChecker(): PerformanceChecker
    }

    override fun registerComponents(
        context: Context, glide: Glide, registry: Registry
    ) {
        super.registerComponents(context, glide, registry)

        if (getPerformanceChecker(context).isHighPerformingDevice) {
            glide.setMemoryCategory(MemoryCategory.NORMAL)
        } else {
            glide.setMemoryCategory(MemoryCategory.LOW)
        }


        registry.append(
            String::class.java,
            InputStream::class.java,
            ImageStreamLoader.Factory(getClient(context))
        )
    }

    override fun applyOptions(
        context: Context, builder: GlideBuilder
    ) {
        if (getPerformanceChecker(context).isHighPerformingDevice) {
            builder.setDefaultRequestOptions(
                RequestOptions()
                    .format(DecodeFormat.PREFER_ARGB_8888)
            )
        } else {
            builder.setDefaultRequestOptions(
                RequestOptions()
                    .format(DecodeFormat.PREFER_RGB_565)
            )
        }
    }

    override fun isManifestParsingEnabled(): Boolean {
        return false
    }

    private fun getClient(context: Context) =
        EntryPoints.get(context.applicationContext, GlideModuleInterface::class.java)
            .getOkHttpClient()

    private fun getPerformanceChecker(context: Context) =
        EntryPoints.get(context.applicationContext, GlideModuleInterface::class.java)
            .getPerformanceChecker()
}