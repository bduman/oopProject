package ACM;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.IndexOptions;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
// ArticleDaoImp
public class Mongo implements ArticleDao {

    private MongoCollection<Document> collection;
    private MongoClient mongo;

    public Mongo() {
        ServerAddress server = new ServerAddress("localhost", 27017);
        MongoClientOptions.Builder builder = MongoClientOptions.builder();
        MongoClientOptions options = builder.build();

        this.mongo = new MongoClient(server, options);
        MongoDatabase database = mongo.getDatabase("oopDatabase");

        this.collection = database.getCollection("article");
        this.collection.createIndex(new Document("id", 1), new IndexOptions().unique(true));
    }

    public List<Article> getAllArticles() {
        List<Article> allArticles = new ArrayList<Article>();
        for (Document document : this.collection.find()) {
            allArticles.add(this.castFromDocument(document));
        }
        return allArticles;
    }

    private Article castFromDocument(Document document) {
        Article article = new Article(document.getInteger("id"),
                document.getString("title"),
                document.getString("authors"),
                document.getString("venue"),
                document.getInteger("year"));
        article.setContent(document.getString("content"));

        return article;
    }

    private Document castFromArticle(Article article) {
        Document document = new Document("id", article.get("id"));
        document.put("title", article.get("title"));
        document.put("authors", article.get("authors"));
        document.put("venue", article.get("venue"));
        document.put("year", article.get("year"));
        document.put("content", article.get("content"));
        return document;
    }

    public Article getArticle(int id) {
        MongoCursor<Document> cursor = this.collection.find(new Document().append("id", id)).iterator();
        Article rArticle = null;
        try {
            if (cursor.hasNext()) {
                rArticle = this.castFromDocument(cursor.next());
            }
        } finally {
            cursor.close();
        }
        return rArticle;
    }

    public List<Article> getArticleByYear(int year) {
        List<Article> articles = new ArrayList<Article>();
        MongoCursor<Document> cursor = this.collection.find(new Document().append("year", year)).iterator();
        Article rArticle = null;
        try {
            while (cursor.hasNext()) {
                rArticle = this.castFromDocument(cursor.next());
                articles.add(rArticle);
            }
        } finally {
            cursor.close();
        }
        return articles;
    }

    public void updateArticle(Article article) {
        this.collection.updateOne(new Document().append("id", article.get("id")), new Document("$set", article));
    }

    public void deleteArticle(Article article) {
        this.collection.deleteOne(new Document().append("id", article.getId()));
    }

    public void insertArticle(Article article) {
        collection.insertOne(castFromArticle(article));
    }

    public void closeConnection() {
        this.mongo.close();
    }
}
