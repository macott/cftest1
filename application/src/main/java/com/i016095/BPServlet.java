package com.i016095;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;

import com.google.gson.Gson;
import com.sap.cloud.sdk.cloudplatform.logging.CloudLoggerFactory;
import com.sap.cloud.sdk.s4hana.connectivity.ErpConfigContext;;

import com.sap.cloud.sdk.s4hana.datamodel.odata.namespaces.businesspartner.BusinessPartner;

@WebServlet("/businesspartners")
public class BPServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = CloudLoggerFactory.getLogger(BPServlet.class);

    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException
    {
        final String bpCategory = request.getParameter("category");
        final ErpConfigContext configContext = new ErpConfigContext();
        final List<BusinessPartner> result;
        
        if (bpCategory == null) result = new GetCachedBPCommand(configContext).execute();
        else result = new GetCachedBPByCategoryCommand(configContext, bpCategory).execute();
        
        
        response.setContentType("application/json");
        response.getWriter().write(new Gson().toJson(result));
        
        }
  }
