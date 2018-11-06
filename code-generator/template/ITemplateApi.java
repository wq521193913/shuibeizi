
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * @author: Administrator
 * @description:
 * @date: 2018/11/2 0002 09:53
 * @modified:
 */
public interface ITemplateApi {

    @RequestMapping(value ="queryTemplateById")
    default SysUser queryTemplateById(Template template) {
        return null;
    }

    @RequestMapping(value ="queryTemplateList")
    default List<Template> queryTemplateList(Map<String, Object> map) {
        return null;
    }

    @RequestMapping(value ="insertTemplate")
    default void insertTemplate(Template template, Consumer<String> errorCall) throws CustomException {
    }

    @RequestMapping(value ="updateTemplateById")
    default int updateTemplateById(Template template, Consumer<String> errorCall) throws CustomException {
    }

    @RequestMapping(value ="deleteTemplate")
    default int deleteTemplate(Integer id, Consumer<String> errorCall) throws CustomException {
    }

}
