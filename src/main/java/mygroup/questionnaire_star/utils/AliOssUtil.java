package mygroup.questionnaire_star.utils;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;

import java.io.InputStream;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AliOssUtil {

    // Endpoint以华东1（杭州）为例，其它Region请按实际情况填写。
    private static final String ENDPOINT = "oss-cn-guangzhou.aliyuncs.com";
    // 从环境变量中获取访问凭证。运行本代码示例之前，请确保已设置环境变量OSS_ACCESS_KEY_ID和OSS_ACCESS_KEY_SECRET。
    //EnvironmentVariableCredentialsProvider credentialsProvider = CredentialsProviderFactory.newEnvironmentVariableCredentialsProvider();
    // 直接填写访问凭证
    private static final String ACCESS_KEY_ID = "LTAI5tDK74j6uEX1cVk1ArLH";
    private static final String ACCESS_KEY_SECRET = "XAALjBCGzeOk9kDp0qMdQsiUYKn8aR";
    // 填写Bucket名称，例如examplebucket。
    private static final String BUCKET_NAME = "big-event-zty";
    // 填写Object完整路径，完整路径中不能包含Bucket名称，例如exampledir/exampleobject.txt。

    //传入文件名objectName和文件输入流对象in，将文件存储到阿里云对象存储（OSS）服务器上，返回访问URL地址
    public static String uploadFile(String objectName, InputStream in) throws Exception {
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(ENDPOINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
        String url = "";
        try {
            // 创建PutObjectRequest对象。
            PutObjectRequest putObjectRequest = new PutObjectRequest(BUCKET_NAME, objectName, in);

            // 如果需要上传时设置存储类型和访问权限，请参考以下示例代码。
            // ObjectMetadata metadata = new ObjectMetadata();
            // metadata.setHeader(OSSHeaders.OSS_STORAGE_CLASS, StorageClass.Standard.toString());
            // metadata.setObjectAcl(CannedAccessControlList.Private);
            // putObjectRequest.setMetadata(metadata);

            // 上传文件
            PutObjectResult result = ossClient.putObject(putObjectRequest);
            //url组成: https://bucket名称.区域节点/objectName
            url = "https://" + BUCKET_NAME + "." + ENDPOINT.substring(ENDPOINT.lastIndexOf("/") + 1) + "/" + objectName;
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }

        return url;
    }

    // 传入url，删除阿里云OSS上的对应图片
    public static boolean deleteFile(String url) {
        // 从URL中提取存储桶名称和对象名称
        String bucketName = extractBucketName(url);
        String objectName = extractObjectName(url);

        boolean isSuccess;
        // 创建OSSClient实例
        OSS ossClient = new OSSClientBuilder().build(ENDPOINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);

        try {
            // 删除对象
            ossClient.deleteObject(bucketName, objectName);
            System.out.println("对象删除成功");
            isSuccess = true;
        } catch (Exception e) {
            System.err.println("对象删除失败：" + e.getMessage());
            isSuccess = false;
        } finally {
            // 关闭OSSClient
            ossClient.shutdown();
        }
        return isSuccess;
    }

    // 从URL中提取存储桶名称
    private static String extractBucketName(String urlString) {
        Pattern pattern = Pattern.compile("//(.*?)\\.");
        Matcher matcher = pattern.matcher(urlString);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    // 从URL中提取对象名称
    private static String extractObjectName(String urlString) {
        try {
            URL url = new URL(urlString);
            String path = url.getPath();
            return path.substring(1); // Remove leading slash
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        String url = "https://big-event-zty.oss-cn-guangzhou.aliyuncs.com/e4d8229a-9bfc-4007-ad40-10470909b016.jpg";
        System.out.println(extractBucketName(url));
        System.out.println(extractObjectName(url));
    }
}
