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
public abstract class GisticSummary implements Serializable {
    
    private Integer gisticRoiId;
    private Integer cancerStudyId;
    private Integer chromosome;
    private String cytoband;
    private Integer widePeakStart;
    private Integer widePeakEnd;
    private Double qValue;
    private boolean amp;    

    /**
     * @return the gisticRoiId
     */
    public Integer getGisticRoiId() {
        return gisticRoiId;
    }

    /**
     * @param gisticRoiId the gisticRoiId to set
     */
    public void setGisticRoiId(Integer gisticRoiId) {
        this.gisticRoiId = gisticRoiId;
    }

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
     * @return the chromosome
     */
    public Integer getChromosome() {
        return chromosome;
    }

    /**
     * @param chromosome the chromosome to set
     */
    public void setChromosome(Integer chromosome) {
        this.chromosome = chromosome;
    }

    /**
     * @return the cytoband
     */
    public String getCytoband() {
        return cytoband;
    }

    /**
     * @param cytoband the cytoband to set
     */
    public void setCytoband(String cytoband) {
        this.cytoband = cytoband;
    }

    /**
     * @return the widePeakStart
     */
    public Integer getWidePeakStart() {
        return widePeakStart;
    }

    /**
     * @param widePeakStart the widePeakStart to set
     */
    public void setWidePeakStart(Integer widePeakStart) {
        this.widePeakStart = widePeakStart;
    }

    /**
     * @return the widePeakEnd
     */
    public Integer getWidePeakEnd() {
        return widePeakEnd;
    }

    /**
     * @param widePeakEnd the widePeakEnd to set
     */
    public void setWidePeakEnd(Integer widePeakEnd) {
        this.widePeakEnd = widePeakEnd;
    }

    /**
     * @return the qValue
     */
    public Double getqValue() {
        return qValue;
    }

    /**
     * @param qValue the qValue to set
     */
    public void setqValue(Double qValue) {
        this.qValue = qValue;
    }

    /**
     * @return the amp
     */
    public boolean getAmp() {
        return amp;
    }

    /**
     * @param amp the amp to set
     */
    public void setAmp(boolean amp) {
        this.amp = amp;
    }
    
}
