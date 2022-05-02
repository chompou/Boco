package boco.configuration;

import boco.models.profile.Personal;
import boco.models.profile.Professional;
import boco.models.profile.Profile;
import boco.models.rental.*;
import boco.repository.profile.NotificationRepository;
import boco.repository.profile.ProfileRepository;
import boco.repository.rental.*;
import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
            String letmepassHash = "$2a$12$9FDSwGUAG.n8bv0feCeaj./slkAPYV42sMy4tPe/osKeNp6HRlB8a";
            int teamProfiles = 10;
            int extraProfiles = 3; // 3 extra profiles
            int totalProfiles = teamProfiles + extraProfiles;
            List<Profile> profileList = new ArrayList<>();
            int listings = 100;
            List<Listing> listingList = new ArrayList<>();
            int leases = 20;
            List<Lease> leaseList = new ArrayList<>();

            Faker faker = new Faker();

            // Making profiles. Password for all profiles are letmepass

            // Profiles for team
            // TODO: ADD

            var p = Personal.builder()
                    .username("")
                    .email("")
                    .description("This user does not exist")
                    .displayName("Deleted User")
                    .passwordHash("donothashthispassword")
                    .address("")
                    .tlf("")
                    .isVerified(false)
                    .ratingGiven(0d)
                    .ratingListing(0d)
                    .ratingProfile(0d)
                    .deactivated(null)
                    .build();

            var p1 = Personal.builder()
                    .username("emilgl")
                    .email("gluckemil@gmail.com")
                    .description(faker.country().capital())
                    .displayName("Emil")
                    .passwordHash(letmepassHash)
                    .address("Baerum")
                    .tlf("12345678")
                    .isVerified(false)
                    .ratingGiven((double)faker.random().nextInt(0,5))
                    .ratingListing((double)faker.random().nextInt(0,5))
                    .ratingProfile((double)faker.random().nextInt(0,5))
                    .deactivated(null)
                    .build();
            var p2 = Personal.builder()
                    .username("olavdei")
                    .email("olav@gmail.com")
                    .description(faker.country().capital())
                    .displayName("Olav")
                    .passwordHash(letmepassHash)
                    .address("Baerum")
                    .tlf("48329432")
                    .isVerified(false)
                    .ratingGiven((double)faker.random().nextInt(0,5))
                    .ratingListing((double)faker.random().nextInt(0,5))
                    .ratingProfile((double)faker.random().nextInt(0,5))
                    .deactivated(null)
                    .build();
            var p3 = Personal.builder()
                    .username("askros")
                    .email("ask@gmail.com")
                    .description(faker.country().capital())
                    .displayName("Ask")
                    .passwordHash(letmepassHash)
                    .address("Baerum")
                    .tlf("13718237")
                    .isVerified(false)
                    .ratingGiven((double)faker.random().nextInt(0,5))
                    .ratingListing((double)faker.random().nextInt(0,5))
                    .ratingProfile((double)faker.random().nextInt(0,5))
                    .deactivated(null)
                    .build();
            var p4 = Personal.builder()
                    .username("eliaseb")
                    .email("elias@gmail.com")
                    .description(faker.country().capital())
                    .displayName("Elias")
                    .passwordHash(letmepassHash)
                    .address("Bergen")
                    .tlf("94729333")
                    .isVerified(false)
                    .ratingGiven((double)faker.random().nextInt(0,5))
                    .ratingListing((double)faker.random().nextInt(0,5))
                    .ratingProfile((double)faker.random().nextInt(0,5))
                    .deactivated(null)
                    .build();
            var p5 = Personal.builder()
                    .username("tobigi")
                    .email("tobias@gmail.com")
                    .description(faker.country().capital())
                    .displayName("Tobias")
                    .passwordHash(letmepassHash)
                    .address("Oslo")
                    .tlf("87234823")
                    .isVerified(false)
                    .ratingGiven((double)faker.random().nextInt(0,5))
                    .ratingListing((double)faker.random().nextInt(0,5))
                    .ratingProfile((double)faker.random().nextInt(0,5))
                    .deactivated(null)
                    .build();
            var p6 = Personal.builder()
                    .username("fanuel")
                    .email("fanuel@gmail.com")
                    .description(faker.country().capital())
                    .displayName("Fanuel")
                    .passwordHash(letmepassHash)
                    .address("Vik")
                    .tlf("12777777")
                    .isVerified(false)
                    .ratingGiven((double)faker.random().nextInt(0,5))
                    .ratingListing((double)faker.random().nextInt(0,5))
                    .ratingProfile((double)faker.random().nextInt(0,5))
                    .deactivated(null)
                    .build();
            var p7 = Personal.builder()
                    .username("jonmk")
                    .email("jonmartin@gmail.com")
                    .description(faker.country().capital())
                    .displayName("Jon Martin")
                    .passwordHash(letmepassHash)
                    .address("Oslo")
                    .tlf("12345678")
                    .isVerified(false)
                    .ratingGiven((double)faker.random().nextInt(0,5))
                    .ratingListing((double)faker.random().nextInt(0,5))
                    .ratingProfile((double)faker.random().nextInt(0,5))
                    .deactivated(null)
                    .build();
            var p8 = Personal.builder()
                    .username("sindrgl")
                    .email("sindre@gmail.com")
                    .description(faker.country().capital())
                    .displayName("Sindre")
                    .passwordHash(letmepassHash)
                    .address("Auuuuuure")
                    .tlf("12345678")
                    .isVerified(false)
                    .ratingGiven((double)faker.random().nextInt(0,5))
                    .ratingListing((double)faker.random().nextInt(0,5))
                    .ratingProfile((double)faker.random().nextInt(0,5))
                    .deactivated(null)
                    .build();
            var p9 = Personal.builder()
                    .username("oyvibjo")
                    .email("oyvind@gmail.com")
                    .description(faker.country().capital())
                    .displayName("Oyvind")
                    .passwordHash(letmepassHash)
                    .address("Ottestad")
                    .tlf("12345678")
                    .isVerified(false)
                    .ratingGiven((double)faker.random().nextInt(0,5))
                    .ratingListing((double)faker.random().nextInt(0,5))
                    .ratingProfile((double)faker.random().nextInt(0,5))
                    .deactivated(null)
                    .build();

            profileRepository.save(p); profileList.add(p);
            profileRepository.save(p1); profileList.add(p1);
            profileRepository.save(p2); profileList.add(p2);
            profileRepository.save(p3); profileList.add(p3);
            profileRepository.save(p4); profileList.add(p4);
            profileRepository.save(p5); profileList.add(p5);
            profileRepository.save(p6); profileList.add(p6);
            profileRepository.save(p7); profileList.add(p7);
            profileRepository.save(p8); profileList.add(p8);
            profileRepository.save(p9); profileList.add(p9);


            // Random profiles
            for (int i = 0; i < extraProfiles; i++) {
                Name name = faker.name();
                var np = Personal.builder()
                        .username(name.username())
                        .address(faker.address().fullAddress())
                        .deactivated(null)
                        .description(faker.lorem().characters(10))
                        .displayName(name.fullName())
                        .isVerified(false)
                        .passwordHash(letmepassHash)
                        .tlf(faker.phoneNumber().phoneNumber())
                        .ratingGiven((double)faker.random().nextInt(0,5))
                        .ratingListing((double)faker.random().nextInt(0,5))
                        .ratingProfile((double)faker.random().nextInt(0,5))
                        .build();
                profileList.add(np);
                profileRepository.save(np);
            }

            // Making listings
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

                var l = Listing.builder()
                        .address(faker.address().fullAddress())
                        .name(name)
                        .price((double)faker.random().nextInt(10, 10000))
                        .description(faker.lorem().characters(faker.random().nextInt(3, 200)))
                        .profile(profileList.get(faker.random().nextInt(1, totalProfiles-1)))
                        .isActive(true)
                        .isAvailable(true)
                        .lastChanged(null)
                        .priceType("Week")
                        .images(null) // images
                        .build();
                listingList.add(l);
                listingRepository.save(l);
            }

            // Making leases
            for (int i = 0; i < leases; i++){
                long fromDate = 1651478864 + faker.random().nextInt(10000, 100000);
                long toDate = fromDate + faker.random().nextInt(10, 10000);
                Listing listing = listingList.get(faker.random().nextInt(0,listings-1));

                Review r1 = new Review((double)faker.random().nextInt(0,5), faker.music().genre());
                Review r2 = new Review((double)faker.random().nextInt(0,5), faker.music().genre());
                Review r3 = new Review((double)faker.random().nextInt(0,5), faker.music().genre());
                var l = Lease.builder()
                        .fromDatetime(fromDate)
                        .toDatetime(toDate)
                        .isApproved(i % 2 == 0)
                        .isCompleted(false)
                        .listing(listing)
                        .owner(listing.getProfile())
                        .profile(profileList.get(faker.random().nextInt(1, totalProfiles-1)))
                        .itemReview(r1)
                        .leaseeReview(r2)
                        .ownerReview(r3)
                        .build();
                leaseRepository.save(l);
                // NOTE REVIEWS SHOULD NOT BE CREATED BEFORE LEASE
            }

            // Making categories


        };
    }

}
