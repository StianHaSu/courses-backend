package my.groupId

import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepository
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional

@ApplicationScoped
class CommentRepository : PanacheRepository<Comments> {

    fun getCommentsFromCourse(courseCode: String) : List<Comments> {
        return list("course like ?1", (courseCode))
    }

    @Transactional
    fun createNewComment(commenter: String, content: String, course: String) {
        val newComment = Comments()
        newComment.commenter = commenter
        newComment.content = content
        newComment.course = course
        persist(newComment)
    }
}