package my.groupId

import com.fasterxml.jackson.databind.BeanDescription
import com.fasterxml.jackson.databind.ObjectMapper
import io.agroal.api.AgroalDataSource
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import java.sql.SQLException

@ApplicationScoped
class DatabaseQueries (
        private val courseRepository: CourseRepository
){

    @Inject
    lateinit var db: AgroalDataSource

    private val jsonBuilder = ObjectMapper()

    fun getAllCoursesForUniversity(university: String): String {
        return jsonBuilder.writeValueAsString(courseRepository.findAllCoursesByUniversity(university))
    }

    fun getCourseData(courseCode: String): String {
        try {
            val query = db.connection.prepareCall(
            "SELECT * FROM course WHERE course_code='$courseCode';"
            ).executeQuery()

            val res = query
            res.next()

            return jsonBuilder.writeValueAsString(
                mapOf(
                    "course_code" to res.getArray("course_code").toString(),
                    "course_name" to res.getArray("course_name").toString(),
                    "course_description" to res.getArray("course_description").toString(),
                    "university" to res.getArray("university").toString(),
                    "useful_rating" to res.getArray("useful_rating").toString(),
                    "difficulty_rating" to res.getArray( "difficulty_rating").toString(),
                    "presentation_rating" to res.getArray("presentation_rating").toString()
                )
            )


        } catch (sqlExe: SQLException) {
            println(sqlExe.message)
            return "Data for $courseCode does not exist"
        }
    }

    fun insertNewCourse(
        courseCode: String,
        courseName: String,
        description: String,
        university: String
    ) {
        val res = db.connection.prepareStatement(
                "INSERT INTO course (course_code, course_name, course_description, university)" +
                    "VALUES ('$courseCode', '$courseName', '$description', '$university')"
        )

        res.execute()
    }

    fun getAllUniversityNamesWithPrefix(prefix: String): String {
        try {
            val query = db.connection.prepareStatement(
                    "SELECT uni_name FROM university WHERE uni_name LIKE '%$prefix%';"
            ).executeQuery()

            val res = query
            val list = mutableListOf<Map<String, String>>()
            while (res.next()) {
                list.add(
                        mapOf(
                                "university_name" to res.getArray("uni_name").toString()
                        )
                )
            }

            return jsonBuilder.writeValueAsString(list)

        } catch (sqlExe: SQLException) {
            return "No matching universities."
        }
    }
}