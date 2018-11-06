
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author Administrator
 * @date #today
*/
@Service
public class TemplateServiceImpl implements ITemplateService{

    @Autowired
    private TemplateDao templateDao;

    /**
     * 新增
     * @param template
     * @return
     * @throws Exception
     */
    public void insertTemplate(Template template, Consumer<String> errorCall) throws CustomException{
        templateDao.insertTemplate(template);
    }

    /**
     * 根据id修改
     * @param template
     * @return
     * @throws Exception
     */
    public int updateTemplateById(Template template, Consumer<String> errorCall) throws CustomException{
        if(null == template || null == template.getUid()){
            errorCall.accept("参数检验有误");
            return 0;
        }
        templateDao.updateTemplateById(template);
    }

    /**
     * 删除
     * @param id
     * @return
     */
    public int deleteTemplate(Integer id, Consumer<String> errorCall) throws CustomException{
        if(null == id){
            errorCall.accept("参数检验有误");
            return 0;
        }
        templateDao.deleteTemplate(id);
    }

    /**
     * 根据id查询
     * @param id
     * @return
     */
    public Template queryTemplateById(Integer id) {
        if(null == id) return null;
        return templateDao.queryTemplateById(id);
    }

    /**
     * 查询列表
     * @param map
     * @return
     */
    public List<Template> queryTemplateList(Map<String, Object> map){
        return templateDao.queryTemplateList(map);
    }

    /**
     * 查询列表
     * @param map
     * @return
     */
    public List<Template> queryTemplatePageList(Map<String, Object> map){
        return templateDao.queryTemplatePageList(map);
    }
}
