package org.raoul.service;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.raoul.domain.PhotoDTO;
import org.raoul.persistence.PhotoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.log4j.Log4j;
import net.coobird.thumbnailator.Thumbnailator;

@Log4j
@Service
public class PhotoServiceImpl implements PhotoService {

	@Autowired
	PhotoMapper pMapper;

	@Override
	public List<PhotoDTO> upload(MultipartFile[] uploadFiles, String rootPath, String memberUid) {

		String dateFolder = getFolder();

		String dataPath = memberUid + File.separator + dateFolder;
		String dataThumbPath = memberUid + File.separator + dateFolder + File.separator + "thumbnail";

		Path parentPath = Paths.get(rootPath, dataPath);
		Path thumbnailPath = Paths.get(rootPath, dataThumbPath);
		
		log.info("parentPath :" + parentPath);

		if (!Files.exists(parentPath)) {
			log.info("make directories");
			try {
				Files.createDirectories(parentPath);
				Files.createDirectories(thumbnailPath);
			} catch (IOException e) {
				log.error(e);
			}

		}

		List<PhotoDTO> listOfPhoto = new ArrayList<>();

		for (MultipartFile multipartFile : uploadFiles) {
			UUID uuid = UUID.randomUUID();

			log.info("uuid: " + uuid.toString());
			log.info("photoName: " + multipartFile.getOriginalFilename());
			log.info("dataPath: " + dataPath);

			// make a dto that contains information.
			PhotoDTO tdto = new PhotoDTO(null, "thumb_" + uuid.toString(), multipartFile.getOriginalFilename(),
					dataThumbPath, null, null, memberUid);
			PhotoDTO dto = new PhotoDTO(null, uuid.toString(), multipartFile.getOriginalFilename(), dataPath, null,
					null, memberUid);
			log.info("a file that about to be saved : " + dto);

			try {
				
				// save thumbnail
				saveResizeFile(multipartFile, "C:" + File.separator + "upload", tdto, 400, 240);
				// save photo
				saveResizeFile(multipartFile, "C:" + File.separator + "upload", dto, 800, 480);

				// add on the list of dto from this operation
				listOfPhoto.add(dto);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// insert DB
		// Separated from upload to insert. Divide a functionality to upload and insert
		// to DB
		// pMapper.insertList(listOfPhoto);

		return listOfPhoto;
	}

	public void insertList(List<PhotoDTO> list) {

		pMapper.insertList(list);
	}
	
	

	public PhotoDTO read(Integer pno) {
		return pMapper.select(pno);
	}

	public int remove(Integer pno) {

		PhotoDTO targetDto = pMapper.select(pno);

		String targetFolderPath = targetDto.getFolderPath();
		String targetPhotoname = targetDto.getUuid()+"_"+targetDto.getOriginalPhotoName();

		log.info("deleting file " + targetPhotoname + " in " + targetFolderPath);

		int count = pMapper.delete(pno);

		// delete dto first, then delete server file.
		log.info(targetFolderPath + File.separator + targetPhotoname);

		Path targetPath = Paths.get(targetFolderPath, targetPhotoname);
		Path targetThumbPath = Paths.get(targetFolderPath, "thumbnail", "thumb_" + targetPhotoname);

		try {
			Files.deleteIfExists(targetPath);
			Files.deleteIfExists(targetThumbPath);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return count;
	}

	// TODO: remove functionality
//	public int removeList(List<PhotoDTO> list) {
//
//		return 0;
//	}
//
//	public int removeWithBoard(Integer bno) {
////		return pMapper.;
//		return 0;
//	}

	public List<PhotoDTO> getListByBoard(Integer bno) {
		return pMapper.findListByBoard(bno);
	}

	public List<PhotoDTO> getListByMember(String uid) {
		return pMapper.findListByMember(uid);
	}

	// resize and save the file with given parameter
	private void saveResizeFile(MultipartFile uploadFile, String rootPath, PhotoDTO dto, int width, int height)
			throws Exception {

		Path filePath = Paths.get(rootPath, dto.getFolderPath(), dto.getUuid()+"_"+dto.getOriginalPhotoName());
		log.info("filePath=== " + filePath);
		log.info("fileName=== " + filePath.getFileName());
		OutputStream os = Files.newOutputStream(filePath);
		log.info("os:" + os);
		Thumbnailator.createThumbnail(uploadFile.getInputStream(), os, width, height);
		os.close();

	}

	// method that make a name of folder yy/mm/dd
	private String getFolder() {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Date date = new Date();

		String str = sdf.format(date);

		return str.replace("-", File.separator);
	}

	// check file type
	@SuppressWarnings("unused")
	private boolean checkImageType(File file) {

		try {
			String contentType = Files.probeContentType(file.toPath());

			return contentType.startsWith("image");
		} catch (IOException e) {
			log.error(e);
		}
		return false;
	}

}

