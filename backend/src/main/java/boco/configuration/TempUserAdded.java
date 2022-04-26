package boco.configuration;

import boco.models.profile.Notification;
import boco.models.profile.Personal;
import boco.models.profile.Professional;
import boco.models.profile.Profile;
import boco.models.rental.*;
import boco.repository.profile.NotificationRepository;
import boco.repository.profile.ProfileRepository;
import boco.repository.rental.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Configuration
public class TempUserAdded {

    @Bean
    CommandLineRunner simpleRunner(ProfileRepository profileRepository,
                                   ReviewRepository reviewRepository,
                                   CategoryTypeRepository categoryTypeRepository,
                                   ListingRepository listingRepository,
                                   ImageRepository imageRepository,
                                   LeaseRepository leaseRepository,
                                   NotificationRepository notificationRepository
                                   ){

        return args ->{
            int magnitude = 10;

            //Defaults
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

            categoryTypeRepository.save(categoryType);
            categoryTypeRepository.save(categoryType1);
            categoryTypeRepository.save(categoryType2);


            //Customs
            try {

                List<Profile> profileList = new ArrayList<>();
                for (int i = 0; i < magnitude; i++) {
                    if (i%2==0){
                        Profile p = new Personal(i+"", i+"@mail.no", i+"", i+"", "pass", i+"", i+"");
                        profileList.add(p);
                    }else {
                        Profile p = new Professional(i+"", i+"@mail.no", i+"", i+"", "pass", i+"", i+"");
                        profileList.add(p);
                    }
                }
                profileRepository.saveAll(profileList);

                List<Review> reviews = new ArrayList<>();
                for (int i = 0; i < magnitude*5; i++) {
                    Review rev = new Review();
                    rev.setComment("Lorem");
                    rev.setRating(Math.random()*5.0);
                }
                reviewRepository.saveAll(reviews);

                List<Listing> listings = new ArrayList<>();
                for (int i = 0; i < magnitude*5; i++) {
                    Listing listing = new Listing();
                    listing.setName("Lorem");
                    listing.setAddress("Ipsum");
                    listing.setDescription("Kyrie eleison");
                    listing.setActive(i%2==0);
                    listing.setAvailable(i%3==0);
                    listing.setLastChanged(Timestamp.valueOf(LocalDateTime.now()));
                    listing.setPrice(i*10);
                    listing.setPriceType("Hour");
                    listing.setRating(i/magnitude);
                    listing.setCategoryTypes(categoryTypes);
                    listing.setProfile(profileRepository.getOne(1l));
                }
                listingRepository.saveAll(listings);



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

                reviewRepository.save(review);
                reviewRepository.save(review1);
                reviewRepository.save(review2);
                reviewRepository.save(review3);
                reviewRepository.save(review4);


                Personal profile = new Personal("emilgl", "gluckemil@gmail.com", "Test",
                        "Emil", "letmepass","Baerum", "12345678");
                Personal profile1 = new Personal("olavdei", "olav@gmail.com", "Test1",
                        "Olav", "letmepass","Baerum", "12345677");
                Personal profile2 = new Personal("askros", "ask@gmail.com", "Test2",
                        "Ask", "letmepass","Baerum", "12345777");
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

                profileRepository.save(profile);
                profileRepository.save(profile1);
                profileRepository.save(profile2);
                profileRepository.save(profile3);
                profileRepository.save(profile4);
                profileRepository.save(profile5);
                profileRepository.save(profile6);
                profileRepository.save(profile7);
                profileRepository.save(profile8);

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
                listing.setCategoryTypes(categoryTypes);
                listing.setProfile(profile8);

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
                listing1.setCategoryTypes(categoryTypes1);
                listing1.setProfile(profile);

                listingRepository.save(listing);
                listingRepository.save(listing1);

                Image image = new Image();
                image.setImage(new byte[0]);
                image.setCaption("Wonderful air");
                image.setListing(listing);
                Image image1 = new Image();
                image1.setImage(new byte[0]);
                image1.setCaption("Epic pic");
                image1.setListing(listing);
                Image image2 = new Image();
                image2.setImage(new byte[0]);
                image2.setCaption("Cool blank");
                image2.setListing(listing1);

                ArrayList<Image> images = new ArrayList<>();
                images.add(image);
                images.add(image1);
                ArrayList<Image> images1 = new ArrayList<>();
                images1.add(image2);
                images1.add(image1);

                imageRepository.save(image);
                imageRepository.save(image1);
                imageRepository.save(image2);


                Lease lease = new Lease();
                lease.setFromDatetime(Timestamp.valueOf(LocalDateTime.of(2022, 6, 17, 15, 0)));
                lease.setToDatetime(Timestamp.valueOf(LocalDateTime.of(2022, 6, 30, 15, 0)));
                lease.setApproved(true);
                lease.setCompleted(false);
                lease.setProfile(profile2);
                lease.setListing(listing1);

                Lease lease1 = new Lease();
                lease1.setFromDatetime(Timestamp.valueOf(LocalDateTime.of(2022, 1, 1, 15, 0)));
                lease1.setToDatetime(Timestamp.valueOf(LocalDateTime.of(2022, 1, 1, 18, 0)));
                lease1.setApproved(true);
                lease1.setCompleted(true);
                lease1.setItemReview(review);
                lease1.setLeaseeReview(review2);
                lease1.setOwnerReview(review1);
                lease1.setProfile(profile7);
                lease1.setListing(listing);

                leaseRepository.save(lease);
                leaseRepository.save(lease1);

                Notification notification = new Notification();
                notification.setIsRead(true);
                notification.setMessage("HEIIII");
                notification.setUrl("path");
                notification.setProfile(profile7);

                ArrayList<Notification> notifyList = new ArrayList<>();

                for (int i = 0; i < 10; i++) {
                    Notification notification1 = new Notification();
                    notification1.setIsRead(i%2==0);
                    notification1.setMessage("HEIIII");
                    notification1.setUrl("path");
                    notification1.setProfile(profile1);
                    notifyList.add(notification1);
                }
                notificationRepository.save(notification);
                notificationRepository.saveAll(notifyList);
            }catch (Exception e){
                System.out.println("Db already populated");
            }
        };
    }

}
