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
public abstract class MutationSummary implements Serializable {

    private Integer mutationEventId;
    private Integer geneticProfileId;
    private Integer sampleId;
    private Integer entrezGeneId;
    private String center;
    private String sequencer;
    private String mutationStatus;
    private String validationStatus;
    private String tumorSeqAllele1;
    private String tumorSeqAllele2;
    private String matchedNormSampleBarcode;
    private String matchNormSeqAllele1;
    private String matchNormSeqAllele2;
    private String tumorValidationAllele1;
    private String tumorValidationAllele2;
    private String matchNormValidationAllele1;
    private String matchNormValidationAllele2;
    private String verificationStatus;
    private String sequencingPhase;
    private String sequenceSource;
    private String validationMethod;
    private String score;
    private String bamFile;
    private Integer tumorAltCount;
    private Integer tumorRefCount;
    private Integer normalAltCount;
    private Integer normalRefCount;
    private String aminoAcidChange;

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
     * @return the center
     */
    public String getCenter() {
        return center;
    }

    /**
     * @param center the center to set
     */
    public void setCenter(String center) {
        this.center = center;
    }

    /**
     * @return the sequencer
     */
    public String getSequencer() {
        return sequencer;
    }

    /**
     * @param sequencer the sequencer to set
     */
    public void setSequencer(String sequencer) {
        this.sequencer = sequencer;
    }

    /**
     * @return the mutationStatus
     */
    public String getMutationStatus() {
        return mutationStatus;
    }

    /**
     * @param mutationStatus the mutationStatus to set
     */
    public void setMutationStatus(String mutationStatus) {
        this.mutationStatus = mutationStatus;
    }

    /**
     * @return the validationStatus
     */
    public String getValidationStatus() {
        return validationStatus;
    }

    /**
     * @param validationStatus the validationStatus to set
     */
    public void setValidationStatus(String validationStatus) {
        this.validationStatus = validationStatus;
    }

    /**
     * @return the tumorSeqAllele1
     */
    public String getTumorSeqAllele1() {
        return tumorSeqAllele1;
    }

    /**
     * @param tumorSeqAllele1 the tumorSeqAllele1 to set
     */
    public void setTumorSeqAllele1(String tumorSeqAllele1) {
        this.tumorSeqAllele1 = tumorSeqAllele1;
    }

    /**
     * @return the tumorSeqAllele2
     */
    public String getTumorSeqAllele2() {
        return tumorSeqAllele2;
    }

    /**
     * @param tumorSeqAllele2 the tumorSeqAllele2 to set
     */
    public void setTumorSeqAllele2(String tumorSeqAllele2) {
        this.tumorSeqAllele2 = tumorSeqAllele2;
    }

    /**
     * @return the matchedNormSampleBarcode
     */
    public String getMatchedNormSampleBarcode() {
        return matchedNormSampleBarcode;
    }

    /**
     * @param matchedNormSampleBarcode the matchedNormSampleBarcode to set
     */
    public void setMatchedNormSampleBarcode(String matchedNormSampleBarcode) {
        this.matchedNormSampleBarcode = matchedNormSampleBarcode;
    }

    /**
     * @return the matchNormSeqAllele1
     */
    public String getMatchNormSeqAllele1() {
        return matchNormSeqAllele1;
    }

    /**
     * @param matchNormSeqAllele1 the matchNormSeqAllele1 to set
     */
    public void setMatchNormSeqAllele1(String matchNormSeqAllele1) {
        this.matchNormSeqAllele1 = matchNormSeqAllele1;
    }

    /**
     * @return the matchNormSeqAllele2
     */
    public String getMatchNormSeqAllele2() {
        return matchNormSeqAllele2;
    }

    /**
     * @param matchNormSeqAllele2 the matchNormSeqAllele2 to set
     */
    public void setMatchNormSeqAllele2(String matchNormSeqAllele2) {
        this.matchNormSeqAllele2 = matchNormSeqAllele2;
    }

    /**
     * @return the tumorValidationAllele1
     */
    public String getTumorValidationAllele1() {
        return tumorValidationAllele1;
    }

    /**
     * @param tumorValidationAllele1 the tumorValidationAllele1 to set
     */
    public void setTumorValidationAllele1(String tumorValidationAllele1) {
        this.tumorValidationAllele1 = tumorValidationAllele1;
    }

