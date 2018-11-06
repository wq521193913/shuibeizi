
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 *
 * @author Administrator
 * @date #today
 */
@Repository
public interface TemplateDao {

    /**
     * 新增
     * @param template
     * @return
     */
    void insertTemplate(Template template);

    /**
     * 根据id修改
     * @param template
     * @return
     */
    int updateTemplateById(Template template);

    /**
     * 删除
     * @param id
     * @return
     */
    int deleteTemplate(Integer id);

    /**
     * 根据id查询
     * @param id
     * @return Template
     */
    Template queryTemplateById(Integer id);

    /**
     * 查询列表
     * @param map
     * @return List<Template>
     */
    List<Template> queryTemplateList(Map<String, Object> map);

    /**
     * 查询分页列表
     * @param map
     * @return List<Template>
     */
    List<Template> queryTemplatePageList(Map<String, Object> map);

    /**
     * 查询分页列表条数
     * @param map
     * @return Integer
     */
    Integer queryTemplatePageCount(Map<String, Object> map);
}
