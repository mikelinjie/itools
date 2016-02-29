package com.mike.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class CrawlerUtils {
	/*获取页面内容
	 * 
	 */
	
	
	public static String getURLContent(String url) {
		String URLContent = "";
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet urlGet = new HttpGet(url);

		urlGet.addHeader("Accept",
				"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		urlGet.addHeader("Accept-Encoding", "gzip, deflate, sdch");
		urlGet.addHeader("Accept-Language", "zh-CN,zh;q=0.8");
		urlGet.addHeader("Connection", "keep-alive");
		urlGet.addHeader("Host", "archive.apache.org");
		urlGet.addHeader(
				"User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2272.101 Safari/537.36");

		try {
			InputStream in = httpclient.execute(urlGet).getEntity()
					.getContent();
			byte[] buf = new byte[1024];
			int len = 0;
			while ((len = in.read(buf)) > 0) {
				URLContent += new String(buf, 0, len);
			}
		} catch (Exception e) {

			System.out.println("======================");
			e.printStackTrace();
		}
		/*
		 * try { Thread.sleep(2500); } catch (InterruptedException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); }
		 */
		return URLContent;
	}

	/**
	 * 
	 * URL直接下载文件
	 * 
	 * String url =
	 * "http://archive.apache.org/dist/abdera/1.0/apache-abdera-1.0-src.tar.gz";
	 * String path = "E:/temp/wedqwe/qweqwe/apache-abdera-1.0-src.tar.gz";
	 * 
	 * @param url
	 * @param path
	 * @throws IOException
	 */

	public static void downloadFile(String url, String path) throws IOException {

		HttpClient client = null;
		try {
			// 创建HttpClient对象
			client = new DefaultHttpClient();
			// 获得HttpGet对象
			HttpGet httpGet = new HttpGet(url);
			// 发送请求获得返回结果
			HttpResponse response = client.execute(httpGet);
			// 如果成功
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				byte[] result = EntityUtils.toByteArray(response.getEntity());
				BufferedOutputStream bw = null;
				try {
					// 创建文件对象
					File f = new File(path);
					// 创建文件路径
					if (!f.getParentFile().exists())
						f.getParentFile().mkdirs();
					// 写入文件
					bw = new BufferedOutputStream(new FileOutputStream(path));
					bw.write(result);
				} catch (Exception e) {
					System.out.println("保存文件错误,path=" + path + ",url=" + url);
				} finally {
					try {
						if (bw != null)
							bw.close();
					} catch (Exception e) {
						System.out
								.println("finally BufferedOutputStream shutdown close");
					} finally {
						try {
							client.getConnectionManager().shutdown();
						} catch (Exception e) {
							System.out
									.println("finally HttpClient shutdown error");
						}
					}
				}
			}
			// 如果失败
			else {
				StringBuffer errorMsg = new StringBuffer();
				errorMsg.append("httpStatus:");
				errorMsg.append(response.getStatusLine().getStatusCode());
				errorMsg.append(response.getStatusLine().getReasonPhrase());
				errorMsg.append(", Header: ");
				Header[] headers = response.getAllHeaders();
				for (Header header : headers) {
					errorMsg.append(header.getName());
					errorMsg.append(":");
					errorMsg.append(header.getValue());
				}
				System.out.println("HttpResonse Error:" + errorMsg);
			}
		} catch (ClientProtocolException e) {
			System.out.println("下载文件保存到本地,http连接异常,path=" + path + ",url="
					+ url);
			throw e;
		} catch (IOException e) {
			System.out.println("下载文件保存到本地,文件操作异常,path=" + path + ",url=" + url);
			throw e;
		} finally {
			try {
				client.getConnectionManager().shutdown();
			} catch (Exception e) {
				System.out.println("finally HttpClient shutdown error");
			}
		}
	}

	public static String regex_href2dir(String str) {
		// 单条替换
		String result = null;
		Pattern pattern = Pattern.compile("<a href=\"(.*)\">");
		Matcher matcher = pattern.matcher(str);
		if (1 != matcher.groupCount()) {
			System.out.println("只能替换一条");
		}
		while (matcher.find()) {
			result = matcher.group();
			result = result.replaceAll("(<a href=\")|(\">)", "");

		}
		return result;
	}
		
	// 根据给定的url找到 页面可供下载的链接 list<String>
	public static List<String> getDirList(String url) {
		// 根据给定的url找到 页面可供下载的链接

		List list = new ArrayList<String>();
		String URLContext = CrawlerUtils.getURLContent(url);
		Pattern pattern = Pattern
				.compile("<img src=\"(.*)\" alt=\"(.*)\"> <a href=\"(.*)\">(.*)</a>");
		Matcher matcher = pattern.matcher(URLContext);
		matcher.find(); // 格根据网站格式 去除第一条记录 Parent Directory
		while (matcher.find()) {
			String str = CrawlerUtils.regex_href2dir(matcher.group());
			list.add(str.trim());
		}
		return list;
	}

}
