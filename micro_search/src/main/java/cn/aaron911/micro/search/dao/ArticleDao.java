package cn.aaron911.micro.search.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import cn.aaron911.micro.search.pojo.Article;

/**
 * 文章数据访问层接口
 */
public interface ArticleDao extends ElasticsearchRepository<Article, String> {
	
	/**
	 * 检索 
	 * @param
	 * @return      
	 */
	public Page<Article> findByTitleOrContentLike(String title, String content, Pageable pageable);
}
