package traffic.board.article.service.response;

import lombok.Getter;
import lombok.ToString;
import traffic.board.article.entity.Article;

import java.time.LocalDateTime;

@ToString
@Getter
public class ArticleResponse {
    private Long id;
    private String title;
    private String content;
    private Long boardId;
    private Long writerId;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public static ArticleResponse from(Article article) {
        ArticleResponse response = new ArticleResponse();
        response.id = article.getId();
        response.title = article.getTitle();
        response.content = article.getContent();
        response.boardId = article.getBoardId();
        response.writerId = article.getWriterId();
        response.createdAt = article.getCreatedAt();
        response.modifiedAt = article.getModifiedAt();

        return response;
    }
}
