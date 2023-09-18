package dinosoul.snacktravelserver.global.util;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3Service {

    private final AmazonS3 amazonS3;
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    /**
     * 이미지를 S3에 업로드하고 업로드된 이미지의 S3 URL을 반환합니다.
     *
     * @param multipartFile 업로드할 이미지 파일
     * @return 업로드된 이미지의 S3 URL
     * @throws IllegalArgumentException 업로드 실패 시 발생하는 예외
     */
    public String upload(MultipartFile multipartFile) {
        if (multipartFile == null || multipartFile.isEmpty()) return null;

        try {
            byte[] fileBytes = multipartFile.getBytes();
            String fileName = generateFileName(multipartFile.getOriginalFilename());
            String contentType = multipartFile.getContentType();
            putS3(fileBytes, fileName, contentType);
            String imageUrl = generateUnsignedUrl(fileName);
            log.info("이미지 업로드 완료: " + imageUrl);
            return imageUrl;
        } catch (IOException e) {
            throw new IllegalArgumentException("업로드 실패");
        }
    }

    public void putS3(byte[] fileBytes, String fileName, String contentType) {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(fileBytes.length);
        metadata.setContentType(contentType);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(fileBytes);
        amazonS3.putObject(new PutObjectRequest(bucket, fileName, inputStream, metadata));
        log.info("파일 생성: " + fileName);
    }

    public void delete(String imageUrl) {
        if (StringUtils.hasText(imageUrl)) {
            String fileName = extractObjectKeyFromUrl(imageUrl);
            try {
                String decodedFileName = URLDecoder.decode(fileName, StandardCharsets.UTF_8);
                if (amazonS3.doesObjectExist(bucket, decodedFileName)) {
                    amazonS3.deleteObject(bucket, decodedFileName);
                    log.info("파일 삭제: " + decodedFileName);
                } else {
                    log.warn("존재하지 않는 파일: " + decodedFileName);
                }
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("삭제 실패");
            }
        }
    }

    public void deletes(List<String> imageUrlList) {
        for (String imageUrl : imageUrlList) {
            if (StringUtils.hasText(imageUrl)) {
                String fileName = extractObjectKeyFromUrl(imageUrl);
                try {
                    String decodedFileName = URLDecoder.decode(fileName, StandardCharsets.UTF_8);
                    if (amazonS3.doesObjectExist(bucket, decodedFileName)) {
                        amazonS3.deleteObject(bucket, decodedFileName);
                        log.info("파일 삭제: " + decodedFileName);
                    } else {
                        log.warn("존재하지 않는 파일: " + decodedFileName);
                    }
                } catch (IllegalArgumentException e) {
                    throw new IllegalArgumentException("삭제 실패");
                }
            }
        }
    }

    public String extractObjectKeyFromUrl(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            return url.getPath().substring(1); // leading slash 제거
        } catch (Exception e) {
            throw new IllegalArgumentException("키 추출 실패");
        }
    }

    /**
     * 업로드할 이미지 파일의 원본 파일 이름으로 고유한 파일 이름을 생성합니다.
     *
     * @param originalFilename 업로드할 이미지 파일의 원본 파일 이름
     * @return 생성된 고유한 파일 이름
     * @throws IllegalArgumentException 파일 이름이 유효하지 않을 경우 발생하는 예외
     */
    public String generateFileName(String originalFilename) {
        if (StringUtils.hasText(originalFilename)) {
            String extension = extractExtension(originalFilename);
            String uniqueId = UUID.randomUUID().toString();
            return uniqueId + "." + extension;
        }
        throw new IllegalArgumentException("유효한 파일이 아닙니다.");
    }

    public String extractExtension(String originalFilename) {
        if (StringUtils.hasText(originalFilename)) {
            int extensionIndex = originalFilename.lastIndexOf(".");
            if (extensionIndex != -1) {
                return originalFilename.substring(extensionIndex + 1);
            }
        }
        throw new IllegalArgumentException("확장자를 추출할 수 없습니다.");
    }

    public String generateUnsignedUrl(String objectKey) {
        String baseUrl = "https://" + bucket + ".s3.amazonaws.com/";
        return baseUrl + objectKey;
    }

    public boolean fileExists(String imageUrl) {
        if (StringUtils.hasText(imageUrl)) {
            String fileName = extractObjectKeyFromUrl(imageUrl);
            try {
                String decodedFileName = URLDecoder.decode(fileName, StandardCharsets.UTF_8);
                return amazonS3.doesObjectExist(bucket, decodedFileName);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("파일 이름 디코딩에 실패 했습니다.");
            }
        }
        return false;
    }
}
