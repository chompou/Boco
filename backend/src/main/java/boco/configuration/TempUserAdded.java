package boco.configuration;

import boco.models.profile.Personal;
import boco.models.profile.Professional;
import boco.models.profile.Profile;
import boco.models.rental.CategoryType;
import boco.models.rental.Image;
import boco.models.rental.Listing;
import boco.models.rental.Review;
import boco.repository.profile.ProfileRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class TempUserAdded {

    @Bean
    CommandLineRunner simpleRunner(ProfileRepository profileRepository){
        return args ->{
            Personal profile = new Personal("emilgl", "emil@gmail.com", "Test",
                    "Emil", "letmepass","Bærum", "12345678");
            Personal profile1 = new Personal("olavdei", "olav@gmail.com", "Test1",
                    "Olav", "letmepass","Bærum", "12345677");
            Personal profile2 = new Personal("askros", "ask@gmail.com", "Test2",
                    "Ask", "letmepass","Bærum", "12345777");
            Profile profile3 = new Personal("eliaseb", "elias@gmail.com", "Test3",
                    "Elias", "letmepass","Bergen", "12347777");
            Personal profile4 = new Personal("tobigi", "tobias@gmail.com", "Test4",
                    "Tobias", "letmepass","Oslo", "12377777");
            Professional profile5 = new Professional("fanuel", "fanuel@gmail.com", "Test5",
                    "Fanuel", "letmepass","Vik", "12777777");
            Professional profile6 = new Professional("jonmk", "jonmartin@gmail.com", "Test6",
                    "Jon Martin", "letmepass","Oslo", "17777777");
            Professional profile7 = new Professional("sindrgl", "sindre@gmail.com", "Test7",
                    "Sindre", "letmepass","Auuuuuure", "77777777");
            Professional profile8 = new Professional("oyvibjo", "oyvind@gmail.com", "Test8",
                    "Oyvind", "letmepass","Ottestad", "11777777");

            Review review = new Review();
            review.setComment("Perfect");
            review.setRating(5.0);
            Review review1 = new Review();
            review1.setComment("Great");
            review1.setRating(4.0);
            Review review2 = new Review();
            review2.setComment("Less Great");
            review2.setRating(3.0);
            Review review3 = new Review();
            review3.setComment("Bad");
            review3.setRating(2.0);
            Review review4 = new Review();
            review4.setComment("Terrible");
            review4.setRating(1.0);

            CategoryType categoryType = new CategoryType();
            categoryType.setName("Sport");
            CategoryType categoryType1 = new CategoryType();
            categoryType1.setName("Electronic");
            CategoryType categoryType2 = new CategoryType();
            categoryType2.setName("Car");

            ArrayList<CategoryType> categoryTypes = new ArrayList<>();
            categoryTypes.add(categoryType);
            categoryTypes.add(categoryType2);
            ArrayList<CategoryType> categoryTypes1 = new ArrayList<>();
            categoryTypes1.add(categoryType1);
            categoryTypes1.add(categoryType2);

            Image image = new Image();
            image.setImage(new byte[0]);
            image.setCaption("Wonderful air");
            Image image1 = new Image();
            image1.setImage(new byte[0]);
            image1.setCaption("Epic pic");
            Image image2 = new Image();
            image2.setImage(new byte[0]);
            image2.setCaption("Cool blank");

            ArrayList<Image> images = new ArrayList<>();
            images.add(image);
            images.add(image1);
            ArrayList<Image> images1 = new ArrayList<>();
            images1.add(image2);
            images1.add(image1);

            Listing listing = new Listing();
            listing.setName("Lemon");
            listing.setAddress("Auuuuuure");
            listing.setDescription("Sour Lemon");
            listing.setActive(true);
            listing.setAvailable(true);
            listing.setLastChanged(Timestamp.valueOf(LocalDateTime.now()));
            listing.setPrice(1000000);
            listing.setPriceType("Hour");
            listing.setRating(5);
            listing.setImages(images);
            listing.setCategoryTypes(categoryTypes);

            Listing listing1 = new Listing();
            listing1.setName("Porsche");
            listing1.setAddress("Oslo");
            listing1.setDescription("Cheap car");
            listing1.setActive(true);
            listing1.setAvailable(true);
            listing1.setLastChanged(Timestamp.valueOf(LocalDateTime.now()));
            listing1.setPrice(1);
            listing1.setPriceType("Day");
            listing1.setRating(1);
            listing1.setImages(images1);
            listing.setCategoryTypes(categoryTypes1);




            profileRepository.save(profile);
        };
    }

}
