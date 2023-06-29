package my.groupId

import com.fasterxml.jackson.databind.BeanDescription
import jakarta.enterprise.context.ApplicationScoped
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import org.jboss.resteasy.reactive.RestPath
import org.jboss.resteasy.reactive.RestQuery

@Path("/")
@ApplicationScoped
class ApiPresentation (
        private val data: DatabaseQueries,
        private val commentRepo: CommentRepository
){

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("{course}")
    fun getCourses(
        @RestPath course: String
    ): String {
        return data.getCourseData(course)
    }

    @POST
    @Path("{course}/comments")
    fun postNewComment(
            @PathParam("course") course: String,
            commentBody: CommentBody
    ){
        commentRepo.createNewComment(commentBody.commenter, commentBody.content, course)
    }

    @POST
    @Path("courses")
    fun createNewCourse(
        courseInfo: CourseBody
    ){
        data.insertNewCourse(courseInfo.courseCode, courseInfo.courseName,
                courseInfo.description, courseInfo.university)
    }

    @GET
    @Path("universities/{uni}")
    fun getAllUniversityCourses(@RestPath uni: String): String {
        return data.getAllCoursesForUniversity(uni)
    }

    @GET
    @Path("universities")
    fun getUniversityNamesByPrefix(@RestQuery uni: String) : String {
        return data.getAllUniversityNamesWithPrefix(uni.lowercase())
    }
}