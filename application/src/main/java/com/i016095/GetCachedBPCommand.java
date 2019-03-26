package com.i016095;
import java.util.Collections;
import java.util.List;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import com.sap.cloud.sdk.cloudplatform.cache.CacheKey;
import com.sap.cloud.sdk.odatav2.connectivity.ODataException;
import com.sap.cloud.sdk.odatav2.connectivity.ODataExceptionType;

import com.sap.cloud.sdk.s4hana.connectivity.CachingErpCommand;
import com.sap.cloud.sdk.s4hana.connectivity.ErpConfigContext;

import lombok.NonNull;

import com.sap.cloud.sdk.s4hana.datamodel.odata.namespaces.businesspartner.BusinessPartner;
import com.sap.cloud.sdk.s4hana.datamodel.odata.services.DefaultBusinessPartnerService;


public class GetCachedBPCommand extends CachingErpCommand<List<BusinessPartner>>
{
    private static final Cache<CacheKey, List<BusinessPartner>> cache =
    CacheBuilder.newBuilder().build();
    
    public GetCachedBPCommand( @NonNull final ErpConfigContext configContext )
    {
        super(GetCachedBPCommand.class, configContext);
    }
    
    @Override
    protected Cache<CacheKey, List<BusinessPartner>> getCache()
    {
        return cache;
    }
    
    @Override
    protected List<BusinessPartner> runCacheable() throws ODataException {
        try {
            final  List<BusinessPartner> businessPartners = new DefaultBusinessPartnerService().getAllBusinessPartner()
            .select(BusinessPartner.BUSINESS_PARTNER,
                    BusinessPartner.BUSINESS_PARTNER_NAME,
                    BusinessPartner.BUSINESS_PARTNER_CATEGORY,
                    BusinessPartner.BUSINESS_PARTNER_GROUPING,
                    BusinessPartner.LAST_NAME,
                    BusinessPartner.FIRST_NAME)
            .top(100)
            .execute(getConfigContext());
            
            return businessPartners;
        }
        catch( final Exception e) {
            throw new ODataException(ODataExceptionType.OTHER, "Failed to get Business Partners from OData command.", e);
        }
    }
    
    @Override
    protected List<BusinessPartner> getFallback() {
        return Collections.emptyList();
    }
}
