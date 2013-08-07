package com.demo.util;

import com.demo.constants.PhotoConstants;
import com.demo.vo.ServerVO;
import org.apache.commons.lang.StringUtils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.util.zip.CRC32;
import java.util.zip.CheckedInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class FileUploadHelper {
    private static final String[] SUPPORT_AUDIO_TYPE = new String[] { ".amr", ".3pg" };

    private static String         DEFAULT_COM_ID     = "123456789";
    private static final int      BUFFER             = 1024;

//    public static void copyFolderToSpecify(File srcPath, File dstPath) throws IOException {
//        if (!srcPath.exists()) {
//            throw new IOException("The source directory is not existed");
//        }
//
//        if (!dstPath.exists()) {
//            dstPath.mkdirs();
//        }
//
//        File[] files = srcPath.listFiles();
//        if (files == null || files.length == 0) {
//            throw new IOException("The source directory is empty");
//        }
//
//        FileChannel in = null;
//        FileChannel out = null;
//        for (File file : files) {
//            try {
//                in = new FileInputStream(file).getChannel();
//                File dstFile = new File(dstPath, file.getName());
//                out = new FileOutputStream(dstFile).getChannel();
//                in.transferTo(0, in.size(), out);
//            } finally {
//                if (in != null)
//                    in.close();
//                if (out != null)
//                    out.close();
//            }
//        }
//    }

    public static File getUploadFilePath(File rootPath,String userId, String fileId) throws IOException {
        if (rootPath == null) {
            throw new IOException("Root Path is not null");
        }
        File filePath = rootPath;
        if (StringUtils.isNotEmpty(userId)) {
            filePath = new File(filePath, userId);
        }
//        System.out.println(filePath.getAbsolutePath());
        if (StringUtils.isNotEmpty(fileId)) {
            filePath = new File(filePath, HashHelper.getMD5FilePathSelector(fileId));
        }
        if (!filePath.exists()) {
            filePath.mkdirs();
        }
        return filePath;
    }

    public static void persistentUpdateFile(File rootPath, String usrId, String fileId, String fileType, InputStream is)
            throws IOException {
        if (StringUtils.isEmpty(fileType)) {
            throw new IOException("File Type is not null");
        }
        File filePath = getUploadFilePath(rootPath, usrId, fileId);

        String fileName = fileId + fileType;
        File file = new File(filePath, fileName);
        if(file.exists()) {
            file.delete();
        }
        write2File(is, file);

        File smallThumbFile = new File(filePath, getThumbnailFileName(fileName, PhotoConstants.THUMB_SMALL_NAME));
        ThumbHelper.scalePhoto(smallThumbFile, file, PhotoConstants.THUMB_SMALL_NAME, 1, true);
    }

    public static void persistentUpdateZipFile(File rootPath, String usrId, String fileId, String zipFileName,
            InputStream is) throws IOException {
        File filePath = rootPath;
        if (!StringUtils.isEmpty(usrId)) {
            filePath = new File(filePath, usrId);
        }

        if (!StringUtils.isEmpty(fileId)) {
            filePath = new File(filePath, HashHelper.getMD5FilePathSelector(fileId));
        }

        if (!filePath.exists()) {
            filePath.mkdirs();
        }
        File file = new File(filePath, zipFileName);
        write2File(is, file);

        // decompress zip file
        decompress(file, filePath);

        // delete zip file
        deleteFile(file.getAbsolutePath());

        // to rename the decompress files and create thumbnail files
        renameAndGenerateThumbnailFiles(filePath.getAbsolutePath(), fileId);
    }

    public static URL getOriginalFileURI(ServerVO server, String fileCreator, String fileId, String fileName)
            throws MalformedURLException {
        String basePath = getBasePathOfOriginalFileURI(server.getWebContext(), fileCreator, fileId, fileName);
        return new URL("http", server.getAddress(), server.getPort(), basePath);
    }

    public static URL getThumbnailFileURI(ServerVO server, String fileCreator, String fileId, String fileName)
            throws MalformedURLException {
        String basePath = getBasePathOfThumbnailFileURI(server.getWebContext(), fileCreator, fileId, fileName);
        return new URL("http", server.getAddress(), server.getPort(), basePath);
    }

    private static void decompress(File srcFile, File destFile) throws IOException {
        CheckedInputStream cis = new CheckedInputStream(new FileInputStream(srcFile), new CRC32());
        ZipInputStream zis = new ZipInputStream(cis);

        ZipEntry entry;
        while ((entry = zis.getNextEntry()) != null) {

            File dirFile = new File(destFile, entry.getName());

            if (entry.isDirectory()) {
                dirFile.mkdirs();
            } else {
                decompressFile(dirFile, zis);
            }

            zis.closeEntry();
        }
        zis.close();
    }

    private static void decompressFile(File destFile, ZipInputStream zis) throws IOException {
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(destFile));

        int count;
        byte data[] = new byte[BUFFER];
        while ((count = zis.read(data, 0, BUFFER)) != -1) {
            bos.write(data, 0, count);
        }

        bos.close();
    }

    private static boolean deleteFile(String deletePath) throws IOException {
        File file = new File(deletePath);
        if (file.isDirectory()) {
            String[] fileList = file.list();
            for (String aFileList : fileList) {
                File delFile = new File(deletePath, aFileList);
                if (!delFile.isDirectory()) {
                    delFile.delete();
                }
            }
            file.delete();
        } else {
            file.delete();
        }
        return true;
    }

    private static boolean renameAndGenerateThumbnailFiles(String fileFolder, String fileName) throws IOException {
        File file = new File(fileFolder);
        if (file.isDirectory()) {
            String[] fileList = file.list();
            for (String originalFileName : fileList) {
                File readFile = new File(fileFolder, originalFileName);
                if (!readFile.isDirectory()) {
                    // Get file's extension type
                    String fileExtension = getExtensionType(originalFileName);
                    String newFileName = fileName + fileExtension;

                    // Original File rename to new name
                    File newFile = new File(fileFolder, newFileName);
                    readFile.renameTo(newFile);

                    // Generate thumbnail files
                    if (!isAudioFile(newFileName)) {
                        File smallThumbFile = new File(fileFolder, getThumbnailFileName(newFileName,
                                PhotoConstants.THUMB_SMALL_NAME));
                        ThumbHelper.scalePhoto(smallThumbFile, newFile, PhotoConstants.THUMB_SMALL_NAME, 1, true);
                    }
                }
            }
        }
        return true;
    }

    public static String getBasePathOfThumbnailFileURI(String webContext, String fileCreator, String fileId,
            String fileName) {
        String thumbnailFileName = getThumbnailFileName(fileName, PhotoConstants.THUMB_SMALL_NAME);
        return getBasePathOfOriginalFileURI(webContext, fileCreator, fileId, thumbnailFileName);
    }

    public static String getBasePathOfOriginalFileURI(String webContext, String fileCreator, String fileId,
            String fileName) {
        StringBuilder sb = new StringBuilder();
        if (StringUtils.isNotEmpty(webContext)) {
            sb.append(webContext);
            sb.append("/");
        }
        if (StringUtils.isNotEmpty(fileCreator)) {
            sb.append(fileCreator);
            sb.append("/");
        }
        if (StringUtils.isNotEmpty(fileId)) {
            String filePath = HashHelper.getMD5FilePathSelector(fileId);
            sb.append(filePath);
            sb.append("/");
        }
        // if (StringUtils.isNotEmpty(fileId)) {
        // sb.append(fileId);
        // sb.append("/");
        // }
        if (StringUtils.isNotEmpty(fileName)) {
            sb.append(fileName);
            sb.append("/");
        }
        String basePath = null;
        if (sb.length() > 0) {
            basePath = sb.substring(0, sb.length() - 1);
        }
        return basePath;
    }

    private static String getThumbnailFileName(String fileName, String dimenString) {
        int fileExtensionIndex = fileName.lastIndexOf(".");
        if (fileExtensionIndex == -1) {
            return fileName + "_" + dimenString;
        }

        String fileExtension = fileName.substring(fileExtensionIndex, fileName.length());
        return fileName.substring(0, fileExtensionIndex) + "_" + dimenString + fileExtension;
    }

    private static boolean isAudioFile(String fileName) {
        String fileExtension = getExtensionType(fileName);
        for (String extension : SUPPORT_AUDIO_TYPE) {
            if (extension.equals(fileExtension)) {
                return true;
            }
        }
        return false;
    }

    private static File write2File(InputStream is, File descFile) throws IOException {
        OutputStream os = null;
        try {
            os = new FileOutputStream(descFile);
            byte buffer[] = new byte[BUFFER];
            while ((is.read(buffer)) != -1) {
                os.write(buffer);
            }
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
                throw e;
            }
        }
        return descFile;
    }

    public static String getExtensionType(String fileName) {
        if(StringUtils.isEmpty(fileName)) {
            throw new IllegalStateException("File name is not empty!");
        }
        int fileExtensionIndex = fileName.lastIndexOf(".");
        if (fileExtensionIndex == -1) {
            throw new IllegalStateException("File has not extension type!");
        }
        return fileName.substring(fileExtensionIndex, fileName.length());
    }

    public static void main(String[] args) throws IOException {
        System.out.println(getExtensionType("xxx.jpg"));

        // String taskId = IdGeneratorHelper.getInstance().generateId();
        //
        // String attachmentId = IdGeneratorHelper.getInstance().generateId();
        // File srcFilePath = getUploadFilePath(new File("c:\\repaircache"),
        // attachmentId);
        // File dstFilePath = getUploadFilePath(new File("c:\\imagecache"),
        // taskId, "711");
        // for (int i = 0; i < 10; i++) {
        // copyFolderToSpecify(srcFilePath, dstFilePath);
        // }
        //
        // copyFolderToSpecify(new
        // File("/home/xw/Documents/uploads/50/ec/d1/714"), new
        // File("/home/xw/Documents/x"));
        // System.out.println(getThumbnailFileName("xxx.jpg",
        // PhotoConstants.THUMB_SMALL_NAME));
        // System.out.println(getThumbnailFileName("yyyy",
        // PhotoConstants.THUMB_SMALL_NAME));
        //
        // String basePath = getBasePathOfOriginalFileURI("imgcache", "123",
        // null, "filename.jpg");
        // String basePath1 =
        // getBasePathOfThumbnailFileURI(URIConstants.PPM_WEB_CONTEXT_ROOT,
        // "123", "711", "filename.jpg");
        // String basePath2 =
        // getBasePathOfClientAppFile(URIConstants.PPM_WEB_CONTEXT_ROOT_CLIENT_APP,
        // null, 2);
        // System.out.println(basePath);
        //
        // URL url = new URL("http", "localhost", 8080, basePath);
        // System.out.println(url.toString());
        // System.out.println(new URL("http", "localhost", 8080,
        // basePath1).toString());
        // System.out.println(new URL("http", "localhost", 8080,
        // basePath2).toString());

        // File rootPath = new File("/home/xw/Documents/uploads");
        // String taskId = "1AF3443E967BEE88D49010E6";
        // String empId = "711";
        // String fileName = "8d52b3f7-4160-4118-b2ef-4cdff995e693.zip";
        //
        // try {
        // persistentUpdateZipFile(rootPath, taskId, empId, fileName, null);
        // } catch (IOException e) {
        // e.printStackTrace(); // To change body of catch statement use File |
        // // Settings | File Templates.
        // }
    }
}
