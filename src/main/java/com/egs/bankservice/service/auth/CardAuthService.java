package com.egs.bankservice.service.auth;

import com.egs.bankservice.exception.BankServiceException;
import com.egs.bankservice.model.auth.CardAuthRequest;
import com.egs.bankservice.model.auth.CardAuthResponse;
import com.egs.bankservice.model.auth.ValidateCardResponse;

public interface CardAuthService {

    ValidateCardResponse validateCard(String card) throws BankServiceException;

    CardAuthResponse cardAuth(CardAuthRequest cardAuthRequest) throws BankServiceException;
}
