package com.snipzer.contact.service;

import com.google.appengine.api.blobstore.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.snipzer.contact.dao.UserDaoObjectify;
import com.snipzer.contact.entity.User;
import com.snipzer.contact.util.StringUtil;

import java.io.IOException;
import java.util.*;

public class PhotoService {

    private static PhotoService INSTANCE = new PhotoService();
    public static PhotoService getInstance() {
        return INSTANCE;
    }
    private PhotoService() {}

    private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();

    public void prepareUploadURL(User contact) {
        String uploadURL = blobstoreService.createUploadUrl(StringUtil.API_V0_PHOTO +contact.id);
        contact.uploadURL(uploadURL);
    }

    public void prepareDownloadURL(User contact) {
        BlobKey photoKey = contact.photoKey;
        if (photoKey != null) {
            String url = StringUtil.API_V0_PHOTO  + contact.id + StringUtil.SLASH + photoKey.getKeyString();
            contact.downloadURL(url);
        }
    }

    public void updatePhoto(Long id, HttpServletRequest req) {
        Map<String, List<BlobKey>> uploads = blobstoreService.getUploads(req);
        if (!uploads.keySet().isEmpty()) {
            // TODO : delete old photo from BlobStore to save disk space
            // update photo BlobKey in Contact entity
            Iterator<String> names = uploads.keySet().iterator();
            String name = names.next();
            List<BlobKey> keys = uploads.get(name);
            User contact = UserDaoObjectify.getInstance().get(id).photoKey(keys.get(0));
            UserDaoObjectify.getInstance().save(contact);
        }
    }

    public void serve(BlobKey blobKey, HttpServletResponse resp)
            throws IOException {
        BlobInfoFactory blobInfoFactory = new BlobInfoFactory(DatastoreServiceFactory.getDatastoreService());
        BlobInfo blobInfo = blobInfoFactory.loadBlobInfo(blobKey);
        resp.setHeader(StringUtil.CONTENT_DISPOSITION, StringUtil.ATTACHMENT_FILENAME + blobInfo.getFilename());
        blobstoreService.serve(blobKey, resp);
    }

}