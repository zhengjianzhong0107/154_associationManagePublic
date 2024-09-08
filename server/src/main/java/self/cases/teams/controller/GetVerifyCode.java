package self.cases.teams.controller;

import cn.hutool.captcha.CaptchaUtil;
import org.springframework.stereotype.Controller;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import self.cases.teams.utils.VerifyCodeUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;



import self.cases.teams.utils.VerifyCodeUtils;
@Controller
@RequestMapping("/Verify")
public class GetVerifyCode {


    // 生成验证码
    @GetMapping("/getVerifyCodeImage")
    @ResponseBody
    public String getImageCode(HttpServletRequest request) throws IOException {
// 1. 使用工具类生成验证码
        String validate_code = null;
        String code = VerifyCodeUtils.generateVerifyCode(4);
        validate_code = code; // 存放生成的验证码
// 2. 将验证码放入ServletContext作用域
        request.getServletContext().setAttribute("code", code);
// 3. 将图片转换成base64格式
// 字节数组输出流在内存中创建一个字节数组缓冲区，所有发送到输出流的数据保存在该字节数组缓冲区中
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//将得到的验证码，使用工具类生成验证码图片，并放入到字节数组缓存区
        VerifyCodeUtils.outputImage(220, 60, byteArrayOutputStream, code);
//使用spring提供的工具类，将字节缓存数组中的验证码图片流转换成Base64的形式
//并返回给浏览器
        return "data:image/png;base64," + Base64Utils.encodeToString(byteArrayOutputStream.toByteArray());
    }

}
