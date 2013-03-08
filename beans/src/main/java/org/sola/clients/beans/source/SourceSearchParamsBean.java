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
package org.sola.clients.beans.source;

import java.util.Date;
import org.sola.clients.beans.AbstractBindingBean;
import org.sola.clients.beans.cache.CacheManager;
import org.sola.clients.beans.referencedata.SourceTypeBean;
import org.sola.clients.beans.system.NepaliDateBean;
import org.sola.services.boundary.wsclients.WSManager;

/**
 * Contains properties used as the parameters to search sources. Could be
 * populated from the {@link SourceSearchParamsTO} object.<br />
 */
public class SourceSearchParamsBean extends AbstractBindingBean {

    public static final String TYPE_CODE_PROPERTY = "typeCode";
    public static final String SOURCE_TYPE_PROPERTY = "sourceType";
    public static final String LA_NUMBER_PROPERTY = "laNumber";
    public static final String REF_NUMBER_PROPERTY = "refNumber";
    public static final String FROM_RECORDATION_DATE_PROPERTY = "fromRecordationDate";
    public static final String TO_RECORDATION_DATE_PROPERTY = "toRecordationDate";
    public static final String FROM_SUBMISSION_DATE_PROPERTY = "fromSubmissionDate";
    public static final String TO_SUBMISSION_DATE_PROPERTY = "toSubmissionDate";
    public static final String FROM_SUBMISSION_NEPALI_DATE_PROPERTY = "fromSubmissionNepaliDate";
    public static final String TO_SUBMISSION_NEPALI_DATE_PROPERTY = "toSubmissionNepaliDate";
    private String laNumber;
    private String refNumber;
    private String fromRecordationDate;
    private String toRecordationDate;
//    private Date fromSubmissionDate;
//    private Date toSubmissionDate;
    private NepaliDateBean fromSubmissionNepaliDate;
    private NepaliDateBean toSubmissionNepaliDate;
    private SourceTypeBean sourceType;
    private String appNumber;

    public SourceSearchParamsBean() {
        super();
        sourceType = new SourceTypeBean();
        fromSubmissionNepaliDate = new NepaliDateBean();
        toSubmissionNepaliDate = new NepaliDateBean();
    }

    public String getFromRecordationDate() {
        if (fromRecordationDate != null) {
            NepaliDateBean.putDashOnString(fromRecordationDate);
        }
        return fromRecordationDate;
    }

    public void setFromRecordationDate(String fromRecordationDate) {
        String old = this.fromRecordationDate;
        this.fromRecordationDate = fromRecordationDate;
        propertySupport.firePropertyChange(FROM_RECORDATION_DATE_PROPERTY, old, this.fromRecordationDate);
    }

    public Date getFromSubmissionDate() {
        if (fromSubmissionNepaliDate != null) {
            return fromSubmissionNepaliDate.getGregorean_date();
        }
        return null;
    }

    public void setFromSubmissionDate(Date fromSubmissionDate) {
        Date oldValue = null;
        if (fromSubmissionNepaliDate != null) {
            oldValue = fromSubmissionNepaliDate.getGregorean_date();
        }
        fromSubmissionNepaliDate.setGregorean_date(fromSubmissionDate);
        propertySupport.firePropertyChange(FROM_SUBMISSION_DATE_PROPERTY, oldValue, fromSubmissionDate);
    }

    public Date getToSubmissionDate() {
        if (toSubmissionNepaliDate != null) {
            return toSubmissionNepaliDate.getGregorean_date();
        }
        return null;
    }

    public void setToSubmissionDate(Date toSubmissionDate) {
        Date oldValue = null;
        if (toSubmissionNepaliDate != null) {
            oldValue = toSubmissionNepaliDate.getGregorean_date();
        }
        toSubmissionNepaliDate.setGregorean_date(toSubmissionDate);
        propertySupport.firePropertyChange(TO_SUBMISSION_DATE_PROPERTY, oldValue, toSubmissionDate);
    }

    public NepaliDateBean getFromSubmissionNepaliDate() {
        return fromSubmissionNepaliDate;
    }

    public void setFromSubmissionNepaliDate(NepaliDateBean fromSubmissionNepaliDate) {
        NepaliDateBean oldValue = this.fromSubmissionNepaliDate;
        this.fromSubmissionNepaliDate = fromSubmissionNepaliDate;
        propertySupport.firePropertyChange(FROM_SUBMISSION_NEPALI_DATE_PROPERTY, oldValue, this.fromSubmissionNepaliDate);
    }

    public NepaliDateBean getToSubmissionNepaliDate() {
        return toSubmissionNepaliDate;
    }

    public void setToSubmissionNepaliDate(NepaliDateBean toSubmissionNepaliDate) {
        NepaliDateBean oldValue = this.toSubmissionNepaliDate;
        this.toSubmissionNepaliDate = toSubmissionNepaliDate;
        propertySupport.firePropertyChange(TO_SUBMISSION_NEPALI_DATE_PROPERTY, oldValue, this.toSubmissionNepaliDate);
    }

    public String getLaNumber() {
        return laNumber;
    }

    public void setLaNumber(String laNumber) {
        String old = this.laNumber;
        this.laNumber = laNumber;
        propertySupport.firePropertyChange(LA_NUMBER_PROPERTY, old, this.laNumber);
    }

    public String getRefNumber() {
        return refNumber;
    }

    public void setRefNumber(String refNumber) {
        String old = this.refNumber;
        this.refNumber = refNumber;
        propertySupport.firePropertyChange(REF_NUMBER_PROPERTY, old, this.refNumber);
    }

    public String getToRecordationDate() {
        if (toRecordationDate != null) {
            NepaliDateBean.putDashOnString(toRecordationDate);
        }
        return toRecordationDate;
    }

    public void setToRecordationDate(String toRecordationDate) {
        String old = this.toRecordationDate;
        this.toRecordationDate = toRecordationDate;
        propertySupport.firePropertyChange(TO_RECORDATION_DATE_PROPERTY, old, this.toRecordationDate);
    }

    public String getTypeCode() {
        return sourceType.getCode();
    }

    public void setTypeCode(String typeCode) {
        String old = sourceType.getCode();
        setSourceType(CacheManager.getBeanByCode(
                CacheManager.getSourceTypes(), typeCode));
        propertySupport.firePropertyChange(TYPE_CODE_PROPERTY, old, typeCode);
    }

    public SourceTypeBean getSourceType() {
        return sourceType;
    }

    public String getAppNumber() {
        return appNumber;
    }

    public void setAppNumber(String appNumber) {
        this.appNumber = appNumber;
    }

    public void setSourceType(SourceTypeBean sourceType) {
        if (this.sourceType == null) {
            this.sourceType = new SourceTypeBean();
        }
        this.setJointRefDataBean(this.sourceType, sourceType, SOURCE_TYPE_PROPERTY);
    }
}
