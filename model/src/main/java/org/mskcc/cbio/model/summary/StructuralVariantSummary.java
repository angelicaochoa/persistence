/*
 * Copyright (c) 2015 Memorial Sloan-Kettering Cancer Center.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY, WITHOUT EVEN THE IMPLIED WARRANTY OF MERCHANTABILITY OR FITNESS
 * FOR A PARTICULAR PURPOSE. The software and documentation provided hereunder
 * is on an "as is" basis, and Memorial Sloan-Kettering Cancer Center has no
 * obligations to provide maintenance, support, updates, enhancements or
 * modifications. In no event shall Memorial Sloan-Kettering Cancer Center be
 * liable to any party for direct, indirect, special, incidental or
 * consequential damages, including lost profits, arising out of the use of this
 * software and its documentation, even if Memorial Sloan-Kettering Cancer
 * Center has been advised of the possibility of such damage.
 */

/*
 * This file is part of cBioPortal.
 *
 * cBioPortal is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package org.mskcc.cbio.model.summary;

import java.io.Serializable;

/**
 *
 * @author ochoaa
 */
public abstract class StructuralVariantSummary implements Serializable {
    
    private Integer geneticProfileId;
    private Integer sampleId;
    private Integer internalId;
    private String breakpointType;
    private String annotation;
    private String comments;
    private String confidenceClass;
    private String connectionType;
    private String eventInfo;
    private Integer mapQ;
    private Integer normalReadCount;
    private Integer normalVariantCount;
    private String pairedEndReadSupport;
    private String site1Chrom;    
    private String site1Desc;
    private String site1Gene;
    private Integer site1Pos;
    private String site2Chrom;
    private String site2Desc;
    private String site2Gene;
    private Integer site2Pos;
    private String splitReadSupport;
    private String svClassName;
    private String svDesc;
    private Integer svLength;
    private Integer tumorReadCount;
    private Integer tumorVariantCount;
    private String variantStatusName;

    /**
     * @return the geneticProfileId
     */
    public Integer getGeneticProfileId() {
        return geneticProfileId;
    }

    /**
     * @param geneticProfileId the geneticProfileId to set
     */
    public void setGeneticProfileId(Integer geneticProfileId) {
        this.geneticProfileId = geneticProfileId;
    }

    /**
     * @return the sampleId
     */
    public Integer getSampleId() {
        return sampleId;
    }

    /**
     * @param sampleId the sampleId to set
     */
    public void setSampleId(Integer sampleId) {
        this.sampleId = sampleId;
    }

    /**
     * @return the internalId
     */
    public Integer getInternalId() {
        return internalId;
    }

    /**
     * @param internalId the internalId to set
     */
    public void setInternalId(Integer internalId) {
        this.internalId = internalId;
    }

    /**
     * @return the breakpointType
     */
    public String getBreakpointType() {
        return breakpointType;
    }

    /**
     * @param breakpointType the breakpointType to set
     */
    public void setBreakpointType(String breakpointType) {
        this.breakpointType = breakpointType;
    }

    /**
     * @return the annotation
     */
    public String getAnnotation() {
        return annotation;
    }

