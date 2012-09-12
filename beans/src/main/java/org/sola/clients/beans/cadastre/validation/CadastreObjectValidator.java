/*
 * Copyright 2012 Food and Agriculture Organization of the United Nations (FAO).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.sola.clients.beans.cadastre.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.sola.clients.beans.cadastre.CadastreObjectSummaryBean;
import org.sola.common.messaging.ClientMessage;
import org.sola.common.messaging.MessageUtility;

/**
 * Constraint validation for {@link CadastreObjectValidator}.
 */
public class CadastreObjectValidator implements ConstraintValidator<CadastreObjectCheck, CadastreObjectSummaryBean> {

    @Override
    public void initialize(CadastreObjectCheck constraintAnnotation) {
    }

    @Override
    public boolean isValid(CadastreObjectSummaryBean cadastreObjectBean, ConstraintValidatorContext context) {
        if(cadastreObjectBean == null){
            return true;
        }
        context.disableDefaultConstraintViolation();
        boolean result = true;
        
        // Check address 
      
        if(cadastreObjectBean.getAddress() == null || 
                cadastreObjectBean.getAddress().getVdcCode() == null || 
                cadastreObjectBean.getAddress().getVdcCode().isEmpty()){
            result = false;
            context.buildConstraintViolationWithTemplate(MessageUtility
                    .getLocalizedMessage(ClientMessage
                    .ADDRESS_VDC_IS_NULL).getMessage())
                    .addConstraintViolation();
        }
        
        if(cadastreObjectBean.getAddress() == null || 
                cadastreObjectBean.getAddress().getWardNo() == null || 
                cadastreObjectBean.getAddress().getWardNo().isEmpty()){
            result = false;
            context.buildConstraintViolationWithTemplate(MessageUtility
                    .getLocalizedMessage(ClientMessage
                    .ADDRESS_WARD_IS_NULL).getMessage())
                    .addConstraintViolation();
        }
        
        // Check parcel
        if(cadastreObjectBean.getMapSheet() == null || 
                cadastreObjectBean.getMapSheet().getMapNumber() == null ||
                cadastreObjectBean.getMapSheet().getMapNumber().isEmpty()){
            result = false;
            context.buildConstraintViolationWithTemplate(MessageUtility
                    .getLocalizedMessage(ClientMessage
                    .CADASTRE_MAP_SHEET_IS_NULL).getMessage())
                    .addConstraintViolation();
        }
        
        if(cadastreObjectBean.getParcelno() == null || cadastreObjectBean.getParcelno().isEmpty()){
            result = false;
            context.buildConstraintViolationWithTemplate(MessageUtility
                    .getLocalizedMessage(ClientMessage
                    .CADASTRE_PARCEL_NUMBER_IS_NULL).getMessage())
                    .addConstraintViolation();
        }
        
        return result;
    }
    
}
