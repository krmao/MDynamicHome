package org.smartrobot.api.exception

class DefaultRetrofitServerException(val code: Int, val msg: String) : RuntimeException()
