package com.rachelleboyette.desertcodecampdemo.model.dto

import com.rachelleboyette.desertcodecampdemo.model.CatImage

class CatImageDto(val url: String) {

    companion object {
        fun fromDto(dto: CatImageDto): CatImage {
            return CatImage(dto.url)
        }
    }
}