package com.bsk.cointracker.auth.model

import com.google.firebase.firestore.Exclude

class User() {
    constructor(uid: String, name: String?, email: String?) : this() {
        this.uid = uid
        this.name = name
        this.email = email
    }

    var uid: String = ""
    var name: String? = null
    var email: String? = null

    @Exclude
    var isAuthenticated = false

    @Exclude
    var isNew = false

    @Exclude
    var isCreated = false
}
