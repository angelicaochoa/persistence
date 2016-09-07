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

package org.mskcc.cbio.model;

import java.io.Serializable;

/**
 *
 * @author ochoaa
 */
public class MutationEvent implements Serializable {

    private Integer mutationEventId;
    private Integer entrezGeneId;
    private String chr;
    private Long startPosition;
    private Long endPosition;
    private String referenceAllele;
    private String tumorSeqAllele;
    private String proteinChange;
    private String mutationType;
    private String functionalImpactScore;
    private Float fisValue;
    private String linkXvar;
    private String linkPdb;
    private String linkMsa;
    private String ncbiBuild;
    private String strand;
    private String variantType;
    private String dbSnpRs;
    private String dbSnpValStatus;
    private String oncotatorDbsnpRs;
    private String oncotatorRefseqMrnaId;
    private String oncotatorCodonChange;
    private String oncotatorUniprotEntryName;
    private String oncotatorUniprotAccession;
    private Integer oncotatorProteinPosStart;
    private Integer oncotatorProteinPosEnd;
    private Boolean canonicalTranscript;
    private String keyword;

    /**
     * @return the mutationEventId
     */
    public Integer getMutationEventId() {
        return mutationEventId;
    }

    /**
     * @param mutationEventId the mutationEventId to set
     */
    public void setMutationEventId(Integer mutationEventId) {
        this.mutationEventId = mutationEventId;
    }

    /**
     * @return the entrezGeneId
     */
    public Integer getEntrezGeneId() {
        return entrezGeneId;
    }

    /**
     * @param entrezGeneId the entrezGeneId to set
     */
    public void setEntrezGeneId(Integer entrezGeneId) {
        this.entrezGeneId = entrezGeneId;
    }

    /**
     * @return the chr
     */
    public String getChr() {
        return chr;
    }

    /**
     * @param chr the chr to set
     */
    public void setChr(String chr) {
        this.chr = chr;
    }

    /**
     * @return the startPosition
     */
    public Long getStartPosition() {
        return startPosition;
    }

    /**
     * @param startPosition the startPosition to set
     */
    public void setStartPosition(Long startPosition) {
        this.startPosition = startPosition;
    }

    /**
     * @return the endPosition
     */
    public Long getEndPosition() {
        return endPosition;
    }

    /**
     * @param endPosition the endPosition to set
     */
    public void setEndPosition(Long endPosition) {
        this.endPosition = endPosition;
    }

    /**
     * @return the referenceAllele
     */
    public String getReferenceAllele() {
        return referenceAllele;
    }

    /**
     * @param referenceAllele the referenceAllele to set
     */
    public void setReferenceAllele(String referenceAllele) {
        this.referenceAllele = referenceAllele;
    }

    /**
     * @return the tumorSeqAllele
     */
    public String getTumorSeqAllele() {
        return tumorSeqAllele;
    }

    /**
     * @param tumorSeqAllele the tumorSeqAllele to set
     */
    public void setTumorSeqAllele(String tumorSeqAllele) {
        this.tumorSeqAllele = tumorSeqAllele;
    }

    /**
     * @return the proteinChange
     */
    public String getProteinChange() {
        return proteinChange;
    }

    /**
     * @param proteinChange the proteinChange to set
     */
    public void setProteinChange(String proteinChange) {
        this.proteinChange = proteinChange;
    }

    /**
     * @return the mutationType
     */
    public String getMutationType() {
        return mutationType;
    }

    /**
     * @param mutationType the mutationType to set
     */
    public void setMutationType(String mutationType) {
        this.mutationType = mutationType;
    }

    /**
     * @return the functionalImpactScore
     */
    public String getFunctionalImpactScore() {
        return functionalImpactScore;
    }

    /**
     * @param functionalImpactScore the functionalImpactScore to set
     */
    public void setFunctionalImpactScore(String functionalImpactScore) {
        this.functionalImpactScore = functionalImpactScore;
    }

    /**
     * @return the fisValue
     */
    public Float getFisValue() {
        return fisValue;
    }

    /**
     * @param fisValue the fisValue to set
     */
    public void setFisValue(Float fisValue) {
        this.fisValue = fisValue;
    }

    /**
     * @return the linkXvar
     */
    public String getLinkXvar() {
        return linkXvar;
    }

    /**
     * @param linkXvar the linkXvar to set
     */
    public void setLinkXvar(String linkXvar) {
        this.linkXvar = linkXvar;
    }

    /**
     * @return the linkPdb
     */
    public String getLinkPdb() {
        return linkPdb;
    }

    /**
     * @param linkPdb the linkPdb to set
     */
    public void setLinkPdb(String linkPdb) {
        this.linkPdb = linkPdb;
    }

    /**
     * @return the linkMsa
     */
    public String getLinkMsa() {
        return linkMsa;
    }

    /**
     * @param linkMsa the linkMsa to set
     */
    public void setLinkMsa(String linkMsa) {
        this.linkMsa = linkMsa;
    }

