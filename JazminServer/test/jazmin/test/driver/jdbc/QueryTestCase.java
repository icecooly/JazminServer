package jazmin.test.driver.jdbc;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jazmin.driver.jdbc.smartjdbc.enums.ConditionType;
import jazmin.driver.jdbc.smartjdbc.enums.OrderByType;
import jazmin.driver.jdbc.smartjdbc.enums.SqlOperator;
import jazmin.driver.jdbc.smartjdbc.provider.SelectProvider;
import jazmin.driver.jdbc.smartjdbc.provider.entity.Aggregation;
import jazmin.driver.jdbc.smartjdbc.provider.entity.SqlBean;
import jazmin.driver.jdbc.smartjdbc.provider.factory.SelectProviderFactory;
import jazmin.driver.jdbc.smartjdbc.provider.where.QueryWhere;
import jazmin.driver.jdbc.smartjdbc.provider.where.Where;
import jazmin.driver.jdbc.smartjdbc.provider.where.Where.Condition;
import jazmin.driver.jdbc.smartjdbc.provider.where.Where.JsonContain;
import jazmin.driver.jdbc.smartjdbc.util.DumpUtil;
import jazmin.test.driver.jdbc.dao.BizDAO;
import jazmin.test.driver.jdbc.domain.entity.*;
import jazmin.test.driver.jdbc.domain.entity.AppTable.AppTableQuery;
import jazmin.test.driver.jdbc.domain.query.AppServerQuery;
import jazmin.test.driver.jdbc.domain.query.ArticleQuery;
import jazmin.test.driver.jdbc.domain.query.ArticleQuery.IdListOrTitle;
import jazmin.test.driver.jdbc.domain.query.UserComplexQuery;
import jazmin.test.driver.jdbc.domain.query.UserComplexQuery.NameOrUserNameOrDeptName;
import jazmin.test.driver.jdbc.domain.query.UserComplexQuery.StatusAndMobile;
import jazmin.test.driver.jdbc.domain.query.UserQuery;
import jazmin.test.driver.jdbc.domain.vo.UserSimple;

/**
 * 
 * @author skydu
 *
 */
