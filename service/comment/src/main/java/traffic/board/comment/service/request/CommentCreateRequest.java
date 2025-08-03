package traffic.board.comment.service.request;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class CommentCreateRequest {

    private Long articleId;

    private String content;

    private Long parentCommentId;

    private Long writerId;

}
