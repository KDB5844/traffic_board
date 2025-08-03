package traffic.board.comment.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClient;
import traffic.board.comment.service.response.CommentResponse;

public class CommentApiTest {
    RestClient restClient = RestClient.create("http://localhost:9001");

    @Test
    void create() {
        CommentResponse response1 = createComment(new CommentCreateRequest(1L, "my comment1", null, 1L));
        CommentResponse response2 = createComment(new CommentCreateRequest(1L, "my comment2", response1.getCommentId(), 1L));
        CommentResponse response3 = createComment(new CommentCreateRequest(1L, "my comment3", response1.getCommentId(), 1L));

        System.out.println("responseId=" + response1.getCommentId());
        System.out.println("\tresponseId=" + response2.getCommentId());
        System.out.println("\tresponseId=" + response3.getCommentId());

//        responseId=210437765741105152
//          responseId=210437766043095040
//          responseId=210437766089232384

    }

    @Test
    void read() {
        CommentResponse response = restClient.get()
                .uri("/v1/comments/{commentId}", 210437765741105152L)
                .retrieve()
                .body(CommentResponse.class);

        System.out.println("response = " + response);
    }

    @Test
    void delete() {
        //        responseId=210437765741105152
//          responseId=210437766043095040
//          responseId=210437766089232384

        restClient.delete()
                .uri("/v1/comments/{commentId}", 210437766089232384L)
                .retrieve();

    }


    CommentResponse createComment(CommentCreateRequest request) {
        System.out.println("request = " + request);
        return restClient.post()
                .uri("/v1/comments")
                .header("Content-Type", "application/json")
                .body(request)
                .retrieve()
                .body(CommentResponse.class);
    }

    @ToString
    @AllArgsConstructor
    @Getter
    public static class CommentCreateRequest {
        private Long articleId;
        private String content;
        private Long parentCommentId;
        private Long writerId;
    }


}
