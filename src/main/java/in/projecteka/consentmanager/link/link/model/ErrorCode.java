package in.projecteka.consentmanager.link.link.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum ErrorCode {
    NO_PATIENT_FOUND(1000),
    MULTIPLE_PATIENTS_FOUND(1001),
    CARE_CONTEXT_NOT_FOUND(1002),
    OTP_INVALID(1003),
    OTP_EXPIRED(1004),
    UNABLE_TO_CONNECT_TO_PROVIDER(1005),
    USER_NOT_FOUND(1006),
    DB_OPERATION_FAILED(1007),
    PROVIDER_NOT_FOUND(1008),
    NETWORK_SERVICE_ERROR(2000),
    CONSENT_REQUEST_NOT_FOUND(1009);

    private int value;
    ErrorCode(int val) {
        value = val;
    }

    // Adding @JsonValue annotation that tells the 'value' to be of integer type while de-serializing.
    @JsonValue
    public int getValue() {
        return value;
    }

    @JsonCreator
    public static ErrorCode getNameByValue(int value) {
        return Arrays.stream(ErrorCode.values())
                .filter(errorCode -> errorCode.value == value)
                .findAny()
                .orElse(ErrorCode.OTP_EXPIRED);
    }
}