    /**
     * @return the ncbiBuild
     */
    public String getNcbiBuild() {
        return ncbiBuild;
    }

    /**
     * @param ncbiBuild the ncbiBuild to set
     */
    public void setNcbiBuild(String ncbiBuild) {
        this.ncbiBuild = ncbiBuild;
    }

    /**
     * @return the strand
     */
    public String getStrand() {
        return strand;
    }

    /**
     * @param strand the strand to set
     */
    public void setStrand(String strand) {
        this.strand = strand;
    }

    /**
     * @return the variantType
     */
    public String getVariantType() {
        return variantType;
    }

    /**
     * @param variantType the variantType to set
     */
    public void setVariantType(String variantType) {
        this.variantType = variantType;
    }

    /**
     * @return the dbSnpRs
     */
    public String getDbSnpRs() {
        return dbSnpRs;
    }

    /**
     * @param dbSnpRs the dbSnpRs to set
     */
    public void setDbSnpRs(String dbSnpRs) {
        this.dbSnpRs = dbSnpRs;
    }

    /**
     * @return the dbSnpValStatus
     */
    public String getDbSnpValStatus() {
        return dbSnpValStatus;
    }

    /**
     * @param dbSnpValStatus the dbSnpValStatus to set
     */
    public void setDbSnpValStatus(String dbSnpValStatus) {
        this.dbSnpValStatus = dbSnpValStatus;
    }

    /**
     * @return the oncotatorDbsnpRs
     */
    public String getOncotatorDbsnpRs() {
        return oncotatorDbsnpRs;
    }

    /**
     * @param oncotatorDbsnpRs the oncotatorDbsnpRs to set
     */
    public void setOncotatorDbsnpRs(String oncotatorDbsnpRs) {
        this.oncotatorDbsnpRs = oncotatorDbsnpRs;
    }

    /**
     * @return the oncotatorRefseqMrnaId
     */
    public String getOncotatorRefseqMrnaId() {
        return oncotatorRefseqMrnaId;
    }

    /**
     * @param oncotatorRefseqMrnaId the oncotatorRefseqMrnaId to set
     */
    public void setOncotatorRefseqMrnaId(String oncotatorRefseqMrnaId) {
        this.oncotatorRefseqMrnaId = oncotatorRefseqMrnaId;
    }

    /**
     * @return the oncotatorCodonChange
     */
    public String getOncotatorCodonChange() {
        return oncotatorCodonChange;
    }

    /**
     * @param oncotatorCodonChange the oncotatorCodonChange to set
     */
    public void setOncotatorCodonChange(String oncotatorCodonChange) {
        this.oncotatorCodonChange = oncotatorCodonChange;
    }

    /**
     * @return the oncotatorUniprotEntryName
     */
    public String getOncotatorUniprotEntryName() {
        return oncotatorUniprotEntryName;
    }

    /**
     * @param oncotatorUniprotEntryName the oncotatorUniprotEntryName to set
     */
    public void setOncotatorUniprotEntryName(String oncotatorUniprotEntryName) {
        this.oncotatorUniprotEntryName = oncotatorUniprotEntryName;
    }

    /**
     * @return the oncotatorUniprotAccession
     */
    public String getOncotatorUniprotAccession() {
        return oncotatorUniprotAccession;
    }

    /**
     * @param oncotatorUniprotAccession the oncotatorUniprotAccession to set
     */
    public void setOncotatorUniprotAccession(String oncotatorUniprotAccession) {
        this.oncotatorUniprotAccession = oncotatorUniprotAccession;
    }

    /**
     * @return the oncotatorProteinPosStart
     */
    public Integer getOncotatorProteinPosStart() {
        return oncotatorProteinPosStart;
    }

    /**
     * @param oncotatorProteinPosStart the oncotatorProteinPosStart to set
     */
    public void setOncotatorProteinPosStart(Integer oncotatorProteinPosStart) {
        this.oncotatorProteinPosStart = oncotatorProteinPosStart;
    }

    /**
     * @return the oncotatorProteinPosEnd
     */
    public Integer getOncotatorProteinPosEnd() {
        return oncotatorProteinPosEnd;
    }

    /**
     * @param oncotatorProteinPosEnd the oncotatorProteinPosEnd to set
     */
    public void setOncotatorProteinPosEnd(Integer oncotatorProteinPosEnd) {
        this.oncotatorProteinPosEnd = oncotatorProteinPosEnd;
    }

    /**
     * @return the canonicalTranscript
     */
    public Boolean getCanonicalTranscript() {
        return canonicalTranscript;
    }

    /**
     * @param canonicalTranscript the canonicalTranscript to set
     */
    public void setCanonicalTranscript(Boolean canonicalTranscript) {
        this.canonicalTranscript = canonicalTranscript;
    }

    /**
     * @return the keyword
     */
    public String getKeyword() {
        return keyword;
    }

    /**
     * @param keyword the keyword to set
     */
    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
    
}
