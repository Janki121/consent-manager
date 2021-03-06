package in.projecteka.consentmanager.user;

import in.projecteka.consentmanager.clients.model.Session;
import in.projecteka.consentmanager.user.model.CoreSignUpRequest;
import in.projecteka.consentmanager.user.model.LockedUser;
import in.projecteka.consentmanager.user.model.PatientRequest;
import in.projecteka.consentmanager.user.model.PatientResponse;
import in.projecteka.consentmanager.user.model.RequesterDetail;
import in.projecteka.consentmanager.user.model.SessionRequest;
import in.projecteka.consentmanager.user.model.SignUpRequest;
import in.projecteka.consentmanager.user.model.UpdatePasswordRequest;
import in.projecteka.consentmanager.user.model.User;
import in.projecteka.consentmanager.user.model.UserSignUpEnquiry;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.jeasy.random.FieldPredicates;

public class TestBuilders {

    private static final EasyRandom easyRandom = new EasyRandom();

    public static SignUpRequest.SignUpRequestBuilder signUpRequest() {
        EasyRandomParameters excludeUnverifiedIdentifiers = new EasyRandomParameters()
                .excludeField(FieldPredicates.named("unverifiedIdentifiers"));
        EasyRandom easyRandom = new EasyRandom(excludeUnverifiedIdentifiers);
        return easyRandom.nextObject(SignUpRequest.SignUpRequestBuilder.class);
    }

    public static CoreSignUpRequest.CoreSignUpRequestBuilder coreSignUpRequest() {
        EasyRandomParameters excludeUnverifiedIdentifiers = new EasyRandomParameters()
                .excludeField(FieldPredicates.named("unverifiedIdentifiers"));
        EasyRandom easyRandom = new EasyRandom(excludeUnverifiedIdentifiers);
        return easyRandom.nextObject(CoreSignUpRequest.CoreSignUpRequestBuilder.class);
    }

    public static Session.SessionBuilder session() {
        return easyRandom.nextObject(Session.SessionBuilder.class);
    }

    public static String string() {
        return easyRandom.nextObject(String.class);
    }

    public static UserSignUpEnquiry.UserSignUpEnquiryBuilder userSignUpEnquiry() {
        return easyRandom.nextObject(UserSignUpEnquiry.UserSignUpEnquiryBuilder.class);
    }

    public static SessionRequest.SessionRequestBuilder sessionRequest() {
        return easyRandom.nextObject(SessionRequest.SessionRequestBuilder.class);
    }

    public static LockedUser.LockedUserBuilder lockedUser() {
        return easyRandom.nextObject(LockedUser.LockedUserBuilder.class);
    }

    public static User.UserBuilder user() {
        return easyRandom.nextObject(User.UserBuilder.class);
    }

    public static UpdatePasswordRequest.UpdatePasswordRequestBuilder updatePasswordRequest() {
        return easyRandom.nextObject(UpdatePasswordRequest.UpdatePasswordRequestBuilder.class);
    }

    public static RequesterDetail.RequesterDetailBuilder requester() {
        return easyRandom.nextObject(RequesterDetail.RequesterDetailBuilder.class);
    }

    public static PatientResponse.PatientResponseBuilder patient() {
        return easyRandom.nextObject(PatientResponse.PatientResponseBuilder.class);
    }

    public static PatientRequest.PatientRequestBuilder patientRequest() {
        return easyRandom.nextObject(PatientRequest.PatientRequestBuilder.class);
    }
}