    /**
     * @return the tumorValidationAllele2
     */
    public String getTumorValidationAllele2() {
        return tumorValidationAllele2;
    }

    /**
     * @param tumorValidationAllele2 the tumorValidationAllele2 to set
     */
    public void setTumorValidationAllele2(String tumorValidationAllele2) {
        this.tumorValidationAllele2 = tumorValidationAllele2;
    }

    /**
     * @return the matchNormValidationAllele1
     */
    public String getMatchNormValidationAllele1() {
        return matchNormValidationAllele1;
    }

    /**
     * @param matchNormValidationAllele1 the matchNormValidationAllele1 to set
     */
    public void setMatchNormValidationAllele1(String matchNormValidationAllele1) {
        this.matchNormValidationAllele1 = matchNormValidationAllele1;
    }

    /**
     * @return the matchNormValidationAllele2
     */
    public String getMatchNormValidationAllele2() {
        return matchNormValidationAllele2;
    }

    /**
     * @param matchNormValidationAllele2 the matchNormValidationAllele2 to set
     */
    public void setMatchNormValidationAllele2(String matchNormValidationAllele2) {
        this.matchNormValidationAllele2 = matchNormValidationAllele2;
    }

    /**
     * @return the verificationStatus
     */
    public String getVerificationStatus() {
        return verificationStatus;
    }

    /**
     * @param verificationStatus the verificationStatus to set
     */
    public void setVerificationStatus(String verificationStatus) {
        this.verificationStatus = verificationStatus;
    }

    /**
     * @return the sequencingPhase
     */
    public String getSequencingPhase() {
        return sequencingPhase;
    }

    /**
     * @param sequencingPhase the sequencingPhase to set
     */
    public void setSequencingPhase(String sequencingPhase) {
        this.sequencingPhase = sequencingPhase;
    }

    /**
     * @return the sequenceSource
     */
    public String getSequenceSource() {
        return sequenceSource;
    }

    /**
     * @param sequenceSource the sequenceSource to set
     */
    public void setSequenceSource(String sequenceSource) {
        this.sequenceSource = sequenceSource;
    }

    /**
     * @return the validationMethod
     */
    public String getValidationMethod() {
        return validationMethod;
    }

    /**
     * @param validationMethod the validationMethod to set
     */
    public void setValidationMethod(String validationMethod) {
        this.validationMethod = validationMethod;
    }

    /**
     * @return the score
     */
    public String getScore() {
        return score;
    }

    /**
     * @param score the score to set
     */
    public void setScore(String score) {
        this.score = score;
    }

    /**
     * @return the bamFile
     */
    public String getBamFile() {
        return bamFile;
    }

    /**
     * @param bamFile the bamFile to set
     */
    public void setBamFile(String bamFile) {
        this.bamFile = bamFile;
    }

    /**
     * @return the tumorAltCount
     */
    public Integer getTumorAltCount() {
        return tumorAltCount;
    }

    /**
     * @param tumorAltCount the tumorAltCount to set
     */
    public void setTumorAltCount(Integer tumorAltCount) {
        this.tumorAltCount = tumorAltCount;
    }

    /**
     * @return the tumorRefCount
     */
    public Integer getTumorRefCount() {
        return tumorRefCount;
    }

    /**
     * @param tumorRefCount the tumorRefCount to set
     */
    public void setTumorRefCount(Integer tumorRefCount) {
        this.tumorRefCount = tumorRefCount;
    }

    /**
     * @return the normalAltCount
     */
    public Integer getNormalAltCount() {
        return normalAltCount;
    }

    /**
     * @param normalAltCount the normalAltCount to set
     */
    public void setNormalAltCount(Integer normalAltCount) {
        this.normalAltCount = normalAltCount;
    }

    /**
     * @return the normalRefCount
     */
    public Integer getNormalRefCount() {
        return normalRefCount;
    }

    /**
     * @param normalRefCount the normalRefCount to set
     */
    public void setNormalRefCount(Integer normalRefCount) {
        this.normalRefCount = normalRefCount;
    }

    /**
     * @return the aminoAcidChange
     */
    public String getAminoAcidChange() {
        return aminoAcidChange;
    }

    /**
     * @param aminoAcidChange the aminoAcidChange to set
     */
    public void setAminoAcidChange(String aminoAcidChange) {
        this.aminoAcidChange = aminoAcidChange;
    }

}
