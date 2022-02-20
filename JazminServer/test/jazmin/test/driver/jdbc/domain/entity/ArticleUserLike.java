package jazmin.test.driver.jdbc.domain.entity;

import jazmin.driver.jdbc.smartjdbc.annotations.Entity;
import jazmin.driver.jdbc.smartjdbc.annotations.ForeignKey;
/**
 * 用户喜爱文章
 * @author skydu
 *
 */
@Entity(tableName = "t_article_user_like")
public class ArticleUserLike extends BaseEntity{
	//
	@ForeignKey(entityClass=Article.class)
	private Integer articleId;
	
	@ForeignKey(entityClass=User.class)
	private Integer userId;

	public Integer getArticleId() {
		return articleId;
	}

	public void setArticleId(Integer articleId) {
		this.articleId = articleId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	//
	
}
