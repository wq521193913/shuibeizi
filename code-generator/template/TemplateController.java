
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

import javax.servlet.ServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author Administrator
 * @date #today
*/
@Controller
@RequestMapping(value = "/template")
public class TemplateController {
	
	private final Logger logger = LoggerFactory.getLogger(TemplateController.class);

    @Autowired
    private ITemplateService templateService;

    /**
     * 新增
     * @param template
     * @return 
     * @author Administrator
     * @date #today
    */
    @RequestMapping(value = "insertTemplate", method = RequestMethod.POST)
    @ResponseBody
    public Result insertTemplate(Template template){
        Result result = new Result();
        try {
            templateService.insertTemplate(template);
        }catch (CustomException ce){
            logger.error("params:" + template, ce);
            return Result.getSystemErrorMsg(ce);
        }catch (Exception e){
            result = Result.getSystemErrorMsg(e);
            logger.error("TemplateController.insertTemplate error:", e);
        }
        return result;
    }

    /**
     * 根据id更新表数据
     * @param template
     * @return 
     * @author Administrator
     * @date #today
    */
    @RequestMapping(value = "updateTemplateById", method = RequestMethod.POST)
    @ResponseBody
    public Result updateTemplateById(Template template){
        Result result = new Result();
        try {
            templateService.updateTemplateById(template);
        }catch (Exception e){
            result = Result.getSystemErrorMsg(e);
            logger.error("TemplateController.updateTemplateById error:", e);
        }
        return result;
    }

    /**
     * 删除表数据
     * @param id
     * @return
     * @author Administrator
     * @date #today
    */
    @RequestMapping(value = "deleteTemplate", method = RequestMethod.POST)
    @ResponseBody
    public Result deleteTemplate(@RequestParam(value = "id")Integer id){
        Result result = new Result();
        try {
            templateService.deleteTemplate(id);
        }catch (Exception e){
            result = Result.getSystemErrorMsg(e);
            logger.error("TemplateController.deleteTemplate error:", e);
        }
        return result;
    }

    /**
     * 根据id查询数据
     * @param id
     * @return 
     * @author Administrator
     * @date #today
    */
    @RequestMapping(value = "queryTemplateById", method = RequestMethod.GET)
    @ResponseBody
    public Result queryTemplateById(@RequestParam(value = "id")Integer id){
        Result result = new Result();
        try {
            Template template = templateService.queryTemplateById(id);
            if(null == template){
                result.setSuccess(false);
                result.setMsg("无法查询此数据");
            }else {
                result.setData(template);
            }
        }catch (Exception e){
            result = Result.getSystemErrorMsg(e);
            logger.error("TemplateController.queryTemplateById error:", e);
        }
        return result;
    }

    /**
     * 查询列表
     * @param
     * @return 
     * @author Administrator
     * @date #today
    */
    @RequestMapping(value = "queryTemplateList", method = RequestMethod.GET)
    @ResponseBody
    public Result queryTemplateList(ServletRequest request){
        Result result = new Result();
        try {
            Map<String, Object> paramMap = WebUtils.getParametersStartingWith(request,"");
            List<Template> templateList = templateService.queryTemplateList(paramMap);
            result.setData(templateList);
        }catch (Exception e){
            result = Result.getSystemErrorMsg(e);
            logger.error("TemplateController.queryTemplateList error:", e);
        }
        return result;
    }
}
