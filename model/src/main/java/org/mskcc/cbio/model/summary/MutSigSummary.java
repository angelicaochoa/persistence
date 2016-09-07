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
public abstract class MutSigSummary implements Serializable {
    
    private Integer cancerStudyId;
    private Integer entrezGeneId;
    private Integer rank;
    private Integer numBasesCovered;
    private Integer numMutations;
    private Float pValue;
    private Float qValue;

    /**
     * @return the cancerStudyId
     */
    public Integer getCancerStudyId() {
        return cancerStudyId;
    }

    /**
     * @param cancerStudyId the cancerStudyId to set
     */
    public void setCancerStudyId(Integer cancerStudyId) {
        this.cancerStudyId = cancerStudyId;
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
     * @return the rank
     */
    public Integer getRank() {
        return rank;
    }

    /**
     * @param rank the rank to set
     */
    public void setRank(Integer rank) {
        this.rank = rank;
    }

    /**
     * @return the numBasesCovered
     */
    public Integer getNumBasesCovered() {
        return numBasesCovered;
    }

    /**
     * @param numBasesCovered the numBasesCovered to set
     */
    public void setNumBasesCovered(Integer numBasesCovered) {
        this.numBasesCovered = numBasesCovered;
    }

    /**
     * @return the numMutations
     */
    public Integer getNumMutations() {
        return numMutations;
    }

    /**
     * @param numMutations the numMutations to set
     */
    public void setNumMutations(Integer numMutations) {
        this.numMutations = numMutations;
    }

    /**
     * @return the pValue
     */
    public Float getPValue() {
        return pValue;
    }

    /**
     * @param pValue the pValue to set
     */
    public void setPValue(Float pValue) {
        this.pValue = pValue;
    }

    /**
     * @return the qValue
     */
    public Float getQValue() {
        return qValue;
    }

    /**
     * @param qValue the qValue to set
     */
    public void setQValue(Float qValue) {
        this.qValue = qValue;
    }
    
}
