package com.samrak.model

/**
 * Created by sametoztoprak on 31.12.2017.
 */

class EntityMaterial {
    var materialId: Long? = null
    var materialName: String? = null
    var materialSurname: String? = null
    var materialData: String? = null

    constructor() {}

    constructor(materialId: Long, materialName: String?, materialSurname: String?, materialData: String?) {
        this.materialId = materialId
        this.materialName = materialName
        this.materialSurname = materialSurname
        this.materialData = materialData
    }
}
