package boco.configuration;

import boco.component.BocoHasher;
import boco.model.profile.Personal;
import boco.model.profile.Profile;
import boco.model.rental.*;
import boco.model.profile.Notification;
import boco.repository.profile.NotificationRepository;
import boco.repository.profile.ProfileRepository;
import boco.repository.rental.*;
import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;


@Configuration
public class TempUserAddV2 {

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
            try {
                String letmepassHash = BocoHasher.encode("letmepass");

                int teamProfiles = 10;
                int extraProfiles = 3; // 3 extra profiles
                int totalProfiles = teamProfiles + extraProfiles;
                List<Profile> profileList = new ArrayList<>();

                int listings = 100;
                List<Listing> listingList = new ArrayList<>();

                int leases = 20;
                List<Lease> leaseList = new ArrayList<>();
                boolean includeReviews = true;

                List<CategoryType> categoryList = new ArrayList<>();

                Faker faker = new Faker(); // Used to create fake data

                // Making categories
                CategoryType categoryType = new CategoryType();
                categoryType.setName("Sport/Hiking");
                categoryList.add(categoryType);
                CategoryType categoryType1 = new CategoryType();
                categoryType1.setName("Electronics");
                categoryList.add(categoryType1);
                CategoryType categoryType2 = new CategoryType();
                categoryType2.setName("Vehicle");
                categoryList.add(categoryType2);
                CategoryType categoryType3 = new CategoryType();
                categoryType3.setName("Tools");
                categoryList.add(categoryType3);
                CategoryType categoryType4 = new CategoryType();
                categoryType4.setName("Interior");
                categoryList.add(categoryType4);
                CategoryType categoryType5 = new CategoryType();
                categoryType5.setName("Hobby/Entertainment");
                categoryList.add(categoryType5);
                CategoryType categoryType6 = new CategoryType();
                categoryType6.setName("School/Office");
                categoryList.add(categoryType6);
                CategoryType categoryType7 = new CategoryType();
                categoryType7.setName("Home/Garden");
                categoryList.add(categoryType7);
                CategoryType categoryType8 = new CategoryType();
                categoryType8.setName("Fashion");
                categoryList.add(categoryType8);
                CategoryType categoryType9 = new CategoryType();
                categoryType9.setName("Musical Instruments");
                categoryList.add(categoryType9);

                categoryTypeRepository.save(categoryType);
                categoryTypeRepository.save(categoryType1);
                categoryTypeRepository.save(categoryType2);
                categoryTypeRepository.save(categoryType3);
                categoryTypeRepository.save(categoryType4);
                categoryTypeRepository.save(categoryType5);
                categoryTypeRepository.save(categoryType6);
                categoryTypeRepository.save(categoryType7);
                categoryTypeRepository.save(categoryType8);
                categoryTypeRepository.save(categoryType9);


                // Making profiles. Password for all profiles are letmepass
                // Profiles for team
                var p = Personal.builder()
                        .username("")
                        .email("")
                        .description("This user does not exist")
                        .displayName("Deleted User")
                        .passwordHash("donothashthispassword")
                        .address(faker.address().fullAddress())
                        .location(faker.address().latitude() + ":" + faker.address().longitude())
                        .tlf("")
                        .isVerified(false)
                        .ratingAsLeasee(0d)
                        .ratingListing(0d)
                        .ratingAsOwner(0d)
                        .deactivated(null)
                        .build();

                var p1 = Personal.builder()
                        .username("emilgl")
                        .email("gluckemil@gmail.com")
                        .description(faker.country().capital())
                        .displayName("Emil")
                        .passwordHash(letmepassHash)
                        .address(faker.address().fullAddress())
                        .location(faker.address().latitude() + ":" + faker.address().longitude())
                        .tlf("12345678")
                        .isVerified(false)
                        .ratingAsLeasee((double) faker.random().nextInt(0, 5))
                        .ratingListing((double) faker.random().nextInt(0, 5))
                        .ratingAsOwner((double) faker.random().nextInt(0, 5))
                        .deactivated(null)
                        .build();
                var p2 = Personal.builder()
                        .username("olavdei")
                        .email("olav@gmail.com")
                        .description(faker.country().capital())
                        .displayName("Olav")
                        .passwordHash(letmepassHash)
                        .address(faker.address().fullAddress())
                        .location(faker.address().latitude() + ":" + faker.address().longitude())
                        .tlf("48329432")
                        .isVerified(false)
                        .ratingAsLeasee((double) faker.random().nextInt(0, 5))
                        .ratingListing((double) faker.random().nextInt(0, 5))
                        .ratingAsOwner((double) faker.random().nextInt(0, 5))
                        .deactivated(null)
                        .build();
                var p3 = Personal.builder()
                        .username("askros")
                        .email("ask@gmail.com")
                        .description(faker.country().capital())
                        .displayName("Ask")
                        .passwordHash(letmepassHash)
                        .address(faker.address().fullAddress())
                        .location(faker.address().latitude() + ":" + faker.address().longitude())
                        .tlf("13718237")
                        .isVerified(false)
                        .ratingAsLeasee((double) faker.random().nextInt(0, 5))
                        .ratingListing((double) faker.random().nextInt(0, 5))
                        .ratingAsOwner((double) faker.random().nextInt(0, 5))
                        .deactivated(null)
                        .build();
                var p4 = Personal.builder()
                        .username("eliaseb")
                        .email("elias@gmail.com")
                        .description(faker.country().capital())
                        .displayName("Elias")
                        .passwordHash(letmepassHash)
                        .address(faker.address().fullAddress())
                        .location(faker.address().latitude() + ":" + faker.address().longitude())
                        .tlf("94729333")
                        .isVerified(false)
                        .ratingAsLeasee((double) faker.random().nextInt(0, 5))
                        .ratingListing((double) faker.random().nextInt(0, 5))
                        .ratingAsOwner((double) faker.random().nextInt(0, 5))
                        .deactivated(null)
                        .build();
                var p5 = Personal.builder()
                        .username("tobigi")
                        .email("tobias@gmail.com")
                        .description(faker.country().capital())
                        .displayName("Tobias")
                        .passwordHash(letmepassHash)
                        .address(faker.address().fullAddress())
                        .location(faker.address().latitude() + ":" + faker.address().longitude())
                        .tlf("87234823")
                        .isVerified(false)
                        .ratingAsLeasee((double) faker.random().nextInt(0, 5))
                        .ratingListing((double) faker.random().nextInt(0, 5))
                        .ratingAsOwner((double) faker.random().nextInt(0, 5))
                        .deactivated(null)
                        .build();
                var p6 = Personal.builder()
                        .username("fanuel")
                        .email("fanuel@gmail.com")
                        .description(faker.country().capital())
                        .displayName("Fanuel")
                        .passwordHash(letmepassHash)
                        .address(faker.address().fullAddress())
                        .location(faker.address().latitude() + ":" + faker.address().longitude())
                        .tlf("12777777")
                        .isVerified(false)
                        .ratingAsLeasee((double) faker.random().nextInt(0, 5))
                        .ratingListing((double) faker.random().nextInt(0, 5))
                        .ratingAsOwner((double) faker.random().nextInt(0, 5))
                        .deactivated(null)
                        .build();
                var p7 = Personal.builder()
                        .username("jonmk")
                        .email("jonmartin@gmail.com")
                        .description(faker.country().capital())
                        .displayName("Jon Martin")
                        .passwordHash(letmepassHash)
                        .address(faker.address().fullAddress())
                        .location(faker.address().latitude() + ":" + faker.address().longitude())
                        .tlf("12345678")
                        .isVerified(false)
                        .ratingAsLeasee((double) faker.random().nextInt(0, 5))
                        .ratingListing((double) faker.random().nextInt(0, 5))
                        .ratingAsOwner((double) faker.random().nextInt(0, 5))
                        .deactivated(null)
                        .build();
                var p8 = Personal.builder()
                        .username("sindrgl")
                        .email("sindre@gmail.com")
                        .description(faker.country().capital())
                        .displayName("Sindre")
                        .passwordHash(letmepassHash)
                        .address(faker.address().fullAddress())
                        .location(faker.address().latitude() + ":" + faker.address().longitude())
                        .tlf("12345678")
                        .isVerified(false)
                        .ratingAsLeasee((double) faker.random().nextInt(0, 5))
                        .ratingListing((double) faker.random().nextInt(0, 5))
                        .ratingAsOwner((double) faker.random().nextInt(0, 5))
                        .deactivated(null)
                        .build();
                var p9 = Personal.builder()
                        .username("oyvibjo")
                        .email("oyvind@gmail.com")
                        .description(faker.country().capital())
                        .displayName("Oyvind")
                        .passwordHash(letmepassHash)
                        .address(faker.address().fullAddress())
                        .location(faker.address().latitude() + ":" + faker.address().longitude())
                        .tlf("12345678")
                        .isVerified(false)
                        .ratingAsLeasee((double) faker.random().nextInt(0, 5))
                        .ratingListing((double) faker.random().nextInt(0, 5))
                        .ratingAsOwner((double) faker.random().nextInt(0, 5))
                        .deactivated(null)
                        .build();

                profileRepository.save(p);
                profileList.add(p);
                profileRepository.save(p1);
                profileList.add(p1);
                profileRepository.save(p2);
                profileList.add(p2);
                profileRepository.save(p3);
                profileList.add(p3);
                profileRepository.save(p4);
                profileList.add(p4);
                profileRepository.save(p5);
                profileList.add(p5);
                profileRepository.save(p6);
                profileList.add(p6);
                profileRepository.save(p7);
                profileList.add(p7);
                profileRepository.save(p8);
                profileList.add(p8);
                profileRepository.save(p9);
                profileList.add(p9);


                // Random profiles
                for (int i = 0; i < extraProfiles; i++) {
                    Name name = faker.name();
                    var np = Personal.builder()
                            .username(name.username())
                            .address(faker.address().fullAddress())
                            .location(faker.address().latitude() + ":" + faker.address().longitude())
                            .deactivated(null)
                            .description(faker.lorem().characters(10))
                            .displayName(name.fullName())
                            .isVerified(false)
                            .passwordHash(letmepassHash)
                            .tlf(faker.phoneNumber().phoneNumber())
                            .ratingAsLeasee((double) faker.random().nextInt(0, 5))
                            .ratingListing((double) faker.random().nextInt(0, 5))
                            .ratingAsOwner((double) faker.random().nextInt(0, 5))
                            .build();
                    profileList.add(np);
                    profileRepository.save(np);
                }

                File file = new File("src/main/resources/testbilde2.png");
                byte[] imageBytes = Files.readAllBytes(file.toPath());


                // Making listings
                var deletedListing = Listing.builder().description("deleted listing")
                        .isActive(false).name("Deleted listing").build();
                listingRepository.save(deletedListing);


                for (int i = 0; i < listings; i++) {
                    String name;
                    if (i % 4 == 0) {
                        name = faker.animal().name();
                    } else if (i % 4 == 1) {
                        name = faker.book().title();
                    } else if (i % 4 == 2) {
                        name = faker.music().instrument();
                    } else {
                        name = faker.pokemon().name();
                    }
                    ArrayList<Image> images = new ArrayList<>();

                    int rand = faker.random().nextInt(0, 2);
                    String priceType;
                    if (rand == 0){
                        priceType = "Hour";
                    } else if (rand == 1){
                        priceType = "Day";
                    } else{
                        priceType = "Week";
                    }

                    var l = Listing.builder()
                            .name(name)
                            .price((double) faker.random().nextInt(10, 10000))
                            .description(faker.lorem().characters(faker.random().nextInt(3, 200)))
                            .profile(profileList.get(faker.random().nextInt(1, totalProfiles - 1)))
                            .isActive(true)
                            .lastChanged(null)
                            .priceType(priceType)
                            .images(null) // images
                            .categoryTypes(List.of(categoryList.get(faker.random().nextInt(0, categoryList.size()-1))))
                            .build();
                    listingRepository.save(l);
                    Image image = new Image();
                    image.setImage(imageBytes);
                    image.setListing(l);
                    images.add(image);
                    imageRepository.save(image);
                    l.setImages(images);
                    listingRepository.save(l);
                    listingList.add(l);
                }

                // Making leases
                for (int i = 0; i < leases; i++) {
                    long fromDate = 1651478864L*1000L + faker.random().nextInt(10000, 100000);
                    long toDate = fromDate + faker.random().nextInt(10, 10000)*1000L;
                    Listing listing = listingList.get(faker.random().nextInt(0, listings - 1));


                    var l = Lease.builder()
                            .fromDatetime(fromDate)
                            .toDatetime(toDate)
                            .isApproved(i % 2 == 0)
                            .isCompleted(false)
                            .listing(listing)
                            .owner(listing.getProfile())
                            .profile(profileList.get(faker.random().nextInt(1, totalProfiles - 1)))
                            .itemReview(null)
                            .leaseeReview(null)
                            .ownerReview(null)
                            .build();
                    leaseRepository.save(l);


                    if (includeReviews) {
                        Review r1 = new Review((double) faker.random().nextInt(0, 5), faker.music().genre());
                        Review r2 = new Review((double) faker.random().nextInt(0, 5), faker.music().genre());
                        Review r3 = new Review((double) faker.random().nextInt(0, 5), faker.music().genre());

                        r1.setLease(l);
                        r2.setLease(l);
                        r3.setLease(l);
                        reviewRepository.save(r1);
                        reviewRepository.save(r2);
                        reviewRepository.save(r3);

                        l.setItemReview(r1);
                        l.setLeaseeReview(r2);
                        l.setOwnerReview(r3);
                        leaseRepository.save(l);
                    }
                }


                // Making notifications
                int numberOfReadAndUnreadNotifications = 10;
                List<Notification> notifications = new ArrayList<>();
                for (Profile profile:profileList) {
                    for (int i = 0; i < numberOfReadAndUnreadNotifications; i++) {
                        Notification read = new Notification().builder().isRead(true).profile(profile)
                                .message("Read :").url(faker.beer().name()).build();
                        notifications.add(read);
                        Notification unread = new Notification().builder().isRead(false).profile(profile)
                                .message("unread :").url(faker.twinPeaks().quote()).build();
                        notifications.add(unread);
                    }
                }
                notificationRepository.saveAll(notifications);

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        };
    }

}
