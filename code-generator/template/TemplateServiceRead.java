
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author: Administrator
 * @description:
 * @date: 2018/10/30 0030 21:35
 * @modified:
 */
@Service
public class TemplateServiceRead implements ITemplateService {

    @Autowired
    private TemplateDao templateDao;

    @Override
    public Template queryTemplateById(Integer id) {
        if(null == id) throw new CustomException("参数检验有误");
        Template template = templateDao.queryTemplateById(id);
        return template;
    }

    @Override
    public List<Template> queryTemplateList(Map<String, Object> map) {
        return templateDao.queryTemplateList(map);
    }
}
