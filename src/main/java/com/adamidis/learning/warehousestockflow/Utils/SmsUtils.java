package com.adamidis.learning.warehousestockflow.Utils;

import com.twilio.Twilio;
import com.twilio.rest.verify.v2.service.Verification;

public class SmsUtils {
//    public static final String FROM_NUMBER = "";
    public static final String SID_KEY = "${TWILIO_ACCOUNT_SID}";
    public static final String TOKEN_KEY = "${TWILIO_ACCOUNT_TOKEN}";

    public static void sendSMS(String to, String messageBody) {
        Twilio.init(SID_KEY, TOKEN_KEY);
        Verification verification = Verification.creator("${TWILIO_VERIFY_SERVICE_SID}", "${TWILIO_FROM_PHONE_NUMBER}", "sms").create();
        System.out.println(verification.getSid());
    }
}
