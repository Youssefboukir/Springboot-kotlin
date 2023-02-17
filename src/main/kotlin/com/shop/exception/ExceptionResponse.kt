package com.shop.exception

import java.util.*


class ExceptionResponse (
    var timestamp: Date,

    var status: Int? = null,

    var message: String? = null,
)