package my.groupId

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity
class Course {
    @Id
    @Column(name = "course_code")
    lateinit var courseCode: String

    @Column(name = "course_name")
    lateinit var courseName: String

    @Column(name = "course_description")
    lateinit var courseDescription: String

    @Column(name = "difficulty_rating")
    var difficulty: Float? = null

    @Column(name = "useful_rating")
    var useful: Float? = null

    @Column(name = "presentation_rating")
    var presentation: Float? = null

    lateinit var university: String
}