package net.beast462.int2204.mimir.application.interfaces;

import netscape.javascript.JSObject;

public interface IAgreementService {
    int getAgreement(String agreement);

    void denyDataRecommendation();

    JSObject acceptDataRecommendation();
}
