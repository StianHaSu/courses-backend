package my.groupId

import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepository
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class CourseRepository : PanacheRepository<Course> {

    fun findAllCoursesByUniversity(university: String): List<Course> {
        return list("university like ?1", ("%$university%"))
    }
}