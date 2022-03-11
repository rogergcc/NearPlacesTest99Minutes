package com.rogergcc.nearplacestest99minutes.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


/**
 * Created on March.
 * year 2022 .
 */


@Parcelize
data class PlaceItem(
    val id: Int = -1,
    @SerializedName("latitude")
    val latitude: Double?,
    @SerializedName("longitude")
    val longitude: Double?,
    @SerializedName("name")
    val name: String,
    @SerializedName("icon_background_color")
    val iconBackgroundColor: String,
    @SerializedName("types")
    val types: List<String>?,
    @SerializedName("vicinity")
    val vicinity: String,
    @SerializedName("photo")
    val photo: String
) : Parcelable

data class PlaceList(val results: List<PlaceItem> = listOf())

// Room
@Entity(tableName = "placeEntity")
data class PlaceEntity(
    @PrimaryKey
    val id: Int = -1,
    @ColumnInfo(name = "latitude")
    val latitude: Double? = -1.0,
    @ColumnInfo(name = "longitude")
    val longitude: Double? = -1.0,
    @ColumnInfo(name = "name")
    val name: String = "",
    @ColumnInfo(name = "iconBackgroundColor")
    val iconBackgroundColor: String = "",
    @ColumnInfo(name = "types")
    val types: String = "",
    @ColumnInfo(name = "vicinity")
    val vicinity: String = "",
    @ColumnInfo(name = "photo")
    val photo: String = "",
)

fun List<PlaceEntity>.toPlaceList(): PlaceList {
    val resultList = mutableListOf<PlaceItem>()
    this.forEach { placeEntity ->
        resultList.add(placeEntity.toPlaceItem())
    }
    return PlaceList(resultList)
}

fun PlaceEntity.toPlaceItem(): PlaceItem = PlaceItem(
    this.id,
    this.latitude,
    this.longitude,
    this.name,
    this.iconBackgroundColor,
    this.types.map { item->item.toString() },
    this.vicinity,
    this.photo

)

fun PlaceItem.toPlaceEntity(): PlaceEntity = PlaceEntity(
    this.id,
    this.latitude,
    this.longitude,
    this.name,
    this.iconBackgroundColor,
    this.types.toString(),
    this.vicinity,
    this.photo
)

