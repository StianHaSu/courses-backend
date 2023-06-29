package my.groupId

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class Comments {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "comments_id_seq")
    var id: Int? = null
    lateinit var commenter: String
    lateinit var content: String
    lateinit var course: String
}