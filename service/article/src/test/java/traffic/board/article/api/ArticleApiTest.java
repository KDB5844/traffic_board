package traffic.board.article.api;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;
import traffic.board.article.service.response.ArticlePageResponse;
import traffic.board.article.service.response.ArticleResponse;

import java.util.List;

public class ArticleApiTest {

    RestClient restClient = RestClient.create("http://localhost:9000");

    @Test
    void createTest() {
        ArticleResponse response = create(new ArticleCreateRequest("hi", "my content", 1L, 1L));
        System.out.println("response = " + response);
    }

    ArticleResponse create(ArticleCreateRequest request) {
        return restClient.post()
                .uri("/v1/articles")
                .body(request)
                .retrieve()
                .body(ArticleResponse.class);

    }

    @Test
    void readTest() {
        ArticleResponse articleResponse = read(207907598575337472L);
        System.out.println("articleResponse = " + articleResponse);
    }

    ArticleResponse read(Long articleId) {
        return restClient.get()
                .uri("v1/articles/{articleId}", articleId)
                .retrieve()
                .body(ArticleResponse.class);
    }

    @Test
    void updateTest() {
        ArticleResponse response = update(207907598575337472L, new ArticleUpdateRequest("hi2", "my content2"));
        System.out.println("response = " + response);
    }

    ArticleResponse update(Long articleId, ArticleUpdateRequest request) {
        return restClient.put()
                .uri("v1/articles/{articleId}", articleId)
                .body(request)
                .retrieve()
                .body(ArticleResponse.class);
    }

    @Test
    void deleteTest() {
        delete(207907598575337472L);
    }

    void delete(Long articleId) {
        restClient.delete()
                .uri("v1/articles/{articleId}", articleId)
                .retrieve();
    }

    @Test
    void readAllTest() {
        ArticlePageResponse response = restClient.get()
                .uri("/v1/articles?boardId=1&pageSize=30&page=50000")
                .retrieve()
                .body(ArticlePageResponse.class);

        System.out.println("response.getArticleCount() = " + response.getArticleCount());
        for (ArticleResponse article : response.getArticles()) {
            System.out.println("article.getId() = " + article.getId());
        }
    }

    @Test
    void readAllInfiniteTest() {
        List<ArticleResponse> articles = restClient.get()
                .uri("/v1/articles/infinite-scroll?boardId=1&pageSize=5")
                .retrieve()
                .body(new ParameterizedTypeReference<List<ArticleResponse>>() {
                });

        System.out.println("firstPage");
        for (ArticleResponse article : articles) {
            System.out.println("article.getId() = " + article.getId());
        }

        List<ArticleResponse> articles2 = restClient.get()
                .uri("/v1/articles/infinite-scroll?boardId=1&pageSize=5&lastArticleId=" + articles.getLast().getId())
                .retrieve()
                .body(new ParameterizedTypeReference<List<ArticleResponse>>() {
                });

        System.out.println("secondPage");
        for (ArticleResponse response : articles2) {
            System.out.println("response.getId() = " + response.getId());
        }

    }

    @Getter
    @AllArgsConstructor
    static class ArticleCreateRequest {
        private String title;
        private String content;
        private Long writerId;
        private Long boardId;
    }

    @Getter
    @AllArgsConstructor
    static class ArticleUpdateRequest {
        private String title;
        private String content;
    }


}