public class QueryTestCase extends BaseTestCase{
	static {
	    System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "debug");
	}
	//
	BizDAO dao;
	//
	public QueryTestCase() {
		dao=new BizDAO();
	}
	//
	/**
	 * 
	 */
	public void testGetById() {
		for(int i=0;i<1;i++) {
			dao.getById(User.class, 1);
		}
	}
	
	public void testQuery() {
		/**查询姓名等于‘张三’的用户*/
		dao.getEntity(User.class,QueryWhere.create().eq("name", "张三"));
		
		/**查询姓名不等于‘张三’的用户*/
		dao.getEntity(User.class,QueryWhere.create().ne("name", "张三"));
		
		/**查询年龄小于25岁的用户*/
		dao.getEntity(User.class,QueryWhere.create().lt("age", 25));
		
		/**查询年龄小于等于25岁的用户*/
		dao.getEntity(User.class,QueryWhere.create().le("age", 25));
		
		/**查询年龄大于25岁的用户*/
		dao.getEntity(User.class,QueryWhere.create().gt("age", 25));
		
		/**查询年龄大于等于25岁的用户*/
		dao.getEntity(User.class,QueryWhere.create().ge("age", 25));
	}
	
	/**
	 * 
	 */
	public void testQueryLike() {
		/**查询姓名包含'张'的用户*/
		dao.getEntity(User.class,QueryWhere.create().like("name","张"));
		
		/**查询姓名是以'张'开头的用户*/
		dao.getEntity(User.class,QueryWhere.create().likeLeft("name","张"));
		
		/**查询姓名是以'张'结尾的用户*/
		dao.getEntity(User.class,QueryWhere.create().likeRight("name","张"));
	}
	//
	public void testQueryNotLike() {	
		/**查询姓名不包含'张'的用户*/
		dao.getEntity(User.class,QueryWhere.create().notLike("name","张"));
		
		/**查询姓名不是以'张'开头的用户*/
		dao.getEntity(User.class,QueryWhere.create().notLikeLeft("name","张"));
		
		/**查询姓名不是以'张'结尾的用户*/
		dao.getEntity(User.class,QueryWhere.create().notLikeRight("name","张"));
	}
	
	public void testQueryIn() {
		dao.getList(User.class,QueryWhere.create().in("status",Arrays.asList(1,2,3)));
		dao.getList(User.class,QueryWhere.create().in("status",new int[] {1,2}));
		dao.getList(User.class,QueryWhere.create().in("status",new byte[] {1,2}));
		dao.getList(User.class,QueryWhere.create().in("status",new long[] {1,2}));
		dao.getList(User.class,QueryWhere.create().in("name",Arrays.asList("张三","张三2")));
		dao.getList(User.class,QueryWhere.create().in("name",new String[] {"张三","张三2"}));
		UserQuery query=new UserQuery();
		query.setStatusInList(Arrays.asList(1,2,3));
		dao.getList(query);
	}
	
	public void testQueryNotIn() {
		dao.getList(User.class,QueryWhere.create().notin("status",Arrays.asList(1,2,3)));
		dao.getList(User.class,QueryWhere.create().notin("status",new int[] {1,2}));
		dao.getList(User.class,QueryWhere.create().notin("status",new byte[] {1,2}));
		dao.getList(User.class,QueryWhere.create().notin("status",new long[] {1,2}));
		dao.getList(User.class,QueryWhere.create().notin("name",Arrays.asList("张三","张三2")));
		dao.getList(User.class,QueryWhere.create().notin("name",new String[] {"张三","张三2"}));
	}
	
	public void testJsonContains() {
		dao.getList(User.class,QueryWhere.create().jsonContainsAny("role_id_list",Arrays.asList(1,2,3)));
		dao.getList(User.class,QueryWhere.create().jsonContainsAny("role_id_list",new int[] {1,2}));
		dao.getList(User.class,QueryWhere.create().jsonContainsAny("role_id_list",new byte[] {1,2}));
		dao.getList(User.class,QueryWhere.create().jsonContainsAny("role_id_list",new long[] {1,2}));
		//
		UserQuery query=new UserQuery();
		query.setRoleId(1);
		dao.getList(query);
		//
		query=new UserQuery();
		query.setGtAge(10);
		query.setRoleIdList(new Integer[] {1,2});
		dao.getList(query);
	}
	
	public void testJsonContainsAll() {
		dao.getList(User.class,QueryWhere.create().jsonContainsAll("role_id_list",Arrays.asList(1,2,3)));
	}
	
	public void testJsonContainsEq() {
		dao.getList(User.class,QueryWhere.create().jsonContainsEq("role_id_list",Arrays.asList(1,2,3)));
	}
	
	public void testJsonContainsNe() {
		dao.getList(User.class,QueryWhere.create().jsonContainsNe("role_id_list",Arrays.asList(1,2,3)));
	}
	
	public void testJsonContainsObjectArray() {
		JsonContain jsonContain=new JsonContain();
		jsonContain.objectField="id";
		dao.getEntity(Article.class,QueryWhere.create().jsonContainsAny(null,"favorite_user_list",new int[] {1,2},jsonContain));
		//
		jsonContain=new JsonContain();
		jsonContain.objectField="userName";
		dao.getEntity(Article.class,QueryWhere.create().jsonContainsAny(null,"favorite_user_list",new String[] {"liubei","guanyu"},jsonContain));
	}
	
	public void testNotJsonContains() {
		dao.getEntity(User.class,QueryWhere.create().jsonNotContainsAny("role_id_list",Arrays.asList(1,2,3)));
		dao.getEntity(User.class,QueryWhere.create().jsonNotContainsAny("role_id_list",new int[] {1,2}));
		dao.getEntity(User.class,QueryWhere.create().jsonNotContainsAny("role_id_list",new byte[] {1,2}));
		dao.getEntity(User.class,QueryWhere.create().jsonNotContainsAny("role_id_list",new long[] {1,2}));
		//
		UserQuery query=new UserQuery();
		query.setNotRoleId(1);
		dao.getList(query);
		//
		query=new UserQuery();
		query.setNotRoleIdList(new Integer[] {1,2});
		dao.getList(query);
	}
	
	public void testQueryIsNull() {
		dao.getEntity(User.class,QueryWhere.create().isNull("name"));
		dao.getEntity(User.class,QueryWhere.create().isNotNull("name"));
		dao.getEntity(User.class,QueryWhere.create().where("name",null));
	}
	
	public void testIsNull() {
		UserQuery query=new UserQuery();
		query.setUserNameIsNull(true);
		query.setPageSize(1);
		dao.getEntity(query);
	}
	
	public void testOrderBy() {
		UserQuery query=new UserQuery();
		query.orderBy("name", "asc");
		List<User> list=dao.getList(query);
		System.out.println(DumpUtil.dump(list));
	}
	
	public void testQueryWhereSql() {
		dao.getEntity(User.class,QueryWhere.create().whereSql("a.name='张三'"));
		Map<String,Object> sqlParam=new HashMap<>();
		sqlParam.put("name", "张三");
		dao.getEntity(User.class,QueryWhere.create().whereSql("a.name=#{name}",sqlParam));
		dao.getEntity(User.class,QueryWhere.create().whereSql("a.name='${name}'",sqlParam));
		sqlParam=new HashMap<>();
		sqlParam.put("status", 1);
		dao.getEntity(User.class,QueryWhere.create().whereSql("a.status=${status}",sqlParam));
		sqlParam=new HashMap<>();
		sqlParam.put("status", 1);
		sqlParam.put("name", "zhang");
		dao.getEntity(User.class,QueryWhere.create().whereSql("a.status=#{status} or a.name like concat('%',#{name},'%')",
				sqlParam));
		sqlParam=new HashMap<>();
		sqlParam.put("status", Arrays.asList(1,2,3));
		sqlParam.put("name", "zhang");
		dao.getEntity(User.class,QueryWhere.create().whereSql("a.status in ${status} or a.name like concat('%',#{name},'%')",
				sqlParam));
	}
	
	public void testQueryOrderByLimit() {
		dao.getEntity(User.class,QueryWhere.create().
				orderBy("id asc").
				orderBy("name desc").
				limit(1));
		
	}
	
	public void testQueryWhereOr() {
		dao.getEntity(User.class,QueryWhere.create(ConditionType.OR).
				like("user_name","root").
				like("name","root")
		);
	}
	
	public void testQueryWhereAndOr() {
		dao.getEntity(User.class,QueryWhere.create().
				eq("status", 1).
				or(new Where().
						like("user_name","root").
						like("l1","name","技术")
				)
		);
	}
	
	public void testQueryWhereAndOrAnd() {
		dao.getEntity(User.class,QueryWhere.create().
				eq("status", 1).
				or(new Where().
						like("user_name","root").
						like("l1","name","技术").
						and(new Where().
								like("user_name","root").
								like("name","root"))
				)
		);
	}
	
	public void testQueryWhereOrAnd() {
		dao.getEntity(User.class,QueryWhere.create(ConditionType.OR).
				eq("status", 1).
				like("user_name", "root").
				or(new Where().
						in("l1","name", new ArrayList<>()).
						like("l1","name","技术")).
				and(new Where().
						like("l1","name","技术").
						like("name","root").
							or(new Where().
									like("l1","name","技术").
									like("name","root"))
				)
		);
		//
		dao.getEntity(User.class,QueryWhere.create(ConditionType.OR).
				eq("status", 1).
				like("user_name", "root").
				or(new Where().
						in("l1","name", new ArrayList<>()).
						and(new Where().
						like("l1","name","技术").
						like("name","root").
							or(new Where().
									like("l1","name","技术").
									like("name","root"))
						)//and
				)//or
		);
	}
	
	/**查询用户列表*/
	public void testUserQuery() {
		UserQuery query=new UserQuery();
		query.setUserName("root");
		query.setName("r");
		query.orderBy("name", OrderByType.ASC.name());
		query.orderBy("id", OrderByType.DESC.name());
		List<User> list=dao.getList(query,"createTime","updateTime");
		System.out.println(DumpUtil.dump(list));
	}
	
	public void testUserQueryInList() {
		UserQuery query=new UserQuery();
		query.setStatusInList(Arrays.asList(1,2));
		System.out.println(DumpUtil.dump(dao.getList(query)));
		//
		query=new UserQuery();
		query.setStatusInList2(new Integer[] {1,2});
		dao.getList(query);
		System.out.println(DumpUtil.dump(dao.getList(query)));
	}
	
	/**AND OR嵌套查询*/
	public void testUserComplexQuery() {
		UserComplexQuery query=new UserComplexQuery();
		query.setGtAge(10);
		NameOrUserNameOrDeptName nameOrUserName=new NameOrUserNameOrDeptName();
		nameOrUserName.setName("t");
		nameOrUserName.setUserName("e");
		nameOrUserName.setDeptName("技术");
		StatusAndMobile statusAndMobile=new StatusAndMobile();
		statusAndMobile.setMobileNo("130");
		statusAndMobile.setStatus(1);
		nameOrUserName.setStatusAndMobile(statusAndMobile);
		query.setNameOrUserName(nameOrUserName);
		List<User> list=dao.getList(query);
		System.out.println(DumpUtil.dump(list));
	}
	
	/**查询用户列表*/
	public void testQueryList() {
		List<User> list=dao.getList(User.class, 
				QueryWhere.create().where("name",SqlOperator.LIKE,"王"));
		System.out.println(DumpUtil.dump(list));
	}
	
	public void testGetWithInnerJoins() {
		UserQuery query=new UserQuery();
		query.setCreateUserName("root");
		query.setDepartmentName("技术");
		List<User> list=dao.getList(query,"createTime","updateTime");
		System.out.println(DumpUtil.dump(list));
		//
		ArticleQuery articleQuery=new ArticleQuery();
		articleQuery.setCreateUserName("root");
		articleQuery.setCreateUserMobileNo("13000000000");
		List<Article> articles=dao.getList(articleQuery,"createTime","updateTime");
		System.out.println(DumpUtil.dump(articles));
	}
	
	public void testQueryInList() {
		UserQuery query=new UserQuery();
		query.setStatusInList(Arrays.asList(1,2));
		List<User> list=dao.getList(query);
		System.out.println(DumpUtil.dump(list));
	}
	
	public void testQueryNotInList() {
		UserQuery query=new UserQuery();
		query.setStatusNotInList(Arrays.asList(1,2));
		List<User> list=dao.getList(query);
		System.out.println(DumpUtil.dump(list));
	}
	
	public void testQueryRoleId() {
		UserQuery query=new UserQuery();
		query.setRoleId(1);
		List<User> list=dao.getList(query);
		System.out.println(DumpUtil.dump(list));
	}
	
	/**查询用户列表总数*/
	public void testGetUsersCounts() {
		UserQuery query=new UserQuery();
		query.setUserName("test");
		int count=dao.getListCount(query);
		System.out.println(count);
	}
	
	/**当然写sql也是支持的*/
	public void testQueryUsers() {
		List<User> users=dao.queryList(User.class, 
				"select * from t_user where name like concat('%',?,'%') and id=?", 
				"liu",
				1);
		System.out.println(DumpUtil.dump(users));
	}
	
	public void testQueryUsers3() {
		List<UserSimple> users=dao.queryList(UserSimple.class, 
				"select *,1 as test from t_user");
		System.out.println(DumpUtil.dump(users));
	}
	
	public void testQueryUsersCount() {
		int count=dao.queryCount(
				"select count(1) from t_user where name like concat('%',?,'%') and id=?", 
				"liu",
				1);
		System.out.println(count);
	}
	
	public void testGetUserIds() {
		List<Integer> userIds=dao.queryForIntegers("select id from t_user where id=?",
				1);
		System.out.println(DumpUtil.dump(userIds));
	}
	//
	/**
	 * 查询文章详情
	 */
	public void testGetArticle() {
		Article info=dao.getById(Article.class,1);
		System.out.println(DumpUtil.dump(info));
	}
	
	public void testGetArticleCreateUserId() {
		ArticleQuery query=new ArticleQuery();
		Set<String>includeFields=new HashSet<>();
		includeFields.add("createUserName");
		List<Article> list=dao.getList(query, includeFields);
		System.out.println(DumpUtil.dump(list));
	}
	
	/**
	 * 查询userId为1的用户喜爱的文章列表
	 */
	public void testGetUserLikeArticles() {
		ArticleQuery query=new ArticleQuery();
		query.setLikeUserId(1);
		query.setPageSize(20);
		List<Article> list=dao.getList(query);
		System.out.println(DumpUtil.dump(list));
	}
	//
	//
	public void testQueryUserList() {
		UserQuery query=new UserQuery();
		query.orderBy("id", "desc");
		query.orderBy("name", "asc");
		List<User> users=dao.getList(query);
		System.out.println(DumpUtil.dump(users));
	}
	
	public void testOr() {
		ArticleQuery query=new ArticleQuery();
		query.setTitleOrContent("Java");
		List<Article> list=dao.getList(query);
		System.out.println(DumpUtil.dump(list));
		//
		query=new ArticleQuery();
		query.setTitle("123");
		IdListOrTitle idListOrTitle=new IdListOrTitle();
		idListOrTitle.setIdInList(new ArrayList<>());
		idListOrTitle.setTitle("123");
		query.setIdListOrTitle(idListOrTitle);
		List<Article> list2=dao.getList(query);
		System.out.println(DumpUtil.dump(list2));
	}
	//
	public void testGetArticleWithInnerJoins() {
		ArticleQuery query=new ArticleQuery();
		query.setCreateUserDepartmentName("技术");
		query.setCreateUserDepartmentName2("技术");
		List<Article> list=dao.getList(query);
		System.out.println(DumpUtil.dump(list));
	}
	
	public void testQueryDouble() {
		UserQuery query=new UserQuery();
		query.setHeightStart(1.65);
		List<User> list=dao.getList(query);
		System.out.println(DumpUtil.dump(list));
	}
	
	public void testQueryLong() {
		UserQuery query=new UserQuery();
		query.setNo(5L);
		List<User> list=dao.getList(query);
		System.out.println(DumpUtil.dump(list));
	}
	
	public void testQueryBoolean() {
		Condition c=new Condition();
		c.key="is_student";
		c.operator=SqlOperator.EQ;
		c.value=true;
		List<User> list=dao.getList(User.class, QueryWhere.create().where(c));
		System.out.println(DumpUtil.dump(list));
	}
	
	public void testQueryIsJsonKeyNull() {
		UserQuery query=new UserQuery();
		query.settingIsNull=true;
		List<User> list=dao.getList(query);
		System.out.println(DumpUtil.dump(list));
	}
	
	public void testSum() {
		Aggregation aggregation=new Aggregation();
		aggregation.func="sum";
		aggregation.expr="article_num";
		SqlBean sqlBean=SelectProviderFactory.create(dao.getSmartDataSource()).
				entityClass(User.class).
				aggregationList(Arrays.asList(aggregation)).
				needOrderBy(false).build();
		Long sum=dao.queryForLong(sqlBean.sql, sqlBean.parameters);
		System.out.println("sum:"+sum);
	}
	
	public void testGroupBy() {
		UserQuery query=new UserQuery();
		SelectProvider selectProvider=SelectProviderFactory.create(dao.getSmartDataSource());
		selectProvider.entityClass(User.class);
		selectProvider.query(query);
		selectProvider.groupBy("a", "age");
		List<User> list=dao.getList(selectProvider);
		System.out.println(DumpUtil.dump(list));
	}
	
	public void testSumAndGroupBy() {
		Aggregation aggregation=new Aggregation();
		aggregation.func="sum";
		aggregation.expr="article_num";
		SqlBean sqlBean=SelectProviderFactory.create(dao.getSmartDataSource()).
				entityClass(User.class).
				aggregationList(Arrays.asList(aggregation)).
				groupBy("a", "age").
				needOrderBy(false).build();
		Long sum=dao.queryForLong(sqlBean.sql, sqlBean.parameters);
		System.out.println("sum:"+sum);
	}
	
	public void testWhere() {
		Where w=new Where();
		Where and1=new Where();
		Where and2=new Where();
		and2.where("user_name", SqlOperator.LIKE,"test");
		and1.and(and2);
		w.and(and1);
		dao.getList(User.class, QueryWhere.create().and(w));
		//
		w=new Where();
		and1=new Where();
		and2=new Where();
		and1.and(and2);
		w.and(and1);
		dao.getList(User.class, QueryWhere.create().and(w));
		//
		w=new Where();
		and1=new Where();
		and2=new Where();
		Where or1=new Where();
		Where or2=new Where();
		or2.where("user_name", SqlOperator.LIKE,"test");
		or2.where("name", SqlOperator.LIKE,"123");
		and1.and(and2);
		and2.or(or1);
		or1.or(or2);
		w.and(and1);
		w.and(new Where());
		w.or(new Where());
		dao.getList(User.class, QueryWhere.create().and(w));
	}
	
	public void testOr2() {
		Where w=new Where();
		Where and1=new Where();
		and1.where("id", SqlOperator.EQ,111);
		Where and2=new Where(ConditionType.OR);
		w.and(and1);
		w.and(and2);
		//
		Where or1=new Where();
		or1.where("user_name", SqlOperator.LIKE,"test");
		or1.where("name", SqlOperator.LIKE,"123");
		and2.or(or1);
		//
		Where or2=new Where();
		or2.where("user_name", SqlOperator.LIKE,"or2");
		and2.or(or2);
		//
		dao.getList(User.class, QueryWhere.create().and(w));
	}
	
	public void testForUpdate() {
		dao.getEntity(User.class,QueryWhere.create().where("id", 1).forUpdate());
	}
	
	public void testIntArray() {
		UserQuery query=new UserQuery();
		query.setIntArray(Arrays.asList(1,2,3));
		dao.getList(query);
	}
	
	public void testStringArray() {
		UserQuery query=new UserQuery();
		query.setStringArray(Arrays.asList("s1","你好"));
		dao.getList(query);
	}
	
	public void testArrayContains() {
		UserQuery query=new UserQuery();
		query.setStringContains(new String[] {"s1","s2"});
		dao.getList(query);
		//
		query=new UserQuery();
		query.setStringContains(new String[] {"s1","你好"});
		dao.getList(query);
	}
	
	public void testArrayNotContains() {
		UserQuery query=new UserQuery();
		query.setStringNotContains(new String[] {"s1","s2"});
		dao.getList(query);
		//
		query=new UserQuery();
		query.setStringNotContains(new String[] {"s1","你好"});
		dao.getList(query);
	}
	
	public void testBool() {
		UserQuery query=new UserQuery();
		query.setIsStudent(true);
		System.out.println(DumpUtil.dump(dao.getList(query)));
	}
	
	public void testIn() {
		UserQuery query=new UserQuery();
		query.setStatusInList(new ArrayList<>());
		dao.getAll(query);
	}
	
	public void testBetweenAnd() throws ParseException {
		dao.getList(User.class,
				QueryWhere.create().betweenAnd("age",Arrays.asList(10,15)));
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		dao.getList(User.class,
				QueryWhere.create().betweenAnd("create_time",
						Arrays.asList(
								sdf.parse("2020-10-09"),
								sdf.parse("2020-11-09"))));
	}
	
	public void testNotBetweenAnd() throws ParseException {
		dao.getList(User.class,
				QueryWhere.create().notBetweenAnd("age",Arrays.asList(10,15)));
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		dao.getList(User.class,
				QueryWhere.create().notBetweenAnd("create_time",
						Arrays.asList(
								sdf.parse("2020-10-09"),
								sdf.parse("2020-11-09"))));
	}
	
	public void testLeftJoin() {
		AppTable table=dao.getEntity(AppTable.class,QueryWhere.create().eq("id", "1"));
		System.out.println(DumpUtil.dump(table));
	}
	//
	public void testJoin() {
		AppTableQuery query=new AppTableQuery();
		query.moduleName="模块1";
		List<AppTable> tables=dao.getList(query);
		System.out.println(DumpUtil.dump(tables));
	}
	
	
	public void testSqlServer() {
		AppServerQuery query=new AppServerQuery();
		query.setAppIdInList(Arrays.asList("222","21"));
		List<AppServer> list = dao.getList(query);

		System.out.println(list);
	}
	
}
