package traffic.board.article.service;

import jakarta.transaction.Transactional;
import kuke.board.common.snowflake.Snowflake;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.stereotype.Service;
import traffic.board.article.entity.Article;
import traffic.board.article.repository.ArticleRepository;
import traffic.board.article.service.request.ArticleCreateRequest;
import traffic.board.article.service.request.ArticleUpdateRequest;
import traffic.board.article.service.response.ArticlePageResponse;
import traffic.board.article.service.response.ArticleResponse;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ArticleService {

    private final Snowflake snowflake = new Snowflake();
    private final ArticleRepository articleRepository;

    @Transactional
    public ArticleResponse create(ArticleCreateRequest request) {
        Article article = articleRepository.save(
                Article.create(snowflake.nextId(), request.getTitle(), request.getContent(), request.getBoardId(), request.getWriterId())
        );
        return ArticleResponse.from(article);
    }

    @Transactional
    public ArticleResponse update(Long articleId, ArticleUpdateRequest request) {
        Article article = articleRepository.findById(articleId).orElseThrow();
        article.update(request.getTitle(), request.getContent());
        return ArticleResponse.from(article);
    }

    public ArticleResponse read(Long articleId) {
        return ArticleResponse.from(articleRepository.findById(articleId).orElseThrow());
    }

    @Transactional
    public void delete(Long articleId) {
        articleRepository.deleteById(articleId);
    }

    public ArticlePageResponse readAll(Long boardId, Long page, Long pageSize) {
        return ArticlePageResponse.of(
                articleRepository.findAll(boardId, (page - 1) * pageSize, pageSize).stream()
                        .map(ArticleResponse::from)
                        .toList(),
                articleRepository.count(
                        boardId,
                        PageCalculator.calculatePageLimit(page, pageSize, 10L)
                )
        );
    }

    public List<ArticleResponse> readAllInfiniteScroll(Long boardId, Long pageSize, Long lastArticleId) {
        List<Article> articles = lastArticleId == null ?
                articleRepository.findAllInfiniteScroll(boardId, pageSize) :
                articleRepository.findAllInfiniteScroll(boardId, pageSize, lastArticleId);
        return articles.stream().map(ArticleResponse::from).toList();
    }


}
