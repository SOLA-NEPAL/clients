/**
 * ******************************************************************************************
 * Copyright (C) 2012 - Food and Agriculture Organization of the United Nations
 * (FAO). All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice,this
 * list of conditions and the following disclaimer. 2. Redistributions in binary
 * form must reproduce the above copyright notice,this list of conditions and
 * the following disclaimer in the documentation and/or other materials provided
 * with the distribution. 3. Neither the name of FAO nor the names of its
 * contributors may be used to endorse or promote products derived from this
 * software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT,STRICT LIABILITY,OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING
 * IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * *********************************************************************************************
 */
package org.sola.clients.beans.party.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.sola.clients.beans.party.PartyBean;
import org.sola.common.messaging.ClientMessage;
import org.sola.common.messaging.MessageUtility;
import org.sola.webservices.transferobjects.EntityAction;

/**
 * Used to make validation for {@link PartyCheck}.
 */
public class PartyValidator implements ConstraintValidator<PartyCheck, PartyBean> {

    @Override
    public void initialize(PartyCheck constraintAnnotation) {
    }

    @Override
    public boolean isValid(PartyBean partyBean, ConstraintValidatorContext context) {
        if (partyBean == null) {
            return true;
        }

        boolean result = true;
        context.disableDefaultConstraintViolation();

        // Check ID document
        if (partyBean.getIdTypeCode() != null && (partyBean.getIdNumber() == null
                || partyBean.getIdTypeCode().length() < 1)) {
            result = false;
            context.buildConstraintViolationWithTemplate(
                    MessageUtility.getLocalizedMessageText(
                    ClientMessage.CHECK_PERSON_ID_DOC_NUMBER)).addConstraintViolation();
        }

        // Check parent
        if (partyBean.getParent() != null) {
            if (partyBean.getParent().getId() != null && partyBean.getId() != null
                    && partyBean.getId().equalsIgnoreCase(partyBean.getParent().getId())) {
                result = false;
                context.buildConstraintViolationWithTemplate(
                        MessageUtility.getLocalizedMessageText(
                        ClientMessage.PARTY_THE_SAME_AS_PARENT)).addConstraintViolation();
            } else {
                if (partyBean.getParent().getParentId() != null && 
                        !partyBean.getParent().getParentId().isEmpty() &&
                        partyBean.getParent().getEntityAction()!=EntityAction.DELETE &&
                        partyBean.getParent().getEntityAction()!=EntityAction.DISASSOCIATE) {
                    result = false;
                    context.buildConstraintViolationWithTemplate(
                            MessageUtility.getLocalizedMessageText(
                            ClientMessage.PARTY_PARENT_IS_CHILD)).addConstraintViolation();
                }
            }
        }
        
        // Check birth date and id issue date
        if (partyBean.getIdIssueDate() != null && partyBean.getBirthDate()!=null){
             int bDate=Integer.parseInt(partyBean.getBirthDate().toString());
             int idDate=Integer.parseInt(partyBean.getIdIssueDate().toString());
            if(bDate>idDate){               
            result = false;
            context.buildConstraintViolationWithTemplate(
                    MessageUtility.getLocalizedMessageText(
                    ClientMessage.CHECK_BIRTH_DATE_ID_ISSUE_DATE)).addConstraintViolation();
            }
        }
        return result;
    }
}