    /**
     * @param annotation the annotation to set
     */
    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }

    /**
     * @return the comments
     */
    public String getComments() {
        return comments;
    }

    /**
     * @param comments the comments to set
     */
    public void setComments(String comments) {
        this.comments = comments;
    }

    /**
     * @return the confidenceClass
     */
    public String getConfidenceClass() {
        return confidenceClass;
    }

    /**
     * @param confidenceClass the confidenceClass to set
     */
    public void setConfidenceClass(String confidenceClass) {
        this.confidenceClass = confidenceClass;
    }

    /**
     * @return the connectionType
     */
    public String getConnectionType() {
        return connectionType;
    }

    /**
     * @param connectionType the connectionType to set
     */
    public void setConnectionType(String connectionType) {
        this.connectionType = connectionType;
    }

    /**
     * @return the eventInfo
     */
    public String getEventInfo() {
        return eventInfo;
    }

    /**
     * @param eventInfo the eventInfo to set
     */
    public void setEventInfo(String eventInfo) {
        this.eventInfo = eventInfo;
    }

    /**
     * @return the mapQ
     */
    public Integer getMapQ() {
        return mapQ;
    }

    /**
     * @param mapQ the mapQ to set
     */
    public void setMapQ(Integer mapQ) {
        this.mapQ = mapQ;
    }

    /**
     * @return the normalReadCount
     */
    public Integer getNormalReadCount() {
        return normalReadCount;
    }

    /**
     * @param normalReadCount the normalReadCount to set
     */
    public void setNormalReadCount(Integer normalReadCount) {
        this.normalReadCount = normalReadCount;
    }

    /**
     * @return the normalVariantCount
     */
    public Integer getNormalVariantCount() {
        return normalVariantCount;
    }

    /**
     * @param normalVariantCount the normalVariantCount to set
     */
    public void setNormalVariantCount(Integer normalVariantCount) {
        this.normalVariantCount = normalVariantCount;
    }

    /**
     * @return the pairedEndReadSupport
     */
    public String getPairedEndReadSupport() {
        return pairedEndReadSupport;
    }

    /**
     * @param pairedEndReadSupport the pairedEndReadSupport to set
     */
    public void setPairedEndReadSupport(String pairedEndReadSupport) {
        this.pairedEndReadSupport = pairedEndReadSupport;
    }

    /**
     * @return the site1Chrom
     */
    public String getSite1Chrom() {
        return site1Chrom;
    }

    /**
     * @param site1Chrom the site1Chrom to set
     */
    public void setSite1Chrom(String site1Chrom) {
        this.site1Chrom = site1Chrom;
    }

    /**
     * @return the site1Desc
     */
    public String getSite1Desc() {
        return site1Desc;
    }

    /**
     * @param site1Desc the site1Desc to set
     */
    public void setSite1Desc(String site1Desc) {
        this.site1Desc = site1Desc;
    }

    /**
     * @return the site1Gene
     */
    public String getSite1Gene() {
        return site1Gene;
    }

    /**
     * @param site1Gene the site1Gene to set
     */
    public void setSite1Gene(String site1Gene) {
        this.site1Gene = site1Gene;
    }

    /**
     * @return the site1Pos
     */
    public Integer getSite1Pos() {
        return site1Pos;
    }

    /**
     * @param site1Pos the site1Pos to set
     */
    public void setSite1Pos(Integer site1Pos) {
        this.site1Pos = site1Pos;
    }

    /**
     * @return the site2Chrom
     */
    public String getSite2Chrom() {
        return site2Chrom;
    }

    /**
     * @param site2Chrom the site2Chrom to set
     */
    public void setSite2Chrom(String site2Chrom) {
        this.site2Chrom = site2Chrom;
    }

    /**
     * @return the site2Desc
     */
    public String getSite2Desc() {
        return site2Desc;
    }

    /**
     * @param site2Desc the site2Desc to set
     */
    public void setSite2Desc(String site2Desc) {
        this.site2Desc = site2Desc;
    }

    /**
     * @return the site2Gene
     */
    public String getSite2Gene() {
        return site2Gene;
    }

    /**
     * @param site2Gene the site2Gene to set
     */
    public void setSite2Gene(String site2Gene) {
        this.site2Gene = site2Gene;
    }

    /**
     * @return the site2Pos
     */
    public Integer getSite2Pos() {
        return site2Pos;
    }

    /**
     * @param site2Pos the site2Pos to set
     */
    public void setSite2Pos(Integer site2Pos) {
        this.site2Pos = site2Pos;
    }

    /**
     * @return the splitReadSupport
     */
    public String getSplitReadSupport() {
        return splitReadSupport;
    }

    /**
     * @param splitReadSupport the splitReadSupport to set
     */
    public void setSplitReadSupport(String splitReadSupport) {
        this.splitReadSupport = splitReadSupport;
    }

    /**
     * @return the svClassName
     */
    public String getSvClassName() {
        return svClassName;
    }

    /**
     * @param svClassName the svClassName to set
     */
    public void setSvClassName(String svClassName) {
        this.svClassName = svClassName;
    }

    /**
     * @return the svDesc
     */
    public String getSvDesc() {
        return svDesc;
    }

    /**
     * @param svDesc the svDesc to set
     */
    public void setSvDesc(String svDesc) {
        this.svDesc = svDesc;
    }

    /**
     * @return the svLength
     */
    public Integer getSvLength() {
        return svLength;
    }

    /**
     * @param svLength the svLength to set
     */
    public void setSvLength(Integer svLength) {
        this.svLength = svLength;
    }

    /**
     * @return the tumorReadCount
     */
    public Integer getTumorReadCount() {
        return tumorReadCount;
    }

    /**
     * @param tumorReadCount the tumorReadCount to set
     */
    public void setTumorReadCount(Integer tumorReadCount) {
        this.tumorReadCount = tumorReadCount;
    }

    /**
     * @return the tumorVariantCount
     */
    public Integer getTumorVariantCount() {
        return tumorVariantCount;
    }

    /**
     * @param tumorVariantCount the tumorVariantCount to set
     */
    public void setTumorVariantCount(Integer tumorVariantCount) {
        this.tumorVariantCount = tumorVariantCount;
    }

    /**
     * @return the variantStatusName
     */
    public String getVariantStatusName() {
        return variantStatusName;
    }

    /**
     * @param variantStatusName the variantStatusName to set
     */
    public void setVariantStatusName(String variantStatusName) {
        this.variantStatusName = variantStatusName;
    }
    
}
