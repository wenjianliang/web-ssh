package vip.r0n9.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import vip.r0n9.JsonUtil;
import vip.r0n9.Test;
import vip.r0n9.model.HostLoginInfo;
import vip.r0n9.ws.WebSshHandler;

@Controller
public class WebSshController {
    @Autowired
    Test test;
    @GetMapping("/")
    public String showIndex(Model model) {
        return "index";
    }
    @GetMapping("/aa")
    public String aa(Model model) {
        System.out.println(test.toString());
        return "aa";
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ResponseBody
    public ObjectNode connect(String hostname, Integer port, String username, String password, MultipartFile privatekey) {

        WebSshHandler.websocketSessionId.increment();
        long wsId = WebSshHandler.websocketSessionId.longValue();

        HostLoginInfo hostLoginInfo = new HostLoginInfo()
                .setHostname(hostname)
                .setUsername(username)
                .setPassword(password)
                .setPort(port)
                .setPrivatekey(privatekey);
        WebSshHandler.hostLoginInfoMap.put(wsId, hostLoginInfo);
        
        ObjectNode node = JsonUtil.createObjectNode();
        node.put("status", 0);
        node.put("id", wsId);
        node.put("encoding", "utf-8");
        return node;
    }
}
