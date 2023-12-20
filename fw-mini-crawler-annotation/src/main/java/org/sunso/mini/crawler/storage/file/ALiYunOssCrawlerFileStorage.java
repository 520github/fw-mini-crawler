package org.sunso.mini.crawler.storage.file;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.StrUtil;
import com.aliyun.oss.OSSClient;
import org.sunso.mini.crawler.annotation.storage.FileStorageAliYunOss;
import org.sunso.mini.crawler.storage.CrawlerFileStorageRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ALiYunOssCrawlerFileStorage implements CrawlerFileStorage {

	private Map<String, OSSClient> ossClientMap = new HashMap<>();

	@Override
	public String storage(CrawlerFileStorageRequest request) {
		return putFileToOss(request);
	}

	private String putFileToOss(CrawlerFileStorageRequest request) {
		OSSClient ossClient = getOSSClient(request);
		FileStorageAliYunOss fileStorageAliYunOss = request.getField().getAnnotation(FileStorageAliYunOss.class);
		String key = getKey(fileStorageAliYunOss);
		ossClient.putObject(fileStorageAliYunOss.bucket(), key, request.getResponse().getInputStream());
		return getShowDomain(fileStorageAliYunOss) + "/" + key;
	}

	private String getShowDomain(FileStorageAliYunOss fileStorageAliYunOss) {
		String showDomain = fileStorageAliYunOss.showDomain();
		if (StrUtil.isBlank(showDomain)) {
			showDomain = "http://" + fileStorageAliYunOss.endpoint();
		}
		return showDomain;
	}

	private String getKey(FileStorageAliYunOss fileStorageAliYunOss) {
		String preKey = fileStorageAliYunOss.preKey();
		if (StrUtil.isBlank(preKey)) {
			preKey = "default";
		}
		StringBuilder sb = new StringBuilder();
		sb.append(preKey);
		sb.append(getDate());
		sb.append(getFileName(fileStorageAliYunOss));
		return sb.toString();
	}

	private String getDate() {
		return new DateTime().toString("yyyy/MM/dd") + "/";
	}

	private String getRandomUuid() {
		return UUID.randomUUID().toString().replace("-", "");
	}

	private String getFileName(FileStorageAliYunOss fileStorageAliYunOss) {
		String fileName = fileStorageAliYunOss.fileName();
		if (StrUtil.isBlank(fileName)) {
			fileName = getRandomUuid();
		}
		if (StrUtil.isNotBlank(fileStorageAliYunOss.fileExtension())) {
			fileName = fileName + "." + fileStorageAliYunOss.fileExtension();
		}
		return fileName;
	}

	private OSSClient getOSSClient(CrawlerFileStorageRequest request) {
		FileStorageAliYunOss fileStorageAliYunOss = request.getField().getAnnotation(FileStorageAliYunOss.class);
		String accessKeyId = fileStorageAliYunOss.accessKeyId();
		String endpoint = fileStorageAliYunOss.endpoint();
		if (StrUtil.isBlank(accessKeyId) || StrUtil.isBlank(endpoint)) {
			return getFirstOSSClient();
		}
		OSSClient ossClient = ossClientMap.get(getCacheKey(accessKeyId, endpoint));
		if (ossClient != null) {
			return ossClient;
		}
		ossClient = new OSSClient(endpoint, accessKeyId, fileStorageAliYunOss.accessKeySecret());
		ossClientMap.put(getCacheKey(accessKeyId, endpoint), ossClient);
		return ossClient;
	}

	private OSSClient getFirstOSSClient() {
		for (String key : ossClientMap.keySet()) {
			return ossClientMap.get(key);
		}
		return null;
	}

	private String getCacheKey(String accessKeyId, String endpoint) {
		return accessKeyId + endpoint;
	}

}
