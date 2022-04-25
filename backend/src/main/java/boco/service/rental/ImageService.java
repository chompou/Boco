package boco.service.rental;

import boco.models.http.ImageResponse;
import boco.models.http.NotificationResponse;
import boco.models.profile.Notification;
import boco.models.rental.Image;
import boco.repository.rental.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ImageService {
    private final ImageRepository imageRepository;

    @Autowired
    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public static List<ImageResponse> convertImage(List<Image> images){
        List<ImageResponse> imageResponse = new ArrayList<>();
        for (Image image :
                images) {
            imageResponse.add(new ImageResponse(image));
        }
        return imageResponse;
    }
}
