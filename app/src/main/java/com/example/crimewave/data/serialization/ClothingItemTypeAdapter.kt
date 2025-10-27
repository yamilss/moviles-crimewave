package com.example.crimewave.data.serialization

import com.example.crimewave.data.model.ClothingItem
import com.example.crimewave.data.model.ProductType
import com.google.gson.*
import java.lang.reflect.Type

class ClothingItemTypeAdapter : JsonDeserializer<ClothingItem> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): ClothingItem {
        val jsonObject = json?.asJsonObject ?: throw JsonParseException("Invalid JSON")
        
        
        val id = jsonObject.get("id")?.asString ?: ""
        val name = jsonObject.get("name")?.asString ?: ""
        val description = jsonObject.get("description")?.asString ?: ""
        val price = jsonObject.get("price")?.asDouble ?: 0.0
        val imageUrl = jsonObject.get("imageUrl")?.asString ?: ""
        
        
        val categoryString = jsonObject.get("category")?.asString ?: "POLERAS"
        val category = try {
            ProductType.valueOf(categoryString)
        } catch (e: IllegalArgumentException) {
            ProductType.POLERAS
        }
        
        
        val isNew = jsonObject.get("isNew")?.asBoolean ?: false
        val isFeatured = jsonObject.get("isFeatured")?.asBoolean ?: false
        val specialOffer = jsonObject.get("specialOffer")?.asString
        val stock = jsonObject.get("stock")?.asInt ?: 10
        val reviewCount = jsonObject.get("reviewCount")?.asInt ?: 0
        
        
        val sizes = try {
            val sizesArray = jsonObject.get("sizes")?.asJsonArray
            sizesArray?.map { it.asString } ?: listOf("S", "M", "L", "XL")
        } catch (e: Exception) {
            listOf("S", "M", "L", "XL")
        }
        
        
        val imageUrls = try {
            val imageUrlsArray = jsonObject.get("imageUrls")?.asJsonArray
            if (imageUrlsArray != null) {
                imageUrlsArray.map { it.asString }
            } else {
                
                listOf(imageUrl, imageUrl + "_2", imageUrl + "_3")
            }
        } catch (e: Exception) {
            
            listOf(imageUrl, imageUrl + "_2", imageUrl + "_3")
        }
        
        return ClothingItem(
            id = id,
            name = name,
            description = description,
            price = price,
            imageUrl = imageUrl,
            imageUrls = imageUrls,
            category = category,
            isNew = isNew,
            isFeatured = isFeatured,
            specialOffer = specialOffer,
            sizes = sizes,
            stock = stock,
            reviewCount = reviewCount
        )
    }
}