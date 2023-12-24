package com.latop.coffetest.network

import android.os.Parcel
import android.os.Parcelable
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

data class LoginRequestBody(
    val login: String, val password: String
)

data class MenuItem(
    val id: Int, val name: String, val imageURL: String, val price: Int, var count: Int = 0
)


data class Location(
    val id: Int, val name: String, val point: Point
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readParcelable(Point::class.java.classLoader)!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeParcelable(point, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Location> {
        override fun createFromParcel(parcel: Parcel): Location {
            return Location(parcel)
        }

        override fun newArray(size: Int): Array<Location?> {
            return arrayOfNulls(size)
        }
    }
}

data class Point(
    val latitude: String, val longitude: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!, parcel.readString()!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(latitude)
        parcel.writeString(longitude)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Point> {
        override fun createFromParcel(parcel: Parcel): Point {
            return Point(parcel)
        }

        override fun newArray(size: Int): Array<Point?> {
            return arrayOfNulls(size)
        }
    }
}

interface ApiService {
    @POST("auth/register")
    suspend fun register(@Body requestBody: LoginRequestBody): Response<ResponseBody>

    @POST("auth/login")
    suspend fun login(@Body requestBody: LoginRequestBody): Response<ResponseBody>

    @GET("locations")
    suspend fun getLocations(@Header("Authorization") token: String): List<Location>

    @GET("/location/{id}/menu")
    suspend fun getMenu(
        @Header("Authorization") token: String, @Path("id") cafeId: Int
    ): List<MenuItem>

    companion object {
        fun create(): ApiService {
            return RetrofitClient.retrofit.create(ApiService::class.java)
        }
    }
}
