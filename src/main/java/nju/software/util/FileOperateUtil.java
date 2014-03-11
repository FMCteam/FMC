package nju.software.util;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * 
 * @author geloin
 * @date 2012-5-5 下午12:05:57
 */
public class FileOperateUtil {
	private static final String REALNAME = "realName";
	private static final String STORENAME = "storeName";
	private static final String SIZE = "size";
	private static final String SUFFIX = "suffix";
	private static final String CONTENTTYPE = "contentType";
	private static final String CREATETIME = "createTime";
	private static final String UPLOADDIR = "uploadDir/";

	/**
	 * 将上传的文件进行重命名
	 * 
	 * @author geloin
	 * @date 2012-3-29 下午3:39:53
	 * @param name
	 * @return
	 */
	private static String rename(String name) {

		Long now = Long.parseLong(new SimpleDateFormat("yyyyMMddHHmmss")
				.format(new Date()));
		Long random = (long) (Math.random() * now);
		String fileName = now + "" + random;

		if (name.indexOf(".") != -1) {
			fileName += name.substring(name.lastIndexOf("."));
		}

		return fileName;
	}

	/**
	 * 压缩后的文件名
	 * 
	 * @author geloin
	 * @date 2012-3-29 下午6:21:32
	 * @param name
	 * @return
	 */
	private static String zipName(String name) {
		String prefix = "";
		if (name.indexOf(".") != -1) {
			prefix = name.substring(0, name.lastIndexOf("."));
		} else {
			prefix = name;
		}
		return prefix + ".zip";
	}

	/**
	 * 上传文件
	 * 
	 * @author zhanglei
	 * @date 2012-5-5 下午12:25:47
	 * @param request
	 * @param newfilename是新建的文件名
	 *            ，不包含后缀名，若为null，则用原来的文件名。
	 * @param fileid为对应控件的id
	 * @return 失败返回null
	 */

	public static File Upload(HttpServletRequest request, String filedir,
			String newfilename, String fileid) {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile file = multipartRequest.getFile(fileid);
		if (newfilename == null || newfilename.equals("")) {
			newfilename = file.getOriginalFilename();
		} else {
			newfilename = newfilename + "."
					+ getExtensionName(file.getOriginalFilename());
		}
		File tmpfile = new File(filedir, "tmpfile");
		tmpfile.mkdirs();

		try {
			file.transferTo(tmpfile);
		} catch (Exception e) {
			e.printStackTrace();
			tmpfile.delete();
			return null;
		}

		File save = new File(filedir, newfilename);
		if (save.exists()) {
			save.delete();
		}
		tmpfile.renameTo(save);
		return save;

	}

	public static String getExtensionName(String filename) {
		if ((filename != null) && (filename.length() > 0)) {
			int dot = filename.lastIndexOf('.');
			if ((dot > -1) && (dot < (filename.length() - 1))) {
				return filename.substring(dot + 1);
			}
		}
		return filename;
	}

	// /**
	// * 下载
	// *
	// * @author geloin
	// * @date 2012-5-5 下午12:25:39
	// * @param request
	// * @param response
	// * @param storeName
	// * @param contentType
	// * @param realName
	// * @throws Exception
	// */
	// public static void download(HttpServletRequest request,
	// HttpServletResponse response, String storeName, String contentType,
	// String realName) throws Exception {
	// response.setContentType("text/html;charset=UTF-8");
	// request.setCharacterEncoding("UTF-8");
	// BufferedInputStream bis = null;
	// BufferedOutputStream bos = null;
	//
	// String ctxPath = request.getSession().getServletContext()
	// .getRealPath("/")
	// + FileOperateUtil.UPLOADDIR;
	// String downLoadPath = ctxPath + storeName;
	//
	// long fileLength = new File(downLoadPath).length();
	//
	// response.setContentType(contentType);
	// response.setHeader("Content-disposition", "attachment; filename="
	// + new String(realName.getBytes("utf-8"), "ISO8859-1"));
	// response.setHeader("Content-Length", String.valueOf(fileLength));
	//
	// bis = new BufferedInputStream(new FileInputStream(downLoadPath));
	// bos = new BufferedOutputStream(response.getOutputStream());
	// byte[] buff = new byte[2048];
	// int bytesRead;
	// while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
	// bos.write(buff, 0, bytesRead);
	// }
	// bis.close();
	// bos.close();
	// }

	/**
	 * 下载
	 * 
	 * @author zhanglei
	 * @date 2012-5-5 下午12:25:39
	 * @param response
	 * @throws Exception
	 */
	public static void Download(HttpServletResponse response,
			String absolutepath) {
		try {
			OutputStream os = response.getOutputStream();
			response.reset();
			response.setContentType("application/octet-stream; charset=utf-8");
			response.setHeader("Content-Disposition", "attachment; filename="
					+ getFileName(absolutepath));
			File pfile = new File(absolutepath);
			os.write(FileUtils.readFileToByteArray(pfile));
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	public static String getFileName(String absolutepath) {
		String fileName = absolutepath;
		if ((absolutepath != null) && (absolutepath.length() > 0)) {
			int dot = absolutepath.lastIndexOf(File.separator);
			if ((dot > -1) && (dot < (absolutepath.length() - 1))) {
				String temp = absolutepath.substring(dot + 1);
				try {
					fileName = URLEncoder.encode(temp, "UTF-8");
					if (fileName.length() > 150) {
						fileName = new String(temp.getBytes("gb2312"), "ISO8859-1");
					}
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return fileName;
	}
}