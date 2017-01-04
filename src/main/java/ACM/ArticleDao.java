package ACM;

import java.util.List;

interface ArticleDao {
    public List<Article> getAllArticles();
    public Article getArticle(int id);
    public void updateArticle(Article article);
    public void deleteArticle(Article article);
}
