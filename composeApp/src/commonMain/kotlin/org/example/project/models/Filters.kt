package com.ytubebuddy.ctefilters.models

import kotlinx.serialization.Serializable

@Serializable
data class Filters(
    var filters: FiltersX
)

@Serializable
data class Age(
    var isSelected: Boolean,
    var title: String
)

@Serializable
data class BestMatch(
    var isSelected: Boolean,
    var title: String
)

@Serializable
data class City(
    var isSelected: Boolean,
    var title: String
)

@Serializable
data class FiltersX(
    var age: List<Age>,
    var bestMatch: List<BestMatch>,
    var city: List<City>,
    var fuel: List<Fuel>,
    var mainList: List<Main>,
    var makesModels: List<MakesModel>,
    var owners: List<Owner>
)

@Serializable
data class Fuel(
    var isSelected: Boolean,
    var title: String
)

@Serializable
data class MakesModel(
    var isSelected: Boolean,
    var models: List<Model>,
    var title: String
)

@Serializable
data class Main(
    var isSelected: Boolean,
    var selectedCount: Int,
    var title: String
)

@Serializable
data class Owner(
    var isSelected: Boolean,
    var title: String
)

@Serializable
data class Variant(
    var isSelected: Boolean,
    var title: String
)

@Serializable
data class Model(
    var isSelected: Boolean,
    var title: String,
    var variant: List<Variant>
)