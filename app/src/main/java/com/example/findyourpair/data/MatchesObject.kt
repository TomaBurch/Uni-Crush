package com.example.findyourpair.data

class MatchesObject(
    private var username: String,
    private var userId: String,
    private var imageUrl: String
) {
    fun getUserId():String{
        return userId
    }
    fun setUserId(userId:String){
        this.userId = userId
    }
    fun getUsername():String{
        return username
    }
    fun setUsername(username:String){
        this.username = username
    }
    fun getImageUrl():String{
        return imageUrl
    }
    fun setImageUrl(imageUrl: String){
        this.imageUrl = imageUrl
    }
}