package com.rachelleboyette.desertcodecampdemo.model.dto

import com.google.gson.annotations.SerializedName
import com.rachelleboyette.desertcodecampdemo.model.Cat

class CatDto(
    val weight: WeightDto,
    val id: String = "",
    val name: String = "",
    val temperament: String = "",
    val origin: String = "",
    val description: String = "",
    @SerializedName("life_span")val lifeSpan: String = "",
    val adaptability: Int = 0,
    @SerializedName("alt_names") val alternativeNames: String? = "",
    @SerializedName("affection_level") val affectionLevel: Int = 0,
    @SerializedName("child_friendly") val childFriendly: Int = 0,
    @SerializedName("dog_friendly") val dogFriendly: Int = 0,
    @SerializedName("energy_level") val energyLevel: Int = 0,
    val grooming: Int = 0,
    @SerializedName("health_issues") val healthIssues: Int = 0,
    val intelligence: Int = 0,
    @SerializedName("shedding_level") val sheddingLevel: Int = 0,
    @SerializedName("social_needs") val socialNeeds: Int = 0,
    @SerializedName("vocalisation") val vocalization: Int = 0,
    @SerializedName("wikipedia_url") val wikipediaUrl: String?
) {

    companion object {
        fun fromDto(catDtos: List<CatDto>?): List<Cat> {
            val cats = mutableListOf<Cat>()

            if (catDtos != null && catDtos.isNotEmpty()) {
                for (cat in catDtos) {
                    cats.add(
                        Cat(
                            WeightDto.fromDto(cat.weight),
                            cat.id,
                            cat.name,
                            cat.temperament,
                            cat.origin,
                            cat.description,
                            cat.lifeSpan,
                            cat.adaptability,
                            cat.alternativeNames,
                            cat.affectionLevel,
                            cat.childFriendly,
                            cat.dogFriendly,
                            cat.energyLevel,
                            cat.grooming,
                            cat.healthIssues,
                            cat.intelligence,
                            cat.sheddingLevel,
                            cat.socialNeeds,
                            cat.vocalization,
                            cat.wikipediaUrl
                        )
                    )
                }
            }
            return cats
        }

        fun fromDto(catDto: CatDto?): Cat {
            if (catDto != null) {
                return Cat(WeightDto.fromDto(catDto.weight),
                    catDto.id,
                    catDto.name,
                    catDto.temperament,
                    catDto.origin,
                    catDto.description,
                    catDto.lifeSpan,
                    catDto.adaptability,
                    catDto.alternativeNames,
                    catDto.affectionLevel,
                    catDto.childFriendly,
                    catDto.dogFriendly,
                    catDto.energyLevel,
                    catDto.grooming,
                    catDto.healthIssues,
                    catDto.intelligence,
                    catDto.sheddingLevel,
                    catDto.socialNeeds,
                    catDto.vocalization,
                    catDto.wikipediaUrl)
            }
            return Cat()
        }
    }
}