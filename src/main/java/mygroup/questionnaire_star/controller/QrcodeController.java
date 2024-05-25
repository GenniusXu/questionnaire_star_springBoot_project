package mygroup.questionnaire_star.controller;

import mygroup.questionnaire_star.utils.QrcodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/qrcode")
public class QrcodeController {
    @Autowired
    QrcodeUtil qrcodeUtil;

    @GetMapping("/generate")
    public String generateQRCode( @RequestParam String content) {

        String qrContent = content;
        return qrcodeUtil.generateBase64(qrContent, "png");
    }
}
