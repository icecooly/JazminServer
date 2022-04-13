/**
 * 
 */
package jazmin.driver.jdbc.smartjdbc.enums;


/**
 * 
 * @author skydu
 *
 */
public enum SqlOperator {
	CUSTOM,
    IN,
    NOT_IN,
    LIKE,
    NOT_LIKE,
    LIKE_LEFT,//key LIKE '%值'
    NOT_LIKE_LEFT,
    LIKE_RIGHT,//key LIKE '值%'
    NOT_LIKE_RIGHT,
    EQ,//等于
    NE,//不等于
    GT,//大于
    GE,//大于等于
    LT,//小于
    LE,//小于等于
    BETWEEN_AND,
    NOT_BETWEEN_AND,
    IS_NULL,
    IS_NOT_NULL,
    JSON_CONTAINS_ANY,//包含任意一个
    JSON_NOT_CONTAINS_ANY,//不包含任意一个
	JSON_CONTAINS_ALL,//全部包含
	JSON_CONTAINS_EQ,//等于
	JSON_CONTAINS_NE,//不等于
	ARRAY_ANY,//数组中包含任意一个元素
	ARRAY_NOT_ANY,//数组中不包含任意一个
	ARRAY_CONTAINS,//数组包含 
	ARRAY_NOT_CONTAINS,//数组不包含
	//
	JSON_BELONG,//属于
	ARRAY_BELONG,//属于
	
}
