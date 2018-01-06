package com.samrak.model

/**
 * Created by sametoztoprak on 4.01.2018.
 */
class EntityTest {
    var testId: Long? = null
    var testName: String? = null
    var testSurname: String? = null
    var testData: String? = null

    constructor() {}

    constructor(testId: Long, testName: String?, testSurname: String?, testData: String?) {
        this.testId = testId
        this.testName = testName
        this.testSurname = testSurname
        this.testData = testData
    }
}