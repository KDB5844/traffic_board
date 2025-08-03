package traffic.board.comment.service.response;

import lombok.Getter;
import lombok.ToString;
import traffic.board.comment.entity.Comment;
import traffic.board.comment.service.request.CommentCreateRequest;

import java.time.LocalDateTime;

@ToString
@Getter
public class CommentResponse {

    private Long commentId;
    private String content;
    private Long parentCommentId;
    private Long articleId; // shardKey
    private Long writerId;
    private Boolean deleted;
    private LocalDateTime createdAt;

    public static CommentResponse from(Comment comment) {
        CommentResponse response = new CommentResponse();
        response.commentId = comment.getCommentId();
        response.content = comment.getContent();
        response.parentCommentId = comment.getParentCommentId();
        response.articleId = comment.getArticleId();
        response.writerId = comment.getWriterId();
        response.deleted = comment.getDeleted();
        response.createdAt = comment.getCreatedAt();

        return response;
    }

}
