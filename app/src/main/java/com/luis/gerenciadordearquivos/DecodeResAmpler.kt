package com.luis.gerenciadordearquivos

import android.graphics.ImageDecoder
import android.os.CancellationSignal
import android.util.Size
import kotlin.math.min

class DecodeResAmpler(val size : Size, val signal : CancellationSignal?) :
    ImageDecoder.OnHeaderDecodedListener {

    override fun onHeaderDecoded(
        decoder: ImageDecoder,
        info: ImageDecoder.ImageInfo,
        source: ImageDecoder.Source
    ) {
        val widthSample = info.size.width / size.width
        val heightSample = info.size.height / size.height
        val sample = min(widthSample, heightSample)
        if (sample > 1) {
            decoder.setTargetSampleSize(sample)
        }
    }
}