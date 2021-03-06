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

import org.sola.clients.beans.converters.TypeConverters;
import org.sola.clients.beans.digitalarchive.DocumentBean;
import org.sola.services.boundary.wsclients.WSManager;
import org.sola.webservices.transferobjects.EntityAction;
import org.sola.webservices.transferobjects.casemanagement.SourceTO;

/**
 * Represents <b>source</b> object in the domain model. Could be populated from
 * the {@link SourceTO} object.<br /> For more information see data dictionary
 * <b>Source</b> schema.
 */
public class SourceBean extends SourceSummaryBean {

    public static final String ARCHIVE_DOCUMENT_PROPERTY = "archiveDocument";
    public static final String MAIN_TYPE_PROPERTY = "mainType";
    public static final String AVAILABILITY_STATUS_CODE_PROPERTY = "availabilityStatusCode";
    public static final String CONTENT_PROPERTY = "content";
    public static final String OWNER_PROPERTY = "owner";
    public static final String DESCRIPTION_PROPERTY = "description";
    public static final String PACKET_NUMBER_PROPERTY = "packetNumber";
    public static final String TAMELI_PROPERTY = "tameliNumber";
    public static final String LIKHAT_REGISTRATION_NUMBER_PROPERTY = "likhatRegistrationNumber";
    public static final String PAGE_NUMBER_PROPERTY = "pageNumber";
    
    private String mainType;
    private String availabilityStatusCode;
    private String content;
    private DocumentBean archiveDocument;
    private String owner;
    private String description;
    private String packetNumber;
    private String tameliNumber;
    private String likhatRegistrationNumber;
    private String pageNumber;

    public SourceBean() {
        super();
    }

    // Properties
    public String getAvailabilityStatusCode() {
        return availabilityStatusCode;
    }

    public void setAvailabilityStatusCode(String value) {
        String old = availabilityStatusCode;
        availabilityStatusCode = value;
        propertySupport.firePropertyChange(AVAILABILITY_STATUS_CODE_PROPERTY, old, value);
    }

    public String getContent() {
        return content;
    }

    public void setContent(String value) {
        String old = content;
        content = value;
        propertySupport.firePropertyChange(CONTENT_PROPERTY, old, value);
    }

    public String getMainType() {
        return mainType;
    }

    public void setMainType(String value) {
        String old = mainType;
        mainType = value;
        propertySupport.firePropertyChange(MAIN_TYPE_PROPERTY, old, value);
    }

    public DocumentBean getArchiveDocument() {
        return archiveDocument;
    }

    public void setArchiveDocument(DocumentBean archiveDocument) {
        this.archiveDocument = archiveDocument;
        propertySupport.firePropertyChange(ARCHIVE_DOCUMENT_PROPERTY, null, archiveDocument);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        String oldValue = this.description;
        this.description = description;
        propertySupport.firePropertyChange(DESCRIPTION_PROPERTY, oldValue, this.description);
    }

    public String getLikhatRegistrationNumber() {
        return likhatRegistrationNumber;
    }

    public void setLikhatRegistrationNumber(String likhatRegistrationNumber) {
        String oldValue = this.likhatRegistrationNumber;
        this.likhatRegistrationNumber = likhatRegistrationNumber;
        propertySupport.firePropertyChange(LIKHAT_REGISTRATION_NUMBER_PROPERTY, oldValue, this.likhatRegistrationNumber);
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        String oldValue = this.owner;
        this.owner = owner;
        propertySupport.firePropertyChange(OWNER_PROPERTY, oldValue, this.owner);
    }

    public String getPacketNumber() {
        return packetNumber;
    }

    public void setPacketNumber(String packetNumber) {
        String oldValue = this.packetNumber;
        this.packetNumber = packetNumber;
        propertySupport.firePropertyChange(PACKET_NUMBER_PROPERTY, oldValue, this.packetNumber);
    }

    public String getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(String pageNumber) {
        String oldValue = this.pageNumber;
        this.pageNumber = pageNumber;
        propertySupport.firePropertyChange(PAGE_NUMBER_PROPERTY, oldValue, this.pageNumber);
    }

    public String getTameliNumber() {
        return tameliNumber;
    }

    public void setTameliNumber(String tameliNumber) {
        String oldValue = this.tameliNumber;
        this.tameliNumber = tameliNumber;
        propertySupport.firePropertyChange(TAMELI_PROPERTY, oldValue, this.tameliNumber);
    }

    @Override
    public String getArchiveDocumentId() {
        if (archiveDocument != null) {
            return archiveDocument.getId();
        } else {
            return null;
        }
    }

    @Override
    public void setArchiveDocumentId(String value) {
    }

    // Methods
    @Override
    public void clean() {
        super.clean();
        setArchiveDocument(null);
        setAvailabilityStatusCode(null);
        setContent(null);
        setMainType(null);
        setSourceType(null);
        setOwner(null);
        setDescription(null);
        setPacketNumber(null);
        setTameliNumber(null);
        setLikhatRegistrationNumber(null);
        setPageNumber(null);
    }

    public void openDocument() {
        if (archiveDocument != null && archiveDocument.getId() != null) {
            DocumentBean.openDocument(archiveDocument.getId());
        }
    }

    public void removeAttachment() {
        if (archiveDocument != null) {
            archiveDocument.setEntityAction(EntityAction.DISASSOCIATE);
        }
    }

    /**
     * Static method to detaches source from transaction.
     *
     * @param sourceId ID of the source.
     */
    public static boolean detachFromTransaction(String sourceId) {
        return WSManager.getInstance().getCaseManagementService().dettachSourceFromTransaction(sourceId);
    }

    /**
     * Static method to attaches source to the transaction.
     *
     * @param serviceId Application service id, bound to transaction.
     * @param sourceId ID of the source.
     */
    public static SourceBean attachToTransaction(String sourceId, String serviceId) {
        SourceBean result = null;
        SourceTO to = WSManager.getInstance().getCaseManagementService().attachSourceToTransaction(serviceId, sourceId);

        if (to != null) {
            result = new SourceBean();
            TypeConverters.TransferObjectToBean(to, SourceBean.class, result);
        }
        return result;
    }

    public void save() {
        TypeConverters.TransferObjectToBean(
                WSManager.getInstance().getCaseManagementService().saveSource(TypeConverters.BeanToTrasferObject(this, SourceTO.class)),
                SourceBean.class, this);
    }

    public static SourceBean getSource(String sourceId) {
        return TypeConverters.TransferObjectToBean(
                WSManager.getInstance().getCaseManagementService().getSourceById(sourceId),
                SourceBean.class, null);
    }
}
