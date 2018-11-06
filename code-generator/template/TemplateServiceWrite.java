
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

/**
 * @author: Administrator
 * @description:
 * @date: 2018/10/30 0030 13:48
 * @modified:
 */
@Service
public class TemplateServiceWrite implements ITemplateService {

    @Autowired
    private TemplateDao templateDao;

    @Override
    public void insertTemplate(Template template, Consumer<String> errorCall) throws CustomException {
        ParamsValidator.getInstance().getValidator(template,null);
        templateDao.insertTemplate(template);
    }

    @Override
    public int updateTemplateById(Template template, Consumer<String> errorCall) throws CustomException {
        int affectRow = 0;
        if(null != template && null != template.getUid()){
            Template userExit = templateDao.queryTemplateById(template.getUid());
            if(null == userExit) {
                errorCall.accept("无法查询到此数据");
                return 0;
            }
            affectRow = templateDao.updateTemplateById(template);
        }else {
            errorCall.accept("参数检验有误:用户id不能为空");
        }
        return affectRow;
    }

    @Override
    public int deleteTemplate(int id, Consumer<String> errorCall) throws CustomException {
        int affectRow = templateDao.deleteTemplate(id);
        return affectRow;
    }
}
