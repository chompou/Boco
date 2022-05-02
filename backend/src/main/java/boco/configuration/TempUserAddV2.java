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
            int teamProfiles = 9;
            int profiles = 10 + teamProfiles;
            List<Profile> profileList = new ArrayList<>();
            int listings = 100;
            List<Listing> listingList = new ArrayList<>();
            int leases = 20;
            List<Lease> leaseList = new ArrayList<>();

            Faker faker = new Faker();

            // Making profiles. Password for all profiles are letmepass

            // Profiles for team
            // TODO: ADD

            // Random profiles
            for (int i = 0; i < profiles; i++) {
                Name name = faker.name();
                var p = Personal.builder()
                        .username(name.username())
                        .address(faker.address().fullAddress())
                        .deactivated(null)
                        .description(faker.lorem().characters(10))
                        .displayName(name.fullName())
                        .isVerified(false)
                        .passwordHash("$2a$12$9FDSwGUAG.n8bv0feCeaj./slkAPYV42sMy4tPe/osKeNp6HRlB8a")
                        .tlf(faker.phoneNumber().phoneNumber())
                        .ratingGiven((double)faker.random().nextInt(0,5))
                        .ratingListing((double)faker.random().nextInt(0,5))
                        .ratingProfile((double)faker.random().nextInt(0,5))
                        .build();
                profileList.add(p);
                profileRepository.save(p);
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
                        .profile(profileList.get(faker.random().nextInt(0, profiles-1)))
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
                        .profile(profileList.get(faker.random().nextInt(0, profiles-1)))
                        .itemReview(r1)
                        .leaseeReview(r2)
                        .ownerReview(r3)
                        .build();
                leaseRepository.save(l);
                // NOTE REVIEWS SHOULD NOT BE CREATED BEFORE LEASE
            }

        };
    }

}
