package duponchel.nicolas.technicaltest.model

import java.io.Serializable

data class Employee(
    val first_name: String,
    val name: String,
    val email_adress: String,
    val thumb_url: String,
    val job_title: String,
    val photo_url: String
) : Serializable
