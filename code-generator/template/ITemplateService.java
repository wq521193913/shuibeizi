
import java.util.List;
import java.util.Map;

/**
 * 
 * @author Administrator
 * @date #today
*/
public interface ITemplateService {

    /**
     * 新增
     * @param template
     * @return
     * @throws Exception
     */
    default void insertTemplate(Template template, Consumer<String> errorCall) throws CustomException{

    };

    /**
     * 根据id修改
     * @param template
     * @return
     * @throws Exception
     */
    default int updateTemplateById(Template template, Consumer<String> errorCall) throws CustomException{
        return 0;
    };

    /**
     * 删除
     * @param id
     * @return
     */
    default int deleteTemplate(Integer id, Consumer<String> errorCall) throws CustomException{
        return 0
    };

    /**
     * 根据id查询
     * @param id
     * @return
     */
    default Template queryTemplateById(Integer id){
        return null;
    };

    /**
     * 查询列表
     * @param map
     * @return
     */
    default List<Template> queryTemplateList(Map<String, Object> map){
        return null;
    };

}
