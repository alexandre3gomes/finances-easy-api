package com.finances.enums

enum class BreakpointEnum(id: Int) {

    MONTHLY(1), WEEKLY(2);

    var id = 0

    init {
        this@BreakpointEnum.id = id
    }
}
