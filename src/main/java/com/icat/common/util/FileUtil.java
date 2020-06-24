package com.icat.common.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 文件下载工具类 author:icat blog:https://blog.techauch.com
 */
public class FileUtil {

	/**
	 * 下载文件到本地
	 */
	@SuppressWarnings("finally")
	public static boolean download(String urlString, String filename, int timeout) {
		boolean ret = false;
		File file = new File(filename);
		try {
			if (file.exists()) {
				ret = true;
			} else {
				// 构造URL
				URL url = new URL(urlString);
				// 打开连接
				HttpURLConnection con = (HttpURLConnection) url.openConnection();
				con.setConnectTimeout(timeout);
				con.setReadTimeout(timeout);
				con.connect();
				int contentLength = con.getContentLength();
				// 输入流
				InputStream is = con.getInputStream();
				// 1K的数据缓冲
				byte[] bs = new byte[1024];
				// 读取到的数据长度
				int len;
				// 输出的文件流

				File file2 = new File(file.getParent());
				file2.mkdirs();
				if (file.isDirectory()) {

				} else {
					file.createNewFile();// 创建文件
				}
				OutputStream os = new FileOutputStream(file);
				// 开始读取
				while ((len = is.read(bs)) != -1) {
					os.write(bs, 0, len);
				}
				// 完毕，关闭所有链接
				os.close();
				is.close();
				if (contentLength != file.length()) {
					file.delete();
					ret = false;
				} else {
					ret = true;
				}
			}
		} catch (IOException e) {
			file.delete();
			e.printStackTrace();
			ret = false;
		} finally {
			return ret;
		}
	}

	/**
	 * 断点续传
	 */
	@SuppressWarnings("finally")
	public static boolean resumeDownload(String urlString, String filename, int timeout) throws Exception {
		boolean ret = false;
		File fileFinal = new File(filename);
		String tmpFileName = filename + ".tmp";
		File file = new File(tmpFileName);

		try {
			if (fileFinal.exists()) {
				ret = true;
			} else {
				long contentStart = 0;
				File file2 = new File(file.getParent());

				if (file.exists()) {
					contentStart = file.length();
				} else {
					file2.mkdirs();
				}
				// 构造URL
				URL url = new URL(urlString);
				// 打开连接
				HttpURLConnection con = (HttpURLConnection) url.openConnection();
				con.setConnectTimeout(timeout);
				con.setReadTimeout(timeout);
				// 设置续传的点
				if (contentStart > 0) {
					con.setRequestProperty("RANGE", "bytes=" + contentStart + "-");
				}
				con.connect();
				// 输入流
				InputStream is = con.getInputStream();
				// 100Kb的数据缓冲
				byte[] bs = new byte[100 * 1024];
				// 读取到的数据长度
				int len;
				RandomAccessFile oSavedFile = new RandomAccessFile(tmpFileName, "rw");
				oSavedFile.seek(contentStart);
				// 开始读取
				while ((len = is.read(bs)) != -1) {
					oSavedFile.write(bs, 0, len);
				}
				// 完毕，关闭所有链接
				oSavedFile.close();
				is.close();
				file.renameTo(fileFinal);
				ret = true;
			}
		} catch (IOException e) {
			file.delete();
			ret = false;
			throw new Exception(e);
		} finally {
			return ret;
		}
	}

	/**
	 * 删除文件夹 folderPath 文件夹完整绝对路径
	 */
	public static void delFolder(String folderPath) {
		try {
			delAllFile(folderPath); // 删除完里面所有内容
			String filePath = folderPath;
			filePath = filePath.toString();
			java.io.File myFilePath = new java.io.File(filePath);
			myFilePath.delete(); // 删除空文件夹
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 删除单个文件 fileName 要删除的文件的文件名
	 */
	public static boolean deleteFile(String fileName) {
		File file = new File(fileName);
		// 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
		if (file.exists() && file.isFile()) {
			if (file.delete()) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * 删除指定文件夹下所有文件 path 文件夹完整绝对路径
	 */
	public static boolean delAllFile(String path) {
		boolean flag = false;
		File file = new File(path);
		if (!file.exists()) {
			return flag;
		}
		if (!file.isDirectory()) {
			return flag;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
				delFolder(path + "/" + tempList[i]);// 再删除空文件夹
				flag = true;
			}
		}
		return flag;
	}

	/**
	 * 读取文件内容
	 *
	 * @param is
	 * @return
	 */
	public static String readFile(InputStream is) {
		BufferedReader br = null;
		StringBuffer sb = new StringBuffer();
		try {
			br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			String readLine = null;
			while ((readLine = br.readLine()) != null) {
				sb.append(readLine);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	/**
	 * 从文件路径得到文件名。
	 *
	 * @param filePath
	 * @return
	 */
	public static String getFileName(String filePath) {
		File file = new File(filePath);
		return file.getName();
	}

	/**
	 * 从文件名得到文件绝对路径。
	 *
	 * @param fileName
	 * @return
	 */
	public static String getFilePath(String fileName) {
		File file = new File(fileName);
		return file.getAbsolutePath();
	}

	/**
	 * 将DOS/Windows格式的路径转换为UNIX/Linux格式的路径。
	 *
	 * @param filePath
	 * @return
	 */
	public static String toUNIXpath(String filePath) {
		return filePath.replace("", "/");
	}

	/**
	 * 得到文件后缀名
	 *
	 * @param fileName
	 * @return
	 */
	public static String getFileExt(String fileName) {
		int point = fileName.lastIndexOf('.');
		int length = fileName.length();
		if (point == -1 || point == length - 1) {
			return "";
		} else {
			return fileName.substring(point + 1, length);
		}
	}

	/**
	 * 将文件名中的类型部分去掉。
	 *
	 * @param filename
	 * @return
	 */
	public static String removeFileExt(String filename) {
		int index = filename.lastIndexOf(".");
		if (index != -1) {
			return filename.substring(0, index);
		} else {
			return filename;
		}
	}
}
