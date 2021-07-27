package com.app.yourpaytask.responsemodel

import java.io.Serializable

data class UserModel(
    val data: ArrayList<Data>,
    val meta: Meta
):Serializable