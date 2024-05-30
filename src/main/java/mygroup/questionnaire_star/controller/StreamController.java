package mygroup.questionnaire_star.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@RestController("/stream")
@CrossOrigin(origins = "*")
public class StreamController {
    @PostMapping("/emitter")
    public SseEmitter sse(@RequestBody String inputParameter, HttpServletResponse response) {
        response.setContentType("text/event-stream");
        response.setCharacterEncoding("UTF-8");
        SseEmitter emitter = new SseEmitter();

        // Simulate asynchronous data retrieval from the database
        new Thread(() -> {
            try {
                // Query the database based on the input parameter and send data in batches
                for (int i = 0; i < 10; i++) {
                    String data = "Data batch " + i + " for parameter: " + inputParameter;
                    emitter.send(data);
                    Thread.sleep(1000); // Simulate delay between batches
                }

                emitter.complete(); // Complete the SSE connection
            } catch (Exception e) {
                emitter.completeWithError(e); // Handle errors
            }
        }).start();

        return emitter;
    }


    // 测试响应流
    //@AnonymousAccess
    @PostMapping("/api/test/sss")
    public void test(String prompt, HttpServletResponse res) throws IOException, InterruptedException {
        System.out.println("【prompt内容】：" + prompt);
        String str = "       什么是爱而不得? \n" +
                "东边日出西边雨，道是无晴却有晴。\n" +
                "他朝若是同淋雪，此生也算共白头。\n" +
                "我本将心向明月，奈何明月照沟渠。\n" +
                "此时相望不相闻，愿逐月华流照君。\n" +
                "衣带渐宽终不悔，为伊消得人憔悴。\n" +
                "此情可待成追忆，只是当时已惘然。\n" +
                "人生若只如初见，何事西风悲画扇。\n" +
                "曾经沧海难为水，除却巫山不是云。\n" +
                "何当共剪西窗烛，却话巴山夜雨时。\n" +
                "天长地久有时尽，此恨绵绵无绝期。\n" +
                "\n";
        // 响应流
        res.setHeader("Content-Type", "text/event-stream");
        res.setContentType("text/event-stream");
        res.setCharacterEncoding("UTF-8");
        res.setHeader("Pragma", "no-cache");
        ServletOutputStream out = null;
        try {
            ServletOutputStream finalOut = out;
            out = new ServletOutputStream() {
                @Override
                public void write(int b) throws IOException {
                    for (int i = 0; i < str.length(); i++) {
                        finalOut.write(String.valueOf(str.charAt(i)).getBytes());
                        // 更新数据流
                        finalOut.flush();
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            };
            //out = res.getOutputStream();

        } finally {
            try {
                if (out != null) out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @PostMapping("/stream")
    public void handleStream(@RequestBody String body, HttpServletResponse response) {
        // 设置响应类型为流式数据
        response.setContentType("application/octet-stream");
        // 假设 streamData 是从外部服务获取的流式数据
        byte[] streamData = getStreamDataFromService(body);
        
        // 将流式数据写入响应体
        try {
            response.getOutputStream().write(streamData);
            response.getOutputStream().flush();
        } catch (IOException e) {
            e.printStackTrace();
            // 处理异常情况
        }
    }

    private byte[] getStreamDataFromService(String body) {
        // 这里应该是调用外部服务并获取流式数据的逻辑
        // 由于示例的简化，这里直接返回一个示例字节数组
        return "示例流数据".getBytes();
    }
}
