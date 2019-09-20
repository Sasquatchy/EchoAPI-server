package org.raoul.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.raoul.domain.PhotoDTO;
import org.raoul.persistence.FrameMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;

import lombok.extern.java.Log;

@Log
@Service
public class FrameServiceImpl implements FrameService {

	@Autowired
	FrameMapper fMapper;

	@Override
	public File makeZipByUfid(String ufid) {

		// delete a tempfile if exist.
		// TODO: DO WE REALLY NEED THIS?
		
		log.info("clearing target space");
		// PATHS ARE NEEDED
		Path zipFolderPath = Paths.get("C:", "upload", "temp");
		Path zipPath = zipFolderPath.resolve(ufid + ".zip");

//		try {
//			Files.delete(zipPath);
//		} catch ( NoSuchFileException e2) {
////			e2.printStackTrace();
//			log.info(e2.toString());
//			log.info("No such file found");
//		}catch(IOException e3) {
//			log.info(e3.toString());
//		}

//		
		// make zip entry
		log.info("make zip entry");

		List<PhotoDTO> listPhoto = null;
		List<Path> listPhotoPath = new ArrayList<Path>();
		List<Path> listTempPhotoPath = new ArrayList<Path>();
		listPhoto = fMapper.getLast20Photos(ufid);

		
		
		//temp dir for temp files from target files
		Path tempDir =null;
		try {
			tempDir= Files.createTempDirectory("Temp_");
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		
		// copy to temp dir
		for (int i= 0; i < listPhoto.size(); ++i) {
			PhotoDTO dto = listPhoto.get(i);
			
			Path sourcePhoto = Paths.get("C:", "upload", dto.getFolderPath(), dto.getUuid() + '_' + dto.getOriginalPhotoName());
			
//			listPhotoPath.add(
//					Paths.get("C:", "upload", dto.getFolderPath(), dto.getUuid() + '_' + dto.getOriginalPhotoName()));
//			listTempPhotoPath.add(Paths.get("C:", "upload", "temp", "tempPhoto", dto.getFolderPath(),
//					dto.getUuid() + '_' + dto.getOriginalPhotoName()));
			Path tempPhoto = tempDir.resolve("slide"+i+".jpg");	//file name : slide12.jpg... and continue...
			log.info(tempPhoto.toString());
			listTempPhotoPath.add(tempPhoto);	
			try {
				Files.copy(sourcePhoto, tempPhoto);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		log.info(listTempPhotoPath.toString());


		// TODO 기존 파일을 임시폴더에 slide숫자.jpg로 저장.
		// 저장후 그 파일들로 압축파일 만들기.

		// making zip file
		log.info("make zip");

		try (OutputStream fout = Files.newOutputStream(zipPath); ZipOutputStream zos = new ZipOutputStream(fout);) {
			listTempPhotoPath.stream().filter(path -> path.toFile().exists()).forEach((path) -> {
				try (FileInputStream fis = new FileInputStream(path.toFile());) {
					log.info("fis.toString(): " + fis.toString());
					log.info("path.getFileName().toString(): " + path.getFileName().toString());
					zos.putNextEntry(new ZipEntry(path.getFileName().toString()));

					byte[] bytes = new byte[1024];
					int length;
					while ((length = fis.read(bytes)) >= 0) {
						zos.write(bytes, 0, length);
					}
				} catch (IOException e) {
					log.info(e.toString());
				}
			});
//			try (OutputStream fout = Files.newOutputStream(zipPath); ZipOutputStream zos = new ZipOutputStream(fout);) {
//				listPhotoPath.stream().filter(path -> path.toFile().exists()).forEach((path) -> {
//					try (FileInputStream fis = new FileInputStream(path.toFile());) {
//						log.info("fis.toString(): " + fis.toString());
//						log.info("path.getFileName().toString(): " + path.getFileName().toString());
//						zos.putNextEntry(new ZipEntry(path.getFileName().toString()));
//						
//						byte[] bytes = new byte[1024];
//						int length;
//						while ((length = fis.read(bytes)) >= 0) {
//							zos.write(bytes, 0, length);
//						}
//					} catch (IOException e) {
//						log.info(e.toString());
//					}
//				});

			log.info("close Entry");
			try {
				zos.closeEntry();
			} catch (IOException e) {
				log.info(e.toString());
			}

		} catch (IOException e1) {
			log.info(e1.toString());
		}

		log.info("send zip");
		File zipFile = zipPath.toFile();
		log.info(zipPath.toString());

		// recursively deleting temp file 
		try {
			FileSystemUtils.deleteRecursively(tempDir);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return zipFile;
	}

}
