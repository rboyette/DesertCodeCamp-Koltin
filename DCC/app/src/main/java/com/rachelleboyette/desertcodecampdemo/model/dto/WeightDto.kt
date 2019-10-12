package com.rachelleboyette.desertcodecampdemo.model.dto

import com.rachelleboyette.desertcodecampdemo.model.Weight

class WeightDto(val imperial: String,
                val metric: String) {
    companion object {
        fun fromDto(weightDto: WeightDto) : Weight {
            return Weight(weightDto.imperial, weightDto.metric)
        }
    }
}