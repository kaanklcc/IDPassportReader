package com.example.idpassportreader.model

import java.security.PublicKey

class EDocument {
    var docType: DocType? = null
    var personDetails: PersonDetails? = null
    var additionalPersonDetails: AdditionalPersonDetails? = null
    var docPublicKey: PublicKey? = null
}