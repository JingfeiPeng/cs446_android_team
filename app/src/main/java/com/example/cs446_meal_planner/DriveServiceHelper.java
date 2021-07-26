package com.example.cs446_meal_planner;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.http.FileContent;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class DriveServiceHelper implements Serializable {
    private final Executor executor;
    private Drive driveService;


    public DriveServiceHelper(Drive driveService) {
        this.executor = Executors.newSingleThreadExecutor();
        this.driveService = driveService;
    }

    public static Drive getGoogleDriveService(Context context, GoogleSignInAccount account, String appName) {
        GoogleAccountCredential credential =
                GoogleAccountCredential.usingOAuth2(
                        context, Collections.singleton(DriveScopes.DRIVE_FILE));
        credential.setSelectedAccount(account.getAccount());
        com.google.api.services.drive.Drive googleDriveService =
                new com.google.api.services.drive.Drive.Builder(
                        AndroidHttp.newCompatibleTransport(),
                        new GsonFactory(),
                        credential)
                        .setApplicationName(appName)
                        .build();
        return googleDriveService;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public Task<String> createImage(String filePath){
        return Tasks.call(executor, () -> {
            File fileMetaData = new File();
            fileMetaData.setName("Recipe" + UUID.randomUUID());

            java.io.File file = new java.io.File(filePath);

            FileContent mediaContent = new FileContent(Files.probeContentType(file.toPath()), file);

            File myFile = null;

            try{
                myFile = driveService.files().create(fileMetaData, mediaContent).execute();
            }catch(Exception e){
                e.printStackTrace();
            }

            if(myFile == null){
                throw new IOException("Null result when requesting file creation");
            }

            return myFile.getId();
        });
    }


    public Task<Bitmap> readFile(String fileId) {
        return Tasks.call(executor, () -> {
            // Retrieve the metadata as a File object.
            OutputStream outputStream = new ByteArrayOutputStream();
            driveService.files().get(fileId)
                    .executeMediaAndDownloadTo(outputStream);
            byte[] data = ((ByteArrayOutputStream) outputStream).toByteArray();
            Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            return bitmap;
        });

    }
}
