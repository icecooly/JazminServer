package jazmin.test.driver.jdbc.domain.vo;

import java.util.List;
import java.util.Map;

import jazmin.driver.jdbc.smartjdbc.annotations.Entity;
import jazmin.driver.jdbc.smartjdbc.annotations.EntityField;
import jazmin.driver.jdbc.smartjdbc.annotations.ForeignKey;
import jazmin.test.driver.jdbc.domain.entity.Article;
import jazmin.test.driver.jdbc.domain.entity.User;

/**
 * 
 * @author skydu
 *
 */
@Entity(tableName = "t_test")
public class Test {
	
	private Integer Id;
	
	@ForeignKey(entityClass = User.class)
	private Integer createUserId;
	
	@EntityField(foreignKeyFields = "createUserId")
	private User createUser;

	private List<Article> articleList;	
	
	private Map<String,List<Article>> articleMap;

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public Integer getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
	}

	public User getCreateUser() {
		return createUser;
	}

	public void setCreateUser(User createUser) {
		this.createUser = createUser;
	}

	public List<Article> getArticleList() {
		return articleList;
	}

	public void setArticleList(List<Article> articleList) {
		this.articleList = articleList;
	}

	public Map<String, List<Article>> getArticleMap() {
		return articleMap;
	}

	public void setArticleMap(Map<String, List<Article>> articleMap) {
		this.articleMap = articleMap;
	}

	//
	
}
