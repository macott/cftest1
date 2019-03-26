package com.i016095;

import lombok.Data;
import com.sap.cloud.sdk.result.ElementName;

@Data
public class BPDetails
{
    @ElementName( "BusinessPartner" )
    private String bpId;

    @ElementName( "BusinessPartnerName" )
    private String bpName;

    @ElementName( "BusinessPartnerCategory" )
    private String bpCategory;

    @ElementName( "BusinessPartnerGrouping" )
    private String bpGrouping;

    @ElementName( "LastName" )
    private String bpLastName;

    @ElementName( "FirstName" )
    private String bpFirstName;
}
