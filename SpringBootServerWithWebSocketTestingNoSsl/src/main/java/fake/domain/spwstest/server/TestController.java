package fake.domain.spwstest.server;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController
{
    @RequestMapping(value="/htest", method=RequestMethod.GET)
    public @ResponseBody Map<String, String> hellowWorld()
    {
    	Map<String, String> m = new HashMap<>();
    	m.put("hello", "world");
    	return m;
    }
}
