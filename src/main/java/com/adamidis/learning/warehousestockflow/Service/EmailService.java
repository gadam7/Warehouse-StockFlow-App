package com.adamidis.learning.warehousestockflow.Service;

import com.adamidis.learning.warehousestockflow.Enum.VerificationType;

public interface EmailService {
    void sendVerificationEmail(String firstName, String email, String verificationUrl, VerificationType verificationType);
}
