package com.samrak.model

/**
 * Created by samet on 30-May-16.
 */
class EntityPerson {

    var keyId: Long? = null
    var keyName: String? = null
    var keySurname: String? = null
    var keyData: String? = null
    var keyInt: Int? = null
    var keyFloat: Float? = null

    constructor() {}

    constructor(keyId: Long, keyName: String, keySurname: String, keyData: String, keyInt: Int, keyFloat: Float) {
        this.keyId = keyId
        this.keyName = keyName
        this.keySurname = keySurname
        this.keyData = keyData
        this.keyInt = keyInt
        this.keyFloat = keyFloat
    }
}
